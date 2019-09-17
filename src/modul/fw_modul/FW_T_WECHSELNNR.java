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
public class FW_T_WECHSELNNR extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 12;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "Wechseln(txt,su,ers,nr)";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_WECHSELNNR() {
		anzeige = "Wechseln($0$,$1$,$2$,$3$)";
		tabellenkalkulatorString = "WECHSELN(($0$);($1$);($2$);($3$))";

		anzEingaenge = 4;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "txt";
		eingangsbezeichungen[1] = "su";
		eingangsbezeichungen[2] = "ers";
		eingangsbezeichungen[3] = "nr";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;
		eingangstypen[1] = DFD_Datentyp.Text;
		eingangstypen[2] = DFD_Datentyp.Text;
		eingangstypen[3] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "Wechseln-nr";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		String text = datenAmEingang[0].getDatenString();
		String suchen = datenAmEingang[1].getDatenString();
		String ersetzen = datenAmEingang[2].getDatenString();
		int anz = (int) datenAmEingang[3].getGanzzahlig();

		if (anz <= 0) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {

			int pos = -1;
			for (int i = 0; i < anz; i++) {
				pos = text.indexOf(suchen, pos+1);
				if (pos < 0)
					break; // Suchstring nicht in nötiger Anzahl gefunden
			}
			if (pos >= 0) {
				String links = text.substring(0, pos);
				text = text.substring(pos);
				String ergStr = text.replaceFirst(suchen, ersetzen);

				erg.setzeWert(links + ergStr);
			} else {
				erg.setzeWert(text);
			}
			erg.setGueltig(DFD__IF_Modul.gueltig);

		}
		return erg;
	}

}
