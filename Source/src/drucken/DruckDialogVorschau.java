/**
 * 
 */
package drucken;

import jtoolbox.D_JGUIDialog;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;

import Dateioperationen.DFD_INI;
import LOG.LOG;
import dfd_gui.DFD_G_Menue;
import global.StartUmgebung;
import jtoolbox.Ausgabe;
import jtoolbox.Behaelter;
import jtoolbox.Combobox;
import jtoolbox.IContainer;
import jtoolbox.ITuWas;
import jtoolbox.Taste;

/**
 * @author otto
 *
 */
public class DruckDialogVorschau extends D_JGUIDialog {

	private static final long serialVersionUID = 5241907009951000805L;

	public static BufferedImage bufferedImage = null;
	
	static int btOKbreite = 100;
	static int btOKhoehe = 50;
	static int rand = 20;

	static int breite = 700;
	static int hoehe = 800;
	static int RandZumBildschirm = 10;

	private int bf(float wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	DruckvorschauAnwendung anwendung;
	// Dialog
	private static DruckDialogVorschau druckvorschau = null;
	// Container der Dialog-Komponente
	private IContainer behaelter;

	private Taste tDrucken;
	private Taste tAbbrechen;

	public static void oeffneDruckvorschau(BufferedImage bufferedImage) {
		if( bufferedImage== null){
			return;
		}
		DruckDialogVorschau.bufferedImage=bufferedImage;
		if (druckvorschau == null) {
			druckvorschau = new DruckDialogVorschau();
		}

		druckvorschau.setzeSichtbar(true);
	}

	/**
	 * @param titel
	 * @param modal
	 */
	private DruckDialogVorschau() {
		this("Druckvorschau");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param titel
	 * @param modal
	 */
	private DruckDialogVorschau(String titel) {
		super(titel, true);

		behaelter = this.leseContainer();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		breite = (int) ((width - RandZumBildschirm * 2) / StartUmgebung.bildFaktor);
		hoehe = (int) ((height - RandZumBildschirm * 2) / StartUmgebung.bildFaktor);

		this.setSize(bf(breite), bf(hoehe));

		tDrucken = new Taste(behaelter, "Drucken", bf(breite - rand - btOKbreite), bf(hoehe - rand - 1.5f * btOKhoehe),
				bf(btOKbreite), bf(btOKhoehe));
		tDrucken.setzeSchriftgroesse(StartUmgebung.getFont().getSize());
		tDrucken.setzeLink(this, DFD_G_Menue.VORSCHAU_DRUCKEN);

		tAbbrechen = new Taste(behaelter, "Abbrechen", bf(breite - 2 * (rand + btOKbreite)),
				bf(hoehe - rand - 1.5f * btOKhoehe), bf(btOKbreite), bf(btOKhoehe));
		tAbbrechen.setzeSchriftgroesse(StartUmgebung.getFont().getSize());
		tAbbrechen.setzeLink(this, DFD_G_Menue.VORSCHAU_ABBRECHEN);

		anwendung = new DruckvorschauAnwendung(behaelter, rand, rand, breite - 2 * rand,
				(int) (hoehe - rand - 2.2 * btOKhoehe));

	}

	private static float zoomfaktor = 1.0F;
//	private static float zoomfaktorAenderung = 1.25F;
	private static boolean bInigelesen = false;

	public static int intZoomWert(float wert) {
		return (int) Math.round(wert * getBfZoomfaktor());
	}

	public static float getZoomfaktor() {
		if (!bInigelesen) {
			zoomfaktor = DFD_INI.dfd_INIwerte.leseFloat("Darstellung", "zoomfaktor", 1.0F);
			bInigelesen = true;
		}
		return zoomfaktor;
	}

	public static float getBfZoomfaktor() {
		return getZoomfaktor() * StartUmgebung.bildFaktor;
	}

	public static String getTootipZoom(String tooltiptext) {
		int htmlsize = (int) (StartUmgebung.bildschirmFaktor(16) * 8);
		String tip = "<html><p style =\"font-size:" + htmlsize + "%\" >" + tooltiptext + "</font></p></html>";
		return tip;
	}

}

class DruckvorschauAnwendung extends Behaelter implements ITuWas {

	public static int hoehe = 150;

	Combobox drucker = null;
	PrinterJob printJob = null;
	String druckername;

	private static int bf(float wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	public DruckvorschauAnwendung(IContainer behaelter, int posX, int posY, int anwBreite, int anwHoehe) {
		super(behaelter, bf(posX), bf(posY), bf(anwBreite), bf(anwHoehe));
		this.setzeMitRand(true);
		this.setzeHintergrundfarbe("hellgrau");

		Ausgabe beschreibung = new Ausgabe(this, "Drucker: ", bf(0), bf(10), bf(90), bf(40));
		// beschreibung.setzeSchriftgroesse((int) (DialogZoom.getBfZoomfaktor()
		// * DFD__GUIKONST.DFD_FONT_GROESSE));
		beschreibung.setzeAusrichtung(2);

		drucker = new Combobox(this, bf(100), bf(10), bf(300), bf(40));
		// drucker.setzeSchriftgroesse((int) (DialogZoom.getBfZoomfaktor() *
		// DFD__GUIKONST.DFD_FONT_GROESSE));

		for (PrintService s : PrintServiceLookup.lookupPrintServices(null, null))
			drucker.textHinzufuegen(s.getName());

		printJob = PrinterJob.getPrinterJob();
		if (printJob != null) {
			druckername = printJob.getPrintService().getName();
			LOG.outln("Drucker: " + printJob.getPrintService().getName());
			drucker.setzeAuswahl(druckername);
		}
		drucker.setzeLink(this, DFD_G_Menue.VORSCHAU_DRUCKERAUSWAHL);
		printJob.setPrintable(new DFD_Drucken(DruckDialogVorschau.bufferedImage));


		PageFormat pageFormat = printJob.getPageFormat(null);
		
		LOG.outln("X:"+pageFormat.getImageableX()*72/2.54);
		LOG.outln("Y:"+pageFormat.getImageableY()*72/2.54);
		LOG.outln("Breite:"+pageFormat.getImageableWidth()*72/2.54);
		LOG.outln("Hoehe:"+pageFormat.getImageableHeight()*72/2.54);
	
		
		PrintRequestAttributeSet attr=new HashPrintRequestAttributeSet();
		
		printJob.pageDialog(attr);

		
		
		attr.add(OrientationRequested.LANDSCAPE);
		try {
			printJob.print(attr);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public void beenden() {

	}

	@Override
	public void tuWas(int ID) {
		if (ID == DFD_G_Menue.VORSCHAU_DRUCKERAUSWAHL) {
			druckername = drucker.leseAuswahl();
			PrintService erg = null;
			for (PrintService s : PrintServiceLookup.lookupPrintServices(null, null)) {
				if (s.getName().equalsIgnoreCase(druckername)) {
					erg = s;
				}
			}

			try {
				printJob.setPrintService(erg);
			} catch (PrinterException e) {

			}

			LOG.outln("default Drucker geändert: " + printJob.getPrintService().getName());

		}

	}

}
