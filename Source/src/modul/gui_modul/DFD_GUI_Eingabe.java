package modul.gui_modul;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

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
import jtoolbox.StaticTools;
import modul.DFD_GModul_Eingabe;
import modul.DFD__Daten;
import modul.DFD__IG_Modul;

public class DFD_GUI_Eingabe extends Zoom_DFD__MausBehaelter implements DFD_I_SENSORAction_Link, DFD_I_GUI_Basismodul {

	DFD_GModul_Eingabe dfd_GModul_Eingabe;

	DFD_Sensor modulsensor;

	Zoom_Eingabe eingabefeld;
	Zoom_Eingabe eingangsBezeichnung;

	public static int IDEingabe = 0;
	public static int IDEingangsbezeichnung = 1;

	static int breitenanpassung = 2;

	// Klassenfabrik
	public static DFD_GUI_Eingabe getDFD_GUI_Eingabe(IContainer behaelter, int neuesX, int neuesY) {
		return getDFD_GUI_Eingabe(behaelter, neuesX, neuesY, false);
	}

	public static DFD_GUI_Eingabe getDFD_GUI_Eingabe(IContainer behaelter, int neuesX, int neuesY, boolean ZENTRIERT) {
		DFD__GUIKONST.zoomAnpassen();
		int neueBreite = DFD__GUIKONST.FM_HORIZONTAL_RASTER * breitenanpassung;
		int neueHoehe = DFD__GUIKONST.FM_VERTIKAL_RASTER * DFD__GUIKONST.FM_ZEILEN_EIN_WEIT
				+ DFD__GUIKONST.FM_KONNECTOR_Y;
		if (ZENTRIERT) {
			neuesX = neuesX - neueBreite / 2;
			neuesY = neuesY - neueHoehe / 2;
		}

		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_GUI_Eingabe ausgabe = new DFD_GUI_Eingabe(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	private DFD_GUI_Eingabe(Zoom__FunktionTool zoom, IContainer behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		super(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		modulsensor = DFD_Sensor.getDFD_Sensor(behaelter, neuesX, neuesY + DFD__GUIKONST.FM_VERTIKAL_RASTER, neueBreite,
				DFD__GUIKONST.FM_VERTIKAL_RASTER + DFD__GUIKONST.FM_KONNECTOR_Y);
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

		eingabefeld = Zoom_Eingabe.getZoomEingabe(obj, "", 0, 0, DFD__GUIKONST.FM_HORIZONTAL_RASTER * breitenanpassung,
				DFD__GUIKONST.FM_VERTIKAL_RASTER);
		eingabefeld.setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
		eingabefeld.setzeSchriftStilFett();
		eingabefeld.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeBearbeiten);
		eingabefeld.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeBearbeiten);
		eingabefeld.mitRand();
		eingabefeld.setEditable();

		eingangsBezeichnung = Zoom_Eingabe.getZoomEingabe(obj, "X", 0, DFD__GUIKONST.FM_VERTIKAL_RASTER,
				DFD__GUIKONST.FM_HORIZONTAL_RASTER * breitenanpassung, DFD__GUIKONST.FM_VERTIKAL_RASTER);
		eingangsBezeichnung.setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
		eingangsBezeichnung.setzeSchriftStilFett();
		eingangsBezeichnung.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
		eingangsBezeichnung.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
		eingangsBezeichnung.mitRand();
		eingangsBezeichnung.setReadonly();

	}

	public void cObjectSetzen() {
		obj = new DFD_CGUI_Eingabe();
	}

	@Override
	public void modulLoeschen() {
		modulsensor.modulLoeschen();
		Zoom__Verwalter.getZoomverwalter().removeZoomkomponente(this);
		obj.ausContainerEntfernen();
	}

	@Override
	public void sensorAktionDRAGG(int x, int y, int ID, DFD_Sensor sensor) {
		CHANGE.setChanged();
		if (sensor == modulsensor) {
			int nx = x;
			int ny = y - (int) (DialogZoom.intZoomWert(DFD__GUIKONST.FM_VERTIKAL_RASTER));

			this.setzePosition(nx, ny);

			if (dfd_GModul_Eingabe != null) {
				dfd_GModul_Eingabe.verschiebeAusgangsPfeile();
			}
		} else {

		}
	}

	@Override
	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor) {
		if (dfd_GModul_Eingabe != null) {
			dfd_GModul_Eingabe.andereModuleDeaktivieren();
		}
	}

