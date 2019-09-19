/**
 * 
 */
package modul.fw_modul;

import modul.DFD__Daten;
import modul.DFD__IF_Modul;

/**
 * @author Witt
 * 
 */
public class FW_T_VERKNUEPFEN extends DFD__FunktionWork {

	// *** Vorbelegung. Muss �berschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 2;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "text & text";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_VERKNUEPFEN() {
		anzeige = "$0$ & $1$";
		tabellenkalkulatorString = "VERKETTEN(($0$) ; ($1$))";

		anzEingaenge = 2;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "TxtA";
		eingangsbezeichungen[1] = "TxtB";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;
		eingangstypen[1] = DFD_Datentyp.Text;

		ausgangsbezeichnung = "txt+txt";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		String textA = datenAmEingang[0].getDatenString();
		String textB = datenAmEingang[1].getDatenString();
		erg.setzeWert(textA + textB);
		erg.setGueltig(DFD__IF_Modul.gueltig);
		return erg;
	}

}
