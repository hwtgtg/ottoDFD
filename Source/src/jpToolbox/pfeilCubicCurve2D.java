package jpToolbox;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.CubicCurve2D;

import Anwendung.DFD_Darstellung.Verbindungen.DFD_VERBINDUNG_Endpunkte;
import Anwendung.DFD_Darstellung.Verbindungen.DFD_Verbindung_GUI;
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
 * Zeichnen geschieht in der Methode <b>paintComponentSpezial(Graphics g)</b><br/>
 * Es koennen die Methoden der Klasse Graphics und Graphics2D verwendet werden.<br/>
 * <hr>
 * 
 * @author Hans Witt
 * 
 * 
 */
@SuppressWarnings("serial")
public class pfeilCubicCurve2D extends BasisComponente
		implements IComponente {
	protected int breite = 100;
	protected int hoehe = 100;
	protected int xPos = 0;
	protected int yPos = 0;
	protected String strFarbe = StaticTools.leseNormalfarbe();

	CubicCurve2D kurve = null;
	Color farbe = Color.BLUE;


	public pfeilCubicCurve2D(IContainer behaelter) {
		behaelter.add(this, 0);
		behaelter.validate();
		setzeZeichenKoordinaten_Delta(0,0);
	}

	public void updateDarstellungDelta(int delta_x, int delta_y) {
		setzeZeichenKoordinaten_Delta(delta_x, delta_y);
	}

	private DFD_VERBINDUNG_SPITZE spitz ;
	
	private void setzeZeichenKoordinaten_Delta(int delta_x, int delta_y) {

		int startPX = ep.startX;
		int startPY = ep.startY;
		int zielPX = ep.zielX;
		int zielPY = ep.zielY;

		int xMin = Math.min(startPX, zielPX) - startX;
		int xMax = Math.max(startPX, zielPX) + startX;

		int yMin = Math.min(startPY, zielPY) - startY;
		int yMax = Math.max(startPY, zielPY) + startY;

		startPX = startPX - xMin;
		startPY = startPY - yMin;

		zielPX = zielPX - xMin;
		zielPY = zielPY - yMin;

		int dy = startY * 2;

		int startCX = startPX;
		int startCY = startPY + dy;
		int zielCX = zielPX;
		int zielCY = zielPY - dy;

		xPos = xMin;
		yPos = yMin;
		breite = xMax - xMin;
		hoehe = yMax - yMin;

		if (kurve == null) {
			kurve = new CubicCurve2D.Float(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
					1.0f, 1.0f);
		}

		kurve.setCurve(startPX, startPY, startCX, startCY, zielCX, zielCY,
				zielPX, zielPY);
		
		spitz=new DFD_VERBINDUNG_SPITZE(zielPX, zielPY,dfdZoomObject.vb_SPITZE);
		
		setzeDimensionen(xPos, yPos, breite, hoehe);

	}

	@SuppressWarnings("static-access")
	@Override
	public void paintComponentSpezial(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		Stroke strokeNormal = g2.getStroke();
		Stroke strokeKURVE = new BasicStroke(dfdZoomObject.vb_basisBreite,
				BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND);
		
		if (dfd_Verbindung != null) {
			farbe = dfd_Verbindung.dfdVerbindungsFarbe;
		} 

		g2.setStroke(strokeKURVE);
		g2.setColor(farbe);
		if (kurve != null) {
			g2.draw(kurve);
			
			g2.drawLine(spitz.xl, spitz.yl, spitz.x, spitz.y);
			g2.drawLine(spitz.xr, spitz.yr, spitz.x, spitz.y);
		}
		g2.setStroke(strokeNormal);

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
		super.setzeBasisfarbe(neueFarbe);
	}

	/**
	 * Entfernen des Graphikobjekts
	 */
	public void entfernen() {
		ausContainerEntfernen();
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


}
