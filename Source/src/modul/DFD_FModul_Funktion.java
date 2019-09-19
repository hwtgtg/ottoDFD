package modul;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dfd_gui.DFD_Arbeitsfenster;
import jpToolbox.DFD_MausBehaelterLayered;
import modul.DFD__IG_Modul.Eingangsstatus;
import modul.fw_modul.DFD__FunktionWork;

public class DFD_FModul_Funktion implements DFD__IF_Modul {

	public static DFD_FModul_Funktion erzeugeFunktionsmodul(DFD__FunktionWork arbeiter, int posX, int posY) {
		return erzeugeFunktionsmodul(arbeiter, posX, posY, false);
	}

	public static DFD_FModul_Funktion erzeugeFunktionsmodul(DFD__FunktionWork arbeiter, int posX, int posY,
			boolean ZENTRIERT) {
		DFD_FModul_Funktion erzeugt = new DFD_FModul_Funktion(arbeiter.anzEingaenge, posX, posY, ZENTRIERT);
		erzeugt.arbeiter = arbeiter;

		// Der Arbeiter existiert erst hier. Daher nicht im Konstruktor

		for (int nr = 0; nr < erzeugt.arbeiter.anzEingaenge; nr++) {
			erzeugt.setzeEingangsbezeichnung(nr);
		}
		erzeugt.setzeModulbezeichnung();
		erzeugt.setzeAusgangsbezeichnung();

		return erzeugt;
	}

	/**
	 * Nummer zum Speichern und für Verbindung zwischen Moduln beim Laden
	 */
	private int dfd_fModulNummer = -1;

	public DFD_GModul_Funktion gModulFunktion;
	int anzahlEingaenge = 0;

	DFD_FModul_Funktion dfd_FModul_Funktion;

	DFD_FAusgang fModulAmAusgang = null;

	DFD__IF_Modul[] fAusgangAmEingang = null;
	DFD__Daten[] datenAmEingang = null;

	DFD__Daten datenAmAusgang = null;

	public DFD__FunktionWork arbeiter;

	@Override
	public String getPopupBezeichnung() {
		return "Funktion";
	}

	@Override
	public boolean isPopupWithCopy() {
		return false;
	}

	@Override
	public boolean isPopupWithEdit() {
		return false;
	}

	public DFD_FModul_Funktion(int anzahlEingaenge, int neuesX, int neuesY) {
		this(anzahlEingaenge, neuesX, neuesY, false);
	}

	public DFD_FModul_Funktion(int anzahlEingaenge, int neuesX, int neuesY, boolean ZENTRIERT) {
		datenAmAusgang = new DFD__Daten();
		datenAmAusgang.setGueltig(!gueltig);

		fModulAmAusgang = new DFD_FAusgang(null, -1);

		fAusgangAmEingang = new DFD__IF_Modul[anzahlEingaenge];
		datenAmEingang = new DFD__Daten[anzahlEingaenge];
		for (int i = 0; i < anzahlEingaenge; i++) {
			datenAmEingang[i] = new DFD__Daten();
			datenAmEingang[i].setGueltig(!gueltig);
		}

		erzeugeFunktion(DFD_Arbeitsfenster.getArbeitsfenster(), anzahlEingaenge, neuesX, neuesY, ZENTRIERT);
		add_FModul();
	}

	public void erzeugeFunktion(DFD_MausBehaelterLayered behaelter, int anzahlEingaenge, int neuesX, int neuesY,
			boolean ZENTRIERT) {
		gModulFunktion = new DFD_GModul_Funktion(behaelter, anzahlEingaenge, neuesX, neuesY, ZENTRIERT);
		gModulFunktion.recall(this);
	}

	public void setzeEingangsbezeichnung(int nr) {
		gModulFunktion.setzeEingangsbezeichnung(nr, arbeiter.getEingangsbezeichungen(nr), "schwarz");
	}

	public void setzeEingangsbezeichnung(int i, String eingangsbezeichnung) {
		arbeiter.setzeEingangsbezeichnung(i, eingangsbezeichnung);
		setzeEingangsbezeichnung(i);

	}

	public void setzeModulbezeichnung() {
		gModulFunktion.setzeModulbezeichnung(arbeiter.erzeugeModulbezeichnungsString());
	}

	public void setzeModulbezeichnung(String modulbezeichnung) {
		arbeiter.setzeModulbezeichnung(modulbezeichnung);
		setzeModulbezeichnung();
	}

