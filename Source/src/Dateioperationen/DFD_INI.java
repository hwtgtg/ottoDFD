package Dateioperationen;

import javax.swing.UIManager;

import dfd_gui.DFD_G_Hauptfenster;
import global.StartUmgebung;
import jtoolbox.INIDatei;

public class DFD_INI {

	// Ini-Werte
	
	private static void startINIwerteAnpassen() {
		
		StartUmgebung.setzeBildFaktor(DFD_INI.dfd_INIwerte.leseFloat("GUI", "bildfaktor", StartUmgebung.bildFaktor ));
		DFD_INI.dfd_INIwerte.setzeFloat("GUI", "bildfaktor", StartUmgebung.bildFaktor );
		
		UIManager.put("OptionPane.messageFont", StartUmgebung.getFont());
		UIManager.put("MenuItem.acceleratorFont", StartUmgebung.getFont());
		
	}
	
	public static INIDatei dfd_INIwerte;
	public static String inihomeAktuell = "";

	public static boolean bGlobalIni = true;
	public static boolean bHomeIni = false;
	
	public static int splitPosition = 150 ;
	
	public static void inidateiLesen() {
		dfd_INIwerte = new INIDatei();
		dfd_INIwerte.lesenINIDatei(StartUmgebung.inidatei_basis);
		
		// Globale Eintraege werden nicht beachtet, wenn ausdruecklich ausgeschlossen 
		if (dfd_INIwerte.leseBoolean("INIGlobal", "cfgGlobal", true)) {
			// Global-INI wird gelesen, wenn kein Eintrag über false vorhanden
			// Ist die Datei nicht vorhanden, wird mit defaultwerten gefuellt
			dfd_INIwerte.setzeBoolean("INIGlobal", "cfgGlobal", true);
			dfd_INIwerte.setzeString("INIGlobal", "progpfadCfg",
					StartUmgebung.inidatei_basis);
		} else {
			bGlobalIni = false ;
		}
		
		if (dfd_INIwerte.geoeffnet) {
			// Basisinidatei exisiert. kein Eintrag -> Keine private Inidatei 
			bHomeIni = dfd_INIwerte.leseBoolean("INIGlobal", "cfgHome", false);
		} else {
			// Ohne 
			bHomeIni = false;
		}
		
		if (bHomeIni) {
			// HOME-INI wird gelesen, wenn kein Eintrag über false vorhanden
			// D.H. auch, wenn kein Global-INI vorhanden
			inihomeAktuell = dfd_INIwerte.leseString("INIGlobal",
					"homepfadCfg");
			if (inihomeAktuell == "") {
				inihomeAktuell = StartUmgebung.inidatei_home;
			}
			dfd_INIwerte.lesenINIDatei(inihomeAktuell);
			dfd_INIwerte.setzeBoolean("INIGlobal", "cfgHome", true);
			dfd_INIwerte.setzeBoolean("INIHome", "cfgHome", true);
			dfd_INIwerte.setzeString("INIGlobal", "homepfadCfg", inihomeAktuell);
		}

		startINIwerteAnpassen();
		
	}


	public static void inidateiSchreiben() {
		// Setze Wert für globale Inidatei-Vorgaben
		dfd_INIwerte.setzeBoolean("INIGlobal", "cfgGlobal", bGlobalIni);
		dfd_INIwerte.setzeBoolean("INIGlobal", "cfgHome", bHomeIni);
		
		dfd_INIwerte.setzeString("INIGlobal", "progpfadCfg", StartUmgebung.inidatei_basis);
		dfd_INIwerte.setzeString("INIGlobal", "progStart", StartUmgebung.startPfad);

		
		
		// Aktualisieren
		DFD_INI.dfd_INIwerte.setzeInteger("GUI", "splitPosition", DFD_G_Hauptfenster.getHauptfenster().leseSplitPosition());

		// HomeIni schreiben
		if (bHomeIni && dfd_INIwerte != null) {
			boolean erg = dfd_INIwerte.schreibeINIDatei( INIDatei.KeinFEHLER );
			if(!erg ){
				// Wenn Homeini nicht geht, global schreiben
				dfd_INIwerte.schreibeINIDatei(StartUmgebung.inidatei_basis, true, INIDatei.KeinFEHLER);
			}
		}
		
		// GlobalIni schreiben
		if ( bGlobalIni && (dfd_INIwerte != null)) {
//			LOG.outln(StartUmgebung.inidatei_basis);
			dfd_INIwerte.schreibeINIDatei(StartUmgebung.inidatei_basis, true, INIDatei.KeinFEHLER);
		}

	}

	
	
}
