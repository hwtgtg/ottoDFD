package modul.gui_modul;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import dfd_gui.DFD_Arbeitsfenster;
import dfd_gui.zoom.DFD__CMausBehaelter;
import dfd_gui.zoom.DFD__MausBehaelter;
import dfd_gui.zoom.Zoom__FunktionTool;
import global.DFD__GUIKONST;
import global.StartUmgebung;
import jtoolbox.StaticTools;

public class DFD_Sensor_Pfeil extends DFD_Sensor {

	public static final DFD_Sensor NULLSENSOR = null;

	public static DFD_Sensor_Pfeil getDFD_Sensor_Pfeil(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_Sensor_Pfeil ausgabe = new DFD_Sensor_Pfeil(zoom, neuesX, neuesY, neueBreite, neueHoehe);
		return ausgabe;
	}

	protected DFD_Sensor_Pfeil(Zoom__FunktionTool zoom, int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		super(zoom, DFD_Arbeitsfenster.getArbeitsfenster().sensorLayer, neuesX, neuesY, neueBreite, neueHoehe);
	}
	
	
	@Override
	public void starteinstellungen(){
		// Press-Release-Dragg aktiviert
		// Press-Release-Dragg aktiviert
		setzeMausereignisse((1 << DFD__MausBehaelter.PRESS) | (1 << DFD__MausBehaelter.RELEASE)
				| (1 << DFD__MausBehaelter.DRAGGED) 
//				| (1 << DFD__MausBehaelter.CLICK)
				);
		unsichtbarMachen();
		setzeLink(this);
		
	}

	

	/**
	 * Mittelpunkt Sensor
	 * 
	 * @param neuesMX
	 * @param neuesMY
	 */
	public void setzeMittelpunkt(int neuesMX, int neuesMY) {
		xPos = neuesMX - breite / 2;
		yPos = neuesMY - hoehe / 2;
		setzePosition(xPos, yPos);
	}

	public Point mittelpunkt() {
		return new Point(xPos + breite / 2, yPos + hoehe / 2);
	}

	public void cObjectSetzen() {
		obj = new DFD_CSensor_Pfeil();
	}

	// Startposition der Komponente beim Druecken der Taste
	int mPposx = 0;
	int mPposy = 0;
	boolean draggEnabled = false;

	public void tuWas(int ID) {
		if (ID == (DFD__MausBehaelter.PRESS)) {
			mPposx = getMX();
			mPposy = getMY();
			// Unterscheiden auf Verschiebe-Rechteck, Eingang oder Ausgang
			draggEnabled = true;
			if (sensorlink != NULLSENSOR) {
				sensorlink.sensorAktionPRESS(xPos + breite / 2, yPos + hoehe / 2, sensorID, this);
			}
		} else if (ID == (DFD__MausBehaelter.CLICK)) {
			// Aktion beenden
			draggEnabled = false;
			if (sensorlink != NULLSENSOR) {
				sensorlink.sensorAktionClick(xPos + breite / 2, yPos + hoehe / 2, sensorID, this, getClickCount());
			}
		} else if (ID == (DFD__MausBehaelter.RELEASE)) {
			// Aktion beenden
			draggEnabled = false;
			if (sensorlink != NULLSENSOR) {
				sensorlink.sensorAktionRELEASE(xPos + breite / 2, yPos + hoehe / 2, sensorID, this);
			}
		} else if (ID == (DFD__MausBehaelter.DRAGGED)) {
			setzePosition(getXPos() + getMX() - mPposx, getYPos() + getMY() - mPposy);
			if (sensorlink != NULLSENSOR) {
				sensorlink.sensorAktionDRAGG(getXPos() + breite / 2, getYPos() + hoehe / 2, sensorID, this);
			}
		}
	}

	public void setzeSensorID(int ID) {
		sensorID = ID;
	}

	public void setzeSensorLink(DFD_I_SENSORAction_Link slink) {
		sensorlink = slink;
	}

	public void treffer() {
		((DFD_CSensor_Pfeil) obj).b_treffer = true;
		obj.repaint();
	}

	public void normal() {
		((DFD_CSensor_Pfeil) obj).b_treffer = false;
		obj.repaint();
	}

	public Area getAerea() {
		return ((DFD_CSensor_Pfeil) obj).getArea();
	}

}

@SuppressWarnings("serial")
class DFD_CSensor_Pfeil extends DFD__CMausBehaelter {

	boolean b_treffer = false;

	Shape sRechteck;

	public Area getArea() {
		while (sRechteck == null) {
			StaticTools.warte(5);
		}
		Area move = new Area(sRechteck);
		move.transform(AffineTransform.getTranslateInstance(xPos, yPos));
		return move;
	}
	
	public void paintComponentSpezial(Graphics g) {
		super.paintComponentSpezial(g);
		Graphics2D g2 = (Graphics2D) g;
		// Graphik-Abmessungen
		breite = getSize().width;
		hoehe = getSize().height;
		
		sRechteck = new Rectangle2D.Float(0, 0, breite, hoehe);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

		if (b_treffer) {
			g2.setColor(DFD__GUIKONST.PFEILSENSOR_TREFFER);
			g2.fillOval(1, 1, breite - 3, hoehe - 3);
		} else {
			g2.setColor(DFD__GUIKONST.PFEILSENSOR_NORMAL);
			g2.fillOval(1, 1, breite - 3, hoehe - 3);
		}
	}
}
