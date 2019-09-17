/**
 * 
 */
package modul.fw_modul;

import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_B_XOR extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 4;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Logisch";
	
	// Name in der Auswahl
	public static final String bezeichnung = "Entweder a oder b";;


	/**
	 * Konstruktor
	 * 
	 */
	public FW_B_XOR() {
		anzeige = "Entweder $0$ oder $1$" ;
		tabellenkalkulatorString = "XODER(($0$) ; ($1$))";

		anzEingaenge = 2;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "a";
		eingangsbezeichungen[1] = "b";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Boolean;
		eingangstypen[1] = DFD_Datentyp.Boolean;

		ausgangsbezeichnung = "XOR";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		boolean a = datenAmEingang[0].getBoolean();
		boolean b = datenAmEingang[1].getBoolean();
		
		erg.setzeWert(a ^ b);
		erg.setGueltig(true);

		return erg;
	}

}
