/**
 * 
 */
package modul.fw_modul;

import jpToolbox.DFD_Datum;
import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_D_DAYOFWEAK_S extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 3;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Datum";

	// Name in der Auswahl
	public static final String bezeichnung = "Wochentag-Text";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_D_DAYOFWEAK_S() {
		anzeige = "Wochentag von $0$";
		tabellenkalkulatorString = "WAHL(WOCHENTAG($0$);Sonntag\";\"Montag\";\"Dienstag\";\"Mittwoch\";\"Donnerstag\";\"Freitag\";\"Samstag\")";

		anzEingaenge = 1;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "d";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Datum;

		ausgangsbezeichnung = "Wochentag";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		if (DFD_Datum.istDatum(datenAmEingang[0].getDatenString())) {
			erg.setzeWert(DFD_Datum.getWochentagText(datenAmEingang[0].getDatenString()));
		} else {
			erg.setzeError();
		}
		erg.setGueltig(true);

		return erg;
	}

}