	@Override
	public void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor) {

	}

	@Override
	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks) {
		((DFD_CGUI_Eingabe) obj).setzeAusgewaehlt(true);
	}

	@Override
	public void setzeAusgewaehlt() {
		if (modulsensor.isSichtbar()) {
			modulsensor.unsichtbarMachen();
		}
		eingabefeld.setEditable();
		eingabefeld.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeBearbeiten);
		eingabefeld.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeBearbeiten);
		eingangsBezeichnung.setReadonly();
		eingangsBezeichnung.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
		eingangsBezeichnung.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
		((DFD_CGUI_Eingabe) obj).setzeAusgewaehlt(true);

	}

	public void setzeEditmodus() {
		CHANGE.setChanged();
		if (modulsensor.isSichtbar()) {
			modulsensor.unsichtbarMachen();
			eingangsBezeichnung.setEditable();

			eingabefeld.setReadonly();
			eingabefeld.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
			eingabefeld.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);

			eingangsBezeichnung.setEditable();
			eingangsBezeichnung.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeBearbeiten);
			eingangsBezeichnung.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeBearbeiten);

		}
		((DFD_CGUI_Eingabe) obj).setzeAusgewaehlt(true);

	}

	@Override
	public void setzeNichtAusgewaehlt() {
		if (!modulsensor.isSichtbar()) {
			modulsensor.sichtbarMachen();
			eingabefeld.setEditable();
			eingabefeld.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeBearbeiten);
			eingabefeld.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeBearbeiten);
			eingangsBezeichnung.setReadonly();
			eingangsBezeichnung.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
			eingangsBezeichnung.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
		}
		((DFD_CGUI_Eingabe) obj).setzeAusgewaehlt(false);

	}

	public void recall(DFD_GModul_Eingabe dfd_GModul_Eingabe) {
		this.dfd_GModul_Eingabe = dfd_GModul_Eingabe;
		modulsensor.aktivierePopup();
		eingabefeld.setzeLink(dfd_GModul_Eingabe);
		eingabefeld.setzeID(IDEingabe);
		eingangsBezeichnung.setzeLink(dfd_GModul_Eingabe);
		eingangsBezeichnung.setzeID(IDEingangsbezeichnung);
	}

	public Point getPointAusgangPixel(int nr) {
		return ((DFD_CGUI_Eingabe) obj).getPointAusgangPixel(nr);
	}

	public Area getAusgangArea() {
		return ((DFD_CGUI_Eingabe) obj).getAusgangArea();
	}

	public String leseEingangswert() {
		return eingabefeld.leseText();
	}

	public void setzeEingangswert(String wert) {
		eingabefeld.setzeAusgabetext(wert);
		eingabefeld.setzeAusrichtung(Eingabefeld.ZENTRIERT);
	}

	public String leseModulbezeichnung() {
		return eingangsBezeichnung.leseText();
	}

	public void setzeModulbezeichnung(String wert) {
		eingangsBezeichnung.setzeAusgabetext(wert);
		eingangsBezeichnung.setzeAusrichtung(Eingabefeld.ZENTRIERT);
	}

	public void setzeAusgangsBezeichnungen(DFD__Daten daten) {
		String tooltiptext = DialogZoom.getTootipZoom(daten.getToolstring());
		eingabefeld.setzeTooltip(tooltiptext);
		eingangsBezeichnung.setzeTooltip(tooltiptext);
		modulsensor.setzeTooltip(tooltiptext);
	}

	@Override
	public void setzeModulPosition(int posX, int posY) {
		DFD_setzePosition(posX, posY);
	}

	@Override
	public DFD__IG_Modul getGmodul() {
		return dfd_GModul_Eingabe;
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		Rectangle dimGuiEingabe = leseDimensionOhneZoom();
		((DFD_CGUI_Eingabe) obj).bildZeichnen(g2, dimDFD, dimGuiEingabe);
		komponentenZeichnen(g2, dimDFD, dimGuiEingabe);
	}

	private void komponentenZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimGuiEingabe) {
		eingabefeld.bildZeichnen(g2, dimDFD, dimGuiEingabe);
		eingangsBezeichnung.bildZeichnen(g2, dimDFD, dimGuiEingabe);
	}

	@Override
	public Rectangle leseRechteck() {
		return zoom.leseDimensionOhneZoom();
	}

}

