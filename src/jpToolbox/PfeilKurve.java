package jpToolbox;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.CubicCurve2D;

import jtoolbox.BasisComponente;
import jtoolbox.IComponente;
import jtoolbox.IContainer;
import jtoolbox.StaticTools;
import jtoolbox.Zeichnung;

/**
 * 
 * Darstellung der Verbinder zwischen Modulen
 * 
 * Nachempfunden der Komponente FreiZeichnen
 * 
 * <h1>Muster einer Komponente, in die man direkt zeichnen kann.</h1> Das
 * Zeichnen geschieht in der Methode <b>paintComponentSpezial(Graphics g)</b>
 * <br/>
 * Es koennen die Methoden der Klasse Graphics und Graphics2D verwendet werden.
 * <br/>
 * <hr>
 * 
 * @author Hans Witt
 * 
 * 
 */
@SuppressWarnings("serial")
public class PfeilKurve extends BasisComponente implements IComponente {
	protected int breite = 100;
	protected int hoehe = 100;
	protected int xPos = 0;
	protected int yPos = 0;
	protected String strFarbe = StaticTools.leseNormalfarbe();

	protected static final int defPfeilbreite = 3;

	CubicCurve2D kurve = null;
	protected Color farbe = Color.BLUE;

	PfeilKurve_Daten daten;

	public PfeilKurve(IContainer behaelter, int startPX, int startPY, int zielPX, int zielPY) {
		this(behaelter, startPX, startPY, zielPX, zielPY, defPfeilbreite);
	}

	public PfeilKurve(IContainer behaelter, int startPX, int startPY, int zielPX, int zielPY, int pfeilbreite) {
		this.daten = new PfeilKurve_Daten(startPX, startPY, zielPX, zielPY, pfeilbreite);
		if (behaelter == null) {
			Zeichnung.gibZeichenflaeche().add(this, 0);
			Zeichnung.gibZeichenflaeche().validate();
		} else {
			behaelter.add(this, 0);
			behaelter.validate();
		}

		berechneZeichenKoordinaten(daten);
	}

	private void berechneZeichenKoordinaten(PfeilKurve_Daten daten) {

		kurve = daten.berechneKurve();

		setzeDimensionen(daten.getXPos(), daten.getYPos(), daten.getBreite(), daten.getHoehe());

	}

	@Override
	public void paintComponentSpezial(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		if (daten != null) {
			Stroke strokeNormal = g2.getStroke();
			Stroke strokeKURVE = new BasicStroke(daten.getLinienbreite(), BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND);

			// if (dfd_Verbindung != null) {
			// farbe = dfd_Verbindung.dfdVerbindungsFarbe;
			// }

			g2.setStroke(strokeKURVE);
			g2.setColor(farbe);
			if (kurve != null) {
				g2.draw(kurve);

				g2.drawLine(daten.getSpitze1X(), daten.getSpitze1Y(), daten.getSpitzeX(), daten.getSpitzeY());
				g2.drawLine(daten.getSpitze2X(), daten.getSpitze2Y(), daten.getSpitzeX(), daten.getSpitzeY());
			}
			g2.setStroke(strokeNormal);
		}
	}

	/**
	 * Das Interface IComponente fordert eine Methode die eine BasisComponente
	 * zurueckliefert. Sie wird benoetigt, um ein Objekt zu einem anderen
	 * Behaelter hinzuzufuegen
	 */
	@Override
	public BasisComponente getBasisComponente() {
		return this;
	}

	// Methode noetig zum Hinzufuegen mit Anpassung beim Behaelter
	// Die Enden werden relativ zur aktuellen Position verschoben
	@Override
	public void verschieben(int dx, int dy) {
		setzePosition(xPos + dx, yPos + dy);
	}

	/*
	 * gueltige Farben: "rot", "gelb", "blau", "gruen", "lila" , "schwarz" ,
	 * "weiss" , "grau","pink","magenta","orange","cyan","hellgrau"
	 */
	public void setzeFarbe(String neueFarbe) {
		strFarbe = neueFarbe;
		farbe = StaticTools.getColor(strFarbe);
		repaint();
		// super.setzeBasisfarbe(neueFarbe);
	}

	public void setzePfeilbreite(float pfeilbreite) {
		kurve = daten.setzePfeilbreite(pfeilbreite);
		setzeDimensionen(daten.getXPos(), daten.getYPos(), daten.getBreite(), daten.getHoehe());
	}

	@Override
	public void setzePosition(int startPX, int startPY) {
		kurve = daten.setzePosition(startPX, startPY);
		setzeDimensionen(daten.getXPos(), daten.getYPos(), daten.getBreite(), daten.getHoehe());
	}

	public void setzeEndpunkte(int startPX, int startPY, int zielPX, int zielPY) {
		kurve = daten.setzePositionen(startPX, startPY, zielPX, zielPY);

		setzeDimensionen(daten.getXPos(), daten.getYPos(), daten.getBreite(), daten.getHoehe());
	}

	public void setzeEndpunkte(int startPX, int startPY, int zielPX, int zielPY, float pfeilbreite) {
		kurve = daten.setzePositionen(startPX, startPY, zielPX, zielPY);

		// LOG.outln("Pfeilkurve setzeEndpunkte x:"+startPX+" y:"+startPY+"
		// x2:"+zielPX+" y2:"+zielPY);

		setzeDimensionen(daten.getXPos(), daten.getYPos(), daten.getBreite(), daten.getHoehe());
		setzePfeilbreite(pfeilbreite);
	}

	/**
	 * Entfernen des Graphikobjekts
	 */
	public void entfernen() {
		ausContainerEntfernen();
	}

	public Point getStartpunkt() {
		return new Point(daten.getStartX(), daten.getStartY());
	}

	public Point getZielpunkt() {
		return new Point(daten.getZielX(), daten.getZielY());
	}

	/**
	 * Destruktor
	 */
	@Override
	protected void finalize() {
		if (!Zeichnung.verweistesGUIElementEntfernen)
			return;
		entfernen();
	}

//	public static void main(String[] args) {
//
//		Zeichnung.setzeRasterEin();
//
//		PfeilKurve pfeil1 = new PfeilKurve(null, 100, 300, 200, 500, 5);
//
//		PfeilKurve pfeil2 = new PfeilKurve(null, 200, 200, 100, 300);
//
//		PfeilKurve pfeil3 = new PfeilKurve(null, 100, 150, 300, 200, 5);
//		pfeil3.setzeFarbe("rot");
//
//		pfeil2.setzeEndpunkte(100, 150, 300, 500);
//		pfeil2.setzeFarbe("gruen");
//
//		pfeil1.setzePosition(0, 300);
//
//		pfeil1.setzePfeilbreite(20F);
//
//	}

}

