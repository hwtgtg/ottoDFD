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
public class FW_T_TEIL extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 6;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Text";

	// Name in der Auswahl
	public static final String bezeichnung = "TEIL(txt,pos,anz)";;

	/**
	 * Konstruktor
	 * 
	 */
	public FW_T_TEIL() {
		anzeige = "Teil($0$,$1$,$2$)";
		tabellenkalkulatorString = "TEIL(($0$),($1$),($2$))";

		anzEingaenge = 3;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "txt";
		eingangsbezeichungen[1] = "pos";
		eingangsbezeichungen[2] = "anz";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Text;
		eingangstypen[1] = DFD_Datentyp.Ganzzahlig;
		eingangstypen[2] = DFD_Datentyp.Ganzzahlig;

		ausgangsbezeichnung = "Teil";
	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		String text = datenAmEingang[0].getDatenString();
		int pos = (int) datenAmEingang[1].getGanzzahlig();
		int anz = (int) datenAmEingang[2].getGanzzahlig();
		if ((pos <= 0)||(anz<0)) {
			erg.setGueltig(!DFD__IF_Modul.gueltig);
			erg.setzeError();
		} else {
			if (pos <= text.length()) {
				if((pos+anz)>text.length()){
					anz = text.length()-pos;
				}
				erg.setzeWert(text.substring(pos, pos+anz));
			} else if (pos>text.length()){
				erg.setzeWert("");
			} 
			erg.setGueltig(DFD__IF_Modul.gueltig);
		}
		return erg;
	}

}
