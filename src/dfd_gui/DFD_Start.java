package dfd_gui;

import global.StartUmgebung;
import jtoolbox.Zeichnung;

public class DFD_Start {

	public static String dateiname = "";
	public static String getDateiname() {
		return dateiname;
	}

	public static String pfadDateiname = "";
	
	public static String getDateinameMitPfad() {
		return pfadDateiname;
	}

	public static void setDateinameMitPfad(String dateiname) {
		DFD_Start.pfadDateiname = dateiname;
		if ((dateiname!=null)&&(!dateiname.equals(""))){
			int pos = dateiname.lastIndexOf(StartUmgebung.fileseparator);
			if (pos>0){
				dateiname=dateiname.substring(pos+1);
			}
		}
		DFD_Start.dateiname=dateiname;
		
		
	}

//	public static String pfadSpeichern = "";

	private static final String programmtitel = "Datenflussdiagramm";

	public DFD_Start() {

	}

	public static void guiErzeugen() {
		DFD_G_Hauptfenster.getHauptfenster();
	}

	public static int toolbarHoehe = 30;
	public static int toolbarBreite = (int)( (toolbarHoehe + 3) * 11.25) ;
	public static int deviderSize = 7;

	public static void setzeTitel(String text) {
		if ((text == null) || (text == "")) {
			Zeichnung.setzeTitel(programmtitel);
		} else {
			Zeichnung.setzeTitel(programmtitel + " " + text);
		}
	}

}
