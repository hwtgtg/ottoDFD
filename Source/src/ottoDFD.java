import global.StartUmgebung;
import jtoolbox.*;

/**
 * @author Witt
 * 
 */
public class ottoDFD {

	public ottoDFD(String[] args) {

		// Umgebungsvariablen der Anwendung lesen
		global.StartUmgebung.startwerte(this , "ottoDFD");
		// Parameter lesen
		StartUmgebung.argsAuswerten(args);
		// IniWerte lesen
		Dateioperationen.DFD_INI.inidateiLesen();
		
		// Exitoptionen setzen
		Zeichnung.setExitmodul(new Dateioperationen.ExitSave());
		dfd_gui.DFD_Start.setzeTitel("");

		
		// Aufbau der Funktions-Datenstruktur
		// Vorhandene Funktionsmodule aus dem Verzeichnis modul\fm_modul
		// einlesen
		funktionenBaum.FunktionenAuswahl_Funktionenlesen.funktionenLesen();

		// Liste für Funktions-Module anlegen
//		modul_alt.DFD_Modulliste_Verwalter.einrichten();

		// Graphische Darstellung
		dfd_gui.DFD_Start.guiErzeugen();
		
	}

	public static void main(String[] args) {
		new ottoDFD( args );
	}

}
