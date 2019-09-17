package modul.gui_modul;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import dfd_gui.zoom.DFD__CMausBehaelter;
import dfd_gui.zoom.DialogZoom;
import dfd_gui.zoom.Zoom_DFD__MausBehaelter;
import dfd_gui.zoom.Zoom_Eingabe;
import dfd_gui.zoom.Zoom__FunktionTool;
import dfd_gui.zoom.Zoom__Verwalter;
import drucken.DFD_Drucken;
import global.CHANGE;
import global.DFD__GUIKONST;
import global.StartUmgebung;
import jtoolbox.Eingabefeld;
import jtoolbox.IContainer;
import modul.DFD_GModul_Anzeige;
import modul.DFD__Daten;
import modul.DFD__IG_Modul;

public class DFD_GUI_Anzeige extends Zoom_DFD__MausBehaelter implements DFD_I_SENSORAction_Link, DFD_I_GUI_Basismodul {

	DFD_GModul_Anzeige dfd_GModul_Anzeige = null;

	DFD_Sensor modulsensor;

	private Zoom_Eingabe ausgabewert;
	private Zoom_Eingabe aEingabe;

	static int breitenanpassung = 2;

	// Klassenfabrik
	public static DFD_GUI_Anzeige getDFD_GUI_Anzeige(IContainer behaelter, int neuesX, int neuesY) {
		return getDFD_GUI_Anzeige(behaelter, neuesX, neuesY, false);
	}

	public static DFD_GUI_Anzeige getDFD_GUI_Anzeige(IContainer behaelter, int neuesX, int neuesY, boolean ZENTRIERT) {
		DFD__GUIKONST.zoomAnpassen();
		int neueBreite = DFD__GUIKONST.FM_HORIZONTAL_RASTER * breitenanpassung;
		int neueHoehe = DFD__GUIKONST.FM_VERTIKAL_RASTER * DFD__GUIKONST.FM_ZEILEN_EIN_WEIT
				+ DFD__GUIKONST.FM_KONNECTOR_Y;
		if (ZENTRIERT) {
			neuesX = neuesX - neueBreite / 2;
			neuesY = neuesY - neueHoehe / 2;
		}

		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_GUI_Anzeige ausgabe = new DFD_GUI_Anzeige(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	private DFD_GUI_Anzeige(Zoom__FunktionTool zoom, IContainer behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		super(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		modulsensor = DFD_Sensor.getDFD_Sensor(behaelter, neuesX, neuesY, neueBreite,
				DFD__GUIKONST.FM_VERTIKAL_RASTER * DFD__GUIKONST.FM_ZEILEN_EIN_WEIT + DFD__GUIKONST.FM_KONNECTOR_Y);
		modulsensor.setzeSensorLink(this);
		modulsensor.sichtbarMachen();

		installiereAnzeigen();

	}

	public void installiereAnzeigen() {
		/*
		 * Jeder Eingang erhält ein Ausgabefeld In der Mitte ist das Namensfeld
		 * Unten ist das Ausgabefeld Klicken auf das Namensfeld schaltet Ein und
		 * Ausgänge um zwischen Eingangsbezeichnung/Formel und
		 * Eingangswert/Ausgangswert
		 */

		ausgabewert = Zoom_Eingabe.getZoomEingabe(obj, "Ausgabewert", 0,
				DFD__GUIKONST.FM_VERTIKAL_RASTER + DFD__GUIKONST.FM_KONNECTOR_Y,
				DFD__GUIKONST.FM_HORIZONTAL_RASTER * breitenanpassung, DFD__GUIKONST.FM_VERTIKAL_RASTER);
		ausgabewert.setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
		ausgabewert.setzeSchriftStilFett();

		aEingabe = Zoom_Eingabe.getZoomEingabe(obj, "Anzeige", 0, DFD__GUIKONST.FM_KONNECTOR_Y,
				DFD__GUIKONST.FM_HORIZONTAL_RASTER * breitenanpassung, DFD__GUIKONST.FM_VERTIKAL_RASTER);
		aEingabe.setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
		aEingabe.setzeSchriftStilFett();
		aEingabe.setReadonly();
		aEingabe.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
		aEingabe.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
		aEingabe.mitRand();
		ausgabewert.setReadonly();
		ausgabewert.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAusgabe);
		ausgabewert.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAusgabe);
		ausgabewert.mitRand();
	}

	public void cObjectSetzen() {
		obj = new DFD_CGUI_Anzeige();
	}

	@Override
	public void modulLoeschen() {
		modulsensor.modulLoeschen();
		Zoom__Verwalter.getZoomverwalter().removeZoomkomponente(this);
		obj.ausContainerEntfernen();
	}

	@Override
	public void sensorAktionDRAGG(int x, int y, int ID, DFD_Sensor sensor) {
		if (sensor == modulsensor) {
			CHANGE.setChanged();
			this.setzePosition(x, y);
			if (dfd_GModul_Anzeige != null) {
				dfd_GModul_Anzeige.verschiebeEingangsPfeil();
			}

		} else {

		}
	}

	@Override
	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor) {
		dfd_GModul_Anzeige.andereModuleDeaktivieren();
	}

