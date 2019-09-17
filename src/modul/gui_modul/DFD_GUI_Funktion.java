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
import jtoolbox.Ausgabe;
import jtoolbox.Eingabefeld;
import jtoolbox.IContainer;
import jtoolbox.StaticTools;
import modul.DFD_GModul_Funktion;
import modul.DFD__Daten;
import modul.DFD__IG_Modul;

public class DFD_GUI_Funktion extends Zoom_DFD__MausBehaelter implements DFD_I_SENSORAction_Link, DFD_I_GUI_Basismodul {

	DFD_Sensor modulsensor;

	private Zoom_Eingabe fName;
	private Zoom_Eingabe[] aEingabe;
	private Zoom_Eingabe ausgabewert;

	public int anzahlEingaenge = 3;

	DFD_GModul_Funktion dfd_GModul_Funktion = null;

	// Wird für den Konstruktor gesetzt. Sonst nicht verwenden!
	protected static int anzahlEingaengeInit = 1;

	// Klassenfabrik
	public static DFD_GUI_Funktion getDFD_GUI_Funktion(IContainer behaelter, int anzahlEingaenge, int neuesX,
			int neuesY) {
		return getDFD_GUI_Funktion(behaelter, anzahlEingaenge, neuesX, neuesY, false);
	}

	public static DFD_GUI_Funktion getDFD_GUI_Funktion(IContainer behaelter, int anzahlEingaenge, int neuesX,
			int neuesY, boolean ZENTRIERT) {
		DFD__GUIKONST.zoomAnpassen();
		int neueBreite = (anzahlEingaenge == 1) ? 2 * DFD__GUIKONST.FM_HORIZONTAL_RASTER
				: anzahlEingaenge * DFD__GUIKONST.FM_HORIZONTAL_RASTER;
		int neueHoehe = DFD__GUIKONST.FM_VERTIKAL_RASTER * DFD__GUIKONST.FM_ZEILEN_WEIT
				+ 2 * DFD__GUIKONST.FM_KONNECTOR_Y;
		if (ZENTRIERT) {
			neuesX = neuesX - neueBreite / 2;
			neuesY = neuesY - neueHoehe / 2;
		}

		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		anzahlEingaengeInit = anzahlEingaenge;

		DFD_GUI_Funktion ausgabe = new DFD_GUI_Funktion(zoom, behaelter, anzahlEingaenge, neuesX, neuesY, neueBreite,
				neueHoehe);

		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	private DFD_GUI_Funktion(Zoom__FunktionTool zoom, IContainer behaelter, int anzahlEingaenge, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		super(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);

		this.anzahlEingaenge = anzahlEingaenge;
		modulsensor = DFD_Sensor.getDFD_Sensor(behaelter, neuesX, neuesY, neueBreite,
				DFD__GUIKONST.FM_VERTIKAL_RASTER * DFD__GUIKONST.FM_ZEILEN_WEIT + 2 * DFD__GUIKONST.FM_KONNECTOR_Y);
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

		int breite = (anzahlEingaenge == 1) ? 2 * DFD__GUIKONST.FM_HORIZONTAL_RASTER
				: anzahlEingaenge * DFD__GUIKONST.FM_HORIZONTAL_RASTER;

		fName = Zoom_Eingabe.getZoomEingabe(obj, "Name", 0,
				DFD__GUIKONST.FM_VERTIKAL_RASTER + DFD__GUIKONST.FM_KONNECTOR_Y, breite,
				DFD__GUIKONST.FM_VERTIKAL_RASTER);
		fName.setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
		fName.setzeSchriftStilFett();
		fName.setReadonly();
		fName.mitRand();

		ausgabewert = Zoom_Eingabe.getZoomEingabe(obj, "AUS", 0,
				DFD__GUIKONST.FM_VERTIKAL_RASTER * 2 + DFD__GUIKONST.FM_KONNECTOR_Y, breite,
				DFD__GUIKONST.FM_VERTIKAL_RASTER);
		ausgabewert.setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
		ausgabewert.mitRand();
		ausgabewert.setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAusgabe);
		ausgabewert.setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAusgabe);
		ausgabewert.setzeAusrichtung(Eingabefeld.ZENTRIERT);
		ausgabewert.setzeSchriftStilFett();

