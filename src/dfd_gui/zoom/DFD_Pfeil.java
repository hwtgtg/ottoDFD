package dfd_gui.zoom;

import java.awt.Point;

import global.DFD__GUIKONST;
import jtoolbox.IContainer;
import jtoolbox.Pfeil;
import jtoolbox.Zeichnung;
import modul.gui_modul.DFD_I_GUI_Basismodul;

public class DFD_Pfeil extends Pfeil  {

	public static final DFD_Pfeil KEINPFEIL = null ;
	public static final int PF_Nr_FREI = -99;

	public boolean fixiert = false;
	public int eingangsNr = PF_Nr_FREI; // positiv -> Eingangsnummer

	DFD_I_GUI_Basismodul modulSpitze;
	DFD_I_GUI_Basismodul modulAnfang;

	boolean gebunden = false;
	
	// true == als EingangsPfeil mit Sensor verbunden
	// wird vom Sensor gesetzt
	private boolean sensorIstEingang = true;
	/**
	 * @return the sensorIstEingang
	 */
	public boolean istSensorEingang() {
		return sensorIstEingang;
	}

	public boolean istSensorAusgang() {
		return ! sensorIstEingang;
	}

	/**
	 * @param sensorIstEingang the sensorIstEingang to set
	 */
	public void setSensorIstEingang() {
		this.sensorIstEingang = true;
	}
	public void setSensorIstAusgang() {
		this.sensorIstEingang = false;
	}

	// keine eigentliche Verknuepfung. wird nur fuer Anzeige gebraucht
	private boolean dummyAusgang = false;

	/**
	 * @return the dummyAusgang
	 */
	public boolean isAusgangDummy() {
		return dummyAusgang;
	}

	/**
	 * @param dummyAusgang the dummyAusgang to set
	 */
	public void setDummyAusgang() {
		this.dummyAusgang = true;
	}

	public DFD_Pfeil(int x1, int y1, int x2, int y2, int ID,
			DFD_I_GUI_Basismodul basisModul) {
		this(Zeichnung.gibZeichenflaeche(), x1, y1, x2, y2, ID, basisModul);
	}

	/**
	 * allgemeiner Konstruktor
	 * 
	 * @param behaelter
	 * @param x2
	 * @param y2
	 */
	public DFD_Pfeil(IContainer behaelter, int x1, int y1, int x2, int y2,
			int ID, DFD_I_GUI_Basismodul modulEingang) {
		super(behaelter, x1, y1, x2, y2);
		this.modulSpitze = modulEingang;
		this.eingangsNr = ID;
		behaelter.validate();
	}

	public void verschiebenUeberPfeilSpitze(int x, int y) {
		if (!gebunden) {
			setzeEndpunkte(x - (x2 - x1), y - (y2 - y1), x, y);
		} else {
			spitzeVerschieben(x, y);
		}
	}

	public void verschiebenUeberPfeilEnde(int x, int y) {
		if (!gebunden) {
			setzeEndpunkte(x, y, x + (x2 - x1), y + (y2 - y1));
		} else {
			endeVerschieben(x, y);
		}
	}

	public void spitzeVerschieben(int x, int y) {
		setzeEndpunkte(x1, y1, x, y);
	}

	public void endeVerschieben(int x, int y) {
		setzeEndpunkte(x, y, x2, y2);
	}

	public Point leseSpitze() {
		return new Point(x2, y2);
	}

	public Point leseEnde() {
		return new Point(x1, y1);
	}

	public void pfeilBinden() {
		setzeFarbe(DFD__GUIKONST.PF_VERBUNDEN);
		sichtbarMachen();
		gebunden = true;
	}

	public void pfeilFreigeben() {
		setzeFarbe(DFD__GUIKONST.PF_FREI);
		gebunden = false;
	}

	public void standardAnzeigeSetzen() {
//		pfeilFreigeben();
//		if (eingangsNr == DFD__GUIKONST.MS_AUSGANGSID) {
//
//			Point ausgangsPunkt = modulSpitze.ausgangMittelpunkt();
//			setzeEndpunkte(ausgangsPunkt.x, ausgangsPunkt.y, ausgangsPunkt.x
//					+ DFD__GUIKONST.PF_DX, ausgangsPunkt.y + DFD__GUIKONST.PF_DY);
//		} else {
//			Point eingangsPunkt = modulSpitze.eingangsMittelpunkt(eingangsNr);
//			setzeEndpunkte(eingangsPunkt.x - DFD__GUIKONST.PF_DX, eingangsPunkt.y - DFD__GUIKONST.PF_DY,
//					eingangsPunkt.x, eingangsPunkt.y);
//		}
	}

}
