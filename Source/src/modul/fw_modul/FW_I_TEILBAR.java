/**
 * 
 */
package modul.fw_modul;

import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_I_TEILBAR extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 03;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "ganzzahlig";
	
	// Name in der Auswahl
	public static final String bezeichnung = "n teilbar durch m ";


	/**
	 * Konstruktor
	 * 
	 */
	public FW_I_TEILBAR() {
		anzeige = "$0$ teilbar durch $1$" ;
		tabellenkalkulatorString = "Rest($0$;$1$)=0";

		anzEingaenge = 2;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "n";
		eingangsbezeichungen[1] = "m";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Ganzzahlig;
		eingangstypen[1] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "Teilbar";
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
			long d_erg = dividend % divisor;
			erg.setzeWert(d_erg == 0);
			erg.setGueltig(true);
		}

		return erg;
	}

}
