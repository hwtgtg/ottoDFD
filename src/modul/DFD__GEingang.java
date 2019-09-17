package modul;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import dfd_gui.zoom.DialogZoom;
import dfd_gui.zoom.Zoom_PfeilKurve;
import global.CHANGE;
import global.DFD__GUIKONST;
import modul.gui_modul.DFD_I_SENSORAction_Link;
import modul.gui_modul.DFD_Sensor;
import modul.gui_modul.DFD_Sensor_Pfeil;

public class DFD__GEingang implements DFD_I_SENSORAction_Link {

	public static boolean verschiebenDurchSensor = true;
	public static boolean keinVerschiebenDurchSensor = false;

	DFD__IG_Modul eigenesGModul = null;
	DFD__GAusgang fremderGAusgang = null;

	DFD_Sensor_Pfeil pfeilsensor = null;

	Zoom_PfeilKurve zoomPfeilkurve;

	int eingangsnummer;

	int startPX;
	int startPY;
	int zielPX;
	int zielPY;

	boolean erstesSetzen = true;

	public DFD__GEingang(DFD__IG_Modul dfd_modul, int nr) {
		eigenesGModul = dfd_modul;
		this.eingangsnummer = nr;
		float zoomfaktorInvers = 1.0F / DialogZoom.getBfZoomfaktor() ;

		zoomPfeilkurve = Zoom_PfeilKurve.getZoom_PfeilKurve(dfd_modul.getBehaelter().pfeilLayer, 0, 0, 1, 1,
				(int) (DFD__GUIKONST.pf_Breite * zoomfaktorInvers), DFD__GUIKONST.PF_FREI);

		pfeilsensor = DFD_Sensor_Pfeil.getDFD_Sensor_Pfeil(0, 0,
				(int) ((DFD__GUIKONST.pfeilsensor_Durchmesser * 2) * zoomfaktorInvers),
				(int) ((DFD__GUIKONST.pfeilsensor_Durchmesser * 2) * zoomfaktorInvers));
		// pfeilsensor.unsichtbarMachen();
		pfeilsensor.setzeSensorLink(this);

		verschiebePfeil(keinVerschiebenDurchSensor);

	}

	public void modulLoeschen() {
		// Wie bei Lösen von fremdem Ausgang
		if (fremderGAusgang != null) {
			fremderGAusgang.loeseEingang(this);
			eigenesGModul.loese_GModulEingangVomFremdenGModulAusgang(eingangsnummer,
					fremderGAusgang.getEigenesGModul());
			fremderGAusgang = null;
			zoomPfeilkurve.setzeFarbe(DFD__GUIKONST.PF_FREI);
			verschiebePfeil(keinVerschiebenDurchSensor);
		}

		pfeilsensor.modulLoeschen();
		zoomPfeilkurve.modulLoeschen();
	}

	public void verschiebePfeil(boolean durchSensor) {
		Point ziel = eigenesGModul.getPointEingangPixel(eingangsnummer);
		zielPX = ziel.x;
		zielPY = ziel.y;
		if (erstesSetzen) {
			erstesSetzen = false;
			startPX = ziel.x;
			startPY = (int) ((ziel.y - DialogZoom.getBfZoomfaktor() * DFD__GUIKONST.pf_LaengeAnfang));
		} else if (!durchSensor && (fremderGAusgang != null)) {
			Point start = fremderGAusgang.getPointAusgangPixel(0);
			startPX = start.x;
			startPY = start.y;
		} else {
			Point start = zoomPfeilkurve.getStartpunkt();
			Point zielAlt = zoomPfeilkurve.getZielpunkt();

			startPX = start.x + zielPX - zielAlt.x;
			startPY = start.y + zielPY - zielAlt.y;
		}

		zoomPfeilkurve.setzeEndpunkte(startPX, startPY, zielPX, zielPY);
		{
			pfeilsensor.setzeMittelpunkt(startPX, startPY);
		}

	}

