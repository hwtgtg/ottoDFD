/**
 * 
 */
package modul.fw_modul;

import jtoolbox.Dezimal;
import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_I_Fakultaet extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 4;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "ganzzahlig";
	
	// Name in der Auswahl
	public static final String bezeichnung = "n!";


	/**
	 * Konstruktor
	 * 
	 */
	public FW_I_Fakultaet() {
		anzeige = "($0$)!" ;
		tabellenkalkulatorString = "FAKULTÄT($0$)";

		anzEingaenge = 1;
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "n";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "Fakultät";
	}



	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();

		long max = datenAmEingang[0].getGanzzahlig();

		Dezimal tmp = new Dezimal(1,0);
		for (long i = 1 ; i <= max; i++){
			tmp = tmp.multipliziere(new Dezimal(i,0));
		}
		
		erg.setzeWert(tmp);
		erg.setGueltig(true);

		return erg;
	}

}
