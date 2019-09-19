package modul.gui_modul;

import modul.DFD__IG_Modul;

/*
 * Kommunikation zwischern Sensoren und anderen Klassen
 */

public interface DFD_I_SENSORAction_Link {

	public static final DFD_I_SENSORAction_Link NULLLINKSENSOR = null ;

	/*
	 * Meldung über die (veraenderte) Position des Sensormittelpunkts
	 * 
	 * Die ID identifiziert den Sensor
	 */
	public void sensorAktionDRAGG(int x, int y, int ID, DFD_Sensor sensor);

	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor);

	public void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor);

	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks);

	public DFD__IG_Modul getGmodul();
}
