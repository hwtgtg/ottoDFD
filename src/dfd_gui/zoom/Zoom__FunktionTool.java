package dfd_gui.zoom;

import java.awt.Rectangle;

import global.DFD__GUIKONST;

public class Zoom__FunktionTool {

	@SuppressWarnings("unused")
	private Zoom__IModul zoomMODUL;

	float xPosOriginal = 0;
	float yPosOriginal = 0;
	float breiteOriginal = 0;
	float hoeheOriginal = 0;
	float fontgroesseOriginal;

	public Zoom__FunktionTool(int neuesX, int neuesY, int neueBreite, int neueHoehe, int fontgroesse) {
		xPosOriginal = neuesX;
		yPosOriginal = neuesY;
		breiteOriginal = neueBreite;
		hoeheOriginal = neueHoehe;
		fontgroesseOriginal = fontgroesse;
	}

	// Anpassungen für Spezialfälle
	float x2PosOriginal = 0;
	float y2PosOriginal = 0;
	float pfeilBreiteOriginal = 0;

	public Zoom__FunktionTool(char c, int startPX, int startPY, int zielPX, int zielPY, int pfeilBreite) {
		this(startPX, startPY, 0, 0, 0);
		if (c == 'P') {
			// Pfeil
			x2PosOriginal = zielPX;
			y2PosOriginal = zielPY;
			pfeilBreiteOriginal = pfeilBreite;
		}
	}

	public void setzeZoomobjekt(Zoom__IModul zoomMODUL) {
		this.zoomMODUL = zoomMODUL;
	}

	// für Positionierung Normal
	public int getX() {
		return (int) (DialogZoom.getBfZoomfaktor() * xPosOriginal);
	}

	public int getY() {
		return (int) (DialogZoom.getBfZoomfaktor() * yPosOriginal);
	}

	public int getBreite() {
		return (int) (DialogZoom.getBfZoomfaktor() * breiteOriginal);
	}

	public int getHoehe() {
		return (int) (DialogZoom.getBfZoomfaktor() * hoeheOriginal);
	}

	public int getFontgroesse() {
		return (int) (DialogZoom.getBfZoomfaktor() * fontgroesseOriginal);
	}

	public int getFontgroesseGross() {
		return (int) (DialogZoom.getBfZoomfaktor() * fontgroesseOriginal * DFD__GUIKONST.FAKTOR_FONTGROSS);
	}

	public void setzeFontgroesse(int neueFontgroesse) {
		// fontgroesseOriginal =
		// StartUmgebung.bildschirmFaktorFloat(neueFontgroesse);
		fontgroesseOriginal = neueFontgroesse;
	}

	// SPezial für Pfeil
	// für Positionierung Normal
	public int getX2() {
		return (int) (DialogZoom.getBfZoomfaktor() * x2PosOriginal);
	}

	public int getY2() {
		return (int) (DialogZoom.getBfZoomfaktor() * y2PosOriginal);
	}

	public int getPfeilbreite() {
		return (int) (DialogZoom.getBfZoomfaktor() * pfeilBreiteOriginal);
	}

	public void neueOriginalGroesseAusObjekt(int breite, int hoehe) {
		float zoomfaktorInvers = 1.0F / DialogZoom.getBfZoomfaktor();
		breiteOriginal = breite * zoomfaktorInvers;
		hoeheOriginal = hoehe * zoomfaktorInvers;
	}

	public void neueOriginalPositionAusObjekt(int xPos, int yPos) {
		float zoomfaktorInvers = 1.0F / DialogZoom.getBfZoomfaktor();
		xPosOriginal = xPos * zoomfaktorInvers;
		yPosOriginal = yPos * zoomfaktorInvers;
	}

	public void neueOriginalEndpunkteAusObjekt(int startPX, int startPY, int zielPX, int zielPY) {
		float zoomfaktorInvers = 1.0F / DialogZoom.getBfZoomfaktor();
		xPosOriginal = startPX * zoomfaktorInvers;
		yPosOriginal = startPY * zoomfaktorInvers;
		x2PosOriginal = zielPX * zoomfaktorInvers;
		y2PosOriginal = zielPY * zoomfaktorInvers;
	}

	public Rectangle leseDimensionOhneZoom() {
		return new Rectangle((int) (xPosOriginal), (int) (yPosOriginal), (int) (breiteOriginal), (int) (hoeheOriginal));
	}

	public Rectangle leseEndpunkteOhneZoom() {
		return new Rectangle((int) (xPosOriginal), (int) (yPosOriginal), (int) (x2PosOriginal), (int) (y2PosOriginal));
	}

	
}
