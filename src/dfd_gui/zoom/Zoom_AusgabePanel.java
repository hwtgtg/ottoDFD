package dfd_gui.zoom;

import java.awt.Rectangle;

import dfd_gui.DFD_Arbeitsfenster;
import global.StartUmgebung;
import jtoolbox.AusgabePanel;
import jtoolbox.IContainer;

public class Zoom_AusgabePanel extends AusgabePanel implements  Zoom__IModul{
	Zoom__FunktionTool zoom;

	// Klassenfabrik
	public static Zoom_AusgabePanel getZoomAusgabePanel(IContainer behaelter, String neuerText, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);
		Zoom_AusgabePanel ausgabe = new Zoom_AusgabePanel(zoom, behaelter, neuerText, neuesX, neuesY, neueBreite,
				neueHoehe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	protected Zoom_AusgabePanel(Zoom__FunktionTool zoom, IContainer behaelter, String neuerText, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		super(behaelter, neuerText, zoom.getX(), zoom.getY(), zoom.getBreite(), zoom.getHoehe());
		this.zoom = zoom;
		zoom.setzeZoomobjekt(this);

		super.setzeSchriftgroesse(zoom.getFontgroesse());
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
	
	@Override
	public void setzeSchriftgroesse(int neueFontgroesse) {
		zoom.setzeFontgroesse(neueFontgroesse);
		super.setzeSchriftgroesse(zoom.getFontgroesse());
	}

	@Override
	public void setzeAnzeigezoomNeu() {
		setzeDimensionen(zoom.getX(), zoom.getY(), zoom.getBreite(),
				zoom.getHoehe());
		super.setzeSchriftgroesse(zoom.getFontgroesse());
	}

	@Override
	public void setzePosition(int posX, int posY) {
		if (posX < 0) {
			posX = 0;
		}
		if (posY < 0) {
			posY = 0;
		}

		if (DFD_setzePosition(posX, posY)) {
			super.setzePosition(posX, posY);
		} else {
		}
	}

	@Override
	public boolean DFD_setzePosition(int posX, int posY) {
		if (DFD_Arbeitsfenster.getArbeiter().testeArbeitsfensterNeu(this, posX, posY, zoom.getBreite(),
				zoom.getHoehe())) {
			zoom.neueOriginalPositionAusObjekt(posX, posY);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setzeGroesse(int breite, int hoehe) {
		if (DFD_setzeGroesse(breite, hoehe)) {
			super.setzeGroesse(breite, hoehe);
		}
	}

	@Override
	public boolean DFD_setzeGroesse(int breite, int hoehe) {
		zoom.neueOriginalGroesseAusObjekt(breite, hoehe);
		DFD_Arbeitsfenster.getArbeiter().testeArbeitsfensterNeu(this, super.xPos, super.yPos, breite, hoehe);
		return true;
	}

	@Override
	public void setzeDimensionen(int posX, int posY, int breite, int hoehe) {
		if (DFD_SetzeDimensionen(posX, posY, breite, hoehe)) {
			super.setzeDimensionen(posX, posY, breite, hoehe);
		} else {
			if (posX < 0) {
				posX = 0;
			}
			if (posY < 0) {
				posY = 0;
			}
			setzeDimensionen(0, 0, breite, hoehe);

		}
	}

	@Override
	public boolean DFD_SetzeDimensionen(int posX, int posY, int breite, int hoehe) {
		return DFD_Arbeitsfenster.getArbeiter().testeArbeitsfensterNeu(this, posX, posY, breite, hoehe);
	}

	@Override
	public Rectangle leseDimensionInclZoom() {
		return new Rectangle(super.xPos, super.yPos, super.breite, super.hoehe);
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
