package analysis;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;

import com.google.common.collect.Sets;

public class PtIdFinder{
	
	public static void main (String[] args) throws FileNotFoundException, IOException{
		String plansFile = args[0];
		String inputConfig = args[1];
		String outputFile = args[2];
		Config config = ConfigUtils.loadConfig(inputConfig);
		config.plans().setInputFile(plansFile);
		int linkRichtungFranz = 20553;
		int linkRichtungFriedrich = 20518;
		ArrayList<String> ptIdU6 = new ArrayList<>();
		Array[] ptIdU6Final;
		
		                
		Scenario scenario = ScenarioUtils.loadScenario(config);
		                
		// Programm geht durch alle PErsonen des Szenarios durch und schaut, ob die Person auf dem entfernten Link gefahren ist
		// Außerdem gilt, dass die Nummerierung der Links auf U-Bahn-Linien ansteigend vorgenommen wurde
		for (Person person : scenario.getPopulation().getPersons().values()) {
		     Plan plan = person.getSelectedPlan();
		                        
		     for (PlanElement pE : plan.getPlanElements()) {
		        if (pE instanceof Leg) {
		            Leg leg = (Leg) pE;
		            if (leg.getMode().equals("pt")) {
		            	if (leg.getRoute().toString().contains("17521_400")){
		            		if((Integer.parseInt(leg.getRoute().getStartLinkId().toString().substring(3)) <= linkRichtungFranz) && (Integer.parseInt(leg.getRoute().getEndLinkId().toString().substring(3)) >= linkRichtungFranz)){
		            			System.out.println(leg.getRoute().getRouteDescription());
		            			System.out.println(person.getId());
		            			ptIdU6.add(person.getId().toString());
		            		}
		            		if((Integer.parseInt(leg.getRoute().getStartLinkId().toString().substring(3)) >= linkRichtungFriedrich) && (Integer.parseInt(leg.getRoute().getEndLinkId().toString().substring(3)) <= linkRichtungFriedrich)){
		            			System.out.println(leg.getRoute().getRouteDescription());
		            			System.out.println(person.getId());
		            			ptIdU6.add(person.getId().toString());
		            		}
		            	}
		            }
		        }
		     }
		 }
//		Set <String> ptIdU6Unique =  Sets.newHashSet(ptIdU6.toArray());
		Set <String> ptIdU6Unique = new HashSet<String>(ptIdU6);

		System.out.println(ptIdU6Unique.toString());
//		ptIdU6Final = (Array[]) ptIdU6Unique.toArray();
		 BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		 java.util.Iterator<String> it = ptIdU6Unique.iterator();
		 while(it.hasNext()){
			 bw.write(it.next());
			 bw.newLine();
		 }
//		 for (Integer i : ptIdU6Unique){
//			 
//		 }
//		 ptIdU6Unique.size();
	}
}
