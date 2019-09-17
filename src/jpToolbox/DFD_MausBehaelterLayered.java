package jpToolbox;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import jtoolbox.IContainer;
import jtoolbox.MausBehaelter;
import jtoolbox.Zeichnung;

public class DFD_MausBehaelterLayered extends MausBehaelter {

	public MausBehaelter modulLayer;
	public MausBehaelter pfeilLayer;
	public MausBehaelter sensorLayer;

	public DFD_MausBehaelterLayered() {
		this(Zeichnung.gibZeichenflaeche(), 0, 0, 400, 400);
	}

	/**
	 * Konstruktor fuer Hauptfenster
	 * 
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public DFD_MausBehaelterLayered(int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), 0, 0, neueBreite, neueHoehe);
	}

	/**
	 * Konstruktor fuer Hauptfenster
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public DFD_MausBehaelterLayered(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, neueBreite, neueHoehe);
	}

	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 */
	public DFD_MausBehaelterLayered(IContainer behaelter) {
		this(behaelter, 0, 0, 400, 400);
	}

	/**
	 * allgemeiner Konstuktor
	 * 
	 * @param behaelter
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public DFD_MausBehaelterLayered(IContainer behaelter, int neuesX, int neuesY, int neueBreite, int neueHoehe) {

		super(behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		modulLayer = new MausBehaelter(this);
		pfeilLayer = new MausBehaelter(this);
		sensorLayer = new MausBehaelter(this);

		modulLayer.setzeGroesse(getBasisComponente().getWidth(), getBasisComponente().getHeight());
		pfeilLayer.setzeGroesse(getBasisComponente().getWidth(), getBasisComponente().getHeight());
		sensorLayer.setzeGroesse(getBasisComponente().getWidth(), getBasisComponente().getHeight());

		getBasisComponente().addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				modulLayer.setzeGroesse(getBasisComponente().getWidth(), getBasisComponente().getHeight());
				pfeilLayer.setzeGroesse(getBasisComponente().getWidth(), getBasisComponente().getHeight());
				sensorLayer.setzeGroesse(getBasisComponente().getWidth(), getBasisComponente().getHeight());
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});

	}

	public void setzeFokusierbar(boolean wert) {
		super.setzeFokusierbar(wert);
		modulLayer.setzeFokusierbar(wert);
		pfeilLayer.setzeFokusierbar(wert);
		sensorLayer.setzeFokusierbar(wert);
	}

	
	public void setzeFokus(){
		super.setzeFokus();
	}
}
