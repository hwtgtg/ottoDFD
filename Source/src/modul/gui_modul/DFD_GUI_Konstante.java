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
import modul.DFD_GModul_Konstante;
import modul.DFD__Daten;
import modul.DFD__IG_Modul;

public class DFD_GUI_Konstante extends Zoom_DFD__MausBehaelter
		implements DFD_I_SENSORAction_Link, DFD_I_GUI_Basismodul {

	DFD_GModul_Konstante dfd_GModul_Konstante;

	DFD_Sensor modulsensor;

	Zoom_Eingabe eingabefeld;

	public static int IDEingabe = 0;

	static int breitenanpassung = 2;

	// Klassenfabrik
	public static DFD_GUI_Konstante getDFD_GUI_Konstante(IContainer behaelter, int neuesX, int neuesY) {
		DFD__GUIKONST.zoomAnpassen();
		return getDFD_GUI_Konstante(behaelter, neuesX, neuesY, false);
	}

	public static DFD_GUI_Konstante getDFD_GUI_Konstante(IContainer behaelter, int neuesX, int neuesY,
			boolean ZENTRIERT) {
		DFD__GUIKONST.zoomAnpassen();
		int neueBreite = DFD__GUIKONST.FM_HORIZONTAL_RASTER * breitenanpassung;
		int neueHoehe = DFD__GUIKONST.FM_VERTIKAL_RASTER + DFD__GUIKONST.FM_KONNECTOR_Y;

		if (ZENTRIERT) {
			neuesX = neuesX - neueBreite / 2;
			neuesY = neuesY - neueHoehe / 2;
		}

		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_GUI_Konstante ausgabe = new DFD_GUI_Konstante(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	private DFD_GUI_Konstante(Zoom__FunktionTool zoom, IContainer behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		super(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		modulsensor = DFD_Sensor.getDFD_Sensor(behaelter, neuesX, neuesY, neueBreite,
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

		eingabefeld = Zoom_Eingabe.getZoomEingabe(obj, "Wert", 0, 0,
				DFD__GUIKONST.FM_HORIZONTAL_RASTER * breitenanpassung, DFD__GUIKONST.FM_VERTIKAL_RASTER);
		eingabefeld.setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
		eingabefeld.setzeSchriftStilFett();
		eingabefeld.mitRand();

		eingabefeld.setReadonly();
		eingabefeld.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
		eingabefeld.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);

	}

	public void cObjectSetzen() {
		obj = new DFD_CGUI_Konstante();
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
			this.setzePosition(x, y);
		}
		if (dfd_GModul_Konstante != null) {
			dfd_GModul_Konstante.verschiebeAusgangsPfeile();
		}

	}

	@Override
	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor) {
		if (dfd_GModul_Konstante != null) {
			dfd_GModul_Konstante.andereModuleDeaktivieren();
		}
	}

	@Override
	public void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor) {

	}

	@Override
	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks) {
		setzeAusgewaehlt();
	}

	@Override
	public void setzeAusgewaehlt() {
		if (modulsensor.isSichtbar()) {
			modulsensor.unsichtbarMachen();
			eingabefeld.setReadonly();
			eingabefeld.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
			eingabefeld.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
		}
		((DFD_CGUI_Konstante) obj).setzeAusgewaehlt(true);
	}

	public void setzeEditmodus() {
		if (modulsensor.isSichtbar()) {
			modulsensor.unsichtbarMachen();

			eingabefeld.setEditable();
			;
			eingabefeld.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeBearbeiten);
			eingabefeld.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeBearbeiten);

		}
		((DFD_CGUI_Konstante) obj).setzeAusgewaehlt(true);

	}

	@Override
	public void setzeNichtAusgewaehlt() {
		if (!modulsensor.isSichtbar()) {
			modulsensor.sichtbarMachen();
			eingabefeld.setReadonly();
			eingabefeld.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
			eingabefeld.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);

		}
		((DFD_CGUI_Konstante) obj).setzeAusgewaehlt(false);

	}

	public void recall(DFD_GModul_Konstante dfd_GModul_Konstante) {
		this.dfd_GModul_Konstante = dfd_GModul_Konstante;
		modulsensor.aktivierePopup();
		eingabefeld.setzeLink(dfd_GModul_Konstante);
		eingabefeld.setzeID(IDEingabe);
	}

	public Point getPointAusgangPixel(int nr) {
		return ((DFD_CGUI_Konstante) obj).getPointAusgangPixel(nr);
	}

	public Area getAusgangArea() {
		return ((DFD_CGUI_Konstante) obj).getAusgangArea();
	}

	public String leseEingangswert() {
		return eingabefeld.leseText();
	}

	public void setzeEingangswert(String wert) {
		eingabefeld.setzeAusgabetext(wert);
		eingabefeld.setzeAusrichtung(Eingabefeld.ZENTRIERT);
	}

	public void setzeAusgangsBezeichnungen(DFD__Daten daten) {
		String tooltiptext = DialogZoom.getTootipZoom(daten.getToolstring());
		eingabefeld.setzeTooltip(tooltiptext);
		modulsensor.setzeTooltip(tooltiptext);
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
		return dfd_GModul_Konstante;
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {

		Rectangle dimGuiKonstante = leseDimensionOhneZoom();
		((DFD_CGUI_Konstante) obj).bildZeichnen(g2, dimDFD, dimGuiKonstante);
		komponentenZeichnen(g2, dimDFD, dimGuiKonstante);

	}

	private void komponentenZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimGuiEingabe) {
		eingabefeld.bildZeichnen(g2, dimDFD, dimGuiEingabe);
	}

}

