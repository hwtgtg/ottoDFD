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
public class FW_T_SUCHEN extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 11;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "SUCHEN(s in Text)";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_SUCHEN() { 
		anzeige = "Suchen($0$,$1$)";
		tabellenkalkulatorString = "SUCHEN(($0$);($1$))";

		anzEingaenge = 2;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "s";
		eingangsbezeichungen[1] = "Txt";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;
		eingangstypen[1] = DFD_Datentyp.Text;

		ausgangsbezeichnung = "Pos Su";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		String suchtext = datenAmEingang[0].getDatenString();
		suchtext = suchtext.toLowerCase();
		String text =datenAmEingang[1].getDatenString();
		text = text.toLowerCase();
		int pos = text.indexOf(suchtext);
		if (pos <0) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {
			erg.setzeWert(pos+1);
			erg.setGueltig(DFD__IF_Modul.gueltig);
		}
		return erg;
	}

}
