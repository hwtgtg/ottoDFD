/**
 * 
 */
package dfd_gui.zoom;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.CubicCurve2D;

import drucken.DFD_Drucken;
import global.DFD__GUIKONST;
import jpToolbox.PfeilKurve;
import jtoolbox.IContainer;

/**
 * @author Witt
 *
 */
@SuppressWarnings("serial")
public class Zoom_PfeilKurve extends PfeilKurve implements Zoom__IModul {

	Zoom__FunktionTool zoom;

	// Klassenfabrik
	public static Zoom_PfeilKurve getZoom_PfeilKurve(IContainer behaelter, int startPX, int startPY, int zielPX,
			int zielPY, int pfeilBreite, String pfeilfarbe) {
		Zoom__FunktionTool zoom = new Zoom__FunktionTool('P', startPX, startPY, zielPX, zielPY, pfeilBreite);
		Zoom_PfeilKurve ausgabe = new Zoom_PfeilKurve(zoom, behaelter, pfeilfarbe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	protected Zoom_PfeilKurve(Zoom__FunktionTool zoom, IContainer behaelter, String pfeilfarbe) {
		super(behaelter, zoom.getX(), zoom.getY(), zoom.getX2(), zoom.getY2(), zoom.getPfeilbreite());
		super.setzeFarbe(pfeilfarbe);
		this.zoom = zoom;
		zoom.setzeZoomobjekt(this);

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
	public void setzeAnzeigezoomNeu() {
		DFD_SetzeEndpunkte(zoom.getX(), zoom.getY(), zoom.getX2(), zoom.getY2(), zoom.getPfeilbreite());
	}

	/**
	 * Liniendicke setzen
	 * 
	 * @param neueBreite
	 */
	public void setzeBreite(int pfeilbreite) {
		setzePfeilbreite(pfeilbreite);
	}

	// @Override
	public void setzePosition(int startPX, int startPY) {
		// zoom.neueOriginalPositionAusObjekt(posX, posY);
		// super.setzePosition(startPX, startPY);
	}

	@Override
	public boolean DFD_setzePosition(int posX, int posY) {
		// nichts zu tun
		return false;
	}

	@Override
	public boolean DFD_setzeGroesse(int breite, int hoehe) {
		// nichts zu tun
		return false;
	}

	@Override
	public boolean DFD_SetzeDimensionen(int posX, int posY, int breite, int hoehe) {

		return false;
	}

	private void DFD_SetzeEndpunkte(int startPX, int startPY, int zielPX, int zielPY, int pfeilBreite) {
		super.setzeEndpunkte(startPX, startPY, zielPX, zielPY, pfeilBreite);

	}

	public void setzeEndpunkte(int startPX, int startPY, int zielPX, int zielPY) {
		zoom.neueOriginalEndpunkteAusObjekt(startPX, startPY, zielPX, zielPY);
		super.setzeEndpunkte(startPX, startPY, zielPX, zielPY);
	}

	public void setzeStartpunkt(int startPX, int startPY) {
		zoom.neueOriginalEndpunkteAusObjekt(startPX, startPY, getZielpunkt().x, getZielpunkt().y);
		super.setzeEndpunkte(startPX, startPY, getZielpunkt().x, getZielpunkt().y);
	}

	@Override
	public Rectangle leseDimensionOhneZoom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle leseDimensionInclZoom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setzeAusgewaehlt() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setzeNichtAusgewaehlt() {
		// TODO Auto-generated method stub

	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		Rectangle dim = zoom.leseEndpunkteOhneZoom();
		dim.x -= dimDFD.x;
		dim.y -= dimDFD.y;

		dim.width -= dimDFD.x;
		dim.height -= dimDFD.y;

		int pfeilBreite = DFD_Drucken.drF(DFD__GUIKONST.PF_BREITE);
		int spitzeLength = pfeilBreite * 4;
		int ctrlStartLength = pfeilBreite * 20;
		int ctrlZielLength = ctrlStartLength;

		float startX = DFD_Drucken.drX(dim.x);
		float startY = DFD_Drucken.drY(dim.y);
		float zielX = DFD_Drucken.drX(dim.width); // ALternativbelegung für x2/y2
		float zielY = DFD_Drucken.drY(dim.height);

		CubicCurve2D kurve = new CubicCurve2D.Float();

		float ctrlStartX = startX;
		float ctrlStartY = 0;

		if (zielY < startY) {
			ctrlStartY = startY + (-(zielY - startY) > ctrlStartLength ? -(zielY - startY) : ctrlStartLength);
		} else {
			ctrlStartY = startY + ((zielY - startY) > ctrlStartLength ? (zielY - startY) : ctrlStartLength);
		}

		float ctrlZielX = zielX;
		float ctrlZielY = 0;
		if (zielY < startY) {
			ctrlZielY = zielY - (-(zielY - startY) > ctrlZielLength ? -(zielY - startY) : ctrlZielLength);
		} else {
			ctrlZielY = zielY - ((zielY - startY) > ctrlZielLength ? (zielY - startY) : ctrlZielLength);
		}

		kurve.setCurve(startX, startY, ctrlStartX, ctrlStartY, ctrlZielX, ctrlZielY, zielX, zielY);
		Stroke strokeNormal = g2.getStroke();
		Stroke strokeKURVE = new BasicStroke(pfeilBreite, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND);

		g2.setStroke(strokeKURVE);
		g2.setColor(farbe);
		if (kurve != null) {
			g2.draw(kurve);

			float spitze1_X = zielX - spitzeLength * 0.7F;
			float spitze1_Y = zielY - spitzeLength * 1.0F;
			float spitze2_X = zielX + spitzeLength * 0.7F;
			float spitze2_Y = zielY - spitzeLength * 1.0F;

			g2.drawLine((int) spitze1_X, (int) spitze1_Y, (int) zielX, (int) zielY);
			g2.drawLine((int) spitze2_X, (int) spitze2_Y, (int) zielX, (int) zielY);
			g2.setStroke(strokeNormal);

		}
	}

}
