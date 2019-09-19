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
public class FW_T_LAENGE extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 1;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "Länge(txt)";

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_LAENGE() {
		anzeige = "Länge($0$)";
		tabellenkalkulatorString = "LÄNGE($0$)";

		anzEingaenge = 1;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "txt";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;

		ausgangsbezeichnung = "Länge";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		int ein = (int) datenAmEingang[0].getDatenString().length();
		erg.setzeWert(ein);
		erg.setGueltig(DFD__IF_Modul.gueltig);

		return erg;
	}

}
