package modul;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dfd_gui.DFD_Arbeitsfenster;
import jpToolbox.DFD_MausBehaelterLayered;

public class DFD_FModul_Beschreibung implements DFD__IF_Modul {

	/**
	 * Nummer zum Speichern und für Verbindung zwischen Moduln beim Laden
	 */
	private int dfd_fModulNummer = -1;

	DFD_GModul_Beschreibung gModulBeschreibung;

	@Override
	public String getPopupBezeichnung() {
		return "Beschreibung";
	}

	@Override
	public boolean isPopupWithCopy() {
		return false;
	}

	@Override
	public boolean isPopupWithEdit() {
		return true;
	}

	public DFD_FModul_Beschreibung(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		this(neuesX,neuesY,neueBreite,neueHoehe,false);
	}

	public DFD_FModul_Beschreibung(int neuesX, int neuesY, int neueBreite, int neueHoehe, boolean ZENTRIERT) {
		erzeugeKonstante(DFD_Arbeitsfenster.getArbeitsfenster(), neuesX, neuesY, neueBreite, neueHoehe,ZENTRIERT);
		add_FModul();
		// Vorbelegung der Werte durch GUI
		setzeDatenAmAusgang();
	}

	public void erzeugeKonstante(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe, boolean ZENTRIERT) {
		gModulBeschreibung = new DFD_GModul_Beschreibung(behaelter, neuesX, neuesY, neueBreite, neueHoehe, ZENTRIERT);
		gModulBeschreibung.recall(this);
	}

	public void setzeDatenAmAusgang() {
		// Kein Ausgang
	}

	@Override
	public void setzeAusgangGueltig() {
		// Kein Ausgang
	}

	@Override
	public void setzeAusgangUngueltig() {
		// Kein Ausgang
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
		return gModulBeschreibung;
	}

	@Override
	public int getDfd_fModulNummer() {
		return dfd_fModulNummer;
	}

	@Override
	public void setDfd_fModulNummer(int dfd_fModulNummer) {
		this.dfd_fModulNummer = dfd_fModulNummer;
	}

	@Override
	public void signalUeberEingang_Gueltig(int eingangsnummer) {
		// Kein Eingang
	}

	@Override
	public void signalUeberEingang_UnGueltig(int eingangsnummer) {
		// Kein Eingang
	}

	@Override
	public void signalEingang_NichtVerbunden(int eingangsnummer) {
		// Kein Eingang
	}

	@Override
	public void signalEingang_Verbunden(int eingangsnummer) {
		// Kein Eingang
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

	@Override
	public void dateiReaderVerbindeFModulEingangMitFremdenFAusgang(int eingangsnummer, DFD__IF_Modul fremderAusgang) {
		// Kein Eingang
	}

	@Override
	public void verbindeFModulEingangMitFremdenFAusgang(DFD__IF_Modul fremderAusgang, int eingangsnummer) {
		// Kein Eingang
	}

	@Override
	public void loeseFModulEingangVonFremdemAusgang(int eingangsnummer) {
		// Kein Eingang
	}

	@Override
	public void loeseAusgangVonFremdemEingang(int eingangsnummer) {
		// Kein Ausgang
	}

	// Daten von anderem Modul lesen

	@Override
	public DFD__Daten leseDatenAmAusgang() {
		// Kein Ausgang
		return null;
	}

	public void setzeModulPosition(int posX, int posY) {
		gModulBeschreibung.setzeModulPosition(posX, posY);
	}

	@Override
	public int leseAnzahlEingaenge() {
		return 0;
	}

	@Override
	public int getDfd_fModulNummerAmEingang(int i) {
		return -1;
	}

	@Override
	public void modulLoeschen() {
		// TODO Auto-generated method stub
		// Verbindungen zu Eingang und Ausgängen löschen über GModul
		// GUI löschen
		gModulBeschreibung.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_FModul();
	}

	public String[] leseZeilen() {
		return gModulBeschreibung.leseZeilen();
	}

	public void print(String string) {
		gModulBeschreibung.print(string);
	}

	public void println(String string) {
		gModulBeschreibung.println(string);
	}

	@Override
	public Rectangle leseRechteck() {
		return gModulBeschreibung.leseRechteck();
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		gModulBeschreibung.bildZeichnen(g2,dimDFD);
	}

	@Override
	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		gModulBeschreibung.bildZeichneVerbindungen(g2,dimDFD);
	}
}
