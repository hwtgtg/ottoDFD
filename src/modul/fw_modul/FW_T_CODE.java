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
public class FW_T_CODE extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 21;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "Code(c)";

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_CODE() {
		anzeige = "Code($0$)";
		tabellenkalkulatorString = "CODE($0$)";

		anzEingaenge = 1;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "c";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;

		ausgangsbezeichnung = "Zeichencode";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		String text = datenAmEingang[0].getDatenString();
		if (text == "") {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
		} else {
			int code = text.substring(0, 1).charAt(0);
			erg.setzeWert(code);
			erg.setGueltig(DFD__IF_Modul.gueltig);
		}
		return erg;
	}

}
