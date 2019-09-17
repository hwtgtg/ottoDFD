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
import modul.gui_modul.DFD_GUI_Funktion;

public class DFD_GModul_Funktion implements DFD__IG_Modul {

	private DFD__GEingang[] gui_eingang;

	@Override
	public DFD__GEingang getGui_eingang(int eingangsnummer) {
		return gui_eingang[eingangsnummer];
	}

	private DFD__GAusgang gui_ausgang;

	private DFD_GUI_Funktion gui_funktion;

	private DFD_MausBehaelterLayered behaelter;

	private int anzahlEingaenge = 1;
	DFD_FModul_Funktion dfd_FModul_Funktion;

	public DFD_GModul_Funktion(DFD_MausBehaelterLayered behaelter, int anzahlEingaenge, int neuesX, int neuesY) {
		this(behaelter, anzahlEingaenge, neuesX, neuesY, false);
	}

	public DFD_GModul_Funktion(DFD_MausBehaelterLayered behaelter, int anzahlEingaenge, int neuesX, int neuesY,
			boolean ZENTRIERT) {
		this.behaelter = behaelter;
		this.anzahlEingaenge = anzahlEingaenge;

		gui_funktion = DFD_GUI_Funktion.getDFD_GUI_Funktion(behaelter.modulLayer, anzahlEingaenge, neuesX, neuesY,
				ZENTRIERT);

		if (anzahlEingaenge > 0) {
			gui_eingang = new DFD__GEingang[anzahlEingaenge];
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				gui_eingang[nr] = new DFD__GEingang(this, nr);
			}
		}
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

