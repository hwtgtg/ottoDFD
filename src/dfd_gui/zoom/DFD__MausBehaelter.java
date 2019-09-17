package dfd_gui.zoom;

import jtoolbox.*;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * <h1>MausBehaelter</h1> erweitert den Behaelter.<br /> 
 * 
 * Ist der Kommunikationslink gesetzt, meldet das Objekt eine <b><i>Mausereignisse</i></b>.<br/><br/>
 * Die zu signalisierenden Ereignisse m&uuml;ssen aktiviert werden<br/>
 * Siehe Konstanten und setzeMausXXX... <br/><br/>
 *  
 * Der MausBehaelter nimmt GUI-Elemente auf<br/> 
 * Diese werden wie beim Behaelter relativ zum Container positioniert. Siehe Behaelter<br/>
 * <hr>
 * 
 * @author Hans Witt
 * @version
 * Version: 3 (9.8.2008) 
 *        Containerklasse fuer GUI-Elemente<br/>
 * Version: 3.1 (14.8.2008) 
 *        Konstruktor auf int neuesX, int neuesY , int neueBreite, int neueHoehe angepasst	<br/>	
 * Version 5.0: (4.9.2010
 *  	   Entfernen für Graphikkomponente eingeführt<br/>
 *  
 *   	   Destruktor entfernt Graphikkomponente automatisch bei gc()<br/>
 */
public class DFD__MausBehaelter implements jtoolbox.IContainer, jtoolbox.IComponente {
	
	public static final int		CLICK				= 0;			// ID = 0
	public static final int		PRESS				= 1;			// ID = 1
	public static final int		RELEASE				= 2;			// ID = 2
	public static final int		ENTER				= 3;			// ID = 3
	public static final int		EXIT				= 4;			// ID = 4
	public static final int		DRAGGED				= 5;			// ID = 5
	public static final int		MOVED				= 6;			// ID = 6
	public static final int		WHEEL				= 7;			// ID = 7
																	
	protected DFD__CMausBehaelter	obj;
	protected int				breite				= 0;
	protected int				hoehe				= 0;
	protected int				xPos				= 0;
	protected int				yPos				= 0;
	
	protected boolean			sichtbar			= true;
	
	protected double			zoomInhalt			= 1;
	
	protected String			anzeigeText			= "Anzeige";
	protected int				fontGroesse			= -1;
	protected String			farbe				= "schwarz";
	protected String			hintergrundFarbe	= null;
	
	/**
	 * Konstuktor fuer Hauptfenster
	 */
	public DFD__MausBehaelter() {
		this(Zeichnung.gibZeichenflaeche());
	}
	
	/**
	 * Konstruktor fuer Hauptfenster
	 * 
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public DFD__MausBehaelter(int neueBreite, int neueHoehe) {
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
	public DFD__MausBehaelter(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, neueBreite,
				neueHoehe);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 */
	public DFD__MausBehaelter(IContainer behaelter) {
		this(behaelter, 0, 0, 100, 50);
	}
	
	/**
	 * allgemeiner Konstuktor
	 * 
	 * @param behaelter
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public DFD__MausBehaelter(IContainer behaelter, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		cObjectSetzen();
		if (behaelter != null) {
			behaelter.add(obj, 0);
			setzeDimensionen(neuesX, neuesY, neueBreite, neueHoehe);
			behaelter.validate();
		}
	}
	
	public void cObjectSetzen() {
		obj = new DFD__CMausBehaelter();
	}
	
	public void setzeBeschreibungstext(String neuerText) {
		anzeigeText = neuerText;
		obj.setText(anzeigeText);
	}
	
	public void setzeSchriftgroesse(int neueFontgroesse) {
		fontGroesse = neueFontgroesse;
		obj.setzeSchriftgroesse(fontGroesse);
	}
	
	public void setzeFarbe(String neueFarbe) {
		farbe = neueFarbe;
		obj.setzeSchriftFarbe(farbe);
	}
	
	public void setzeHintergrundfarbe(String neueFarbe) {
		hintergrundFarbe = neueFarbe;
		obj.setzeHintergrundfarbe(hintergrundFarbe);
	}
	
	public void setzeMitRand(boolean mitRand) {
		obj.setzeMitRand(mitRand);
	}
	
	/**
	 * Das Interface IComponente fordert eine Methode die eine BasisComponente
	 * zurueckliefert. Sie wird benoetigt, um ein Objekt zu einem anderen
	 * Behaelter hinzuzufuegen
	 */
	public BasisComponente getBasisComponente() {
		return obj;
	}
	
	/**
	 * fuegt eine Komponente der Toolbox zu Behaelter
	 * 
	 * @param comp
	 */
	public void hinzufuegen(IComponente comp) {
		comp.getBasisComponente().ausContainerEntfernen();
		obj.add(comp.getBasisComponente(), 0);
	}
	
	public void hinzufuegenUndAnpassen(IComponente comp) {
		comp.getBasisComponente().ausContainerEntfernen();
		obj.add(comp.getBasisComponente(), 0);
		comp.getBasisComponente().verschieben(-xPos, -yPos);
	}
	
	public double setzeZoomfaktor(double zf) {
		zoomInhalt = obj.setzeZoomfaktor(zf);
		return zoomInhalt;
	}
	
	/**
	 * Setzen des KommunikationsIDs. Der ID wird beim Aufruf von tuWas zur&uuml;ckgegeben und kann den Aufrufer indentifizieren<br/>
	 * 
	 * @param ID
	 */
	public void setzeID(int ID) {
		obj.setzeID(ID);
	}
	
	/**
	 * Setzen des Kommunikationslinks<br/>
	 * 
	 * @param linkObj
	 */
	public void setzeLink(ITuWas linkObj) {
		obj.setzeLink(linkObj);
	}
	
	/**
	 * Setzen des Kommunikationslinks<br/>
	 * Setzen des KommunikationsIDs. Der ID wird beim Aufruf von tuWas zur&uuml;ckgegeben und kann den Aufrufer indentifizieren<br/>
	 * 
	 * @param linkObj
	 * @param ID
	 */
	public void setzeLink(ITuWas linkObj, int ID) {
		obj.setzeLink(linkObj, ID);
	}
	
	/**
	 * MauseEreignisse zuruecksetzen
	 */
	public void ruecksetzenMaus() {
		obj.setMouseAction(0);
	}
	
	/**
	 * Aktiviert MausClickEreignis
	 */
	public void setzeMausClick() {
		obj.setMouseAction(obj.getMouseAction() | (1 << CLICK));
	}
	
	/**
	 * Aktiviert MausPress- und -ReleaseEreignis
	 */
	public void setzeMausPressRelease() {
		obj.setMouseAction(obj.getMouseAction() | (1 << PRESS) | (1 << RELEASE));
	}
	
	/**
	 * Betreten und Verlassen des Behaelters: <br/>
	 * Aktiviert MausEnter und -ExitEreignis
	 */
	public void setzeMausEnterExit() {
		obj.setMouseAction(obj.getMouseAction() | (1 << ENTER) | (1 << EXIT));
	}
	
	/**
	 * Aktiviert MausBewegung ohne und mit gedr&uuml;ckter Maustaste
	 */
	public void setzeMausDraggedMoved() {
		obj.setMouseAction(obj.getMouseAction() | (1 << DRAGGED) | (1 << MOVED));
	}
	
	/**
	 * Aktiviert MausRadEreignis
	 */
	public void setzeMausRad() {
		obj.setMouseAction(obj.getMouseAction() | (1 << WHEEL));
	}
	
	/**
	 * Aktiviert MausEreignisse
	 * 
	 * @param ereignisse
	 *            Der Parametere ereignisse ist ein Mathematische oder "|"  aus
<pre>
        1 << MausBehaelter.CLICK
        1 << MausBehaelter.PRESS
        1 << MausBehaelter.RELEASE
        1 << MausBehaelter.ENTER
        1 << MausBehaelter.EXIT
        1 << MausBehaelter.DRAGGED
        1 << MausBehaelter.MOVED
        1 << MausBehaelter.WHEEL
</pre>
	 *            Keine Kontrolle ueber zulaessige Werte !
	 */
	public void setzeMausereignisse(int ereignisse) {
		obj.setMouseAction(ereignisse);
	}
	
	/**
	 * Aktiviert alle Mausereignisse
	 */
	public void setzeAlleMausereignisse() {
		obj.setMouseAction((1 << DFD__MausBehaelter.CLICK)
				| (1 << DFD__MausBehaelter.PRESS) | (1 << DFD__MausBehaelter.RELEASE)
				| (1 << DFD__MausBehaelter.ENTER) | (1 << DFD__MausBehaelter.EXIT)
				| (1 << DFD__MausBehaelter.DRAGGED) | (1 << DFD__MausBehaelter.MOVED)
				| (1 << DFD__MausBehaelter.WHEEL));
	}
	
	/**
	 * Status der Komponente abrufen und ruecksetzen
	 */
	public boolean mausAktion() {
		return obj.isAction();
	}
	
	public int getMX() {
		return obj.getMX();
	}
	
	public int getMY() {
		return obj.getMY();
	}
	
	public int getClickCount() {
		return obj.getClickCount();
	}
	
	public int getButton() {
		return obj.getButton();
	}
	
	public boolean getShift() {
		return obj.getShift();
	}
	
	public boolean getCtrl() {
		return obj.getCtrl();
	}
	
	public boolean getAlt() {
		return obj.getAlt();
	}
	
	public int getRotation() {
		return obj.getRotation();
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	/**
	 * Groesse des Anzeigefelds aendern *
	 */
	public void setzeGroesse(int neueBreite, int neueHoehe) {
		breite = neueBreite;
		hoehe = neueHoehe;
		obj.setzeGroesse((int) (breite / zoomInhalt),
				(int) (hoehe / zoomInhalt));
	}
	
	/**
	 * neue Position
	 * 
	 * @param neuesX
	 * @param neuesY
	 */
	public void setzePosition(int neuesX, int neuesY) {
		xPos = neuesX;
		yPos = neuesY;
		obj.setzePosition((int) (xPos / zoomInhalt), (int) (yPos / zoomInhalt));
	}
	
	/**
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public void setzeDimensionen(int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		xPos = neuesX;
		yPos = neuesY;
		breite = neueBreite;
		hoehe = neueHoehe;
		obj.setzeDimensionen((int) (xPos / zoomInhalt),
				(int) (yPos / zoomInhalt), (int) (breite / zoomInhalt),
				(int) (hoehe / zoomInhalt));
	}
	
	public void verschieben(int dx, int dy) {
		setzePosition(xPos + dx, yPos + dy);
	}
	
	/**
	 * Mache sichtbar.
	 */
	public void sichtbarMachen() {
		sichtbar = true;
		obj.sichtbarMachen();
	}
	
	
	public boolean isSichtbar(){
		return sichtbar;
	}
	/**
	 * Mache unsichtbar.
	 */
	public void unsichtbarMachen() {
		sichtbar = false;
		obj.unsichtbarMachen();
	}
	
	public void setzeMitRaster(boolean mitRaster) {
		obj.setzeMitRaster(mitRaster);
	}
	
	public void setzeDeltaX(int deltaX) {
		obj.setzeDeltaX(deltaX);
	}
	
	public void setzeDeltaY(int deltaY) {
		obj.setzeDeltaY(deltaY);
	}
	
	/**
	 * Fuer Interface IContainer
	 */
	public Component add(Component comp, int index) {
		return this.obj.add(comp, index);
	}
	
	public void setzeKomponentenKoordinaten(JComponent obj, int x, int y,
			int width, int height) {
		this.obj.setzeKomponentenKoordinaten(obj, x, y, width, height);
	}
	
	public void setzeKomponentenGroesse(JComponent obj, int width, int height) {
		this.obj.setzeKomponentenGroesse(obj, width, height);
	}
	
	public void setzeKomponentenPosition(JComponent obj, int x, int y) {
		this.obj.setzeKomponentenPosition(obj, x, y);
	}
	
	public void validate() {
		obj.validate();
	}
	
	/*
	 * Ende Interface IContainer
	 */
	/**
	 * gibt z.B. fuer den Dialog das JPanel-Objekt zurueck
	 */
	public JPanel getPanel() {
		return obj;
	}
	
	public double getBehaelterZoom() {
		return obj.getBehaelterZoom();
	}
	
	/**
	 * Entfernen des Graphikobjekts
	 */
	public void entfernen() {
		if (obj != null) {
			obj.ausContainerEntfernen();
			obj = null;
		}
	}
	
	/**
	 * Destruktor
	 */
	protected void finalize() {
	if (!Zeichnung.verweistesGUIElementEntfernen) return;
		if (obj != null) entfernen();
	}
	
}

