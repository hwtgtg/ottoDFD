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
public class FW_T_REPEAT extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 20;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "Wiederhole(txt,anz)";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_REPEAT() {
		anzeige = "Wiederhole($0$,$1$)";
		tabellenkalkulatorString = "WIEDERHOLEN(($0$);($1$))";

		anzEingaenge = 2;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "txt";
		eingangsbezeichungen[1] = "anz";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;
		eingangstypen[1] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "Links";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		String text = datenAmEingang[0].getDatenString();
		int anz = (int) datenAmEingang[1].getGanzzahlig();
		if (anz < 0) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {
			String ergStr = "";
			for (int i = 0; i < anz; i++) {
				ergStr += text ;
			}
			erg.setzeWert(ergStr);
			erg.setGueltig(DFD__IF_Modul.gueltig);
		}
		return erg;
	}

}
