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
public class FW_A_Hoch extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 7;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Zahlen";
	
	// Name in der Auswahl
	public static final String bezeichnung = "x HOCH n";;


	/**
	 * Konstruktor
	 * 
	 */
	public FW_A_Hoch() {
		anzeige = "$0$ ^ $1$" ;
		tabellenkalkulatorString = "($0$) ^ ($1$)";

		anzEingaenge = 2;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";
		eingangsbezeichungen[1] = "n";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Dezimal;
		eingangstypen[1] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "Potenz";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		if (((int) datenAmEingang[1].getGanzzahlig()) < 0) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {

			Dezimal tmp = new Dezimal(datenAmEingang[0].getDezimal());
			tmp = tmp.hoch((int) datenAmEingang[1].getGanzzahlig());
			erg.setzeWert(tmp);
			erg.setGueltig(true);
		}
		return erg;
	}

}
