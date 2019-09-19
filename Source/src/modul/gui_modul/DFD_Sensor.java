package modul.gui_modul;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import dfd_gui.zoom.DFD__CMausBehaelter;
import dfd_gui.zoom.DFD__MausBehaelter;
import dfd_gui.zoom.Zoom_DFD__MausBehaelter;
import dfd_gui.zoom.Zoom__FunktionTool;
import dfd_gui.zoom.Zoom__Verwalter;
import global.DFD__GUIKONST;
import global.StartUmgebung;
import jtoolbox.IContainer;
import jtoolbox.ITuWas;
import jtoolbox.StaticTools;

/**
 * Default-Sensor.
 * 
 * reagiert auf Bewegen und Doppelklick
 * 
 * @author Witt
 *
 */

public class DFD_Sensor extends Zoom_DFD__MausBehaelter implements ITuWas {

	public static final DFD_Sensor KEIN_SENSOR = null;

	public static final DFD_I_SENSORAction_Link ZIEL_SENSOR_NULL = null;

	protected int sensorID;

	PopUpGUIModul popup = null;

	DFD_I_SENSORAction_Link sensorlink = DFD_I_SENSORAction_Link.NULLLINKSENSOR;

	public static DFD_Sensor getDFD_Sensor(IContainer behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_Sensor ausgabe = new DFD_Sensor(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		return ausgabe;
	}

	protected DFD_Sensor(Zoom__FunktionTool zoom, IContainer behaelter, int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		super(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		starteinstellungen();
	}
	
	public void starteinstellungen(){
		// Press-Release-Dragg aktiviert
		setzeMausereignisse((1 << DFD__MausBehaelter.PRESS) | (1 << DFD__MausBehaelter.RELEASE)
				| (1 << DFD__MausBehaelter.DRAGGED) | (1 << DFD__MausBehaelter.CLICK));
		unsichtbarMachen();

		setzeLink(this);
		
	}

	@Override
	public void cObjectSetzen() {
		obj = new DFD_CSensor();
	}

	// Startposition der Komponente beim Druecken der Taste
	int mPposx = 0;
	int mPposy = 0;
	boolean draggEnabled = false;

	Cursor defaultcursor;

	@Override
	public void tuWas(int ID) {
		if (ID == (DFD__MausBehaelter.PRESS)) {
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
		} else if (ID == (DFD__MausBehaelter.CLICK)) {
			// Aktion beenden
			draggEnabled = false;
			if (sensorlink != ZIEL_SENSOR_NULL) {
				sensorlink.sensorAktionClick(xPos, yPos, sensorID, this, getClickCount());
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

	public void setzeSensorID(int ID) {
		sensorID = ID;
	}

	public void setzeSensorLink(DFD_I_SENSORAction_Link slink) {
		sensorlink = slink;
	}

	public void setzeTooltip(String toolstring) {
		// TODO Sollte nicht vorkommen
		if (toolstring == null)
			toolstring = "";
		if (obj != null)
			obj.setToolTipText(toolstring);
	}

	public void aktivierePopup() {
		popup = new PopUpGUIModul(this, sensorlink);
	}

	@Override
	public void modulLoeschen() {
		Zoom__Verwalter.getZoomverwalter().removeZoomkomponente(this);
		entfernen();
	}

	public void setzeMitHintergrund(boolean mitHintergrund) {
		((DFD_CSensor) obj).setzeMitHintergrund(mitHintergrund);
	}

}

@SuppressWarnings("serial")
class DFD_CSensor extends DFD__CMausBehaelter {

	boolean mitHintergrund = false;

	public void setzeMitHintergrund(boolean mitHintergrund) {
		this.mitHintergrund = mitHintergrund;
	}

	public void paintComponentSpezial(Graphics g) {
		super.paintComponentSpezial(g);
		Graphics2D g2 = (Graphics2D) g;
		// Graphik-Abmessungen

		if (StartUmgebung.DEBUG) {
			breite = getSize().width;
			hoehe = getSize().height;
			g2.setColor(Color.DARK_GRAY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

			g2.drawOval(1, 1, breite - 3, hoehe - 3);
		}

		if (mitHintergrund) {

			Color hintergrund = StaticTools.getColor(DFD__GUIKONST.Auswahlfarbe);

			hintergrund = new Color(hintergrund.getRed() * 1.0F / 255, hintergrund.getGreen() * 1.0F / 255,
					hintergrund.getBlue() * 1.0F / 255, 0.2F);
			g2.setColor(hintergrund);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.fillRect(1, 1, breite - 3, hoehe - 3);
			hintergrund = StaticTools.getColor(DFD__GUIKONST.Auswahlfarbe);
			g2.setColor(hintergrund);
			g2.drawRect(3, 3, breite - 6, hoehe - 6);

		}

	}
}
