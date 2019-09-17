package modul;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.Area;

import global.CHANGE;
import global.DFD__GUIKONST;
import jpToolbox.DFD_MausBehaelterLayered;
import jtoolbox.StaticTools;
import modul.gui_modul.DFD_GUI_Anzeige;

public class DFD_GModul_Anzeige implements DFD__IG_Modul {

	private DFD__GEingang[] gui_eingang;

	@Override
	public DFD__GEingang getGui_eingang(int eingangsnummer) {
		return gui_eingang[eingangsnummer];
	}

	private DFD_GUI_Anzeige gui_anzeige;

	private DFD_MausBehaelterLayered behaelter;

	private int anzahlEingaenge = 1;
	private DFD_FModul_Anzeige dfd_FModul_Anzeige = null;

	public DFD_GModul_Anzeige(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY) {
		this(behaelter,neuesX,neuesY,false);
	}

	public DFD_GModul_Anzeige(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, boolean ZENTRIERT) {
		this.behaelter = behaelter;
		gui_anzeige = DFD_GUI_Anzeige.getDFD_GUI_Anzeige(behaelter.modulLayer, neuesX, neuesY,ZENTRIERT);

		if (anzahlEingaenge > 0) {
			gui_eingang = new DFD__GEingang[anzahlEingaenge];
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				gui_eingang[nr] = new DFD__GEingang(this, nr);
			}
		}

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

	public void recall(DFD_FModul_Anzeige dfd_FModul_Anzeige) {
		this.dfd_FModul_Anzeige = dfd_FModul_Anzeige;
		gui_anzeige.recall(this);
	}

	@Override
	public void andereModuleDeaktivieren() {
		DFD__GModulverwalter.getModulverwalter().setzeAlleAnderenModule_NichtAusgewaehlt(this);
	}

