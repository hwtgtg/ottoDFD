package modul;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dfd_gui.DFD_Arbeitsfenster;
import jpToolbox.DFD_MausBehaelterLayered;

public class DFD_FModul_Konstante implements DFD__IF_Modul {

	/**
	 * Nummer zum Speichern und für Verbindung zwischen Moduln beim Laden
	 */
	private int dfd_fModulNummer = -1;

	DFD_GModul_Konstante gModulKonstante;

	DFD__IF_Modul fModulAmAusgang = null;
	int eingangFModulAmAusgang = 0;

	DFD__Daten datenAmAusgang = null;

	@Override
	public String getPopupBezeichnung() {
		return "Konstante";
	}

	@Override
	public boolean isPopupWithCopy() {
		return false;
	}

	@Override
	public boolean isPopupWithEdit() {
		return true;
	}
	
	public DFD_FModul_Konstante(int neuesX, int neuesY) {
		this(neuesX,neuesY,false);
	}

	public DFD_FModul_Konstante(int neuesX, int neuesY, boolean ZENTRIERT) {
		datenAmAusgang = new DFD__Daten();
		erzeugeKonstante(DFD_Arbeitsfenster.getArbeitsfenster(), neuesX, neuesY,ZENTRIERT);
		add_FModul();
		// Vorbelegung der Werte durch GUI
		setzeDatenAmAusgang();
	}

	private void erzeugeKonstante(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, boolean ZENTRIERT) {
		gModulKonstante = new DFD_GModul_Konstante(behaelter, neuesX, neuesY,ZENTRIERT);
		gModulKonstante.recall(this);
	}

	
	public void setzeDatenAmAusgang() {
		datenAmAusgang.setzeWert(gModulKonstante.getKonstante());
		datenAmAusgang.setTabellenkalkulatorString(gModulKonstante.getAusgangsbezeichnung());
		datenAmAusgang.setAusgangbezeichnung(gModulKonstante.getAusgangsbezeichnung());
		datenAmAusgang.setToolstring(gModulKonstante.getKonstante());
		datenAmAusgang.setTabellenkalkulatorString(gModulKonstante.getKonstante());

		// für Tabellenkalkulator Wahrheitswerte und Strings aufbereiten
		datenAmAusgang.fuerTabellenkalkulatorAufbereiten();

		setzeAusgangGueltig();
	}

	private void signalisiereAusgangGueltig(boolean gueltig) {
		gModulKonstante.setzeAusgangsBezeichnungen(datenAmAusgang);

		if (fModulAmAusgang != null) {
			if (datenAmAusgang.gueltig) {
				fModulAmAusgang.signalUeberEingang_Gueltig(eingangFModulAmAusgang);
			} else {
				fModulAmAusgang.signalUeberEingang_UnGueltig(eingangFModulAmAusgang);
			}
		}

	}

	@Override
	public void setzeAusgangGueltig() {
		if (datenAmAusgang.getDatenString().equals("")) {
			datenAmAusgang.setGueltig(false);
			setzeAusgangUngueltig();
		} else {
			datenAmAusgang.setGueltig(true);
			signalisiereAusgangGueltig(gueltig);
		}
	}

	@Override
	public void setzeAusgangUngueltig() {
		datenAmAusgang.setGueltig(!gueltig);
		signalisiereAusgangGueltig(!gueltig);
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
		return gModulKonstante;
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
		setzeAusgangUngueltig();
		fModulAmAusgang = null;
		eingangFModulAmAusgang = -1;
	}

	// Daten von anderem Modul lesen

	@Override
	public DFD__Daten leseDatenAmAusgang() {
		return datenAmAusgang;
	}

	// Vorbelegungen
	public void setzeEingangswert(String wert) {
		gModulKonstante.setzeEingangswert(wert);
		setzeDatenAmAusgang();
	}

	public String leseEingangswert() {
		return gModulKonstante.leseEingangswert();
	}

	public void setzeModulPosition(int posX, int posY) {
		gModulKonstante.setzeModulPosition(posX, posY);
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
		gModulKonstante.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_FModul();
	}

	@Override
	public Rectangle leseRechteck() {
		return gModulKonstante.leseRechteck();
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		gModulKonstante.bildZeichnen(g2, dimDFD);
	}

	@Override
	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		gModulKonstante.bildZeichneVerbindungen(g2, dimDFD);
	}

	
}
