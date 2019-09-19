package dfd_gui.zoom;

import java.awt.Rectangle;

import dfd_gui.DFD_Arbeitsfenster;
import jtoolbox.IContainer;
import jtoolbox.Pfeil;

public class Zoom_Pfeil extends Pfeil implements  Zoom__IModul {

	Zoom__FunktionTool zoom;

	// Klassenfabrik
	public static Zoom_Pfeil getZoomPfeil(IContainer behaelter, int x1, int y1, int x2, int y2, int pfeilBreite,
			String pfeilFarbe) {
		Zoom__FunktionTool zoom = new Zoom__FunktionTool('P', x1, y1, x2, y2, pfeilBreite);
		Zoom_Pfeil ausgabe = new Zoom_Pfeil(zoom, behaelter, pfeilFarbe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	protected Zoom_Pfeil(Zoom__FunktionTool zoom, IContainer behaelter, String pfeilFarbe) {
		super(behaelter, zoom.getX(), zoom.getY(), zoom.getX2(), zoom.getY2());
		this.setzeBreite(zoom.getPfeilbreite());
		this.zoom = zoom;
		zoom.setzeZoomobjekt(this);
		super.setzeFarbe(pfeilFarbe);

		addZoomfenster();
	}

	@Override
	public void addZoomfenster() {
		Zoom__Verwalter.getZoomverwalter().addZoomkomponente(this);
	}

	@Override
	public void modulLoeschen() {
		Zoom__Verwalter.getZoomverwalter().removeZoomkomponente(this);
		entfernen();
	}

	public void setzeSchriftgroesse(int neueFontgroesse) {

	}

	@Override
	public void setzeAnzeigezoomNeu() {
		setzeDimensionen(zoom.getX(), zoom.getY(), zoom.getX2(), zoom.getY2());
		setzeBreite(zoom.getPfeilbreite());
	}

	// @Override
	public void setzePosition(int posX, int posY) {
		if (posX < 0) {
			posX = 0;
		}
		if (posY < 0) {
			posY = 0;
		}

		if (DFD_setzePosition(posX, posY)) {
			// super.setzePosition(posX, posY);
			super.setzeEndpunkte(posX, posY, super.x2, super.y2);

		} else {
		}
	}

	// @Override
	public boolean DFD_setzePosition(int posX, int posY) {
		if (DFD_Arbeitsfenster.getArbeiter().testeArbeitsfensterNeu(this, posX, posY, zoom.getBreite(),
				zoom.getHoehe())) {
			zoom.neueOriginalPositionAusObjekt(posX, posY);
			return true;
		} else {
			return false;
		}
	}

	// @Override
	public void setzeGroesse(int breite, int hoehe) {
	}

	// @Override
	public boolean DFD_setzeGroesse(int breite, int hoehe) {
		return true;
	}

	// @Override
	public void setzeDimensionen(int posX, int posY, int posX2, int posY2) {
		// Parameter 3 und 4 werden umdefiniert zu x2 und y2 !
		int breit = posX2 - posX;
		int xLiO = (breit > 0) ? posX : posX2;
		int hoch = posY2 - posY;
		int yLiO = (hoch > 0) ? posY : posY2;

		if (DFD_SetzeDimensionen(xLiO, yLiO, breit, hoch)) {
			super.setzeEndpunkte(posX, posY, posX2, posY2);
		} else {
			if (posX < 0) {
				posX = 0;
			}
			if (posY < 0) {
				posY = 0;
			}
			setzeDimensionen(0, 0, posX2, posY2);

		}
	}

	@Override
	public boolean DFD_SetzeDimensionen(int posX, int posY, int breite, int hoehe) {
		return DFD_Arbeitsfenster.getArbeiter().testeArbeitsfensterNeu(this, posX, posY, breite, hoehe);
	}

	@Override
	public Rectangle leseDimensionInclZoom() {
		return new Rectangle(super.x1, super.y1, super.x2, super.y2);
	}

	@Override
	public Rectangle leseDimensionOhneZoom() {
		return zoom.leseDimensionOhneZoom();
	}

	@Override
	public void setzeAusgewaehlt() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setzeNichtAusgewaehlt() {
		// TODO Auto-generated method stub

	}

}
