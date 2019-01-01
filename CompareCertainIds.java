package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class CompareCertainIds {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inputConfigBC = args[0];
		String inputConfigPF = args[1];
		String inputPlanBC = args[2];
		String inputPlanPF = args[3];
		String inputIds = args[4];
		String outputFile = args[5];
		Config configBC = ConfigUtils.loadConfig(inputConfigBC);
		Config configPF = ConfigUtils.loadConfig(inputConfigPF);
		configBC.plans().setInputFile(inputPlanBC);
		configPF.plans().setInputFile(inputPlanPF);
		FileWriter fw = new FileWriter(outputFile);
		BufferedWriter bw = new BufferedWriter(fw);
		FileReader fr = new FileReader(inputIds);
		double scoreGesBC = 0;
		double distanceGesBC = 0;
		double durationGesBC = 0;
		double scoreGesPF = 0;
		double distanceGesPF = 0;
		double durationGesPF = 0;
		double scorePersonBC = 0;
		double scorePersonPF = 0;
		int counter = 0;
		int counterLong = 0;
		int counterHuge = 0;
		
		Scenario scenarioBC = ScenarioUtils.loadScenario(configBC);
		Scenario scenarioPF = ScenarioUtils.loadScenario(configPF);
		Person PersonCheck;
		ArrayList<String> idsToCheck = new ArrayList<>();
		idsToCheck = loadIds(inputIds);
//		ArrayList<String> closeStats = new ArrayList<>();
//		ArrayList<String> mediumStats = new ArrayList<>();
//		ArrayList<String> badStats = new ArrayList<>();

		// Programm bekommt alle Leg Distance und Leg Duration für alle Agenten des Base Case
		for (Person personBC : scenarioBC.getPopulation().getPersons().values()) {
			if(idsToCheck.contains(personBC.getId().toString())){
				counter +=1;
			     Plan plan = personBC.getSelectedPlan();
			     Double scoreOfSelected = plan.getScore();
			     scoreGesBC += scoreOfSelected;
			     
			     for (PlanElement pE : plan.getPlanElements()) {
			    	 if(pE instanceof Leg){
			    		 Leg leg = (Leg) pE;
			    		 double distanceLeg = leg.getRoute().getDistance();
			    		 double durationLeg = leg.getRoute().getTravelTime();
			    		 distanceGesBC += distanceLeg;
			    		 durationGesBC += durationLeg;
			    	 }
			     }
			     System.out.println(personBC.getSelectedPlan().toString());
			     System.out.println(personBC.getSelectedPlan().getPlanElements().toString());

			}
		}

		System.out.println("Base Case " + "Scores avg" + (scoreGesBC / counter) + " Distance avg" + (distanceGesBC / counter)
				+ " Duration avg" + (durationGesBC / counter));
		
		// Programm bekommt alle Leg Distance und Leg Duration für alle Agenten der Baustelle
		for (Person personPF : scenarioPF.getPopulation().getPersons().values()) {
			if(idsToCheck.contains(personPF.getId().toString())){
			     Plan plan = personPF.getSelectedPlan();
			     Double scoreOfSelected = plan.getScore();
			     scoreGesBC += scoreOfSelected;
			    
			     for (PlanElement pE : plan.getPlanElements()) {
			    	 if(pE instanceof Leg){
			    		 Leg leg = (Leg) pE;
			    		 double distanceLeg = leg.getRoute().getDistance();
			    		 double durationLeg = leg.getRoute().getTravelTime();
			    		 distanceGesPF += distanceLeg;
			    		 durationGesPF += durationLeg;
			    		 if(distanceLeg > 35000){
			    			 counterLong +=1;
			    		 }
			    		 if(distanceLeg > 60000 && distanceLeg < 35000){
			    			 counterHuge +=1;
			    		 }
			    	 }
			     }
			     
			     System.out.println(personPF.getSelectedPlan().toString());
			     System.out.println(personPF.getSelectedPlan().getPlanElements().toString());
			     System.out.println("Long " + counterLong + " Huge " + counterHuge);

			}
		}
		System.out.println(counter);
		System.out.println("Planfall " + "Scores avg" + (scoreGesPF / counter) + " Distance avg" + (distanceGesPF / counter)
				+ " Duration avg" + (durationGesPF / counter));
	}
		
		

	
	public static ArrayList<String> loadIds(String Ids) throws FileNotFoundException{
		FileReader fr = new FileReader(Ids);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> listIds = new ArrayList<>();
		
		try{
			for (String z = br.readLine(); z != null; z = br.readLine()){
				listIds.add(z);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(listIds);
		System.out.println(listIds.size());
		return listIds;
	}

}
