
public class DepartureList {
	private static String sekZuUhrzeit(int Time){
		// Dieses Programm erzeugt die Taktzeit für die Fahrzeuge des Transit Schedules
		String Zeit;
		int Stunden = 0;
		int Minuten = 0;
		int Sekunden =0;
		String StundenZweistellig = null;
		String MinutenZweistellig = null;
		String SekundenZweistellig = null;
		Stunden = Time / 3600;
		Minuten = (Time - (3600 * Stunden)) / 60;
		Sekunden = Time - ((Minuten * 60) + (Stunden * 3600));
		if(Stunden < 10){
			StundenZweistellig = ("0" + Stunden);
		} else {
			StundenZweistellig = Integer.toString(Stunden);
		}
		if(Minuten < 10){
			MinutenZweistellig = ("0" + Minuten);
		} else {
			MinutenZweistellig = Integer.toString(Minuten);
		}
		if(Sekunden < 10){
			SekundenZweistellig = ("0" + Sekunden);
		} else {
			SekundenZweistellig = Integer.toString(Sekunden);
		}
		Zeit = (StundenZweistellig + ":" + MinutenZweistellig + ":" + SekundenZweistellig);		
		return Zeit;
	}
	public static void main (String[] args){
		// u6 13800
		// u5 14400
		int startZeitInSek = 16200;
		int offSetZehnInSek = 600;
		int offSetVierInSek = 240;
		int offSetFuenfInSek = 300;
		int i;
		int departureTime = startZeitInSek;
		int fahrzeuge = 70500;
		int idStart = 90000500;
		
		// 12 bis 6; 12 bis 7; 30 bis 9, 8 bis 9:32, 66 bis 15:02, 52 bis 18:30,
		// 30 bis 21:00, 21 bis 0:30 Total sinds 232
//		for(i = 0; i < 233; i++){
		for(i = 0; i < 232; i++){
			// u6 i<13
			// u5 i<7
			while(i<7){
				departureTime += offSetZehnInSek;
				String departureZeit = sekZuUhrzeit(departureTime);
				int aktuelleId = idStart +i;
				int aktuellesFahrzeug = fahrzeuge + i;
				System.out.println("				<departure id=\"" + aktuelleId + "\" departureTime=\"" + departureZeit + "\" vehicleRefId=\"tr_" + aktuellesFahrzeug + "\"/>");
				break;
			}
			// u6 i>12,i<25
			// u5 i>6, i<185
			while(i > 6 && i < 185){
				departureTime +=offSetFuenfInSek;
				String departureZeit = sekZuUhrzeit(departureTime);
				int aktuelleId = idStart +i;
				int aktuellesFahrzeug = fahrzeuge + i;
				System.out.println("				<departure id=\"" + aktuelleId + "\" departureTime=\"" + departureZeit + "\" vehicleRefId=\"tr_" + aktuellesFahrzeug + "\"/>");
				break;
			}
//			while(i > 24 && i < 63){
//				departureTime += offSetVierInSek;
//				String departureZeit = sekZuUhrzeit(departureTime);
//				int aktuelleId = idStart +i;
//				int aktuellesFahrzeug = fahrzeuge + i;
//				System.out.println("				<departure id=\"" + aktuelleId + "\" departureTime=\"" + departureZeit + "\" vehicleRefId=\"tr_" + aktuellesFahrzeug + "\"/>");
//				break;
//			}
//			while(i > 62 && i < 129){
//				departureTime +=offSetFuenfInSek;
//				String departureZeit = sekZuUhrzeit(departureTime);
//				int aktuelleId = idStart +i;
//				int aktuellesFahrzeug = fahrzeuge + i;
//				System.out.println("				<departure id=\"" + aktuelleId + "\" departureTime=\"" + departureZeit + "\" vehicleRefId=\"tr_" + aktuellesFahrzeug + "\"/>");
//				break;
//			}
//			while(i > 128 && i < 181){
//				departureTime += offSetVierInSek;
//				String departureZeit = sekZuUhrzeit(departureTime);
//				int aktuelleId = idStart +i;
//				int aktuellesFahrzeug = fahrzeuge + i;
//				System.out.println("				<departure id=\"" + aktuelleId + "\" departureTime=\"" + departureZeit + "\" vehicleRefId=\"tr_" + aktuellesFahrzeug + "\"/>");
//				break;
//			}
//			// um nach Tegel zu kommen fährt sie im fünfminuten Takt bis 211.
//			// um nach Mariendorf zu kommen fährt sie nur bis 206 und anschließend alle 10
//			while(i > 180 && i < 206){
//				departureTime += offSetFuenfInSek;
//				String departureZeit = sekZuUhrzeit(departureTime);
//				int aktuelleId = idStart +i;
//				int aktuellesFahrzeug = fahrzeuge + i;
//				System.out.println("				<departure id=\"" + aktuelleId + "\" departureTime=\"" + departureZeit + "\" vehicleRefId=\"tr_" + aktuellesFahrzeug + "\"/>");
//				break;
//			}
			// bis 232 wenn man länger im 5min Takt gefahren ist, ansonsten 229
			// u5 i>184,i<209
			while(i > 184 && i < 209){
				departureTime += offSetZehnInSek;
				String departureZeit = sekZuUhrzeit(departureTime);
				int aktuelleId = idStart +i;
				int aktuellesFahrzeug = fahrzeuge + i;
				System.out.println("				<departure id=\"" + aktuelleId + "\" departureTime=\"" + departureZeit + "\" vehicleRefId=\"tr_" + aktuellesFahrzeug + "\"/>");
				break;
			}
		}
	}
}
