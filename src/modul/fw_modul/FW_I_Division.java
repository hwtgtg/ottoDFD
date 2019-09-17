/**
 * 
 */
package modul.fw_modul;

import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_I_Division extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 01;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "ganzzahlig";
	
	// Name in der Auswahl
	public static final String bezeichnung = " n / m (ganzzahlig)";;


	/**
	 * Konstruktor
	 * 
	 */
	public FW_I_Division() {
		anzeige = "$0$ / $1$" ;
		tabellenkalkulatorString = "Ganzzahl(($0$) / ($1$))";

		anzEingaenge = 2;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "n";
		eingangsbezeichungen[1] = "m";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Ganzzahlig;
		eingangstypen[1] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "Div ganzzahlig";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		long dividend = datenAmEingang[0].getGanzzahlig();
		long divisor = datenAmEingang[1].getGanzzahlig();
		if (divisor == 0) {
			erg.setzeError();
			erg.setGueltig(false);
		} else {
			long d_erg = dividend / divisor;
			erg.setzeWert(d_erg);
			erg.setGueltig(true);
		}

		return erg;
	}

}
