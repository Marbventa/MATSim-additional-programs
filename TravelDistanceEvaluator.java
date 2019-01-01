package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.matsim.api.core.v01.Id;
import org.matsim.core.utils.io.IOUtils;

public class TravelDistanceEvaluator {
	
	public static void main (String[] args) throws IOException{
	
		FileReader fr;
		int travelTimeTotal = 0;
		double travelDistanceTotal = 0;
		int Agentenanzahl = 1;
		int numberOfLegs = 0;
		String scoreOfAgent = null;
		double scoreOfAgents = 0;
		String outputFile = args[0]; //"/Users/Gero/Desktop/Filewriter.txt"
		String inputIds = args[1];	///Users/Gero/Desktop/AgentsOnLinks/StraßenIds.txt
		String inputPlans = args[2];	///Users/Gero/Desktop/OutputLastIteration/BaseCase/b10_1.output_plans.xml
		FileWriter fw = new FileWriter (outputFile );
		BufferedWriter bw = new BufferedWriter(fw);

		try {
			fr = new FileReader(inputIds);
			BufferedReader ao = new BufferedReader(fr);
			// eine Datei mit allen IDs wird eingelesen die wir betrachten wollen.
			for (String z = ao.readLine(); z != null; z = ao.readLine()){
					try {
						fr = new FileReader(inputPlans);  
//						fr = new FileReader("/Users/Gero/Desktop/berlin-v5.2-1pct.output_plans.xml");  
						BufferedReader br = new BufferedReader(fr);
						//Nun gleicht java die ganze XML Datei ab mit der einen Zeile z
						for (String x = br.readLine(); x != null; x = br.readLine()){
							boolean breaker = true;
							if(x.contains(" id=\"" + z + "\"")){
								String nextline = br.readLine();
								//Abbruchkriterium um fest zu stellen, wann der selected Plan geschrieben ist
								//für den Agenten
								boolean abc = true;
								while (nextline != null && abc){
									if (nextline.contains("selected=\"yes\"")){
										scoreOfAgent = nextline.substring((nextline.indexOf("\"")+1), nextline.indexOf("\" select"));
										System.out.println(z + "	" + scoreOfAgent);
										scoreOfAgents += Double.parseDouble(scoreOfAgent);
										abc = false;
									}
									try {
										//sobald wie wir den selected Plan herauslesen wird eine weiitere If-Schleife
										//gestartet. Bei der While ist das Abbruchkriterium erneut das ende des Plans
										if (nextline != null && nextline.contains("selected=\"yes\"")){
											boolean adhd = true;
											while (adhd){
												if (!nextline.contains("/plan")){
													nextline = br.readLine();
												}
												if (nextline.contains("/plan")){
													System.out.println(nextline + "	" + travelTimeTotal + "	" + travelDistanceTotal);
													Agentenanzahl +=1;
													adhd = false;
													breaker = false;
													break;
												}
												//Falls wir eine "travel_duration" finden wird diese nun ausgegeben
												if (nextline.contains("trav_")) {
													numberOfLegs +=1;
													String SubstringZeitEins = nextline.substring(nextline.lastIndexOf("trav_time=\""), nextline.indexOf("dis"));
													String SubstringZeit = SubstringZeitEins.substring(11, 19);
													int Sekunden = 0;
													Sekunden = (Integer.parseInt(SubstringZeit.substring(0,2))*3600) + (Integer.parseInt(SubstringZeit.substring(3, 5))*60) + Integer.parseInt(SubstringZeit.substring(6,8));
													String SubstringDistance = nextline.substring(nextline.lastIndexOf("distance"), nextline.indexOf(">"));
													String SubstringDistanceVehicle = null;
													String SubstringDistanceCropped = null;
													if(SubstringDistance.contains("vehicleRefId")){
														SubstringDistanceVehicle = SubstringDistance.substring(10, SubstringDistance.indexOf("vehicle"));
														SubstringDistanceCropped = SubstringDistanceVehicle.substring(0, SubstringDistanceVehicle.indexOf("\""));
													} else {
														SubstringDistanceCropped = SubstringDistance.substring(10,SubstringDistance.lastIndexOf("\""));
													}
													travelTimeTotal += Sekunden;
													travelDistanceTotal += Double.parseDouble(SubstringDistanceCropped);
													System.out.println(Agentenanzahl + "	" + SubstringZeit + "	" + Sekunden + "	" + SubstringDistanceCropped);
													bw.append(Agentenanzahl + "	" + SubstringZeit + "	" + Sekunden + "	" + SubstringDistanceCropped + "\n");
													
													br.readLine();
												}
												
											}
										}
									} catch (NullPointerException a) {
										// TODO Auto-generated catch block
										a.printStackTrace();
									}
									if (abc == false){ break;}
									nextline = br.readLine();

								}
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			bw.append("Insgesamt gibt es " + Agentenanzahl + "Agenten" + "\n");
			bw.append("Die durchschnittliche Score betrug" + (scoreOfAgents / Agentenanzahl));
			bw.append("Die Agenten sind insgesamt " + travelTimeTotal +"Sekunden gefahren und pro Agenten " + (travelTimeTotal / Agentenanzahl) + "mit durchschnittlich " +  (travelTimeTotal / numberOfLegs) + "Sekunden pro Leg" + "\n");
			bw.append("Die Agenten sind insgesamt " + travelDistanceTotal +"Einheiten gefahren und pro Agent " + (travelDistanceTotal / Agentenanzahl) + "mit durchschnittlich " + (travelDistanceTotal/ numberOfLegs) + "Einheiten pro Leg " + "/n");
			bw.close();

		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
