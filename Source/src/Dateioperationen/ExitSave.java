package Dateioperationen;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import global.CHANGE;
import jtoolbox.IExitModul;
import jtoolbox.Zeichnung;

public class ExitSave implements IExitModul {

	// Das soll bei exit passierten
	public boolean exitoptions() {
		// INI-Datei schreiben
		DFD_INI.inidateiSchreiben();
		if (CHANGE.isChanged()) {
			// BEi Rückgabe false Abbrechen
			FunktionsWriter.schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN_JaNein() ;
		}
		return true;
	}
	

	// ruft Beenden mit Exitoption auf
	public static void exit() {
		WindowEvent wev = new WindowEvent(Zeichnung.gibJFrame(), WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);		
	}
}