	public String leseModulbezeichnung() {
		return gModulFunktion.leseModulbezeichnung();
	}

	public void setzeAusgangsbezeichnung() {
		gModulFunktion.setzeAusgangsbezeichnung(arbeiter.getAusgangsbezeichung());
	}

	public void setzeAusgangsbezeichnung(String ausgangsbezeichnung) {
		arbeiter.setzeAusgangsbezeichnung(ausgangsbezeichnung);
		setzeAusgangsbezeichnung();
	}

	public void setzeModulbezeichnungen() {
		for (int nr = 0; nr < anzahlEingaenge; nr++) {
			setzeEingangsbezeichnung(nr);
		}
		setzeModulbezeichnung();
		setzeAusgangsbezeichnung();
	}

	@Override
	public void setzeAusgangGueltig() {
		if (fModulAmAusgang.fremderAusgang != null) {
			fModulAmAusgang.fremderAusgang.signalUeberEingang_Gueltig(fModulAmAusgang.eingangsnummer);
		}
	}

	@Override
	public void setzeAusgangUngueltig() {
		datenAmAusgang.setGueltig(false);

		// Hervorhebung aufheben
		if (datenAmAusgang.getSpezial() != null) {
			for (int i = 0; i < anzahlEingaenge; i++) {
				if (!(datenAmEingang[i] == null) && datenAmEingang[i].isGueltig()) {
					gModulFunktion.setzeEingangsstatus(i, Eingangsstatus.OK);
				}
			}
		}

		if (fModulAmAusgang.fremderAusgang != null) {
			fModulAmAusgang.fremderAusgang.signalUeberEingang_UnGueltig(fModulAmAusgang.eingangsnummer);
		}
	}

	@Override
	public void add_FModul() {
		DFD__FModulverwalter.getModulverwalter().add_FModul(this);
	}

	@Override
	public void remove_FModul() {
		DFD__FModulverwalter.getModulverwalter().removeFmodul(this);
	}

	@Override
	public DFD__IG_Modul get_GModul() {
		return gModulFunktion;
	}

	@Override
	public int getDfd_fModulNummer() {
		return dfd_fModulNummer;
	}

	@Override
	public void setDfd_fModulNummer(int dfd_fModulNummer) {
		this.dfd_fModulNummer = dfd_fModulNummer;
	}

	/**
	 * Wird aufgerufen über Ausgang von Fremdmodul und über Eingang Verbinden
	 */
	@Override
	public void signalUeberEingang_Gueltig(int eingangsnummer) {
		datenAmEingang[eingangsnummer] = new DFD__Daten(fAusgangAmEingang[eingangsnummer].leseDatenAmAusgang());
		DFD__Daten eingangstest = arbeiter.eingangsDatenUeberprüefen(datenAmEingang, eingangsnummer);
		// Vorbetrachtung der Eingangsdaten
		// Zurückgegeben wird ein Datensatz
		// - Ergebnis-Eingangsstatus setzt aktuellen Eingangsstatus
		gModulFunktion.setzeEingangsstatus(eingangsnummer, eingangstest.getEingangsStatus());
		if (eingangstest.istError()) {
			// - Ergebnis- isError bedeutet Weitergabe der ERROR-Meldung
			datenAmAusgang = eingangstest;
			datenAnAusgangUebermitteln();
		} else if(eingangstest.isBearbeitet()){
			// - Ergebnis- isBearbeitet bedeutet Weitergabe der Daten
			datenAmAusgang = eingangstest;
			datenAnAusgangUebermitteln();
		} else if (eingangstest.isGueltig()){
		// - Ergebnis- isGueltig bedeutet, dass die Funktion
		// arbeiter.funktionBerechnen aufzurufen ist
			datenAmAusgang = arbeiter.funktionBerechnen(datenAmEingang);
			datenAnAusgangUebermitteln();
		}
	}

