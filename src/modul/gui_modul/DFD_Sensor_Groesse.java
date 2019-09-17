package modul.gui_modul;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import dfd_gui.DFD_Arbeitsfenster;
import dfd_gui.zoom.DFD__CMausBehaelter;
import dfd_gui.zoom.DFD__MausBehaelter;
import dfd_gui.zoom.Zoom__FunktionTool;
import global.StartUmgebung;

public class DFD_Sensor_Groesse extends DFD_Sensor {

	public static DFD_Sensor_Groesse getDFD_Sensor_Groesse(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_Sensor_Groesse ausgabe = new DFD_Sensor_Groesse(zoom, neuesX, neuesY, neueBreite, neueHoehe);
		return ausgabe;
	}

	protected DFD_Sensor_Groesse(Zoom__FunktionTool zoom, int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		super(zoom, DFD_Arbeitsfenster.getArbeitsfenster().sensorLayer, neuesX, neuesY, neueBreite, neueHoehe);
	}

	@Override
	public void starteinstellungen() {
		// Press-Release-Dragg aktiviert
		// Press-Release-Dragg aktiviert
		setzeMausereignisse((1 << DFD__MausBehaelter.PRESS) | (1 << DFD__MausBehaelter.RELEASE)
				| (1 << DFD__MausBehaelter.DRAGGED) | (1 << DFD__MausBehaelter.ENTER) | (1 << DFD__MausBehaelter.EXIT)

		);
		unsichtbarMachen();
		setzeLink(this);

	}

	Cursor defaultcursor;

	@Override
	public void tuWas(int ID) {
		if (ID == (DFD__MausBehaelter.ENTER)) {
			defaultcursor = getBasisComponente().getCursor();
			Cursor c = new Cursor(Cursor.SE_RESIZE_CURSOR);
			getBasisComponente().setCursor(c);
		} else if (ID == (DFD__MausBehaelter.EXIT)) {
			getBasisComponente().setCursor(defaultcursor);
		} else if (ID == (DFD__MausBehaelter.PRESS)) {
			mPposx = getMX();
			mPposy = getMY();

			defaultcursor = getBasisComponente().getCursor();
			// Cursor c = new Cursor(Cursor.SE_RESIZE_CURSOR);
			Cursor c = new Cursor(Cursor.MOVE_CURSOR);
			getBasisComponente().setCursor(c);

			// Unterscheiden auf Verschiebe-Rechteck, Eingang oder Ausgang
			draggEnabled = true;
			if (sensorlink != ZIEL_SENSOR_NULL) {
				sensorlink.sensorAktionPRESS(xPos, yPos, sensorID, this);
			}
		} else if (ID == (DFD__MausBehaelter.RELEASE)) {
			// Aktion beenden
			getBasisComponente().setCursor(defaultcursor);
			draggEnabled = false;
			if (sensorlink != ZIEL_SENSOR_NULL) {
				sensorlink.sensorAktionRELEASE(xPos, yPos, sensorID, this);
			}
		} else if (ID == (DFD__MausBehaelter.DRAGGED)) {
			setzePosition(getXPos() + getMX() - mPposx, getYPos() + getMY() - mPposy);
			if (sensorlink != ZIEL_SENSOR_NULL) {
				sensorlink.sensorAktionDRAGG(getXPos(), getYPos(), sensorID, this);
			}
		}
	}

	@Override
	public void cObjectSetzen() {
		obj = new DFD_CSensor_Groesse();
	}

}

@SuppressWarnings("serial")
class DFD_CSensor_Groesse extends DFD__CMausBehaelter {

	public void paintComponentSpezial(Graphics g) {
		super.paintComponentSpezial(g);
		Graphics2D g2 = (Graphics2D) g;
		// Graphik-Abmessungen

		breite = getSize().width;
		hoehe = getSize().height;

		// if (StartUmgebung.DEBUG) {
		//
		// g2.setColor(Color.RED);
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		//
		// g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,
		// BasicStroke.JOIN_MITER));
		//
		// g2.drawOval(1, 1, breite - 3, hoehe - 3);
		// }

		g2.setColor(Color.BLACK);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

		g2.drawLine(breite - 3, 3, breite - 3, hoehe - 1);
		g2.drawLine(breite - 2, 2, breite - 2, hoehe - 1);
		g2.drawLine(breite - 1, 1, breite - 1, hoehe - 1);
		g2.drawLine(3, hoehe - 3, breite - 1, hoehe - 3);
		g2.drawLine(2, hoehe - 2, breite - 1, hoehe - 2);
		g2.drawLine(1, hoehe - 1, breite - 1, hoehe - 1);

	}
}
