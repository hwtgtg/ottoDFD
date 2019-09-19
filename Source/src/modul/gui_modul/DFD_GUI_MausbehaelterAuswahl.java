package modul.gui_modul;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import dfd_gui.zoom.DFD__CMausBehaelter;
import dfd_gui.zoom.DialogZoom;
import dfd_gui.zoom.Zoom_DFD__MausBehaelter;
import dfd_gui.zoom.Zoom__FunktionTool;
import dfd_gui.zoom.Zoom__Verwalter;
import global.CHANGE;
import global.DFD__GUIKONST;
import global.StartUmgebung;
import jtoolbox.IContainer;
import modul.DFD_GModul_Funktion;
import modul.DFD__IG_Modul;

public class DFD_GUI_MausbehaelterAuswahl extends Zoom_DFD__MausBehaelter
		implements DFD_I_SENSORAction_Link, DFD_I_GUI_Basismodul {

	DFD_Sensor modulsensor;
	DFD_Sensor_Groesse groessesensor;

//	private Zoom_Eingabe fName;
//	private Zoom_Eingabe[] aEingabe;
//	private Zoom_Eingabe ausgabewert;

	// DFD_GModul_Funktion dfd_GModul_Funktion = null;

	// Klassenfabrik
	public static DFD_GUI_MausbehaelterAuswahl getDFD_GUI_MausbehaelterAuswahl(IContainer behaelter,
			int anzahlEingaenge, int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		return getDFD_GUI_MausbehaelterAuswahl(behaelter, neuesX, neuesY, neueBreite, neueHoehe, false);
	}

	public static DFD_GUI_MausbehaelterAuswahl getDFD_GUI_MausbehaelterAuswahl(IContainer behaelter, int neuesX,
			int neuesY, int neueBreite, int neueHoehe, boolean ZENTRIERT) {
		DFD__GUIKONST.zoomAnpassen();
		if (ZENTRIERT) {
			neuesX = neuesX - neueBreite / 2;
			neuesY = neuesY - neueHoehe / 2;
		}

		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_GUI_MausbehaelterAuswahl ausgabe = new DFD_GUI_MausbehaelterAuswahl(zoom, behaelter, neuesX, neuesY,
				neueBreite, neueHoehe);

		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	private DFD_GUI_MausbehaelterAuswahl(Zoom__FunktionTool zoom, IContainer behaelter, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		super(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);

		modulsensor = DFD_Sensor.getDFD_Sensor(behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		modulsensor.setzeSensorLink(this);
		modulsensor.sichtbarMachen();

		groessesensor = DFD_Sensor_Groesse.getDFD_Sensor_Groesse(
				neuesX + neueBreite - DFD__GUIKONST.SENSOR_GroesseAendern,
				neuesY + neueHoehe - DFD__GUIKONST.SENSOR_GroesseAendern, DFD__GUIKONST.SENSOR_GroesseAendern,
				DFD__GUIKONST.SENSOR_GroesseAendern);
		groessesensor.sichtbarMachen();
		groessesensor.setzeSensorLink(this);

		installiereAnzeigen();

	}

	public void installiereAnzeigen() {
		/*
		 * Jeder Eingang erhält ein Ausgabefeld In der Mitte ist das Namensfeld
		 * Unten ist das Ausgabefeld Klicken auf das Namensfeld schaltet Ein und
		 * Ausgänge um zwischen Eingangsbezeichnung/Formel und
		 * Eingangswert/Ausgangswert
		 */

	}

	public void cObjectSetzen() {
		obj = new DFD_CGUI_MausbehaelterAuswahl();
	}

	public void recall(DFD_GModul_Funktion dfd_Funktion) {
		// this.dfd_GModul_Funktion = dfd_Funktion;
		modulsensor.aktivierePopup();
	}

	@Override
	public void modulLoeschen() {
		modulsensor.modulLoeschen();
		groessesensor.modulLoeschen();
		Zoom__Verwalter.getZoomverwalter().removeZoomkomponente(this);
		super.entfernen();
	}

	@Override
	public void sensorAktionDRAGG(int x, int y, int ID, DFD_Sensor sensor) {
		CHANGE.setChanged();
		if (sensor == modulsensor) {
			this.setzePosition(x, y);

		} else if (sensor == groessesensor) {
			int gr = DialogZoom.intZoomWert(DFD__GUIKONST.SENSOR_GroesseAendern);
			int neueBreite = x - this.xPos + gr;
			if (neueBreite <= DialogZoom.intZoomWert(DFD__GUIKONST.BeschreibungText_Minimum)) {
				neueBreite = DialogZoom.intZoomWert(DFD__GUIKONST.BeschreibungText_Minimum);
			}
			int neueHoehe = y - this.yPos + gr;
			if (neueHoehe <= DialogZoom.intZoomWert(DFD__GUIKONST.BeschreibungText_Minimum)) {
				neueHoehe = DialogZoom.intZoomWert(DFD__GUIKONST.BeschreibungText_Minimum);
			}
			this.setzeGroesse(neueBreite, neueHoehe);
			modulsensor.setzeGroesse(neueBreite, neueHoehe);
		} else {

		}
	}

	@Override
	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor) {
	}

	@Override
	public void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor) {
		if (sensor == modulsensor) {
			int gr = DialogZoom.intZoomWert(DFD__GUIKONST.SENSOR_GroesseAendern);
			groessesensor.setzeDimensionen(x + breite - gr, y + hoehe - gr, gr, gr);
		}
	}

	@Override
	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks) {

	}

	@Override
	public void setzeNichtAusgewaehlt() {
		if (!modulsensor.isSichtbar()) {
			modulsensor.sichtbarMachen();
		}
		// obj.setzeAusgewaehlt(false);
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
		return null;
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		((DFD_CGUI_MausbehaelterAuswahl) obj).bildZeichnen(g2, dimDFD, leseDimensionOhneZoom());
	}

}

@SuppressWarnings("serial")
class DFD_CGUI_MausbehaelterAuswahl extends DFD__CMausBehaelter {

	public DFD_CGUI_MausbehaelterAuswahl() {
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
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

		// Umriss
		g.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawRoundRect(0, DFD__GUIKONST.fm_KONNECTOR_Y, breite - 1, hoehe - DFD__GUIKONST.fm_KONNECTOR_Y * 2 - 1,
				DFD__GUIKONST.fm_RECHTECK_RADIUS, DFD__GUIKONST.fm_RECHTECK_RADIUS);

		if (ausgewaehlt) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

			g.setColor(Color.GRAY);

			g2.drawRect(0, 0, breite - 1, hoehe - 1);

		}

	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle leseDimensionOhneZoom) {
//		, float druckbildFaktor
		// TODO Auto-generated method stub

	}

}
