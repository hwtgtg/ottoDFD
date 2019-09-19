/**
 * 
 */
package modul.fw_modul;

import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_T_WECHSELN extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 11;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "Wechseln(txt,su,ers)";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_WECHSELN() {
		anzeige = "Wechseln($0$,$1$,$2$)";
		tabellenkalkulatorString = "WECHSELN(($0$);($1$);($2$))";

		anzEingaenge = 3;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "txt";
		eingangsbezeichungen[1] = "su";
		eingangsbezeichungen[2] = "ers";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;
		eingangstypen[1] = DFD_Datentyp.Text;
		eingangstypen[2] = DFD_Datentyp.Text;

		ausgangsbezeichnung = "Wechseln";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		String text = datenAmEingang[0].getDatenString();
		String suchen = datenAmEingang[1].getDatenString();
		String ersetzen = datenAmEingang[2].getDatenString();
		String ergStr=text.replaceAll(suchen, ersetzen);
		erg.setzeWert(ergStr);
		return erg;
	}

}
