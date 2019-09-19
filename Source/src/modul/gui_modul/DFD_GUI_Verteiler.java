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
import dfd_gui.zoom.Zoom__FunktionTool;
import dfd_gui.zoom.Zoom__Verwalter;
import drucken.DFD_Drucken;
import global.CHANGE;
import global.DFD__GUIKONST;
import global.StartUmgebung;
import jtoolbox.IContainer;
import jtoolbox.StaticTools;
import modul.DFD_GModul_Verteiler;
import modul.DFD__Daten;
import modul.DFD__IG_Modul;

public class DFD_GUI_Verteiler extends Zoom_DFD__MausBehaelter
		implements DFD_I_SENSORAction_Link, DFD_I_GUI_Basismodul {

	DFD_Sensor modulsensor;

	DFD_GModul_Verteiler dfd_GModul_Verteiler = null;

	// Klassenfabrik
	public static DFD_GUI_Verteiler getDFD_GUI_Verteiler(IContainer behaelter, int neuesX, int neuesY) {
		return getDFD_GUI_Verteiler(behaelter, neuesX, neuesY, false);
	}

	public static DFD_GUI_Verteiler getDFD_GUI_Verteiler(IContainer behaelter, int neuesX, int neuesY,
			boolean ZENTRIERT) {
		DFD__GUIKONST.zoomAnpassen();
		int neueBreite = DFD__GUIKONST.FV_DURCHMESSER;
		int neueHoehe = DFD__GUIKONST.FV_DURCHMESSER;
		if (ZENTRIERT) {
			neuesX = neuesX - neueBreite / 2;
			neuesY = neuesY - neueHoehe / 2;
		}

		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_GUI_Verteiler ausgabe = new DFD_GUI_Verteiler(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	private DFD_GUI_Verteiler(Zoom__FunktionTool zoom, IContainer behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		super(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		modulsensor = DFD_Sensor.getDFD_Sensor(behaelter, neuesX, neuesY, DFD__GUIKONST.FV_DURCHMESSER,
				DFD__GUIKONST.FV_DURCHMESSER);
		modulsensor.setzeSensorLink(this);
		modulsensor.sichtbarMachen();

		installiereAnzeigen();

	}

	public void installiereAnzeigen() {

	}

	@Override
	public void cObjectSetzen() {
		obj = new DFD_CGUI_Verteiler();
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
			if (dfd_GModul_Verteiler != null) {
				dfd_GModul_Verteiler.verschiebeEingangsUndAusgangsPfeile();
			}

		} else {

		}
	}

	@Override
	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor) {
		dfd_GModul_Verteiler.andereModuleDeaktivieren();
	}

	@Override
	public void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor) {

	}

	@Override
	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks) {
			dfd_GModul_Verteiler.setzeAusgewaehlt();
			setzeAusgewaehlt();
	}

	@Override
	public void setzeAusgewaehlt() {
//		if (modulsensor.isSichtbar()) {
//			modulsensor.unsichtbarMachen();
//		}
		((DFD_CGUI_Verteiler) obj).setzeAusgewaehlt(true);
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		if (!modulsensor.isSichtbar()) {
			modulsensor.sichtbarMachen();
		}
		((DFD_CGUI_Verteiler) obj).setzeAusgewaehlt(false);
	}

	public void recall(DFD_GModul_Verteiler dfd_GModul_Verteiler) {
		this.dfd_GModul_Verteiler = dfd_GModul_Verteiler;
		modulsensor.aktivierePopup();
	}

	public Point getPointEingangPixel() {
		return ((DFD_CGUI_Verteiler) obj).getPointEingangPixel();
	}

	public Point getPointAusgangPixel(int nr) {
		return ((DFD_CGUI_Verteiler) obj).getPointAusgangPixel(nr);
	}

	public Area getAusgangArea() {
		return ((DFD_CGUI_Verteiler) obj).getAusgangArea();
	}

	public void setzeAusgangabeBezeichnung(DFD__Daten daten) {
		String tooltiptext = DialogZoom.getTootipZoom(daten.getToolstring());
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
		return dfd_GModul_Verteiler;
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		((DFD_CGUI_Verteiler) obj).bildZeichnen(g2, dimDFD, leseDimensionOhneZoom());
	}

}