@SuppressWarnings("serial")
class DFD_CGUI_Eingabe extends DFD__CMausBehaelter {

	Shape sRechteck;

	public Area getAusgangArea() {
		while (sRechteck == null) {
			StaticTools.warte(5);
		}
		Area move = new Area(sRechteck);
		move.transform(AffineTransform.getTranslateInstance(xPos, yPos));
		return move;
	}

	public DFD_CGUI_Eingabe() {

	}

	public Point getPointAusgangPixel(int nr) {
		return new Point(xPos + breite / 2, yPos + hoehe - DFD__GUIKONST.fm_KONNECTOR_Y / 2 - 1);
	}

	boolean ausgewaehlt = false;

	public void setzeAusgewaehlt(boolean ausgewaehlt) {
		this.ausgewaehlt = ausgewaehlt;
		repaint();
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

		// Ausgang
		g.setColor(DFD__GUIKONST.paintAusgang);
		sRechteck = new Rectangle2D.Float((breite - DFD__GUIKONST.fm_KONNEKTOR_X) / 2,
				hoehe - DFD__GUIKONST.fm_KONNECTOR_Y - 1, DFD__GUIKONST.fm_KONNEKTOR_X, DFD__GUIKONST.fm_KONNECTOR_Y);
		g2.fillRect((breite - DFD__GUIKONST.fm_KONNEKTOR_X) / 2, hoehe - DFD__GUIKONST.fm_KONNECTOR_Y - 1,
				DFD__GUIKONST.fm_KONNEKTOR_X, DFD__GUIKONST.fm_KONNECTOR_Y);

		// Umriss
		g.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawRoundRect(0, DFD__GUIKONST.fm_KONNECTOR_Y, breite - 1,
				hoehe - DFD__GUIKONST.fm_Vertikal_RASTER - DFD__GUIKONST.fm_KONNECTOR_Y - 1,
				DFD__GUIKONST.fm_RECHTECK_RADIUS, DFD__GUIKONST.fm_RECHTECK_RADIUS);


	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimGuiEingabe) {
		
		Rectangle dim = new Rectangle(dimGuiEingabe);

		dim.x -= dimDFD.x;
		dim.y -= dimDFD.y;

		// Hintergrund
		g2.setColor(DFD__GUIKONST.paintHintergrund);
		g2.fillRoundRect(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y), DFD_Drucken.drF(dim.width - 1),
				DFD_Drucken.drF(DFD__GUIKONST.FM_VERTIKAL_RASTER * 2),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));
		// Ausgang
		g2.setColor(DFD__GUIKONST.paintAusgang);
		g2.fillRect(DFD_Drucken.drX(dim.x + (dim.width - DFD__GUIKONST.FM_KONNEKTOR_X) / 2),
				DFD_Drucken.drY(dim.y + dim.height - DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drF(DFD__GUIKONST.FM_KONNEKTOR_X), DFD_Drucken.drF(DFD__GUIKONST.FM_KONNECTOR_Y));

		// Umriss
		g2.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawRoundRect(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y), DFD_Drucken.drF(dim.width - 1),
				DFD_Drucken.drF(DFD__GUIKONST.FM_VERTIKAL_RASTER * 2),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));

		g2.drawLine(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_VERTIKAL_RASTER),
				DFD_Drucken.drX(dim.x) +DFD_Drucken.drF( dim.width - 2), DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_VERTIKAL_RASTER));

	}

}