	public void setzeAusgewaehlt() {
		pfeilsensor.normal();
		pfeilsensor.sichtbarMachen();
		// pfeilsensor.Sensitiv();
	}

	public void setzeNichtAusgewaehlt() {
		pfeilsensor.unsichtbarMachen();
	}

	@Override
	public void sensorAktionDRAGG(int x, int y, int ID, DFD_Sensor sensor) {

		zoomPfeilkurve.setzeStartpunkt(x, y);
		verschiebePfeil(verschiebenDurchSensor);

		if (DFD__GModulverwalter.getModulverwalter().testeMitAusgaengen(eigenesGModul,
				pfeilsensor.getAerea()) != null) {
			pfeilsensor.treffer();
		} else {
			pfeilsensor.normal();
		}

	}

	@Override
	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor) {
		zoomPfeilkurve.setzeStartpunkt(x, y);
		verschiebePfeil(verschiebenDurchSensor);

		DFD__IG_Modul ausgangGModul = DFD__GModulverwalter.getModulverwalter().testeMitAusgaengen(eigenesGModul,
				pfeilsensor.getAerea());
		if (fremderGAusgang == ausgangGModul) {
			// Vorher = nachher. Nichts zu tun
			return;
		}
		
		CHANGE.setChanged();
		
		// Wenn bisher verbunden, Löse bisheriges modul
		loeseVomBisherigenAusgangsModulWennVerbunden();

		if (ausgangGModul != null) {
			verbindeMitneuemAusgangsGModul(ausgangGModul);
		}
	}

	public void loeseVomBisherigenAusgangsModulWennVerbunden() {
		if (fremderGAusgang != null) {
			fremderGAusgang.loeseEingang(this);
			eigenesGModul.loese_GModulEingangVomFremdenGModulAusgang(eingangsnummer,
					fremderGAusgang.getEigenesGModul());
			fremderGAusgang = null;
			zoomPfeilkurve.setzeFarbe(DFD__GUIKONST.PF_FREI);
			verschiebePfeil(keinVerschiebenDurchSensor);
		}
	}

	public void verbindeMitneuemAusgangsGModul(DFD__IG_Modul ausgangGModul) {
		fremderGAusgang = ausgangGModul.getAusgang();
		zoomPfeilkurve.setzeFarbe(DFD__GUIKONST.PF_VERBUNDEN);
		eigenesGModul.verbinde_GModulEingangMitFremdemGModulAusgang(eingangsnummer, ausgangGModul);
		verschiebePfeil(keinVerschiebenDurchSensor);
	}

	@Override
	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks) {
		// TODO Auto-generated method stub

	}

	public synchronized void loeseVonAusgang() {
		// Wird vom fremden Ausgang beim Ersetzen durch einen anderen Eingang
		// aufgerufen
		if (fremderGAusgang == null) {
			return;
		}

		eigenesGModul.loese_GModulEingangVomFremdenGModulAusgang(eingangsnummer, fremderGAusgang.getEigenesGModul());

		zoomPfeilkurve.setzeFarbe(DFD__GUIKONST.PF_FREI);

		Point start = fremderGAusgang.getPointAusgangPixel(0);

		startPX = start.x + DFD__GUIKONST.pf_Loesen;
		startPY = start.y + DFD__GUIKONST.pf_Loesen;

		// Vor Verschiebepfeil! Sonst wird auf den alten Ausgang eingestellt!
		fremderGAusgang = null;

		zoomPfeilkurve.setzeStartpunkt(startPX, startPY);
		verschiebePfeil(keinVerschiebenDurchSensor);
	}

	public void setzeEingangsstatus(String statusfarbe) {
		zoomPfeilkurve.setzeFarbe(statusfarbe);
	}

	@Override
	public DFD__IG_Modul getGmodul() {
		return eigenesGModul;
	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		zoomPfeilkurve.bildZeichnen( g2, dimDFD);
		
	}

}