	@Override
	public void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor) {

	}

	@Override
	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks) {
		dfd_GModul_Anzeige.setzeAusgewaehlt();
	}

	@Override
	public void setzeAusgewaehlt() {
		if (modulsensor.isSichtbar()) {
			modulsensor.unsichtbarMachen();
			aEingabe.setReadonly();
			aEingabe.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
			aEingabe.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
			ausgabewert.setReadonly();
			ausgabewert.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAusgabe);
			ausgabewert.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAusgabe);

		}
		((DFD_CGUI_Anzeige) obj).setzeAusgewaehlt(true);
	}

	public void setzeEditmodus() {
		if (modulsensor.isSichtbar()) {
			modulsensor.unsichtbarMachen();
			aEingabe.setEditable();
			aEingabe.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeBearbeiten);
			aEingabe.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeBearbeiten);
			ausgabewert.setReadonly();
			ausgabewert.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAusgabe);
			ausgabewert.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAusgabe);

		}
		((DFD_CGUI_Anzeige) obj).setzeAusgewaehlt(true);
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		if (!modulsensor.isSichtbar()) {
			modulsensor.sichtbarMachen();
			aEingabe.setReadonly();
			aEingabe.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
			aEingabe.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
			ausgabewert.setReadonly();
			ausgabewert.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAusgabe);
			ausgabewert.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAusgabe);

		}
		((DFD_CGUI_Anzeige) obj).setzeAusgewaehlt(false);
	}

	public void recall(DFD_GModul_Anzeige dfd_GModul_Anzeige) {
		this.dfd_GModul_Anzeige = dfd_GModul_Anzeige;
		modulsensor.aktivierePopup();
	}

	public Point getPointEingangPixel() {
		return ((DFD_CGUI_Anzeige) obj).getPointEingangPixel();
	}

	
	public void setzeAusgangsbezeichnung(String text, boolean error) {
		if (error) {
			ausgabewert.setzeHintergrundfarbe(DFD__GUIKONST.DFD_HINTERGRUND_ERROR);
		} else {
			ausgabewert.setzeHintergrundfarbe(DFD__GUIKONST.DFD_HINTERGRUND_NORMAL);
		}
		ausgabewert.setzeAusgabetext(text);
		ausgabewert.setzeAusrichtung(Eingabefeld.ZENTRIERT);
	}

	
	public void setzeAusgangabeBezeichnung(DFD__Daten daten) {
		String tooltiptext = DialogZoom.getTootipZoom(daten.getToolstring());
		ausgabewert.setzeAusgabetext("                ");
		setzeAusgangsbezeichnung(daten.getDatenString(DFD__GUIKONST.dfd_ausgabeStellen), daten.istError());
		ausgabewert.setzeTooltip(tooltiptext);
		ausgabewert.setzeAusrichtung(Eingabefeld.ZENTRIERT);
		aEingabe.setzeTooltip(tooltiptext);
		modulsensor.setzeTooltip(tooltiptext);
	}

	public String leseModulbezeichnung() {
		return aEingabe.leseText();
	}

	public void setzeModulbezeichnung(String wert) {
		aEingabe.setzeAusgabetext(wert);
		aEingabe.setzeAusrichtung(Eingabefeld.ZENTRIERT);
	}

	public int lesePosX() {
		return zoom.leseDimensionOhneZoom().x;
	}

	public int lesePosY() {
		return zoom.leseDimensionOhneZoom().y;
	}

	public int leseBreite() {
		return zoom.leseDimensionOhneZoom().width;
	}

	public int leseHoehe() {
		return zoom.leseDimensionOhneZoom().height;
	}

	@Override
	public Rectangle leseRechteck() {
		return zoom.leseDimensionOhneZoom();
	}

	public void setzeModulPosition(int posX, int posY) {
		DFD_setzePosition(posX, posY);
	}

	@Override
	public DFD__IG_Modul getGmodul() {
		return dfd_GModul_Anzeige;
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		Rectangle dimGuiAnzeige = leseDimensionOhneZoom();
		((DFD_CGUI_Anzeige) obj).bildZeichnen(g2, dimDFD, dimGuiAnzeige);

		// LOG.outln("Fenster: "+dimDFD.toString());
		komponentenZeichnen(g2, dimDFD, dimGuiAnzeige);

	}

	private void komponentenZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimGuiEingabe) {
		ausgabewert.bildZeichnen(g2, dimDFD, dimGuiEingabe);
		aEingabe.bildZeichnen(g2, dimDFD, dimGuiEingabe);
	}

}

