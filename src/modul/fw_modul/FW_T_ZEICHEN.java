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
public class FW_T_ZEICHEN extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 22;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "Zeichen(n)";

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_ZEICHEN() {
		anzeige = "Zeichen($0$)";
		tabellenkalkulatorString = "ZEICHEN($0$)";

		anzEingaenge = 1;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "n";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "Zeichencode";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		int ein = (int) datenAmEingang[0].getGanzzahlig();
		if (ein <= 0) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {
			String zeichen  = ""+(char)ein;
			erg.setzeWert(zeichen);
			erg.setGueltig(DFD__IF_Modul.gueltig);
		}
		return erg;
	}

}
