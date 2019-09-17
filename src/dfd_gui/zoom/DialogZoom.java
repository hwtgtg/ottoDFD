package dfd_gui.zoom;

import Dateioperationen.DFD_INI;
import global.StartUmgebung;
import jtoolbox.Ausgabe;
import jtoolbox.Behaelter;
import jtoolbox.D_JGUIDialog;
import jtoolbox.IContainer;
import jtoolbox.Taste;
import vToolbox.vSchiebereglerMitAnzeige;

@SuppressWarnings("serial")
public class DialogZoom extends D_JGUIDialog {

	private static final int BEENDEN = 1199;

	static int btOKbreite = 100;
	static int btOKhoehe = 50;
	static int rand = 20;

	static int breite = 600;
	static int hoehe = ZoomAnwendung.hoehe + rand + 2 * btOKhoehe;

	private int bf(float wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	ZoomAnwendung anwendung;
	// Dialog
	private static DialogZoom dialog = null;
	// Container der Dialog-Komponente
	private IContainer behaelter;

	private Taste ende;

	public static void oeffneDialogzoom() {
		if (dialog == null) {
			dialog = new DialogZoom("Zoom-Einstellungen", D_JGUIDialog.MODAL);
		}
		dialog.setzeZoomwert(zoomfaktor);
		dialog.setzeSichtbar(true);
	}

	private void setzeZoomwert(float zoomfaktor) {
		anwendung.setzeZoomwert(zoomfaktor);

	}

	private DialogZoom(String titel, boolean modal) {
		super(titel, modal);
		// Dialog anlegen
		// Ein modaler Dialog blockiert andere Fenster des Programms ! </br>
		// Dialoge werden verborgen, nicht geschlossen!
		// Behaelter fuer Dialogkomponenten lesen
		behaelter = this.leseContainer();
		this.setSize(bf(breite), bf(hoehe));

		anwendung = new ZoomAnwendung(behaelter);

		ende = new Taste(behaelter, "OK", bf(breite - rand - btOKbreite), bf(hoehe - rand - 1.5f * btOKhoehe),
				bf(btOKbreite), bf(btOKhoehe));
		ende.setzeSchriftgroesse(StartUmgebung.getFont().getSize());
		ende.setzeLink(this, BEENDEN);
	}

	private static float zoomfaktor = 1.0F;
	private static float zoomfaktorAenderung = 1.25F;
	private static boolean bInigelesen = false;

	public static int intZoomWert(float wert) {
		return (int) Math.round( wert * getBfZoomfaktor());
	}

	public static float getZoomfaktor() {
		if (!bInigelesen) {
			zoomfaktor = DFD_INI.dfd_INIwerte.leseFloat("Darstellung", "zoomfaktor", 1.0F);
			bInigelesen = true;
		}
		return zoomfaktor;
	}
	
	public static float getBfZoomfaktor(){
		return getZoomfaktor()*StartUmgebung.bildFaktor;
	}

	public static float getZoomfaktorInvers() {
		return 1.0F/zoomfaktor;
	}
	
	public static synchronized void setzeZoomfaktor(float wert) {
		zoomfaktor = wert;
		DFD_INI.dfd_INIwerte.setzeFloat("Darstellung", "zoomfaktor", zoomfaktor);
		Zoom__Verwalter.getZoomverwalter().zoomAnpassen();
	}

	public synchronized static void normal() {
		setzeZoomfaktor(1.0F);
	}

	public synchronized static void plus() {
		setzeZoomfaktor(getZoomfaktor() * zoomfaktorAenderung);
	}

	public synchronized static void minus() {
		setzeZoomfaktor(getZoomfaktor() / zoomfaktorAenderung);
	}

	public synchronized static void zoom() {
		oeffneDialogzoom();
	}

	public static String getTootipZoom(String tooltiptext) {
		int htmlsize = (int) (StartUmgebung.bildschirmFaktor(16) * 8);
		String tip = "<html><p style =\"font-size:" + htmlsize + "%\" >" + tooltiptext + "</font></p></html>";
		return tip;
	}

}

class ZoomAnwendung extends Behaelter {

	public static int hoehe = 150;

	private static int bf(float wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	public void setzeZoomwert(float zoomfaktor) {
		regler.setzeWert((int) (zoomfaktor * 100));
	}

	vSchiebereglerMitAnzeige regler;

	public ZoomAnwendung(IContainer behaelter) {
		super(behaelter, bf(DialogZoom.rand), bf(DialogZoom.rand), bf(DialogZoom.breite - 2 * DialogZoom.rand), hoehe);
		this.setzeMitRand(true);
		this.setzeHintergrundfarbe("hellgrau");

		// Bildschirmauflösung

		Ausgabe beschreibung = new Ausgabe(this, "Zoom: ", 0, 10, 200, 40);
		beschreibung.setzeAusrichtung(2);

		regler = new vSchiebereglerMitAnzeige(this, 202, 0, breite - 203, 60);
		regler.setzeBereich(10, 400, 100);
		regler.setzeTeilung(39);

	}

	public void beenden() {
		DialogZoom.setzeZoomfaktor((float) regler.leseWert() / 100);

		// D_Meldung dlg = new D_Meldung();
		// dlg.setzeTitel("Hinweis");
		// dlg.setzeMeldungstext("Auflösungsänderung erst nach Neustart!");
		// dlg.icon_hinweis();;
		// dlg.zeigeMeldung();

	}

}