	private void datenAnAusgangUebermitteln() {
		if (datenAmAusgang.getSpezial() != null) {
			for (int i = 0; i < datenAmAusgang.spezial.length; i++) {
				if (datenAmAusgang.spezial[i] > 0) {
					// hervorheben
					gModulFunktion.setzeEingangsstatus(datenAmAusgang.spezial[i], Eingangsstatus.HERVORGEHOBEN);

				} else {
					// abschwächen
					gModulFunktion.setzeEingangsstatus(-datenAmAusgang.spezial[i], Eingangsstatus.ABGESCHWAECHT);
				}
			}

		}

		datenAmAusgang.setToolstring(arbeiter.erzeugeErweitertenModulbezeichnungsString(datenAmEingang));
		datenAmAusgang.setAusgangbezeichnung(arbeiter.erzeugeErweitertenModulbezeichnungsString(datenAmEingang));
		datenAmAusgang.setTabellenkalkulatorString(arbeiter.erzeugeTabellenkalkulatorString(datenAmEingang));
		
		gModulFunktion.setzeAusgangsBezeichnungen(datenAmAusgang);
	
		
		// Ausgang gültig setzen
		setzeAusgangGueltig();
	}

	@Override
	public void signalUeberEingang_UnGueltig(int eingangsnummer) {
		gModulFunktion.setzeEingangsstatus(eingangsnummer, Eingangsstatus.Verbunden);
		datenAmEingang[eingangsnummer].setGueltig(!gueltig);
		gModulFunktion.setzeAusgangsBezeichnungen(DFD__Daten.getDatenUngueltig());
		if ((fModulAmAusgang != null) && (fModulAmAusgang.fremderAusgang != null)) {
			fModulAmAusgang.fremderAusgang.signalUeberEingang_UnGueltig(fModulAmAusgang.eingangsnummer);
		}
	}

	@Override
	public void signalEingang_NichtVerbunden(int eingangsnummer) {
		gModulFunktion.setzeEingangsstatus(eingangsnummer, Eingangsstatus.Frei);
		gModulFunktion.setzeAusgangsBezeichnungen(DFD__Daten.getDatenUngueltig());

		setzeAusgangUngueltig();
	}

	@Override
	public void signalEingang_Verbunden(int eingangsnummer) {
		signalUeberEingang_Gueltig(eingangsnummer);
	}

	// Anzeigeart setzen
	@Override
	public void setzeTooltipAnzeigeWert() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setzeTooltipAnzeigeAnzeige() {
		// TODO Auto-generated method stub

	}

	// Aufruf über G-Module
	@Override
	public void loeseFModulEingangVonFremdemAusgang(int eingangsnummer) {
		this.fAusgangAmEingang = null;
		signalEingang_NichtVerbunden(eingangsnummer);
	}

	@Override
	public void loeseAusgangVonFremdemEingang(int eingangsnummer) {

	}

	@Override
	public void dateiReaderVerbindeFModulEingangMitFremdenFAusgang(int eingangsnummer, DFD__IF_Modul fremderAusgang) {
		// verbindeFModulEingangMitFremdenFAusgang(fAusgangAmEingang[eingangsnummer],
		// eingangsnummer);
		gModulFunktion.verbindeGModulMitfremdemGModul(eingangsnummer, fremderAusgang.get_GModul());
	}

	/**
	 * Wird aufgerufen von einem gEingang
	 */
	@Override
	public void verbindeFModulEingangMitFremdenFAusgang(DFD__IF_Modul fremderAusgang, int eingangsnummer) {
		this.fAusgangAmEingang[eingangsnummer] = fremderAusgang;
	}

	// Daten von anderem Modul lesen

	@Override
	public DFD__Daten leseDatenAmAusgang() {
		return datenAmAusgang;
	}

	public void verbindeFAusgangMitFremdenFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		fModulAmAusgang = new DFD_FAusgang(fModulEingang, eingangsnummer);
	}

	public void loeseFAusgangVomFremdenFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		fModulAmAusgang.eingangsnummer = -1;
		fModulAmAusgang.fremderAusgang = null;
	}

	public void setzeModulPosition(int posX, int posY) {
		gModulFunktion.setzeModulPosition(posX, posY);
	}

	@Override
	public int leseAnzahlEingaenge() {
		return arbeiter.anzEingaenge;
	}

	@Override
	public int getDfd_fModulNummerAmEingang(int i) {
		if (fAusgangAmEingang[i] == null) {
			return -1;
		} else {
			return fAusgangAmEingang[i].getDfd_fModulNummer();
		}
	}

	@Override
	public void modulLoeschen() {
		// Verbindungen zu Eingang und Ausgängen löschen über GModul
		// GUI löschen
		gModulFunktion.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_FModul();
	}

	@Override
	public Rectangle leseRechteck() {
		return gModulFunktion.leseRechteck();
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		gModulFunktion.bildZeichnen(g2, dimDFD);
	}

	@Override
	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		gModulFunktion.bildZeichneVerbindungen(g2, dimDFD);
	}

}