	@Override
	public void setzeAusgewaehlt() {
		// Wird von der Funktions_GUI aufgerufen
		if (anzahlEingaenge > 0) {
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				gui_eingang[nr].setzeAusgewaehlt();
			}
		}
		gui_anzeige.setzeAusgewaehlt();
		andereModuleDeaktivieren();
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		gui_anzeige.setzeNichtAusgewaehlt();
		if (anzahlEingaenge > 0) {
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				gui_eingang[nr].setzeNichtAusgewaehlt();
			}
		}

	}

	public void verschiebeEingangsPfeil() {
		if (anzahlEingaenge > 0) {
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				gui_eingang[nr].verschiebePfeil(DFD__GEingang.keinVerschiebenDurchSensor);
			}
		}
	}

	@Override
	public DFD_MausBehaelterLayered getBehaelter() {
		return behaelter;
	}

	@Override
	public Point getPointEingangPixel(int nr) {
		return gui_anzeige.getPointEingangPixel();
	}

	@Override
	public Point getPointAusgangPixel(int nr) {
		return null;
	}

	@Override
	public Area getAusgangArea() {
		return null;
	}

	@Override
	public void setzeEingangsbezeichnung(int nr, String text, String farbe) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setzeAusgangsBezeichnungen(DFD__Daten daten) {
		gui_anzeige.setzeAusgangabeBezeichnung(daten);
	}

	// OK, ERROR , Verbunden , geloest

	@Override
	public void setzeEingangsstatus(int nr, Eingangsstatus status) {
		switch (status) {
		case OK:
			for ( int i = 0 ; i < DFD__GUIKONST.leseVerzoegerung() ; i++){
				gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_OK);
				StaticTools.warte(200);
				gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_VERBUNDEN);
				StaticTools.warte(200);
			}
			gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_OK);
			break;
		case ERROR:
			for ( int i = 0 ; i < DFD__GUIKONST.leseVerzoegerung() ; i++){
				gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_ERROR);
				StaticTools.warte(200);
				gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_VERBUNDEN);
				StaticTools.warte(200);
			}
			gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_ERROR);
			break;
		case Verbunden:
			gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_VERBUNDEN);
			break;
		case Frei:
			gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_FREI);
			break;
		default:
		}

	}

	@Override
	public DFD__GAusgang getAusgang() {
		return null;
	}

	public void verbindeGModulMitfremdemGModul(int nr, DFD__IG_Modul fremdesGModul) {
		gui_eingang[nr].verbindeMitneuemAusgangsGModul(fremdesGModul);
	}

	// Verbindung der G-Module, ausgelöst über G-Modul

	DFD__IG_Modul fremdesGModul = null;

	@Override
	public void loese_GModulEingangVomFremdenGModulAusgang(int eingangsnummer, DFD__IG_Modul ausgangGModul) {
		ausgangGModul.loeseFuGModulAusgangvomFEingang(dfd_FModul_Anzeige, eingangsnummer);
	}

	@Override
	public void verbinde_GModulEingangMitFremdemGModulAusgang(int eingangsnummer, DFD__IG_Modul ausgangGModul) {
		// Aufgerufen von DFD__GEingang
		verbinde_FModulMitFremdenAusgang(ausgangGModul, eingangsnummer);
		ausgangGModul.verbindeFuGModulAusgangMitNeuemFEingang(dfd_FModul_Anzeige, eingangsnummer);
	}

	@Override
	public void verbindeFuGModulAusgangMitNeuemFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		// Hier gibt es nichts zu tun. Kein Ausgang vorhanden
	}

	@Override
	public synchronized void loeseFuGModulAusgangvomFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		// Hier gibt es nichts zu tun. Kein Ausgang vorhanden
	}

	// Verbindung der F-Module, ausgelöst über G-Modul

	@Override
	public DFD__IF_Modul getFmodul() {
		return dfd_FModul_Anzeige;
	}

	@Override
	public void verbinde_FModulMitFremdenAusgang(DFD__IG_Modul fremdesGModul, int eingangsnummer) {
		if (dfd_FModul_Anzeige != null) {
			dfd_FModul_Anzeige.verbindeFModulEingangMitFremdenFAusgang(fremdesGModul.getFmodul(), eingangsnummer);
		}
	}

	@Override
	public void loese_FModulVomFremdenAusgang(int eingangsnummer) {
		if (dfd_FModul_Anzeige != null) {
			dfd_FModul_Anzeige.loeseFModulEingangVonFremdemAusgang(eingangsnummer);
			;
		}
	}

	@Override
	public void setzeModulbezeichnung(String wert) {
		gui_anzeige.setzeModulbezeichnung(wert);
	}

	public String leseModulbezeichnung() {
		return gui_anzeige.leseModulbezeichnung();
	}

	@Override
	public Rectangle leseRechteck() {
		// TODO Auto-generated method stub
		return gui_anzeige.leseRechteck();
	}

	
	public void setzeModulPosition(int posX, int posY) {
		gui_anzeige.setzeModulPosition(posX, posY);
	}

	@Override
	public void pop_copy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pop_delete() {
		if (dfd_FModul_Anzeige != null) {
			dfd_FModul_Anzeige.modulLoeschen();
			CHANGE.setChanged();
		}
	}

	@Override
	public void pop_edit() {
		gui_anzeige.setzeEditmodus();
		CHANGE.setChanged();
	}

	@Override
	public void pop_CopyToolstring() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(dfd_FModul_Anzeige.datenAmEingang.getToolstring()), null);
	}
	
	@Override
	public void pop_CopyTabellenkalkulatorstring() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection("="+dfd_FModul_Anzeige.datenAmEingang.getTabellenkalkulatorString()), null);
	}
	
	public void modulLoeschen() {
		// Eingangspfeil löschen
		if (anzahlEingaenge > 0) {
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				if (gui_eingang[nr] != null) {
					gui_eingang[nr].modulLoeschen();
				}
			}
		}
		// GUI löschen
		gui_anzeige.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_GModul(this);

	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD ) {
		gui_anzeige.bildZeichnen(g2, dimDFD);
	}

	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		if (anzahlEingaenge > 0) {
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				if (gui_eingang[nr] != null) {
					gui_eingang[nr].bildZeichnen(g2, dimDFD);
				}
			}
		}
	}


}
