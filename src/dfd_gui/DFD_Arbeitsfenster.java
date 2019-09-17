package dfd_gui;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.print.Printable;
import java.io.IOException;

import dfd_gui.zoom.DFD__MausBehaelter;
import funktionenBaum.FW_Module_ImBaum;
import funktionenBaum.FW__FunktionsKlasse;
import funktionenBaum.dnd.DFD_TransferHandlerDropArbeitsfenster;
import funktionenBaum.dnd.DFD_Transferable;
import global.CHANGE;
import global.DFD__GUIKONST;
import global.StartUmgebung;
import jpToolbox.DFD_MausBehaelterLayered;
import jtoolbox.BehaelterScroll;
import jtoolbox.IContainer;
import jtoolbox.ITuWas;
import jtoolbox.Zeichnung;
import modul.DFD__GModulverwalter;
import modul.fw_modul.DFD__FunktionWork;
import modul.gui_modul.DFD_ArbeitsfensterArbeit;

public class DFD_Arbeitsfenster extends DFD_MausBehaelterLayered implements ITuWas, DropTargetListener {

	private static DFD_Arbeitsfenster arbeitsfenster;
	private static BehaelterScroll scroller;

	public DFD_ArbeitsfensterArbeit arbeit;

	public static DFD_ArbeitsfensterArbeit getArbeiter() {
		return getArbeitsfenster().arbeit;
	}

	public static DFD_Arbeitsfenster getArbeitsfenster() {
		if (arbeitsfenster == null) {
			scroller = new BehaelterScroll(DFD_G_Hauptfenster.getHauptfenster().hauptfensterSplit.getBehaelterRU(), 0,
					0, 1000, 1000);
			scroller.expand();
			arbeitsfenster = new DFD_Arbeitsfenster(scroller);
			arbeitsfenster.setzeMitRand(true);
			scroller.setzeScrollbarDimension(StartUmgebung.bildschirmFaktor(StartUmgebung.scrollbarBreite));
		}
		arbeitsfenster.setzeFokusierbar(true);

		arbeitsfenster.getBasisComponente().setTransferHandler(new DFD_TransferHandlerDropArbeitsfenster());

		return arbeitsfenster;
	}

	private DFD_Arbeitsfenster(IContainer behaelter) {
		super(behaelter, 0, 0, 1000, 1500);

		arbeitsfenster = this;
		DFD__GUIKONST.zoomAnpassen();

		arbeit = new DFD_ArbeitsfensterArbeit(this);
		// Für Mausaktionen
		setzeMausereignisse((1 << DFD__MausBehaelter.PRESS) | (1 << DFD__MausBehaelter.RELEASE)
				| (1 << DFD__MausBehaelter.DRAGGED) | (1 << DFD__MausBehaelter.MOVED));

		setzeLink(this);

		setzeSchriftgroesse(DFD__GUIKONST.DFD_FONT_GROESSE);
		setzeSchriftStilFett();
		
		aktiviereDroptarget();
		DFD__GUIKONST.rasterAnpassen();

		new PopUpHauptfenster();

		spezial();

	}

	public static void rasterBearbeiten(boolean mitRaster) {
		arbeitsfenster.setzeMitRaster(mitRaster);
		if (mitRaster) {
			arbeitsfenster.setzeRasterfarbe(DFD__GUIKONST.Rasterfarbe);
			arbeitsfenster.setzeDeltaX(DFD__GUIKONST.fm_Horizontal_RASTER / 2);
			arbeitsfenster.setzeDeltaY(DFD__GUIKONST.fm_Vertikal_RASTER * 2);
		}
	}

	// Startposition der Komponente beim Druecken der Taste
	int mPposx = 0;
	int mPposy = 0;
	boolean draggEnabled = false;

	@Override
	public void tuWas(int ID) {
		if (ID == (DFD__MausBehaelter.PRESS)) {
			mPposx = getMX();
			mPposy = getMY();
			draggEnabled = true;

		} else if (ID == (DFD__MausBehaelter.RELEASE)) {
			// Aktion beenden
			draggEnabled = false;
			DFD__GModulverwalter.getModulverwalter().setzeAlleModuleNichtAusgewaehlt();

		} else if (ID == (DFD__MausBehaelter.DRAGGED)) {
			// TODO
		} else if (ID == (DFD__MausBehaelter.MOVED)) {

		}
	}

