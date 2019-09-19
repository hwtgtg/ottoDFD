package modul;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.Area;

import global.CHANGE;
import jpToolbox.DFD_MausBehaelterLayered;
import jtoolbox.ITuWas;
import modul.gui_modul.DFD_GUI_Konstante;

public class DFD_GModul_Konstante implements DFD__IG_Modul, ITuWas {

	private DFD__GAusgang gui_ausgang;
	private DFD_GUI_Konstante gui_konstante;

	DFD_FModul_Konstante dfd_FModul_Konstante = null;

	private DFD_MausBehaelterLayered behaelter;

	public DFD_GModul_Konstante(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY) {
		this(behaelter, neuesX, neuesY, false);
	}

	public DFD_GModul_Konstante(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, boolean ZENTRIERT) {
		this.behaelter = behaelter;

		gui_konstante = DFD_GUI_Konstante.getDFD_GUI_Konstante(behaelter.modulLayer, neuesX, neuesY,ZENTRIERT);

		gui_ausgang = new DFD__GAusgang(this);

		// Modulverwaltung der Oberfläche
		add_GModul(this);
	}

	@Override
	public void add_GModul(DFD__IG_Modul gmodul) {
		DFD__GModulverwalter.getModulverwalter().add_GModul(this);
	}

	@Override
	public void remove_GModul(DFD__IG_Modul gmodul) {
		DFD__GModulverwalter.getModulverwalter().removeGModul(this);
	}

	public void recall(DFD_FModul_Konstante dfd_FModul_Konstante) {
		this.dfd_FModul_Konstante = dfd_FModul_Konstante;
		gui_ausgang.recall(this);
		gui_konstante.recall(this);
	}

	@Override
	public void andereModuleDeaktivieren() {
		DFD__GModulverwalter.getModulverwalter().setzeAlleAnderenModule_NichtAusgewaehlt(this);
	}

	@Override
	public void setzeAusgewaehlt() {
		// Wird von der Funktions_GUI aufgerufen
		andereModuleDeaktivieren();
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		gui_konstante.setzeNichtAusgewaehlt();
	}

	// Eingang ändert sich
	@Override
	public void tuWas(int ID) {
		if (dfd_FModul_Konstante != null) {
			CHANGE.setChanged();
			dfd_FModul_Konstante.setzeDatenAmAusgang();
		}
	}

	public String getKonstante() {
		return gui_konstante.leseEingangswert();
	}

	@Override
	public void setzeAusgangsBezeichnungen(DFD__Daten daten) {
		gui_konstante.setzeAusgangsBezeichnungen(daten);
	}

	public String getAusgangsbezeichnung() {
		return getKonstante();
	}

	// ***********************************************************

	public void verschiebeAusgangsPfeile() {
		gui_ausgang.verschiebePfeil();
	}

	@Override
	public DFD_MausBehaelterLayered getBehaelter() {
		return behaelter;
	}

	@Override
	public Point getPointEingangPixel(int nr) {
		return null;
	}

	@Override
	public Point getPointAusgangPixel(int nr) {
		return gui_konstante.getPointAusgangPixel(nr);
	}

	@Override
	public Area getAusgangArea() {
		return gui_konstante.getAusgangArea();
	}

	@Override
	public void setzeEingangsstatus(int nr, Eingangsstatus status) {
	}

	@Override
	public DFD__GAusgang getAusgang() {
		return gui_ausgang;
	}

	// Verbindung zu fremdem Modul
	@Override
	public DFD__GEingang getGui_eingang(int eingangsnummer) {
		return null;
	}

	// Verbindung der G-Module, ausgelöst über G-Modul

	int fremdeEingangsnummer;
	DFD__IG_Modul fremdesGModul;

	@Override
	public void loese_GModulEingangVomFremdenGModulAusgang(int eingangsnummer, DFD__IG_Modul fremdesGModul) {
		// Aufgerufen von DFD__GEingang
		// Kein EIngang vorhanden
	}

	@Override
	public void verbinde_GModulEingangMitFremdemGModulAusgang(int eingangsnummer, DFD__IG_Modul fremdesGModul) {
		// Aufgerufen von DFD__GEingang
		// Kein EIngang vorhanden
	}

	@Override
	public void verbindeFuGModulAusgangMitNeuemFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		gui_ausgang.verbindeMitNeuemEingang(fModulEingang.get_GModul().getGui_eingang(eingangsnummer));
		dfd_FModul_Konstante.fModulAmAusgang = fModulEingang;
		dfd_FModul_Konstante.eingangFModulAmAusgang = eingangsnummer;
		fModulEingang.signalEingang_Verbunden(eingangsnummer);
	}

	@Override
	public synchronized void loeseFuGModulAusgangvomFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		gui_ausgang.loeseEingang(fModulEingang.get_GModul().getGui_eingang(eingangsnummer));
		fModulEingang.signalEingang_NichtVerbunden(eingangsnummer);
		dfd_FModul_Konstante.fModulAmAusgang = null;
		dfd_FModul_Konstante.eingangFModulAmAusgang = -1;
	}

	// Verbindung der F-Module, ausgelöst vom G-Modul

	@Override
	public DFD__IF_Modul getFmodul() {
		return dfd_FModul_Konstante;
	}

	@Override
	public void verbinde_FModulMitFremdenAusgang(DFD__IG_Modul fremdesGModul, int eingangsnummer) {
		// Kein Eingang
	}

	@Override
	public void loese_FModulVomFremdenAusgang(int eingangsnummer) {
		// Kein Eingang
	}

	@Override
	public void setzeEingangsbezeichnung(int nr, String text, String farbe) {
		// Kein Eingang
	}

	public void setzeEingangswert(String wert) {
		gui_konstante.setzeEingangswert(wert);
	}

	@Override
	public void setzeModulbezeichnung(String text) {
		// Keine Modulbezeichnung
	}

	public String leseEingangswert() {
		return gui_konstante.leseEingangswert();
	}

	
	@Override
	public Rectangle leseRechteck() {
		// TODO Auto-generated method stub
		return gui_konstante.leseRechteck();
	}

	public void setzeModulPosition(int posX, int posY) {
		gui_konstante.setzeModulPosition(posX, posY);
	}

	
	@Override
	public void pop_copy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pop_delete() {
		if (dfd_FModul_Konstante != null) {
			dfd_FModul_Konstante.modulLoeschen();
			CHANGE.setChanged();
		}
	}

	@Override
	public void pop_edit() {
		gui_konstante.setzeEditmodus();
		CHANGE.setChanged();
	}

	@Override
	public void pop_CopyToolstring() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(dfd_FModul_Konstante.datenAmAusgang.getToolstring()), null);
	}
	
	@Override
	public void pop_CopyTabellenkalkulatorstring() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection("="+dfd_FModul_Konstante.datenAmAusgang.getTabellenkalkulatorString()), null);
	}
	
	public void modulLoeschen() {
		gui_ausgang.modulLoeschen();

		// GUI löschen
		gui_konstante.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_GModul(this);
	}

	@Override
	public void bildZeichnen(Graphics2D g2 , Rectangle dimDFD) {
		gui_konstante.bildZeichnen(g2, dimDFD);
	}

	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		// Keine Verbindungen von hier aus
	}
}
