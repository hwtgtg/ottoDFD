/**
 * 
 */
package modul.fw_modul;

import modul.DFD__Daten;
import modul.DFD__IG_Modul;

/**
 * @author Witt
 * 
 */
public class FW_B_Wenn extends DFD__FunktionWork {

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 6;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "Logisch";

	// Name in der Auswahl
	public static final String bezeichnung = "Wenn a, dann b, sonst c";

	/**
	 * Konstruktor
	 * 
	 */
	public FW_B_Wenn() {
		anzeige = "Wenn $0$, dann $1$, sonst $2$";
		tabellenkalkulatorString = "Wenn(($0$);($1$);($2$))";

		anzEingaenge = 3;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "a";
		eingangsbezeichungen[1] = "b";
		eingangsbezeichungen[2] = "c";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Boolean;
		eingangstypen[1] = DFD_Datentyp.Text;
		eingangstypen[2] = DFD_Datentyp.Text;

		ausgangsbezeichnung = "Entscheidung";

	}

	/** Die folgende Methode kann im Spezialfall überschriebe werden */
	// Vorbetrachtung der Eingangsdaten
	// Zurückgegeben wird ein Datensatz
	// - Ergebnis-Eingangsstatus setzt aktuellen Eingangsstatus
	// - Ergebnis- isError bedeutet Weitergabe der ERROR-Meldung
	// - Ergebnis- isBearbeitet bedeutet Weitergabe der Daten
	// - Ergebnis- isGueltig bedeutet, dass die Funktion
	// arbeiter.funktionBerechnen aufzurufen ist

	@Override
	public DFD__Daten eingangsDatenUeberprüefen(DFD__Daten[] datenAmEingang, int eingangsnummer) {

		DFD__Daten erg = new DFD__Daten();

		if (eingangsnummer == 0) {
			// Teste, ob Eingangsdaten gültig.
			// Eingangsstatus entsprechend setzen
			if (!datenAmEingang[eingangsnummer].isGueltig()) {
				erg.setGueltig(false);
				erg.setEingangsStatus(DFD__IG_Modul.Eingangsstatus.Verbunden);
				return erg;
			}

			// Teste, ob Eingangsdaten zur Funktion passen.
			// Wenn Nein, Ausgang ungültig , Ende
			// Eingangsstatus entsprechend setzen
			if (datenAmEingang[eingangsnummer].istError()
					|| !testeEingang(eingangsnummer, datenAmEingang[eingangsnummer])) {
				erg.setEingangsStatus(DFD__IG_Modul.Eingangsstatus.ERROR);
				datenAmEingang[eingangsnummer].setzeError();
				erg.setzeError();
				erg.setGueltig(true);
				return erg ;
			} else {
				erg.setEingangsStatus(DFD__IG_Modul.Eingangsstatus.OK);
			}

			if (datenAmEingang[0].getBoolean()) {
				// Ausgang 1 durchschalten
				if ((datenAmEingang[1] == null) || !datenAmEingang[0].isGueltig()) {
					erg.setGueltig(false);
					return erg;
				}
				erg.setzeWert(new DFD__Daten(datenAmEingang[1]));
				if(datenAmEingang[1].istError())erg.setzeError();
				int[] spezial = { 1, -2 };
				erg.setSpezial(spezial);
				erg.setBearbeitet(true);
				erg.setGueltig(true);
				return erg;
			} else {
				// Ausgang 2 durchschalten
				if ((datenAmEingang[2] == null) || !datenAmEingang[2].isGueltig()) {
					erg.setGueltig(false);
					return erg;
				}
				erg.setzeWert(new DFD__Daten(datenAmEingang[2]));
				if(datenAmEingang[2].istError())erg.setzeError();
				int[] spezial = { 2, -1 };
				erg.setSpezial(spezial);
				erg.setBearbeitet(true);
				erg.setGueltig(true);
				return erg;
			}

		} else {
			// Teste, ob Eingangsdaten gültig.
			// Eingangsstatus entsprechend setzen
			if (!datenAmEingang[eingangsnummer].isGueltig()) {
				erg.setEingangsStatus(DFD__IG_Modul.Eingangsstatus.Verbunden);
			} else 	if (datenAmEingang[eingangsnummer].istError()
					|| !testeEingang(eingangsnummer, datenAmEingang[eingangsnummer])) {
				erg.setEingangsStatus(DFD__IG_Modul.Eingangsstatus.ERROR);
				datenAmEingang[eingangsnummer].setzeError();
			}
			
			// Testen, ob Eingang 0 boolean
			if(	!datenAmEingang[0].isGueltig() ){
				erg.setGueltig(false);
				return erg;
			} else if ( datenAmEingang[0].istError()){
				erg.setzeError();
				erg.setGueltig(true);
				return erg ;
			}

			if (datenAmEingang[0].getBoolean()) {
				// Ausgang 1 durchschalten
				if ((datenAmEingang[1] == null) || !datenAmEingang[0].isGueltig()) {
					erg.setGueltig(false);
					return erg;
				}
				erg.setzeWert(new DFD__Daten(datenAmEingang[1]));
				if(datenAmEingang[1].istError()) erg.setzeError();
				int[] spezial = { 1, -2 };
				erg.setSpezial(spezial);
				erg.setBearbeitet(true);
				erg.setGueltig(true);
				return erg;
			} else {
				// Ausgang 2 durchschalten
				if ((datenAmEingang[2] == null) || !datenAmEingang[2].isGueltig()) {
					erg.setGueltig(false);
					return erg;
				}
				erg.setzeWert(new DFD__Daten(datenAmEingang[2]));
				if(datenAmEingang[2].istError()) erg.setzeError();
				int[] spezial = { 2, -1 };
				erg.setSpezial(spezial);
				erg.setBearbeitet(true);
				erg.setGueltig(true);
				return erg;
			}
			
		} 

	}

	@Override
	public DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang) {
		DFD__Daten erg = new DFD__Daten();
		erg.setGueltig(true);
		if (datenAmEingang[0].getBoolean()) {
			erg.setzeWert(new DFD__Daten(datenAmEingang[1]));
			int[] spezial = { 1, -2 };
			erg.setSpezial(spezial);
		} else {
			erg.setzeWert(new DFD__Daten(datenAmEingang[2]));
			int[] spezial = { 2, -1 };
			erg.setSpezial(spezial);
		}
		return erg;
	}

}
