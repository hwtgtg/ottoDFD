package modul;

import java.awt.Graphics2D;
import java.awt.Rectangle;

// Oberklasse für DFD_Module

public interface DFD__IF_Modul {

	boolean gueltig = true;
	boolean error = true;

	// Für Modulauswahl
	// public void add_FModul(DFD__IF_Modul fModul);
	void remove_FModul();

	void add_FModul();

	public int getDfd_fModulNummer();

	public void setDfd_fModulNummer(int dfd_fModulNummer);

	/**
	 * wird vom fremden Ausgang aufgerufen
	 * 
	 * @param eingangsnummer
	 */
	public void signalUeberEingang_Gueltig(int eingangsnummer);

	/**
	 * wird vom fremden Ausgang aufgerufen
	 * 
	 * @param eingangsnummer
	 */
	public void signalUeberEingang_UnGueltig(int eingangsnummer);

	public void signalEingang_NichtVerbunden(int eingangsnummer);

	public void signalEingang_Verbunden(int eingangsnummer);

	void dateiReaderVerbindeFModulEingangMitFremdenFAusgang(int eingangsnummer, DFD__IF_Modul fremderAusgang);

	public void verbindeFModulEingangMitFremdenFAusgang(DFD__IF_Modul fremderAusgang, int eingangsnummer);

	public void loeseFModulEingangVonFremdemAusgang(int eingangsnummer);

	void loeseAusgangVonFremdemEingang(int eingangsnummer);

	void setzeAusgangGueltig();

	void setzeAusgangUngueltig();

	public DFD__IG_Modul get_GModul();

	// Lesen der Daten vom fremden Modul
	public DFD__Daten leseDatenAmAusgang();

	public void setzeTooltipAnzeigeWert();

	public void setzeTooltipAnzeigeAnzeige();

	public int leseAnzahlEingaenge();

	public int getDfd_fModulNummerAmEingang(int i);

	public String getPopupBezeichnung();

	public boolean isPopupWithCopy();

	public boolean isPopupWithEdit();

	public void modulLoeschen();

	public Rectangle leseRechteck();

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD);

	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD);

}