	private void spezial() {
		// Spezial , zum Testen
		// ----------------------------------------------------
		// StartUmgebung.DEBUG = true;
		Zeichnung.maximiere();

		// DFD_FModul_Beschreibung besch1 = new DFD_FModul_Beschreibung(300,
		// 210, 300, 200);
		//// besch1.setzeEingangswert("wahr");

		// DFD_FModul_Konstante kon1 = new DFD_FModul_Konstante(300, 210);
		// kon1.setzeEingangswert("wahr");

		// @SuppressWarnings("unused")
		// DFD_GModul_Beschreibung textmodul = new DFD_GModul_Beschreibung(this,
		// 50, 300, 200, 100);
		//
		// DFD_FModul_Verteiler vert1 = new DFD_FModul_Verteiler(200, 300);
		// vert1.setDfd_fModulNummer(1);
		// DFD_FModul_Verteiler vert2 = new DFD_FModul_Verteiler(200, 520);
		// vert2.setDfd_fModulNummer(2);
		//
		// DFD_FModul_Eingabe ein1 = new DFD_FModul_Eingabe(300, 210);
		// ein1.setDfd_fModulNummer(11);
		// ein1.setzeEingangswert("wahr");
		//
		// DFD_FModul_Eingabe ein2 = new DFD_FModul_Eingabe(400, 80);
		// ein2.setDfd_fModulNummer(12);
		// ein2.setzeEingangswert("Text");
		//
		// DFD_FModul_Eingabe ein3 = new DFD_FModul_Eingabe(500, 210);
		// ein3.setDfd_fModulNummer(13);
		// ein3.setzeEingangswert("3,5");

		// DFD_FModul_Eingabe ein4 = new DFD_FModul_Eingabe(600, 80);
		// ein4.setDfd_fModulNummer(14);
		// ein4.setzeEingangswert("3,5");
		//
		// DFD_FModul_Eingabe ein5 = new DFD_FModul_Eingabe(700, 10);
		// ein5.setDfd_fModulNummer(15);
		// ein5.setzeEingangswert("8");
		//
		// DFD_FModul_Anzeige aus1 = new DFD_FModul_Anzeige(350, 550);
		// aus1.setDfd_fModulNummer(21);
		//
		// DFD_FModul_Anzeige aus2 = new DFD_FModul_Anzeige(550, 550);
		// aus2.setDfd_fModulNummer(22);

		// DFD_FModul_Anzeige aus3 = new DFD_FModul_Anzeige(150, 250);
		// aus3.setDfd_fModulNummer(23);
		//
		// FW_A_PLUS plus01 = new FW_A_PLUS();
		// DFD_FModul_Funktion f01 =
		// DFD_FModul_Funktion.erzeugeFunktionsmodul(plus01, 700, 150);
		// f01.setDfd_fModulNummer(31);
		//
		// FW_A_Minus minus01 = new FW_A_Minus();
		// DFD_FModul_Funktion f02 =
		// DFD_FModul_Funktion.erzeugeFunktionsmodul(minus01, 700, 300);
		// f02.setDfd_fModulNummer(32);
		//
		// FW_B_Wenn wenn01 = new FW_B_Wenn();
		// DFD_FModul_Funktion f03 =
		// DFD_FModul_Funktion.erzeugeFunktionsmodul(wenn01, 300, 350);
		// f03.setDfd_fModulNummer(33);
		//
		// DFD__FModulverwalter.verknuepfe(33, 0, 11);
		// DFD__FModulverwalter.verknuepfe(33, 1, 12);
		// DFD__FModulverwalter.verknuepfe(33, 2, 13);
		//
		// DFD__FModulverwalter.verknuepfe(22, 0, 2);
		//
		// DFD__FModulverwalter.verknuepfe(2, 0, 33);
		//

	}

	// ********************************************************* Droptarget

	private void aktiviereDroptarget() {

		@SuppressWarnings("unused")
		DropTarget dtg = new DropTarget(this.getBasisComponente(), this);
		this.getBasisComponente().setTransferHandler(new DFD_TransferHandlerDropArbeitsfenster());

	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {

			FW__FunktionsKlasse dfdmodul = (FW__FunktionsKlasse) dtde.getTransferable()
					.getTransferData(DFD_Transferable.dfdFlavor);

			if (FW_Module_ImBaum.existiert(dfdmodul.leseKlassenbezeichnung())) {
				DFD__FunktionWork.erzeugeModul(dfdmodul.leseKlassenbezeichnung(), dtde.getLocation().x,
						dtde.getLocation().y, 180, 40);
			}
			CHANGE.setChanged();
		} catch (final UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	public Printable getAnzeigeObjekt() {
		return obj;
	}

}