@SuppressWarnings("serial")
class DFD_CGUI_Verteiler extends DFD__CMausBehaelter {

	public DFD_CGUI_Verteiler() {
		super();
	}

	Shape sRechteck;

	public Area getAusgangArea() {
		while (sRechteck == null) {
			StaticTools.warte(5);
		}
		Area move = new Area(sRechteck);
		move.transform(AffineTransform.getTranslateInstance(xPos, yPos));
		return move;
	}

	public Point getPointAusgangPixel(int nr) {
		return new Point(xPos + breite / 2, yPos + hoehe * 7 / 8);
	}

	public Point getPointEingangPixel() {
		return new Point(xPos + breite / 2, yPos + hoehe / 8);
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
			g2.fillRoundRect(0, 0, breite - 1, hoehe - 1,DFD__GUIKONST.fm_RECHTECK_RADIUS,DFD__GUIKONST.fm_RECHTECK_RADIUS);
		}

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

		// Äusseren Kreis setzen
		g2.setColor(DFD__GUIKONST.paintHintergrund);
		g2.fillOval(0, 0, breite, hoehe);
		g2.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawOval(1, 1, breite - 2, hoehe - 2);

		// Oberen Halbkreis rot
		g2.setColor(DFD__GUIKONST.paintEingang);
		g2.fillArc(2, 2, breite - 4, hoehe - 4, 0, 180);

		// Unteren Halbkreis grün
		g2.setColor(DFD__GUIKONST.paintAusgang);
		g2.fillArc(2, 2, breite - 4, hoehe - 4, 180, 180);

		// Inneren Kreis setzen
		g2.setColor(DFD__GUIKONST.paintHintergrund);
		g2.fillOval(breite * 3 / 8, hoehe * 3 / 8, breite / 4, hoehe / 4);

		sRechteck = new Rectangle2D.Float(0, hoehe / 2, breite, hoehe / 2);
	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dim) {
		dim.x -= dimDFD.x;
		dim.y -= dimDFD.y;

		// Hintergrund setzen

		g2.setColor(DFD__GUIKONST.paintHintergrund);
		g2.fillOval(DFD_Drucken.drX(dim.x + 1), DFD_Drucken.drY(dim.y + 1), DFD_Drucken.drF(dim.width - 2),
				DFD_Drucken.drF(dim.height - 2));

		// Oberen Halbkreis rot
		g2.setColor(DFD__GUIKONST.paintEingang);
		g2.fillArc(DFD_Drucken.drX(dim.x + 1), DFD_Drucken.drY(dim.y + 1), DFD_Drucken.drF(dim.width - 2),
				DFD_Drucken.drF(dim.height - 2), 0, 180);

		// Unteren Halbkreis grün
		g2.setColor(DFD__GUIKONST.paintAusgang);
		g2.fillArc(DFD_Drucken.drX(dim.x + 1), DFD_Drucken.drY(dim.y + 1), DFD_Drucken.drF(dim.width - 2),
				DFD_Drucken.drF(dim.height - 2), 180, 180);

		// Inneren Kreis setzen
		g2.setColor(DFD__GUIKONST.paintHintergrund);
		g2.fillOval(DFD_Drucken.drX(dim.x + dim.width * 3 / 8), DFD_Drucken.drY(dim.y + dim.height * 3 / 8),
				DFD_Drucken.drF(dim.width / 4), DFD_Drucken.drF(dim.height / 4));

		// Äusseren Kreis setzen

		g2.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawOval(DFD_Drucken.drX(dim.x + 1), DFD_Drucken.drY(dim.y + 1), DFD_Drucken.drF(dim.width - 2),
				DFD_Drucken.drF(dim.height - 2));

	}

}
