package modul;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;

import jpToolbox.DFD_MausBehaelterLayered;

// Oberklasse für DFD_Module

public interface DFD__IG_Modul {

	// Für Modulauswahl
	public void add_GModul(DFD__IG_Modul gmodul);

	public void setzeAusgewaehlt();

	public void setzeNichtAusgewaehlt();

	// Modul-Methoden
	public enum Eingangsstatus {
		OK, ERROR, Verbunden, Frei, HERVORGEHOBEN, ABGESCHWAECHT
	}

	DFD_MausBehaelterLayered getBehaelter();

	Point getPointEingangPixel(int nr);

	Point getPointAusgangPixel(int nr);

	public Area getAusgangArea();

	void setzeAusgangsBezeichnungen(DFD__Daten daten);

	void setzeModulbezeichnung(String text);

	void setzeEingangsbezeichnung(int nr, String text, String farbe);

	void setzeEingangsstatus(int nr, Eingangsstatus status);

	public DFD__GEingang getGui_eingang(int eingangsnummer);

	public DFD__GAusgang getAusgang();

	public void andereModuleDeaktivieren();

	public DFD__IF_Modul getFmodul();

	public void loese_FModulVomFremdenAusgang(int eingangsnummer);

	public void loese_GModulEingangVomFremdenGModulAusgang(int eingangsnummer, DFD__IG_Modul ausgangGModul);

	public void verbinde_GModulEingangMitFremdemGModulAusgang(int eingangsnummer, DFD__IG_Modul ausgangGModul);

	public void verbindeFuGModulAusgangMitNeuemFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer);

	public void loeseFuGModulAusgangvomFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer);

	public void verbinde_FModulMitFremdenAusgang(DFD__IG_Modul fremdesGModul, int eingangsnummer);

	// POPUP-Funktionen
	public void pop_copy();

	public void pop_delete();

	public void pop_edit();

	public void pop_CopyToolstring();

	public void pop_CopyTabellenkalkulatorstring();

	void remove_GModul(DFD__IG_Modul gmodul);

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD);

	Rectangle leseRechteck();


	
}