@SuppressWarnings("serial")
class DFD_CGUI_Konstante extends DFD__CMausBehaelter {

	Shape sRechteck;

	public Area getAusgangArea() {
		while (sRechteck == null) {
			StaticTools.warte(5);
		}
		Area move = new Area(sRechteck);
		move.transform(AffineTransform.getTranslateInstance(xPos, yPos));
		return move;
	}

	public DFD_CGUI_Konstante() {

	}

	boolean ausgewaehlt = false;

	public void setzeAusgewaehlt(boolean ausgewaehlt) {
		this.ausgewaehlt = ausgewaehlt;
		repaint();
	}

	public Point getPointAusgangPixel(int nr) {
		return new Point(xPos + breite / 2, yPos + hoehe - DFD__GUIKONST.fm_KONNECTOR_Y / 2 - 1);
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
	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimGuiKonstante) {

		Rectangle dim = new Rectangle(dimGuiKonstante);

		dim.x -= dimDFD.x;
		dim.y -= dimDFD.y;

		// Hintergrund
		g2.setColor(DFD__GUIKONST.paintHintergrund);
		// g2.setColor(Color.MAGENTA);
		g2.fillRoundRect(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y), DFD_Drucken.drF(dim.width - 1),
				DFD_Drucken.drF(DFD__GUIKONST.FM_VERTIKAL_RASTER), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));
		// Ausgang
		g2.setColor(DFD__GUIKONST.paintAusgang);
		g2.fillRect(DFD_Drucken.drX(dim.x + (dim.width - DFD__GUIKONST.FM_KONNEKTOR_X) / 2),
				DFD_Drucken.drY(dim.y + dim.height - DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drF(DFD__GUIKONST.FM_KONNEKTOR_X), DFD_Drucken.drF(DFD__GUIKONST.FM_KONNECTOR_Y));

		// Umriss
		g2.setColor(DFD__GUIKONST.paintVordergrund);
		// g2.setColor(Color.MAGENTA);
		g2.drawRoundRect(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y), DFD_Drucken.drF(dim.width - 1),
				DFD_Drucken.drF(DFD__GUIKONST.FM_VERTIKAL_RASTER), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));

	}

}