		// Für Eingänge
		breite = (anzahlEingaenge == 1) ? 2 * DFD__GUIKONST.FM_HORIZONTAL_RASTER : DFD__GUIKONST.FM_HORIZONTAL_RASTER;

		if (anzahlEingaenge > 0) {
			aEingabe = new Zoom_Eingabe[anzahlEingaenge];
			char anzeige = 'A';
			for (int i = 0; i < anzahlEingaenge; i++) {
				aEingabe[i] = Zoom_Eingabe.getZoomEingabe(obj, "" + anzeige, i * DFD__GUIKONST.FM_HORIZONTAL_RASTER,
						DFD__GUIKONST.FM_KONNECTOR_Y, breite, DFD__GUIKONST.FM_VERTIKAL_RASTER);
				aEingabe[i].setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
				aEingabe[i].setReadonly();
				aEingabe[i].mitRand();
				anzeige++;
			}
		}
	}

	public void cObjectSetzen() {
		obj = new DFD_CGUI_Funktion(anzahlEingaengeInit);
	}

	public void recall(DFD_GModul_Funktion dfd_Funktion) {
		this.dfd_GModul_Funktion = dfd_Funktion;
		modulsensor.aktivierePopup();
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

			if (dfd_GModul_Funktion != null) {
				dfd_GModul_Funktion.verschiebeEingangsUndAusgangsPfeile();
			}
		} else {

		}
	}

	@Override
	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor) {
		dfd_GModul_Funktion.andereModuleDeaktivieren();
	}

	@Override
	public void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor) {
	}

	@Override
	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks) {
		dfd_GModul_Funktion.setzeAusgewaehlt();
		((DFD_CGUI_Funktion) obj).setzeAusgewaehlt(true);
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		if (!modulsensor.isSichtbar()) {
			modulsensor.sichtbarMachen();
		}
		((DFD_CGUI_Funktion) obj).setzeAusgewaehlt(false);
	}

	public Point getPointEingangPixel(int nr) {
		return ((DFD_CGUI_Funktion) obj).getPointEingangPixel(nr);
	}

	public Point getPointAusgangPixel(int nr) {
		return ((DFD_CGUI_Funktion) obj).getPointAusgangPixel(nr);
	}

	public Area getAusgangArea() {
		return ((DFD_CGUI_Funktion) obj).getAusgangArea();
	}

	public void setzeAusgaBezeichnung(DFD__Daten daten) {
		String tooltiptext = DialogZoom.getTootipZoom(daten.getToolstring());
		setzeAusgangsbezeichnung(daten.getDatenString(DFD__GUIKONST.dfd_ausgabeStellen), daten.istError());
		modulsensor.setzeTooltip(tooltiptext);
	}

	public void setzeEingangsbezeichnung(int nr, String text, String farbe) {
		aEingabe[nr].setzeAusgabetext(text);
		aEingabe[nr].setzeAusrichtung(Ausgabe.ZENTRIERT);
		aEingabe[nr].setzeSchriftfarbe(farbe);
	}

	public void setzeModulbezeichnung(String text) {
		fName.setzeAusgabetext(text);
		fName.setzeAusrichtung(Ausgabe.ZENTRIERT);
	}

	public String leseModulbezeichnung() {
		return fName.leseText();
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

	public void setzeModulPosition(int posX, int posY) {
		DFD_setzePosition(posX, posY);
	}

	@Override
	public Rectangle leseRechteck() {
		return zoom.leseDimensionOhneZoom();
	}

	@Override
	public DFD__IG_Modul getGmodul() {
		return dfd_GModul_Funktion;
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		Rectangle dimGuiFunktion = leseDimensionOhneZoom();
		((DFD_CGUI_Funktion) obj).bildZeichnen(g2, dimDFD, dimGuiFunktion);
		komponentenZeichnen(g2, dimDFD, dimGuiFunktion);
	}

	private void komponentenZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimGuiEingabe) {

		fName.bildZeichnen(g2, dimDFD, dimGuiEingabe);
		ausgabewert.bildZeichnen(g2, dimDFD, dimGuiEingabe);

		if (anzahlEingaenge > 0) {
			for (int i = 0; i < anzahlEingaenge; i++) {
				aEingabe[i].bildZeichnen(g2, dimDFD, dimGuiEingabe);
			}
		}
	}

}

