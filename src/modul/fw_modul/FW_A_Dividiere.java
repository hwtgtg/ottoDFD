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
public class FW_A_Dividiere extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 4;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Zahlen";
	
	// Name in der Auswahl
	public static final String bezeichnung = "x : y";;


	/**
	 * Konstruktor
	 * 
	 */
	public FW_A_Dividiere() {
		anzeige = "$0$ : $1$" ;
		tabellenkalkulatorString = "($0$) // ($1$)";

		anzEingaenge = 2;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";
		eingangsbezeichungen[1] = "y";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Dezimal;
		eingangstypen[1] = DFD_Datentyp.Dezimal;

		ausgangsbezeichnung = "Quotient";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		
		Dezimal tmp = new Dezimal(datenAmEingang[0].getDezimal());
		tmp = tmp.dividiereGerundet(datenAmEingang[1].getDezimal());
		
		if( tmp.isValid()){
			erg.setzeWert(tmp);
			erg.setGueltig(true);
		} else {
			erg.setzeWert(tmp);
			erg.setzeError();
			erg.setGueltig(false);
		}
		
		return erg;
	}

}
