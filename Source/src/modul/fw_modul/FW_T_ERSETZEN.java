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
public class FW_T_ERSETZEN extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 13;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "Ersetzen(txt,von,anz,ers)";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_ERSETZEN() {
		anzeige = "Ersetzen($0$,$1$,$2$,$3$)";
		tabellenkalkulatorString = "ERSETZEN(($0$),($1$),($2$),($3$))";

		anzEingaenge = 4;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "txt";
		eingangsbezeichungen[1] = "von";
		eingangsbezeichungen[2] = "anz";
		eingangsbezeichungen[3] = "ers";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;
		eingangstypen[1] = DFD_Datentyp.Ganzzahlig;
		eingangstypen[2] = DFD_Datentyp.Ganzzahlig;
		eingangstypen[3] = DFD_Datentyp.Text;

		ausgangsbezeichnung = "Wechseln-nr";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		String text = datenAmEingang[0].getDatenString();
		int pos = (int) datenAmEingang[1].getGanzzahlig();
		int anz = (int) datenAmEingang[2].getGanzzahlig();
		String ersetzen = datenAmEingang[3].getDatenString();

		if ((pos <= 0) || (anz < 0)) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {

			String links = text.substring(0, pos - 1);
			if ((pos + anz) <= text.length()) {
				text = text.substring(pos + anz - 1);
			}else {
				text = "";
			}
			
			erg.setzeWert(links + ersetzen + text);
			// } else {
			// erg.setzeWert(text);
			// }
			erg.setGueltig(DFD__IF_Modul.gueltig);

		}
		return erg;
	}

}
