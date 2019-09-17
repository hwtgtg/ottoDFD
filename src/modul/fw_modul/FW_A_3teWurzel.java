/**
 * 
 */
package modul.fw_modul;

import jtoolbox.Dezimal;
import modul.DFD__Daten;
import modul.DFD__IF_Modul;

/**
 * @author Witt
 * 
 */
public class FW_A_3teWurzel extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 8;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Zahlen";
	
	// Name in der Auswahl
	public static final String bezeichnung = "3-te Wurzel(x)";


	/**
	 * Konstruktor
	 * 
	 */
	public FW_A_3teWurzel() {
		anzeige = "3-te Wurzel($0$)" ;
		tabellenkalkulatorString = "($0$) ^ (1/3)";

		anzEingaenge = 1;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Dezimal;

		ausgangsbezeichnung = "Runden";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		Dezimal tmp = new Dezimal(datenAmEingang[0].getDezimal());
		double wert = tmp.toDouble();
		double wrzl = Math.cbrt(wert);
		
		if (wrzl == Double.NaN) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {
			erg.setzeWert(wrzl);
			erg.setGueltig(DFD__IF_Modul.gueltig);
		}
		return erg;
	}

}