@SuppressWarnings("serial")
class DFD_CGUI_Anzeige extends DFD__CMausBehaelter {
	int anzahlEingaenge = 1;

	public DFD_CGUI_Anzeige() {

	}

	public Point getPointEingangPixel() {
		return new Point(xPos + (DFD__GUIKONST.fm_Horizontal_RASTER * DFD_GUI_Anzeige.breitenanpassung) / 2,
				yPos + DFD__GUIKONST.fm_KONNECTOR_Y / 2);
	}

	boolean ausgewaehlt = false;

	public void setzeAusgewaehlt(boolean ausgewaehlt) {
		this.ausgewaehlt = ausgewaehlt;
		repaint();
	}

	public void anzahlEingaenge(int anzahlEingaenge) {
		this.anzahlEingaenge = anzahlEingaenge;
	}

	/**
	 * Die Darstellung der Komponente wird hier programmiert.
	 */
	public void paintComponentSpezial(Graphics g) {
		super.paintComponentSpezial(g);
		Graphics2D g2 = (Graphics2D) g;

		// Graphik-Abmessungen
		breite = getSize().width;
		hoehe = getSize().height;

		if (ausgewaehlt) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
			g.setColor(DFD__GUIKONST.modulAusgewaehlt);
			g2.fillRect(0, 0, breite - 1, hoehe - 1);

		}
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		g.setColor(DFD__GUIKONST.paintEingang);

		g2.fillRect(
				(DFD__GUIKONST.fm_Horizontal_RASTER * DFD_GUI_Anzeige.breitenanpassung - DFD__GUIKONST.fm_KONNEKTOR_X)
						/ 2,
				0, DFD__GUIKONST.fm_KONNEKTOR_X, DFD__GUIKONST.fm_KONNECTOR_Y);

		g.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawRoundRect(0, DFD__GUIKONST.fm_KONNECTOR_Y, breite - 1, hoehe - DFD__GUIKONST.fm_KONNECTOR_Y * 2 - 1,
				DFD__GUIKONST.fm_RECHTECK_RADIUS, DFD__GUIKONST.fm_RECHTECK_RADIUS);

	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimGuiAnzeige) {
		Rectangle dim = new Rectangle(dimGuiAnzeige);

		// float druckbildFaktor
		dim.x -= dimDFD.x;
		dim.y -= dimDFD.y;

		// Hintergrund
		g2.setColor(DFD__GUIKONST.paintHintergrund);
		// g2.setColor(Color.MAGENTA);
		g2.fillRoundRect(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drF(dim.width - 1), DFD_Drucken.drF(DFD__GUIKONST.FM_VERTIKAL_RASTER * 2),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));

		// Ausgang
		g2.setColor(DFD__GUIKONST.paintAusgang);
		g2.fillRect(DFD_Drucken.drX(dim.x + (dim.width - DFD__GUIKONST.FM_KONNEKTOR_X) / 2), DFD_Drucken.drY(dim.y + 0),
				DFD_Drucken.drF(DFD__GUIKONST.FM_KONNEKTOR_X), DFD_Drucken.drF(DFD__GUIKONST.FM_KONNECTOR_Y));

		// Umriss
		g2.setColor(DFD__GUIKONST.paintVordergrund);
		// g2.setColor(Color.MAGENTA);
		g2.drawRoundRect(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drF(dim.width - 2), DFD_Drucken.drF(DFD__GUIKONST.FM_VERTIKAL_RASTER * 2),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));

		g2.drawLine(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_KONNECTOR_Y + DFD__GUIKONST.FM_VERTIKAL_RASTER),
				DFD_Drucken.drX(dim.x) +DFD_Drucken.drF( dim.width - 2), DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_KONNECTOR_Y + DFD__GUIKONST.FM_VERTIKAL_RASTER));

	}

}