	public void recall(DFD_FModul_Funktion dfd_FModul_Funktion) {
		this.dfd_FModul_Funktion = dfd_FModul_Funktion;
		gui_ausgang.recall(this);
		gui_funktion.recall(this);
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
				;
			}
		}
		andereModuleDeaktivieren();
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		gui_funktion.setzeNichtAusgewaehlt();
		if (anzahlEingaenge > 0) {
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				gui_eingang[nr].setzeNichtAusgewaehlt();
			}
		}

	}

	public void verschiebeEingangsUndAusgangsPfeile() {
		if (anzahlEingaenge > 0) {
			for (int nr = 0; nr < anzahlEingaenge; nr++) {
				gui_eingang[nr].verschiebePfeil(DFD__GEingang.keinVerschiebenDurchSensor);
			}
		}
		gui_ausgang.verschiebePfeil();

	}

	@Override
	public DFD_MausBehaelterLayered getBehaelter() {
		return behaelter;
	}

	@Override
	public Point getPointEingangPixel(int nr) {
		return gui_funktion.getPointEingangPixel(nr);
	}

	@Override
	public Point getPointAusgangPixel(int nr) {
		return gui_funktion.getPointAusgangPixel(nr);
	}

	@Override
	public Area getAusgangArea() {
		return gui_funktion.getAusgangArea();
	}

	@Override
	public void setzeEingangsbezeichnung(int nr, String text, String farbe) {
		gui_funktion.setzeEingangsbezeichnung(nr, text, farbe);
	}

	@Override
	public void setzeModulbezeichnung(String text) {
		gui_funktion.setzeModulbezeichnung(text);
	}

	public String leseModulbezeichnung() {
		return gui_funktion.leseModulbezeichnung();
	}

	public void setzeAusgangsbezeichnung(String text) {
		gui_funktion.setzeAusgangsbezeichnung(text, !DFD__IF_Modul.error);
	}

	@Override
	public void setzeAusgangsBezeichnungen(DFD__Daten daten) {
		gui_funktion.setzeAusgaBezeichnung(daten);
	}

	@Override
	public void setzeEingangsstatus(int nr, Eingangsstatus status) {
		switch (status) {
		case OK:
			for (int i = 0; i < DFD__GUIKONST.leseVerzoegerung(); i++) {
				gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_OK);
				StaticTools.warte(200);
				gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_VERBUNDEN);
				StaticTools.warte(200);
			}
			gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_OK);
			break;
		case ERROR:
			for (int i = 0; i < DFD__GUIKONST.leseVerzoegerung(); i++) {
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
		case HERVORGEHOBEN:
			gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_HERVORHEBEN);
			break;
		case ABGESCHWAECHT:
			gui_eingang[nr].setzeEingangsstatus(DFD__GUIKONST.PF_ABSCHWAECHEN);
			break;
		default:
		}
	}

	@Override
	public DFD__GAusgang getAusgang() {
		return gui_ausgang;
	}

	public void verbindeGModulMitfremdemGModul(int nr, DFD__IG_Modul fremdesGModul) {
		gui_eingang[nr].verbindeMitneuemAusgangsGModul(fremdesGModul);
	}

	// Verbindung der G-Module, ausgelöst über G-Modul

	@Override
	public void loese_GModulEingangVomFremdenGModulAusgang(int eingangsnummer, DFD__IG_Modul ausgangGModul) {
		ausgangGModul.loeseFuGModulAusgangvomFEingang(dfd_FModul_Funktion, eingangsnummer);
	}

	@Override
	public void verbinde_GModulEingangMitFremdemGModulAusgang(int eingangsnummer, DFD__IG_Modul ausgangGModul) {
		// Aufgerufen von DFD__GEingang
		verbinde_FModulMitFremdenAusgang(ausgangGModul, eingangsnummer);
		ausgangGModul.verbindeFuGModulAusgangMitNeuemFEingang(dfd_FModul_Funktion, eingangsnummer);
	}

	@Override
	public void verbindeFuGModulAusgangMitNeuemFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		gui_ausgang.verbindeMitNeuemEingang(fModulEingang.get_GModul().getGui_eingang(eingangsnummer));
		dfd_FModul_Funktion.verbindeFAusgangMitFremdenFEingang(fModulEingang, eingangsnummer);
		fModulEingang.signalEingang_Verbunden(eingangsnummer);
	}

	@Override
	public void loeseFuGModulAusgangvomFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		gui_ausgang.loeseEingang(fModulEingang.get_GModul().getGui_eingang(eingangsnummer));
		fModulEingang.signalEingang_NichtVerbunden(eingangsnummer);
		dfd_FModul_Funktion.loeseFAusgangVomFremdenFEingang(fModulEingang, eingangsnummer);
	}

	@Override
	public DFD__IF_Modul getFmodul() {
		return dfd_FModul_Funktion;
	}

	@Override
	public void verbinde_FModulMitFremdenAusgang(DFD__IG_Modul fremdesGModul, int eingangsnummer) {
		if (dfd_FModul_Funktion != null) {
			dfd_FModul_Funktion.verbindeFModulEingangMitFremdenFAusgang(fremdesGModul.getFmodul(), eingangsnummer);
		}
	}

	@Override
	public void loese_FModulVomFremdenAusgang(int eingangsnummer) {
		if (dfd_FModul_Funktion != null) {
			dfd_FModul_Funktion.loeseFModulEingangVonFremdemAusgang(eingangsnummer);
			;
		}
	}

	@Override
	public Rectangle leseRechteck() {
		// TODO Auto-generated method stub
		return gui_funktion.leseRechteck();
	}

	public void setzeModulPosition(int posX, int posY) {
		gui_funktion.setzeModulPosition(posX, posY);
	}

	@Override
	public void pop_copy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pop_delete() {
		if (dfd_FModul_Funktion != null) {
			dfd_FModul_Funktion.modulLoeschen();
			CHANGE.setChanged();
		}
	}

	@Override
	public void pop_edit() {
		// TODO Auto-generated method stub
		CHANGE.setChanged();
	}

	@Override
	public void pop_CopyToolstring() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(dfd_FModul_Funktion.datenAmAusgang.getToolstring()), null);
	}
	
	@Override
	public void pop_CopyTabellenkalkulatorstring() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection("="+dfd_FModul_Funktion.datenAmAusgang.getTabellenkalkulatorString()), null);
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
		gui_ausgang.modulLoeschen();

		// GUI löschen
		gui_funktion.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_GModul(this);
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		gui_funktion.bildZeichnen(g2, dimDFD);
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
