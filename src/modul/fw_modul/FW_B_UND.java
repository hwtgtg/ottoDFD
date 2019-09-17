/**
 * 
 */
package modul.fw_modul;

import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_B_UND extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 1;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Logisch";
	
	// Name in der Auswahl
	public static final String bezeichnung = "a UND b";;


	/**
	 * Konstruktor
	 * 
	 */
	public FW_B_UND() {
		anzeige = "$0$ UND $1$" ;
		tabellenkalkulatorString = "UND(($0$) ; ($1$))";

		anzEingaenge = 2;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "a";
		eingangsbezeichungen[1] = "b";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Boolean;
		eingangstypen[1] = DFD_Datentyp.Boolean;

		ausgangsbezeichnung = "UND";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		boolean a = datenAmEingang[0].getBoolean();
		boolean b = datenAmEingang[1].getBoolean();
		
		erg.setzeWert(a && b);
		erg.setGueltig(true);

		return erg;
	}

}
