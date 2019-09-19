package dfd_gui.zoom;

import java.awt.Rectangle;

public interface Zoom__IModul {

	void addZoomfenster();
	public void modulLoeschen();
	
	// Zoomt auf neuen Zoomfaktor ;
	// Berechnet zuerst Float-Werte und rechnet dann Integerwerte neu
	public void setzeAnzeigezoomNeu();
	
	// F�r �nderung des Arbeitfensters
	public boolean DFD_setzePosition(int posX, int posY);

	public boolean DFD_setzeGroesse(int breite, int hoehe);

	public boolean DFD_SetzeDimensionen(int posX, int posY, int breite, int hoehe);
	
	// Setzt Position und berechnet float-Positionswerte
	public Rectangle leseDimensionOhneZoom();

	public Rectangle leseDimensionInclZoom();

	// Setze ausgewaehlt f�r dfd
	public void setzeAusgewaehlt();

	public void setzeNichtAusgewaehlt();


}
