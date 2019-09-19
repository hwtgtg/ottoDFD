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
public class FW_D_ISDATE extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 1;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Datum";
	
	// Name in der Auswahl
	public static final String bezeichnung = "d ist Datum";;


	/**
	 * Konstruktor
	 * 
	 * muss angepasst werden
	 */
	public FW_D_ISDATE() {
		anzeige = "$0$ ist Datum" ;
		tabellenkalkulatorString = " ";

		anzEingaenge = 1;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "d";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;

		ausgangsbezeichnung = "Datum";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		erg.setzeWert(DFD_Datum.istDatum(datenAmEingang[0].getDatenString()));
		erg.setGueltig(true);

		return erg;
	}

}
