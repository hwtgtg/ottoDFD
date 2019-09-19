package dfd_gui.klassenImMenue;

import global.StartUmgebung;
import jtoolbox.D_JGUIDialog;
import jtoolbox.IContainer;
import jtoolbox.Taste;
import jtoolbox.TextfensterFarbe;

@SuppressWarnings("serial")
public class DialogUeber extends D_JGUIDialog {

	private static int breite = 500;
	private static int hoehe = 500;

	private static int rand = 20;

	private int btOKbreite = 100;
	private int btOKhoehe = 50;

	private int bf(float wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	// Dialog

	private static DialogUeber dialog = null;
	// Container der Dialog-Komponente
	private IContainer behaelter;

	TextfensterFarbe ausgabe;

	private Taste ende;

	private DialogUeber(String titel, boolean modal) {
		super(titel, modal);
		// Dialog anlegen
		// Ein modaler Dialog blockiert andere Fenster des Programms ! </br>
		// Dialoge werden verborgen, nicht geschlossen!
		// Behaelter fuer Dialogkomponenten lesen
		behaelter = this.leseContainer();

		// Groesse des Dialogfensters einstellen
		this.setSize(bf(breite), bf(hoehe));
		this.zentrieren();
		this.setFont(StartUmgebung.getFont());

		ausgabe = new TextfensterFarbe(behaelter, bf(rand), bf(rand), bf(breite-2*rand), hoehe-rand- 2* btOKhoehe);
		
		ausgabe.setzeSchriftgroesse(StartUmgebung.getFont().getSize());
		ausgabe.setzeNurAnzeige();
		ausgabe.println("DFD");
		ausgabe.println("");
		ausgabe.println("Erstellen eines Datenflussdiagramms");
		ausgabe.println("Version 1.2");
		ausgabe.println("");
		ausgabe.println("");
		ausgabe.println("Autor:");
		ausgabe.println("Hans Witt");
		ausgabe.println("Email: hans.witt@pluto.ffb.shuttle.de");

		ende = new Taste(behaelter, "OK", bf(breite - rand - btOKbreite) , bf(hoehe-rand-1.5f*btOKhoehe), bf(btOKbreite), bf(btOKhoehe));
		ende.setzeSchriftgroesse(StartUmgebung.getFont().getSize());
		ende.setzeLink(this);
	}

	/**
	 * Der Dialog wird sichtbar. Der modale Dialog blokiert die Eingabe des
	 * Hauptprogramms
	 */
	private void setzeSichtbar() {
		// Dialog anzeigen
		this.setVisible(true);
	}

	public static void oeffneDialog() {
		if (dialog == null) {
			dialog = new DialogUeber("Über .. ", D_JGUIDialog.MODAL);
		}
		dialog.setzeSichtbar();
	}
}
