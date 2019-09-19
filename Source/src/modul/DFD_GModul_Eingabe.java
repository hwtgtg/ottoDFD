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
import modul.gui_modul.DFD_GUI_Eingabe;

public class DFD_GModul_Eingabe implements DFD__IG_Modul, ITuWas {

	private DFD_GUI_Eingabe gui_eingabe;
	private DFD__GAusgang gui_ausgang;

	DFD_FModul_Eingabe dfd_FModul_Eingabe = null;

	private DFD_MausBehaelterLayered behaelter;

	public DFD_GModul_Eingabe(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY) {
		this(behaelter, neuesX, neuesY, false);
	}

	public DFD_GModul_Eingabe(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, boolean ZENTRIERT) {
		this.behaelter = behaelter;

		gui_eingabe = DFD_GUI_Eingabe.getDFD_GUI_Eingabe(behaelter.modulLayer, neuesX, neuesY, ZENTRIERT);

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

	public void recall(DFD_FModul_Eingabe dfd_FModul_Eingabe) {
		this.dfd_FModul_Eingabe = dfd_FModul_Eingabe;
		gui_ausgang.recall(this);
		gui_eingabe.recall(this);
	}

	@Override
	public void andereModuleDeaktivieren() {
		DFD__GModulverwalter.getModulverwalter().setzeAlleAnderenModule_NichtAusgewaehlt(this);
	}

	@Override
	public void setzeAusgewaehlt() {
		// Wird von der Funktions_GUI aufgerufen
		gui_eingabe.setzeAusgewaehlt();
		andereModuleDeaktivieren();
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		gui_eingabe.setzeNichtAusgewaehlt();
	}

	// Eingang ändert sich
	@Override
	public void tuWas(int ID) {
		if (dfd_FModul_Eingabe != null) {
			CHANGE.setChanged();
			dfd_FModul_Eingabe.setzeDatenAmAusgang();
		}
	}

	public String getEingabe() {
		return gui_eingabe.leseEingangswert();
	}

	@Override
	public void setzeAusgangsBezeichnungen(DFD__Daten daten) {
		gui_eingabe.setzeAusgangsBezeichnungen(daten);
	}

	public String getAusgangsbezeichnung() {
		return gui_eingabe.leseModulbezeichnung();
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
		return gui_eingabe.getPointAusgangPixel(nr);
	}

	@Override
	public Area getAusgangArea() {
		return gui_eingabe.getAusgangArea();
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
		// Kein Eingang vorhanden
	}

	@Override
	public void verbindeFuGModulAusgangMitNeuemFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		gui_ausgang.verbindeMitNeuemEingang(fModulEingang.get_GModul().getGui_eingang(eingangsnummer));
		dfd_FModul_Eingabe.fModulAmAusgang = fModulEingang;
		dfd_FModul_Eingabe.eingangFModulAmAusgang = eingangsnummer;
		fModulEingang.signalEingang_Verbunden(eingangsnummer);
	}

	@Override
	public synchronized void loeseFuGModulAusgangvomFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		gui_ausgang.loeseEingang(fModulEingang.get_GModul().getGui_eingang(eingangsnummer));
		fModulEingang.signalEingang_NichtVerbunden(eingangsnummer);
		dfd_FModul_Eingabe.fModulAmAusgang = null;
		dfd_FModul_Eingabe.eingangFModulAmAusgang = -1;
	}

	// Verbindung der F-Module, ausgelöst vom G-Modul

	@Override
	public DFD__IF_Modul getFmodul() {
		return dfd_FModul_Eingabe;
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
	public void setzeEingangsbezeichnung(int nr, String wert, String farbe) {
		// Kein Eingang
	}

	public void setzeEingangswert(String wert) {
		gui_eingabe.setzeEingangswert(wert);
	}

	@Override
	public void setzeModulbezeichnung(String wert) {
		gui_eingabe.setzeModulbezeichnung(wert);
	}

	public String leseEingangswert() {
		return gui_eingabe.leseEingangswert();
	}

	public String leseModulbezeichnung() {
		return gui_eingabe.leseModulbezeichnung();
	}

	@Override
	public Rectangle leseRechteck() {
		return gui_eingabe.leseRechteck();
	}


	public void setzeModulPosition(int posX, int posY) {
		gui_eingabe.setzeModulPosition(posX, posY);
	}

	@Override
	public void pop_copy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pop_delete() {
		if (dfd_FModul_Eingabe != null) {
			dfd_FModul_Eingabe.modulLoeschen();
			CHANGE.setChanged();
		}
	}

	@Override
	public void pop_edit() {
		gui_eingabe.setzeEditmodus();
		CHANGE.setChanged();
	}

	@Override
	public void pop_CopyToolstring() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(dfd_FModul_Eingabe.datenAmAusgang.getToolstring()), null);
	}
	
	@Override
	public void pop_CopyTabellenkalkulatorstring() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection("="+dfd_FModul_Eingabe.datenAmAusgang.getTabellenkalkulatorString()), null);
	}
	

	public void modulLoeschen() {
		gui_ausgang.modulLoeschen();

		// GUI löschen
		gui_eingabe.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_GModul(this);
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		gui_eingabe.bildZeichnen(g2, dimDFD);
	}

	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		// Keine Verbinder von hier aus
	}
}
