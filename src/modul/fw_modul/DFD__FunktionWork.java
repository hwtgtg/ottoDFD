/**
 * 
 */
package modul.fw_modul;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dfd_gui.zoom.DialogZoom;
import jpToolbox.DFD_Datum;
import modul.DFD_FModul_Anzeige;
import modul.DFD_FModul_Beschreibung;
import modul.DFD_FModul_Eingabe;
import modul.DFD_FModul_Funktion;
import modul.DFD_FModul_Konstante;
import modul.DFD_FModul_Verteiler;
import modul.DFD__Daten;
import modul.DFD__IG_Modul;

/**
 * @author Witt
 * 
 */
public abstract class DFD__FunktionWork {

	// Name in der Funktionsanzeige
	public String anzeige;

	// Bezeichnung in der Auswertung für Tabellenkalkulator
	// Klammern um Eingangsvariablen nicht vergessen
	public String tabellenkalkulatorString;
	public String[] eingangsbezeichungen;
	public String ausgangsbezeichnung;
	public int anzEingaenge;
	public DFD_Datentyp[] eingangstypen;

	// *** Vorbelegung. Muss überschrieben werden
	// ************************************
	// Sortierung innerhalb der Gruppe
	public static final int sortierung = 99;
	// Gruppe, in die die Funktioon eingeordnet wird
	public static final String gruppe = "TestGruppe";

	// Name in der Auswahl
	public static final String bezeichnung = "a XXX b YYY c";;

	/**
	 * Konstruktor
	 * 
	 * muss angepasst werden
	 */
	public DFD__FunktionWork() {
		anzeige = "$0$ XXX $1$ YYY $2$";
		tabellenkalkulatorString = "($0$) HHH ($1$) WWW ($2$)";

		anzEingaenge = 3;

		eingangsbezeichungen = new String[anzEingaenge];
		eingangsbezeichungen[0] = "x";
		eingangsbezeichungen[1] = "y";
		eingangsbezeichungen[2] = "z";

		eingangstypen = new DFD_Datentyp[anzEingaenge];
		eingangstypen[0] = DFD_Datentyp.Boolean;
		eingangstypen[1] = DFD_Datentyp.Text;
		eingangstypen[2] = DFD_Datentyp.Dezimal;

		ausgangsbezeichnung = "Ergebnis";
	}

	
	
	/** Die folgende Methode kann im Spezialfall überschriebe werden */
	// Vorbetrachtung der Eingangsdaten
	// Zurückgegeben wird ein Datensatz 
	// - Ergebnis-Eingangsstatus setzt aktuellen Eingangsstatus
	// - Ergebnis- isError bedeutet Weitergabe der ERROR-Meldung
	// - Ergebnis- isBearbeitet bedeutet Weitergabe der Daten
	// - Ergebnis- isGueltig bedeutet, dass die Funktion arbeiter.funktionBerechnen aufzurufen ist
	public DFD__Daten eingangsDatenUeberprüefen(DFD__Daten[] datenAmEingang, int eingangsnummer) {
		DFD__Daten erg = new DFD__Daten();
		// Teste, ob Eingangsdaten gültig.
		// Eingangsstatus entsprechend setzen
		if (!datenAmEingang[eingangsnummer].isGueltig()) {
			erg.setGueltig(false);
			erg.setEingangsStatus(DFD__IG_Modul.Eingangsstatus.Verbunden); 
			return erg ;
		}
		
		boolean errorAmEingang = false ;
		// Teste, ob Eingangsdaten zur Funktion passen.
		// Wenn Nein, Ausgang ungültig , Ende
		// Eingangsstatus entsprechend setzen
		if (datenAmEingang[eingangsnummer].istError() || !testeEingang(eingangsnummer, datenAmEingang[eingangsnummer])) {
			erg.setEingangsStatus(DFD__IG_Modul.Eingangsstatus.ERROR); 
			datenAmEingang[eingangsnummer].setzeError();
			errorAmEingang = true;
		} else{
			erg.setEingangsStatus(DFD__IG_Modul.Eingangsstatus.OK); 
		}

		// Sonst:
		// Eingangsstatus aller Eingaänge prüfen,
		// Wenn Nein, Ausgang ungültig , Ende
		for (DFD__Daten edaten : datenAmEingang) {
			if ((edaten == null) || !edaten.isGueltig()) {
				erg.setGueltig(false);
				return erg ;
			}
		}

		for (DFD__Daten edaten : datenAmEingang) {
			if (edaten.istError()) {
				errorAmEingang = true ;
			}
		}
		
		if ( errorAmEingang) erg.setzeError();
		
		erg.setGueltig(true);
		return erg;
	}

	
	public abstract DFD__Daten funktionBerechnen(DFD__Daten[] datenAmEingang);

	// ********************************************************************************

	public static final DFD__FunktionWork KEINEFUNKTION = null;

	public String getEingangsbezeichungen(int eingangsnummer) {
		return eingangsbezeichungen[eingangsnummer];
	}

	public void setzeEingangsbezeichnung(int i, String eingangsbezeichnung) {
		eingangsbezeichungen[i] = eingangsbezeichnung;
	}

	public String getAusgangsbezeichung() {
		return ausgangsbezeichnung;
	}

