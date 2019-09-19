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
public class FW_D_DIFFERENZ extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 4;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Datum";

	// Name in der Auswahl
	public static final String bezeichnung = "Differenz-Tage";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_D_DIFFERENZ() {
		anzeige = " $0$ - $1$ in Tagen ";
		tabellenkalkulatorString = "($0$) - ($1$)";

		anzEingaenge = 2;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "d1";
		eingangsbezeichungen[1] = "d2";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Datum;
		eingangstypen[1] = DFD_Datentyp.Datum;

		ausgangsbezeichnung = "Wochentag";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		if ((DFD_Datum.istDatum(datenAmEingang[0].getDatenString()))
				&& (DFD_Datum.istDatum(datenAmEingang[1].getDatenString()))) {
			erg.setzeWert(DFD_Datum.getDifferenzInTagen(datenAmEingang[0].getDatenString(),datenAmEingang[1].getDatenString()));
		} else {
			erg.setzeError();
		}
		erg.setGueltig(true);

		return erg;
	}

}
