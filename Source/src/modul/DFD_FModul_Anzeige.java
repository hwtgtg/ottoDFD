package modul;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dfd_gui.DFD_Arbeitsfenster;
import jpToolbox.DFD_MausBehaelterLayered;
import modul.DFD__IG_Modul.Eingangsstatus;

public class DFD_FModul_Anzeige implements DFD__IF_Modul {

	/**
	 * Nummer zum Speichern und für Verbindung zwischen Moduln beim Laden
	 */
	private int dfd_fModulNummer = -1;

	DFD_GModul_Anzeige gModulAnzeige;

	DFD__IF_Modul fAusgangAmEingang = null;

	DFD__Daten datenAmEingang = null;

	@Override
	public String getPopupBezeichnung() {
		return "Anzeige";
	}

	@Override
	public boolean isPopupWithCopy() {
		return false;
	}

	@Override
	public boolean isPopupWithEdit() {
		return true;
	}

	public DFD_FModul_Anzeige(int neuesX, int neuesY) {
		this(neuesX, neuesY, false);
	}

	public DFD_FModul_Anzeige(int neuesX, int neuesY, boolean ZENTRIERT) {
		datenAmEingang = new DFD__Daten();
		erzeugeAnzeige(DFD_Arbeitsfenster.getArbeitsfenster(), neuesX, neuesY, ZENTRIERT);
		add_FModul();
	}

	public void erzeugeAnzeige(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, boolean ZENTRIERT) {
		datenAmEingang.setGueltig(!gueltig);
		gModulAnzeige = new DFD_GModul_Anzeige(behaelter, neuesX, neuesY, ZENTRIERT);
		gModulAnzeige.recall(this);
	}

	@Override
	public void setzeAusgangGueltig() {
		// Nichts zu tun
	}

	@Override
	public void setzeAusgangUngueltig() {
		// Nichts zu tun
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
		return gModulAnzeige;
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
		if (!fAusgangAmEingang.leseDatenAmAusgang().gueltig) {
			signalUeberEingang_UnGueltig(eingangsnummer);
		} else {
			gModulAnzeige.setzeEingangsstatus(eingangsnummer, fAusgangAmEingang.leseDatenAmAusgang().istError()?Eingangsstatus.ERROR:Eingangsstatus.OK);
			gModulAnzeige.setzeAusgangsBezeichnungen(fAusgangAmEingang.leseDatenAmAusgang());
			
			// Eingangsdaten wie Daten am Ausgang vorher 
			datenAmEingang = new DFD__Daten(fAusgangAmEingang.leseDatenAmAusgang() );
			
		}
	}

	@Override
	public void signalUeberEingang_UnGueltig(int eingangsnummer) {
		gModulAnzeige.setzeEingangsstatus(eingangsnummer, Eingangsstatus.Verbunden);
		gModulAnzeige.setzeAusgangsBezeichnungen(DFD__Daten.getDatenUngueltig());
	}

	@Override
	public void signalEingang_NichtVerbunden(int eingangsnummer) {
		gModulAnzeige.setzeEingangsstatus(eingangsnummer, Eingangsstatus.Frei);
		gModulAnzeige.setzeAusgangsBezeichnungen(DFD__Daten.getDatenUngueltig());
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
		// Kein Ausgang
	}

	/**
	 * Wird aufgerufen beim Lesen aus Datei
	 */
	@Override
	public void dateiReaderVerbindeFModulEingangMitFremdenFAusgang(int eingangsnummer, DFD__IF_Modul fremderAusgang) {
		gModulAnzeige.verbindeGModulMitfremdemGModul(0, fremderAusgang.get_GModul());
	}

	/**
	 * Wird aufgerufen von einem gEingang
	 */
	@Override
	public void verbindeFModulEingangMitFremdenFAusgang(DFD__IF_Modul fremderAusgang, int eingangsnummer) {
		this.fAusgangAmEingang = fremderAusgang;
	}

	// Daten von anderem Modul lesen

	@Override
	public DFD__Daten leseDatenAmAusgang() {
		// Anzeige hat keinen Ausgang
		return null;
	}

	public void setzeModulbezeichnung(String wert) {
		gModulAnzeige.setzeModulbezeichnung(wert);
	}

	public String leseModulbezeichnung() {
		return gModulAnzeige.leseModulbezeichnung();
	}

	public void setzeModulPosition(int posX, int posY) {
		gModulAnzeige.setzeModulPosition(posX, posY);
	}

	@Override
	public int leseAnzahlEingaenge() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getDfd_fModulNummerAmEingang(int i) {
		if (fAusgangAmEingang == null) {
			return -1;
		} else {
			return fAusgangAmEingang.getDfd_fModulNummer();
		}
	}

	@Override
	public void modulLoeschen() {
		// TODO Auto-generated method stub
		// Verbindungen zu Eingang und Ausgängen löschen über GModul
		// GUI löschen
		gModulAnzeige.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_FModul();
	}

	@Override
	public Rectangle leseRechteck() {
		return gModulAnzeige.leseRechteck();
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		gModulAnzeige.bildZeichnen(g2,dimDFD);
	}

	@Override
	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		gModulAnzeige.bildZeichneVerbindungen(g2,dimDFD);
	}

}
