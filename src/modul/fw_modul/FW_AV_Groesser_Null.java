/**
 * 
 */
package modul.fw_modul;

import jtoolbox.Dezimal;
import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_AV_Groesser_Null extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 10;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Vergleich - Zahlen";
	
	// Name in der Auswahl
	public static final String bezeichnung = "x > 0";;


	/**
	 * Konstruktor
	 * 
	 */
	public FW_AV_Groesser_Null() {
		anzeige = "$0$ > 0" ;
		tabellenkalkulatorString = "($0$) > 0";

		anzEingaenge = 1;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Dezimal;

		ausgangsbezeichnung = "Groesser 0";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		Dezimal tmp = new Dezimal(datenAmEingang[0].getDezimal());
		Boolean b_erg = tmp.istGroesserNull();

		erg.setzeWert(b_erg);
		erg.setGueltig(true);

		return erg;
	}

}
