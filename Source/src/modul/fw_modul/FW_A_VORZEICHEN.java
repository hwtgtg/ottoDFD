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
public class FW_A_VORZEICHEN extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 25 ;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Zahlen";
	
	// Name in der Auswahl
	public static final String bezeichnung = "Vorzeichen(x)";;


	/**
	 * Konstruktor
	 * 
	 */
	public FW_A_VORZEICHEN() {
		anzeige = "Vorzeichen($0$)" ;
		tabellenkalkulatorString = "VORZEICHEN($0$)";

		anzEingaenge = 1;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Dezimal;

		ausgangsbezeichnung = "Vorzeichen";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		Dezimal tmp = new Dezimal(datenAmEingang[0].getDezimal());
		double dTemp = tmp.toDouble();
		int ierg =(dTemp>=0)?+1:-1;
		if (dTemp==0)ierg=0;
		erg.setzeWert(ierg);
		erg.setGueltig(true);

		return erg;
	}

}
