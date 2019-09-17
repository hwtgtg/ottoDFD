/**
 * 
 */
package modul.fw_modul;

import jtoolbox.Dezimal;
import modul.DFD__Daten;
import modul.DFD__IF_Modul;

/**
 * @author Witt
 * 
 */
public class FW_A2_LOG_A_B extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 53;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Zahlen-2";

	// Name in der Auswahl
	public static final String bezeichnung = "log_b(a)";

	/**
	 * Konstruktor
	 * 
	 */
	public FW_A2_LOG_A_B() {
		anzeige = "log_$1$($0$)";
		tabellenkalkulatorString = "LOG($0$) // LOG($1$)";

		anzEingaenge = 2;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "a";
		eingangsbezeichungen[1] = "b";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Dezimal;
		eingangstypen[1] = DFD_Datentyp.Dezimal;

		ausgangsbezeichnung = "Log a Basis b";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		Dezimal tmp = new Dezimal(datenAmEingang[0].getDezimal());
		Dezimal basis = new Dezimal(datenAmEingang[1].getDezimal());
		double d_tmp = Math.log(tmp.toDouble());
		double d_basis = Math.log(basis.toDouble());

		if (basis.toDouble() == 1) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {
			d_tmp = d_tmp/d_basis;
			if (Double.isNaN(d_tmp) || Double.isInfinite(d_tmp)) {
				erg.setGueltig(!DFD__IF_Modul.gueltig);
				erg.setzeError();
			} else {
				erg.setzeWert(d_tmp);
				erg.setGueltig(DFD__IF_Modul.gueltig);
			}
		}
		return erg;
	}

}
