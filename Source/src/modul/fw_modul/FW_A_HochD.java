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
public class FW_A_HochD extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 7;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Zahlen";

	// Name in der Auswahl
	public static final String bezeichnung = "x HOCH y";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_A_HochD() {
		anzeige = "$0$ ^ $1$";
		tabellenkalkulatorString = "($0$) ^ ($1$)";

		anzEingaenge = 2;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";
		eingangsbezeichungen[1] = "y";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Dezimal;
		eingangstypen[1] = DFD_Datentyp.Dezimal;

		ausgangsbezeichnung = "Potenz";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		double basis = datenAmEingang[0].getDezimal().toDouble();
		if (basis < 0) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {
			double exponent = datenAmEingang[1].getDezimal().toDouble();

			double dErg = Math.pow(basis, exponent);
			
			if (dErg == Double.NaN) {
				erg.setGueltig(!DFD__IF_Modul.gueltig);
				erg.setzeError();
			} else {
				erg.setzeWert(dErg);
				erg.setGueltig(DFD__IF_Modul.gueltig);
			}
		}
		return erg;
	}

}
