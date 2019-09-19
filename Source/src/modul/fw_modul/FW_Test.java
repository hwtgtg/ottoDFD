/**
 * 
 */
package modul.fw_modul;


import modul.DFD__Daten;

/**
 * @author Witt
 * 
 */
public class FW_Test extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 99;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "TestGruppe";

	// Name in der Auswahl
	public static final String bezeichnung = "a XXX b YYY c";
	// Name in der Funktionsanzeige
	public static final String anzeige = "$0$ XXX $1$ YYY $2$";

	// Bezeichnung in der Auswertung für Tabellenkalkulator
	// Klammern um Eingangsvariablen nicht vergessen
	public static String TabellenString = "($0$) HHH ($1$) WWW ($2$)";

	public static final int anzEingaenge = 3;

	public String[] eingangsbezeichungen ;

	// ********************************************************************************

	/**
	 */
	public FW_Test() {
		
		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";
		eingangsbezeichungen[1] = "y";
		eingangsbezeichungen[2] = "z";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Boolean;
		eingangstypen[1] = DFD_Datentyp.Ganzzahlig;
		eingangstypen[2] = DFD_Datentyp.Dezimal;
		
		ausgangsbezeichnung = "Test";

	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		erg.setGueltig(true);
		erg.setzeWert((datenAmEingang[2].getDezimal().addiere(datenAmEingang[1].getDezimal())).toString());
		return erg;
	}

}
