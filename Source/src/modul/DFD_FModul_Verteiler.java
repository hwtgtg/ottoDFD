package modul;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import dfd_gui.DFD_Arbeitsfenster;
import jpToolbox.DFD_MausBehaelterLayered;
import modul.DFD__IG_Modul.Eingangsstatus;

public class DFD_FModul_Verteiler implements DFD__IF_Modul {

	/**
	 * Nummer zum Speichern und für Verbindung zwischen Moduln beim Laden
	 */
	private int dfd_fModulNummer = -1;

	DFD_GModul_Verteiler gModulVerteiler;

	Vector<DFD_FAusgang> fModuleAmAusgang = null;

	DFD__IF_Modul fAusgangAmEingang = null;

	DFD__Daten datenAmAusgang = null;

	@Override
	public String getPopupBezeichnung() {
		return "Verteiler";
	}

	@Override
	public boolean isPopupWithCopy() {
		return false;
	}

	@Override
	public boolean isPopupWithEdit() {
		return false;
	}

	public DFD_FModul_Verteiler(int neuesX, int neuesY) {
		this(neuesX, neuesY, false);
	}

	public DFD_FModul_Verteiler(int neuesX, int neuesY, boolean ZENTRIERT) {
		datenAmAusgang = new DFD__Daten();
		datenAmAusgang.setGueltig(!gueltig);

		fModuleAmAusgang = new Vector<DFD_FAusgang>();

		erzeugeAnzeige(DFD_Arbeitsfenster.getArbeitsfenster(), neuesX, neuesY, ZENTRIERT);
		add_FModul();
	}

	public void erzeugeAnzeige(DFD_MausBehaelterLayered behaelter, int neuesX, int neuesY, boolean ZENTRIERT) {
		gModulVerteiler = new DFD_GModul_Verteiler(behaelter, neuesX, neuesY, ZENTRIERT);
		gModulVerteiler.recall(this);
	}

	@Override
	public void setzeAusgangGueltig() {
		for (DFD_FAusgang fModul : fModuleAmAusgang) {
			if (datenAmAusgang.gueltig) {
				fModul.fremderAusgang.signalUeberEingang_Gueltig(fModul.eingangsnummer);
			} else {
				fModul.fremderAusgang.signalUeberEingang_UnGueltig(fModul.eingangsnummer);
			}
		}
	}

	@Override
	public void setzeAusgangUngueltig() {
		datenAmAusgang.setGueltig(!gueltig);
		for (DFD_FAusgang fModul : fModuleAmAusgang) {
			if (fModul.fremderAusgang != null) {
				fModul.fremderAusgang.signalUeberEingang_UnGueltig(fModul.eingangsnummer);
			}
		}
	}

	@Override
	public void add_FModul() {
		DFD__FModulverwalter.getModulverwalter().add_FModul(this);
	}

	@Override
	public void remove_FModul() {
		DFD__FModulverwalter.getModulverwalter().removeFmodul(this);
	}

	@Override
	public DFD__IG_Modul get_GModul() {
		return gModulVerteiler;
	}

	@Override
	public int getDfd_fModulNummer() {
		return dfd_fModulNummer;
	}

	@Override
	public void setDfd_fModulNummer(int dfd_fModulNummer) {
		this.dfd_fModulNummer = dfd_fModulNummer;
	}

	/**
	 * Wird aufgerufen über Ausgang von Fremdmodul und über Eingang Verbinden
	 */
	@Override
	public void signalUeberEingang_Gueltig(int eingangsnummer) {
		gModulVerteiler.setzeEingangsstatus(eingangsnummer, fAusgangAmEingang.leseDatenAmAusgang().istError()?Eingangsstatus.ERROR:Eingangsstatus.OK);
		gModulVerteiler.setzeAusgangsBezeichnungen(fAusgangAmEingang.leseDatenAmAusgang());

		setzeAusgangUngueltig();
		// Entkoppeln vom Eingang
		datenAmAusgang = new DFD__Daten(fAusgangAmEingang.leseDatenAmAusgang());
		setzeAusgangGueltig();
	}