	public void setzeAusgangsbezeichnung(String ausgangsbezeichnung) {
		this.ausgangsbezeichnung = ausgangsbezeichnung;
	}

	public void setEingangsbezeichungen(String eingangsbezeichung, int eingangnummer) {
		this.eingangsbezeichungen[eingangnummer] = eingangsbezeichung;
	}

	public DFD_Datentyp getEingangstyp(int eingangsnummer) {
		return eingangstypen[eingangsnummer];
	}

	public boolean testeEingang(int eingangsnummer, DFD__Daten eingangsdaten) {
		if (eingangsdaten == null)
			return false;
		if (!eingangsdaten.isGueltig())
			return false;
		if (eingangstypen[eingangsnummer] == DFD_Datentyp.Text)
			return true;
		if ((eingangstypen[eingangsnummer] == DFD_Datentyp.Dezimal) && (eingangsdaten.istDezimalzahl()))
			return true;
		if ((eingangstypen[eingangsnummer] == DFD_Datentyp.Ganzzahlig) && (eingangsdaten.istGanzzahlig()))
			return true;
		if ((eingangstypen[eingangsnummer] == DFD_Datentyp.Boolean) && (eingangsdaten.istBoolean()))
			return true;
		if ((eingangstypen[eingangsnummer] == DFD_Datentyp.Datum) && (DFD_Datum.istDatum(eingangsdaten.getDatenString())))
			return true;
		return false;
	}

	public String erzeugeModulbezeichnungsString() {
		String fString = anzeige;
		for (int nr = 0; nr < anzEingaenge; nr++) {
			String port = "$" + nr + "$";

			fString = fString.replace(port, eingangsbezeichungen[nr]);
		}
		return fString;
	}

	public void setzeModulbezeichnung(String modulbezeichnung) {
		this.anzeige = modulbezeichnung;
	}

	public String erzeugeErweitertenModulbezeichnungsString(DFD__Daten[] datenAmEingang) {
		String fString = anzeige;
		for (int nr = 0; nr < anzEingaenge; nr++) {
			String port = "$" + nr + "$";

			fString = fString.replace(port, "(" + datenAmEingang[nr].getAusgangbezeichnung() + ")");
		}
		return fString;
	}
	
	public String erzeugeTabellenkalkulatorString(DFD__Daten[] datenAmEingang) {
		String fString = tabellenkalkulatorString;
		for (int nr = 0; nr < anzEingaenge; nr++) {
			String port = "$" + nr + "$";
			fString = fString.replace(port, datenAmEingang[nr].getTabellenkalkulatorString());
		}
		return fString;
	}

	
	/**
	 * 
	 * @param modultyp
	 * @param posX
	 * @param posY
	 * @param breite
	 * @param hoehe
	 * 
	 *            Bei Änderung auch in Funktionsreader anpassen !
	 * 
	 */
	public static void erzeugeModul(String modultyp, int posX, int posY, int breite, int hoehe) {

		float zoomfaktorInvers = 1.0F / DialogZoom.intZoomWert(1000) * 1000;
		boolean ZENTRIERT = true;

		posX = (int) (zoomfaktorInvers * posX);
		posY = (int) (zoomfaktorInvers * posY);
		breite = (int) (zoomfaktorInvers * breite);
		hoehe = (int) (zoomfaktorInvers * hoehe);

		if (modultyp.equalsIgnoreCase("DFD_FModul_Eingabe")) {
			// ************************************************************
			// Eingabemodul
			new DFD_FModul_Eingabe(posX, posY, ZENTRIERT);
		} else if (modultyp.equalsIgnoreCase("DFD_FModul_Konstante")) {
			// ************************************************************
			// Eingabemodul - Konstante

			new DFD_FModul_Konstante(posX, posY, ZENTRIERT);
		} else if (modultyp.equalsIgnoreCase("DFD_FModul_Beschreibung")) {
			// ************************************************************
			// Beschreibung
			new DFD_FModul_Beschreibung(posX, posY, breite, hoehe, ZENTRIERT);

		} else if (modultyp.equalsIgnoreCase("DFD_FModul_Anzeige")) {
			// ************************************************************
			// Anzeigemodul

			new DFD_FModul_Anzeige(posX, posY, ZENTRIERT);
		} else if (modultyp.equalsIgnoreCase("DFD_FModul_Verteiler")) {
			// ************************************************************
			// Verteilermodul

			new DFD_FModul_Verteiler(posX, posY, ZENTRIERT);
		} else {
			// Bleibt modultyp "DFD_FModul_Funktion"
			// Im String modultyp steht dann die Funktionsklasse
			// DFD__FunktionWork-Klassenbezeichnung

			DFD__FunktionWork wmodul = null;

			try {
				Class<?> c3 = Class.forName("modul.fw_modul." + modultyp);
				@SuppressWarnings("unchecked")
				Constructor<DFD__FunktionWork> constructor = (Constructor<DFD__FunktionWork>) c3.getConstructor();
				wmodul = constructor.newInstance();

			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (ClassNotFoundException e) {
			} catch (NoSuchMethodException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}

			if (wmodul != null) {
				DFD_FModul_Funktion.erzeugeFunktionsmodul(wmodul, posX, posY, ZENTRIERT);
			}
		}

	}


}
