
public class AddVehicles {
	
	// Programm erzeugt eine Liste von neuen Fahrzeugen die auf der Konsole ausgegeben werden, um 
	// anschließend an die bereits vorhandene Datei koppiert zu werden.
	public static void main (String[] args){
		for (int id = 70500; id<71000; id++){
			System.out.println("	<vehicle id=\"tr_" + id + "\" type=\"defaultTransitVehicleType\"/>");
		}
	}
}
