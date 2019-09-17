package dfd_gui.klassenImMenue;

import Dateioperationen.DFD_INI;
import global.StartUmgebung;
import vToolbox.vSchiebereglerMitAnzeige;
import jtoolbox.Ausgabe;
import jtoolbox.Behaelter;
import jtoolbox.D_JGUIDialog;
import jtoolbox.D_Meldung;
import jtoolbox.IContainer;
import jtoolbox.Taste;

@SuppressWarnings("serial")
public class Dialogoptionen extends D_JGUIDialog {

	private static final int BEENDEN = 1199;

	static int btOKbreite = 100;
	static int btOKhoehe = 50;
	static int rand = 20;

	static int breite = 600;
	static int hoehe = OptionenAnwendung.hoehe + rand + 2 * btOKhoehe;

	private int bf(float wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	OptionenAnwendung anwendung;

	// Dialog
	private static Dialogoptionen dialog = null;
	// Container der Dialog-Komponente
	private IContainer behaelter;

	private Taste ende;

	private Dialogoptionen(String titel, boolean modal) {
		super(titel, modal);
		// Dialog anlegen
		// Ein modaler Dialog blockiert andere Fenster des Programms ! </br>
		// Dialoge werden verborgen, nicht geschlossen!
		// Behaelter fuer Dialogkomponenten lesen
		behaelter = this.leseContainer();
		this.setSize(bf(breite), bf(hoehe));

		anwendung = new OptionenAnwendung(behaelter);

		ende = new Taste(behaelter, "OK", bf(breite - rand - btOKbreite), bf(hoehe - rand - 1.5f * btOKhoehe),
				bf(btOKbreite), bf(btOKhoehe));
		ende.setzeSchriftgroesse(StartUmgebung.getFont().getSize());
		ende.setzeLink(this);
		ende.setzeID(BEENDEN);

	}

	public static void oeffneOptionenDialog() {
		if (dialog == null) {
			dialog = new Dialogoptionen("Einstellungen für DFD", D_JGUIDialog.MODAL);
		}
		dialog.setzeSichtbar(true);
	}
	@Override
	public void tuWas(int ID) {
		if (ID == BEENDEN) {
			anwendung.beenden();
			setzeSichtbar(false);
		}
	}

}

class OptionenAnwendung extends Behaelter {

	public static int hoehe = 150;

	private static int bf(float wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	vSchiebereglerMitAnzeige regler;

	public OptionenAnwendung(IContainer behaelter) {
		super(behaelter, bf(Dialogoptionen.rand), bf(Dialogoptionen.rand),
				bf(Dialogoptionen.breite - 2 * Dialogoptionen.rand), hoehe);
		this.setzeMitRand(true);
		this.setzeHintergrundfarbe("hellgrau");

		// Bildschirmauflösung

		Ausgabe beschreibung = new Ausgabe(this, "Bildschirmauflösung: ", 0, 10, 200, 40);
		beschreibung.setzeAusrichtung(2);

		regler = new vSchiebereglerMitAnzeige(this, 202, 0, breite - 203, 60);
		regler.setzeBereich(50, 350, 96);
		regler.setzeTeilung(50);

		regler.setzeWert((int) (StartUmgebung.bildFaktor * 100));
	}

	public void beenden() {
		// Bildfaktor darf nicht in Startumgebung gesetzt  werden.
		// Er darf erst nach Neustart verfügbar sein.
		DFD_INI.dfd_INIwerte.setzeFloat("GUI", "bildfaktor", (float) regler.leseWert()/100);

		D_Meldung dlg = new D_Meldung();
		dlg.setzeTitel("Hinweis");
		dlg.setzeMeldungstext("Auflösungsänderung erst nach Neustart!");
		dlg.icon_hinweis();;
		dlg.zeigeMeldung();
	}

}