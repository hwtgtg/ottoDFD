package modul;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Area;

import global.CHANGE;
import jpToolbox.DFD_MausBehaelterLayered;
import jtoolbox.ITuWas;
import modul.gui_modul.DFD_GUI_BeschreibungText;

public class DFD_GModul_Beschreibung implements DFD__IG_Modul, ITuWas {

	private DFD_GUI_BeschreibungText gui_beschreibung;

	DFD_FModul_Beschreibung dfd_FModul_Beschreibung = null;

	@SuppressWarnings("unused")
	private DFD_MausBehaelterLayered behaelter;

	public DFD_GModul_Beschreibung(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		this(behaelter, neuesX, neuesY, neueBreite, neueHoehe, false);
	}

	public DFD_GModul_Beschreibung(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe, boolean ZENTRIERT) {
		this.behaelter = behaelter;
		gui_beschreibung = DFD_GUI_BeschreibungText.getDFD_GUI_BeschreibungText(behaelter.modulLayer, neuesX, neuesY,
				neueBreite, neueHoehe, ZENTRIERT);

		gui_beschreibung.setzeSchriftStilFett();

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

	public void recall(DFD_FModul_Beschreibung dfd_FModul_Beschreibung) {
		this.dfd_FModul_Beschreibung = dfd_FModul_Beschreibung;
		gui_beschreibung.recall(this);
	}

	@Override
	public void andereModuleDeaktivieren() {
		DFD__GModulverwalter.getModulverwalter().setzeAlleAnderenModule_NichtAusgewaehlt(this);
	}

	@Override
	public void setzeAusgewaehlt() {
		andereModuleDeaktivieren();
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		gui_beschreibung.setzeNichtAusgewaehlt();
	}

	// Eingang ändert sich
	@Override
	public void tuWas(int ID) {
		// Nichts zum weitergeben
	}

	@Override
	public void setzeAusgangsBezeichnungen(DFD__Daten daten) {
		gui_beschreibung.setzeAusgangsBezeichnungen(daten);
	}

	// ***********************************************************

	@Override
	public DFD_MausBehaelterLayered getBehaelter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPointEingangPixel(int nr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPointAusgangPixel(int nr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Area getAusgangArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setzeEingangsstatus(int nr, Eingangsstatus status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setzeEingangsbezeichnung(int nr, String text, String farbe) {
		// Keine Eingangsbezeichnung

	}

	@Override
	public void setzeModulbezeichnung(String text) {
		// Keine Modulbezeichnung
	}

	@Override
	public DFD__GAusgang getAusgang() {
		return null;
	}

	@Override
	public DFD__IF_Modul getFmodul() {
		return dfd_FModul_Beschreibung;
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
	public DFD__GEingang getGui_eingang(int eingangsnummer) {
		return null;
	}

	@Override
	public void loese_GModulEingangVomFremdenGModulAusgang(int eingangsnummer, DFD__IG_Modul fremdesGModul) {
		// TODO Auto-generated method stub

	}

	@Override
	public void verbinde_GModulEingangMitFremdemGModulAusgang(int eingangsnummer, DFD__IG_Modul fremdesGModul) {
		// Aufgerufen von DFD__GEingang
		// TODO Auto-generated method stub

	}

	@Override
	public void verbindeFuGModulAusgangMitNeuemFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void loeseFuGModulAusgangvomFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		// Nichts zu tun, kein Ausgang
	}

	@Override
	public Rectangle leseRechteck() {
		// TODO Auto-generated method stub
		return gui_beschreibung.leseRechteck();
	}
	
	public void setzeModulPosition(int posX, int posY) {
		gui_beschreibung.setzeModulPosition(posX, posY);
	}

	@Override
	public void pop_copy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pop_delete() {
		if (dfd_FModul_Beschreibung != null) {
			dfd_FModul_Beschreibung.modulLoeschen();
			CHANGE.setChanged();
		}
	}

	@Override
	public void pop_edit() {
		gui_beschreibung.setzeAusgewaehlt();
		CHANGE.setChanged();
	}

	@Override
	public void pop_CopyToolstring() {
		// Nichts zu tun
	}
	
	@Override
	public void pop_CopyTabellenkalkulatorstring() {
		// Nichts zu tun		
	}
	
	public void modulLoeschen() {
		// GUI löschen
		gui_beschreibung.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_GModul(this);
	}

	public String[] leseZeilen() {
		return gui_beschreibung.leseZeilen();
	}

	public void print(String string) {
		gui_beschreibung.print(string);
	}

	public void println(String string) {
		gui_beschreibung.println(string);
	}

	@Override
	public void bildZeichnen(Graphics2D g2 , Rectangle dimDFD) {
		gui_beschreibung.bildZeichnen(g2, dimDFD);
	}

	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		// Keine Verbinder von hier aus
	}


}