@SuppressWarnings("serial")
class DFD_CGUI_Funktion extends DFD__CMausBehaelter {

	int anzahlEingaenge = 1;

	Shape sRechteck;

	public Area getAusgangArea() {
		while (sRechteck == null) {
			StaticTools.warte(5);
		}
		Area move = new Area(sRechteck);
		move.transform(AffineTransform.getTranslateInstance(xPos, yPos));
		return move;
	}

	public DFD_CGUI_Funktion(int anzahlEingaenge) {
		this.anzahlEingaenge = anzahlEingaenge;
	}

	public Point getPointAusgangPixel(int nr) {
		return new Point(xPos + breite / 2, yPos + hoehe - DFD__GUIKONST.fm_KONNECTOR_Y / 2 - 1);
	}

	public Point getPointEingangPixel(int i) {
		if (anzahlEingaenge == 1) {
			return new Point(xPos + breite / 2, yPos + DFD__GUIKONST.fm_KONNECTOR_Y / 2);
		} else {
			return new Point(xPos + i * DFD__GUIKONST.fm_Horizontal_RASTER + (DFD__GUIKONST.fm_Horizontal_RASTER) / 2,
					yPos + DFD__GUIKONST.fm_KONNECTOR_Y / 2);
		}
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

		// Eingaenge
		g.setColor(DFD__GUIKONST.paintEingang);
		if (anzahlEingaenge == 1) {
			g2.fillRect((breite - DFD__GUIKONST.fm_KONNEKTOR_X) / 2, 0, DFD__GUIKONST.fm_KONNEKTOR_X,
					DFD__GUIKONST.fm_KONNECTOR_Y);

		} else {
			for (int i = 0; i < anzahlEingaenge; i++) {
				g2.fillRect(
						i * DFD__GUIKONST.fm_Horizontal_RASTER
								+ (DFD__GUIKONST.fm_Horizontal_RASTER - DFD__GUIKONST.fm_KONNEKTOR_X) / 2,
						0, DFD__GUIKONST.fm_KONNEKTOR_X, DFD__GUIKONST.fm_KONNECTOR_Y);
			}
		}

		// Ausgang
		g.setColor(DFD__GUIKONST.paintAusgang);

		sRechteck = new Rectangle2D.Float((breite - DFD__GUIKONST.fm_KONNEKTOR_X) / 2,
				hoehe - DFD__GUIKONST.fm_KONNECTOR_Y - 1, DFD__GUIKONST.fm_KONNEKTOR_X, DFD__GUIKONST.fm_KONNECTOR_Y);
		g2.fillRect((breite - DFD__GUIKONST.fm_KONNEKTOR_X) / 2, hoehe - DFD__GUIKONST.fm_KONNECTOR_Y - 1,
				DFD__GUIKONST.fm_KONNEKTOR_X, DFD__GUIKONST.fm_KONNECTOR_Y);

		// Umriss
		g.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawRoundRect(0, DFD__GUIKONST.fm_KONNECTOR_Y, breite - 1, hoehe - DFD__GUIKONST.fm_KONNECTOR_Y * 2 - 1,
				DFD__GUIKONST.fm_RECHTECK_RADIUS, DFD__GUIKONST.fm_RECHTECK_RADIUS);

	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimGuiFunktion) {
		Rectangle dim = new Rectangle(dimGuiFunktion);

		dim.x -= dimDFD.x;
		dim.y -= dimDFD.y;

		// Hintergrund
		g2.setColor(DFD__GUIKONST.paintHintergrund);
		g2.fillRoundRect(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drF(dim.width - 1), DFD_Drucken.drF(DFD__GUIKONST.FM_VERTIKAL_RASTER * 3),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));
		// Eingaenge
		g2.setColor(DFD__GUIKONST.paintEingang);
		if (anzahlEingaenge == 1) {
			g2.fillRect(DFD_Drucken.drX(dim.x + (dim.width - DFD__GUIKONST.FM_KONNEKTOR_X) / 2), DFD_Drucken.drY(dim.y),
					DFD_Drucken.drF(DFD__GUIKONST.FM_KONNEKTOR_X), DFD_Drucken.drF(DFD__GUIKONST.FM_KONNECTOR_Y));

		} else {
			for (int i = 0; i < anzahlEingaenge; i++) {
				g2.setColor(DFD__GUIKONST.paintEingang);
				g2.fillRect(
						DFD_Drucken.drX(dim.x + i * DFD__GUIKONST.FM_HORIZONTAL_RASTER
								+ (DFD__GUIKONST.FM_HORIZONTAL_RASTER - DFD__GUIKONST.FM_KONNEKTOR_X) / 2),
						DFD_Drucken.drY(dim.y), DFD_Drucken.drF(DFD__GUIKONST.FM_KONNEKTOR_X),
						DFD_Drucken.drF(DFD__GUIKONST.FM_KONNECTOR_Y));

				if (i != 0) {
					g2.setColor(DFD__GUIKONST.paintVordergrund);
					g2.drawLine(DFD_Drucken.drX(dim.x + i * DFD__GUIKONST.FM_HORIZONTAL_RASTER),
							DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_KONNECTOR_Y),
							DFD_Drucken.drX(dim.x + i * DFD__GUIKONST.FM_HORIZONTAL_RASTER),
							DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_KONNECTOR_Y + DFD__GUIKONST.FM_VERTIKAL_RASTER));
				}
			}
		}

		// Ausgang
		g2.setColor(DFD__GUIKONST.paintAusgang);
		g2.fillRect(DFD_Drucken.drX(dim.x + (dim.width - DFD__GUIKONST.FM_KONNEKTOR_X) / 2),
				DFD_Drucken.drY(dim.y + dim.height - DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drF(DFD__GUIKONST.FM_KONNEKTOR_X), DFD_Drucken.drF(DFD__GUIKONST.FM_KONNECTOR_Y));

		// Umriss
		g2.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawRoundRect(DFD_Drucken.drX(dim.x), DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drF(dim.width - 1), DFD_Drucken.drF(DFD__GUIKONST.FM_VERTIKAL_RASTER * 3),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));

		g2.drawLine(DFD_Drucken.drX(dim.x),
				DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_VERTIKAL_RASTER + DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drX(dim.x) + DFD_Drucken.drF(dim.width - 2),
				DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_VERTIKAL_RASTER + DFD__GUIKONST.FM_KONNECTOR_Y));
		g2.drawLine(DFD_Drucken.drX(dim.x),
				DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_VERTIKAL_RASTER * 2 + DFD__GUIKONST.FM_KONNECTOR_Y),
				DFD_Drucken.drX(dim.x) + DFD_Drucken.drF(dim.width - 2),
				DFD_Drucken.drY(dim.y + DFD__GUIKONST.FM_VERTIKAL_RASTER * 2 + DFD__GUIKONST.FM_KONNECTOR_Y));

	}

}
