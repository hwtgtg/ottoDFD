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
public class FW_D_PLUSJAHRE extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 7;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Datum";

	// Name in der Auswahl
	public static final String bezeichnung = "addiere Jahre";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_D_PLUSJAHRE() {
		anzeige = " $0$ + $1$ Jahre ";
		tabellenkalkulatorString = "DATUM(JAHR($0$)+($1$);MONAT($0$);TAG($0$))" ;

		anzEingaenge = 2;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "d";
		eingangsbezeichungen[1] = "n";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Datum;
		eingangstypen[1] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "addiere Jahre";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		erg.setzeWert(DFD_Datum.getCalString(
				DFD_Datum.datumPlusJahre(datenAmEingang[0].getDatenString(), (int) datenAmEingang[1].getGanzzahlig())));
		erg.setGueltig(true);

		return erg;
	}

}
