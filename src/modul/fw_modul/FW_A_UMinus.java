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
public class FW_A_UMinus extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 5;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Zahlen";
	
	// Name in der Auswahl
	public static final String bezeichnung = "-x";;


	/**
	 * Konstruktor
	 * 
	 */
	public FW_A_UMinus() {
		anzeige = "-$0$" ;
		tabellenkalkulatorString = "(-$0$)";

		anzEingaenge = 1;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Dezimal;
		
		ausgangsbezeichnung = "Negation";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		Dezimal tmp = new Dezimal(0.0);
		tmp = tmp.subtrahiere(datenAmEingang[0].getDezimal());

		erg.setzeWert(tmp);
		erg.setGueltig(true);

		return erg;
	}

}