	@Override
	public void signalUeberEingang_UnGueltig(int eingangsnummer) {
		gModulVerteiler.setzeEingangsstatus(eingangsnummer, Eingangsstatus.Verbunden);
		gModulVerteiler.setzeAusgangsBezeichnungen(DFD__Daten.getDatenUngueltig());

		setzeAusgangUngueltig();
	}

	@Override
	public void signalEingang_NichtVerbunden(int eingangsnummer) {
		gModulVerteiler.setzeEingangsstatus(eingangsnummer, Eingangsstatus.Frei);
		gModulVerteiler.setzeAusgangsBezeichnungen(DFD__Daten.getDatenUngueltig());
		setzeAusgangUngueltig();
	}

	@Override
	public void signalEingang_Verbunden(int eingangsnummer) {
		signalUeberEingang_UnGueltig(eingangsnummer);
	}

	// Anzeigeart setzen
	@Override
	public void setzeTooltipAnzeigeWert() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setzeTooltipAnzeigeAnzeige() {
		// TODO Auto-generated method stub

	}

	// Aufruf über G-Module
	@Override
	public void loeseFModulEingangVonFremdemAusgang(int eingangsnummer) {
		this.fAusgangAmEingang = null;
		signalEingang_NichtVerbunden(eingangsnummer);

	}

	@Override
	public void loeseAusgangVonFremdemEingang(int eingangsnummer) {

	}

	@Override
	public void dateiReaderVerbindeFModulEingangMitFremdenFAusgang(int eingangsnummer, DFD__IF_Modul fremderAusgang) {
		gModulVerteiler.verbindeGModulMitfremdemGModul(0, fremderAusgang.get_GModul());
	}

	/**
	 * Wird aufgerufen von einem gEingang
	 */
	@Override
	public void verbindeFModulEingangMitFremdenFAusgang(DFD__IF_Modul fremderAusgang, int eingangsnummer) {
		this.fAusgangAmEingang = fremderAusgang;
	}

	// Daten von anderem Modul lesen

	@Override
	public DFD__Daten leseDatenAmAusgang() {
		return datenAmAusgang;
	}

	public void verbindeFAusgangMitFremdenFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		fModuleAmAusgang.addElement(new DFD_FAusgang(fModulEingang, eingangsnummer));
	}

	public void loeseFAusgangVomFremdenFEingang(DFD__IF_Modul fModulEingang, int eingangsnummer) {
		DFD_FAusgang tmp = null;

		for (int i = 0; i < fModuleAmAusgang.size(); i++) {
			tmp = fModuleAmAusgang.get(i);
			if ((tmp.fremderAusgang == fModulEingang) && (tmp.eingangsnummer == eingangsnummer)) {
				fModuleAmAusgang.remove(tmp);
				break;
			}
		}
	}

	public void setzeModulPosition(int posX, int posY) {
		gModulVerteiler.setzeModulPosition(posX, posY);
	}

	@Override
	public int leseAnzahlEingaenge() {
		return 1;
	}

	@Override
	public int getDfd_fModulNummerAmEingang(int i) {
		if (fAusgangAmEingang == null) {
			return -1;
		} else {
			return fAusgangAmEingang.getDfd_fModulNummer();
		}
	}

	@Override
	public void modulLoeschen() {
		// Verbindungen zu Eingang und Ausgängen löschen über GModul
		// GUI löschen
		gModulVerteiler.modulLoeschen();

		// Aus Verwalterliste löschen
		remove_FModul();

	}

	@Override
	public Rectangle leseRechteck() {
		return gModulVerteiler.leseRechteck();
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {
		gModulVerteiler.bildZeichnen(g2, dimDFD);
	}

	@Override
	public void bildZeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		gModulVerteiler.bildZeichneVerbindungen(g2, dimDFD);
	}

}
