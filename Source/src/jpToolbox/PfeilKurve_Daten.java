package jpToolbox;

import java.awt.geom.CubicCurve2D;
import java.awt.geom.Rectangle2D;

public class PfeilKurve_Daten {

	float posX = 0;
	float posY = 0;
	float breite = 0;
	float hoehe = 0;

	float startX = 0;
	float startY = 0;

	float zielX = 0;
	float zielY = 0;

	float ctrlStartX = 0;
	float ctrlStartY = 0;

	float ctrlZielX = 0;
	float ctrlZielY = 0;

	float ctrlStartLength = 200;
	float ctrlZielLength = 200;

	float pfeilBreite = 0;

	CubicCurve2D kurve = null;

	float spitze1_X = 0;
	float spitze1_Y = 0;
	float spitze2_X = 0;
	float spitze2_Y = 0;

	float spitzeLength = 0;

	public PfeilKurve_Daten(int startPX, int startPY, int zielPX, int zielPY, int pfeilbreite) {
		this.pfeilBreite = pfeilbreite;
		this.spitzeLength = this.pfeilBreite * 4;
		this.ctrlStartLength = this.pfeilBreite * 20;
		this.ctrlZielLength = this.ctrlStartLength;
		this.startX = startPX;
		this.startY = startPY;
		this.zielX = zielPX;
		this.zielY = zielPY;

		berechneKurve();
	}

	public CubicCurve2D setzePosition(int startPX, int startPY) {
		zielX = zielX + posX;
		zielY = zielY + posY;
		this.posX = 0;
		this.posY = 0;
		this.startX = startPX;
		this.startY = startPY;

		return berechneKurve();
	}

	public CubicCurve2D setzePositionen(int startPX, int startPY, int zielPX, int zielPY, float pfeilBreite) {
		this.pfeilBreite = pfeilBreite;
		this.spitzeLength = this.pfeilBreite * 4;
		this.ctrlStartLength = this.pfeilBreite * 20;
		this.ctrlZielLength = this.ctrlStartLength;

		this.posX = 0;
		this.posY = 0;
		this.startX = startPX;
		this.startY = startPY;
		this.zielX = zielPX;
		this.zielY = zielPY;

		return berechneKurve();

	}

	public CubicCurve2D setzePositionen(int startPX, int startPY, int zielPX, int zielPY) {
		this.posX = 0;
		this.posY = 0;
		this.startX = startPX;
		this.startY = startPY;
		this.zielX = zielPX;
		this.zielY = zielPY;

		return berechneKurve();

	}

	public CubicCurve2D setzePfeilbreite(float pfeilbreite) {
		this.pfeilBreite = pfeilbreite;
		this.spitzeLength = this.pfeilBreite * 4;
		this.ctrlStartLength = this.pfeilBreite * 20;
		this.ctrlZielLength = this.ctrlStartLength;

		startX = startX + posX;
		startY = startY + posY;
		zielX = zielX + posX;
		zielY = zielY + posY;
		this.posX = 0;
		this.posY = 0;

		return berechneKurve();
	}

	public synchronized CubicCurve2D berechneKurve() {

		if (kurve == null) {
			kurve = new CubicCurve2D.Float(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f);
		}

		startX = startX + posX;
		startY = startY + posY;
		zielX = zielX + posX;
		zielY = zielY + posY;

		ctrlStartX = startX;

		if (zielY < startY) {
			ctrlStartY = startY + (-(zielY - startY) > ctrlStartLength ? -(zielY - startY) : ctrlStartLength);
		} else {
			ctrlStartY = startY + ((zielY - startY) > ctrlStartLength ? (zielY - startY) : ctrlStartLength);
		}

		ctrlZielX = zielX;
		if (zielY < startY) {
			ctrlZielY = zielY - (-(zielY - startY) > ctrlZielLength ? -(zielY - startY) : ctrlZielLength);
		} else {
			ctrlZielY = zielY - ((zielY - startY) > ctrlZielLength ? (zielY - startY) : ctrlZielLength);
		}

		
		kurve.setCurve(startX, startY, ctrlStartX, ctrlStartY, ctrlZielX, ctrlZielY, zielX, zielY);

		// ausgabe(1);

		Rectangle2D dim = kurve.getBounds();

		posX = (float) dim.getMinX() - spitzeLength;
		posY = (float) dim.getMinY() - spitzeLength;

		breite = (float) dim.getWidth() + 2 * spitzeLength;
		hoehe = (float) dim.getHeight() + 2 * spitzeLength;

		startX = startX - posX;
		startY = startY - posY;
		ctrlStartX = ctrlStartX - posX;
		ctrlStartY = ctrlStartY - posY;
		ctrlZielX = ctrlZielX - posX;
		ctrlZielY = ctrlZielY - posY;
		zielX = zielX - posX;
		zielY = zielY - posY;
		kurve.setCurve(startX, startY, ctrlStartX, ctrlStartY, ctrlZielX, ctrlZielY, zielX, zielY);

		spitze1_X = zielX - spitzeLength * 0.7F;
		spitze1_Y = zielY - spitzeLength * 1.0F;
		spitze2_X = zielX + spitzeLength * 0.7F;
		spitze2_Y = zielY - spitzeLength * 1.0F;

		// ausgabe(2);
		return kurve;
	}

	public int getStartX() {
		return (int) (startX + posX);
	}

	public int getStartY() {
		return (int) (startY + posY);
	}

	public int getZielX() {
		return (int) (zielX + posX);
	}

	public int getZielY() {
		return (int) (zielY + posY);
	}

	public int getXPos() {
		return (int) posX;
	}

	public int getYPos() {
		return (int) posY;
	}

	public int getBreite() {
		return (int) breite;
	}

	public int getHoehe() {
		return (int) hoehe;
	}

	public int getSpitzeX() {
		return (int) zielX;
	}

	public int getSpitzeY() {
		return (int) zielY;
	}

	public int getSpitze1X() {
		return (int) spitze1_X;
	}

	public int getSpitze1Y() {
		return (int) spitze1_Y;
	}

	public int getSpitze2X() {
		return (int) spitze2_X;
	}

	public int getSpitze2Y() {
		return (int) spitze2_Y;
	}

	public float getLinienbreite() {
		return pfeilBreite;
	}

	public void ausgabe(int nr) {

		// LOG.outln("------------------------------- Nr:" + nr);
		// LOG.outln("posX " + posX);
		// LOG.outln("posY " + posY);
		// ;
		// LOG.outln("breite " + breite);
		// LOG.outln("hoehe " + hoehe);
		//
		// LOG.outln("startX " + startX);
		// LOG.outln("startY " + startY);
		//
		// LOG.outln("zielX " + zielX);
		// LOG.outln("zielY " + zielY);
		//
		// LOG.outln("ctrlStartX " + ctrlStartX);
		// LOG.outln("ctrlStartY " + ctrlStartY);
		//
		// LOG.outln("ctrlZielX " + ctrlZielX);
		// LOG.outln("ctrlZielY " + ctrlZielY);
		//
		// LOG.outln("ctrlStartLength " + ctrlStartLength);
		// LOG.outln("ctrlZielLength " + ctrlZielLength);
		//
		// LOG.outln("pfeilBreite " + pfeilBreite);
		//
		// LOG.outln("spitze1_X " + spitze1_X);
		// LOG.outln("spitze1_Y " + spitze1_Y);
		// LOG.outln("spitze2_X " + spitze2_X);
		// LOG.outln("spitze2_Y " + spitze2_Y);
		//
		// LOG.outln("spitzeLength " + spitzeLength);

	}

}
