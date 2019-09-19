package jpToolbox;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import jtoolbox.IContainer;
import jtoolbox.MausBehaelter;
import jtoolbox.Zeichnung;

public class MausBehaelterLayered extends MausBehaelter {

	public MausBehaelter hinten;
	public MausBehaelter mitte;
	public MausBehaelter vorn;

	public MausBehaelterLayered() {
		this(Zeichnung.gibZeichenflaeche(), 0, 0, 400, 400);
	}

	/**
	 * Konstruktor fuer Hauptfenster
	 * 
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public MausBehaelterLayered(int neueBreite, int neueHoehe) {
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
	public MausBehaelterLayered(int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, neueBreite,
				neueHoehe);
	}

	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 */
	public MausBehaelterLayered(IContainer behaelter) {
		this(behaelter, 0, 0, 400, 400);
	}

	/**
	 * allgemeiner Konstuktor
	 * 
	 * @param behaelter
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public MausBehaelterLayered(IContainer behaelter, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {

		super(behaelter, neuesX, neuesY, neueBreite, neueHoehe);

		hinten = new MausBehaelter(this);
		mitte = new MausBehaelter(this);
		vorn = new MausBehaelter(this);

		hinten.setzeGroesse(getBasisComponente().getWidth(),
				getBasisComponente().getHeight());
		mitte.setzeGroesse(getBasisComponente().getWidth(),
				getBasisComponente().getHeight());
		vorn.setzeGroesse(getBasisComponente().getWidth(), getBasisComponente()
				.getHeight());

		getBasisComponente().addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				hinten.setzeGroesse(getBasisComponente().getWidth(),
						getBasisComponente().getHeight());
				mitte.setzeGroesse(getBasisComponente().getWidth(),
						getBasisComponente().getHeight());
				vorn.setzeGroesse(getBasisComponente().getWidth(),
						getBasisComponente().getHeight());
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
}
