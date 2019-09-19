package dfd_gui;

import global.CHANGE;
import global.LOCK_MENUE;
import global.StartUmgebung;

import java.awt.image.BufferedImage;

import Dateioperationen.ExitSave;
import Dateioperationen.FunktionsReader;
import Dateioperationen.FunktionsWriter;
import bild.dfdBildErzeugen;
import bild.dfdZurZwischenablage;
import dfd_gui.klassenImMenue.DialogUeber;
import dfd_gui.klassenImMenue.Dialogoptionen;
import dfd_gui.zoom.DialogZoom;
import dfd_gui.zoom.Zoom__Verwalter;
import drucken.DruckDialogVorschau;
import drucken.dfdDrucken;
import jtoolbox.ITuWas;
import jtoolbox.Menue;
import jtoolbox.MenueEintrag;
import jtoolbox.MenueLeiste;
import modul.DFD__FModulverwalter;

public class DFD_G_Menue implements ITuWas {

	// Dateimenue - 1100
	public static final int NEU = 1101;
	public static final int OEFFNEN = 1111;
	public static final int SPEICHERN = 1121;
	public static final int SPEICHERNUNTER = 1122;
	private static final int OPTIONEN = 1180;
	public static final int BEENDEN = 1199;

	public static final String AKTIVIERUNG_NEU = "ctrl N";
	public static final String AKTIVIERUNG_OEFFNEN = "ctrl O";
	public static final String AKTIVIERUNG_SPEICHERN = "ctrl S";
	public static final String AKTIVIERUNG_SPEICHERNUNTER = "ctrl U";
	private static final String AKTIVIERUNG_OPTIONEN = "alt O";
	public static final String AKTIVIERUNG_BEENDEN = "alt F4";

	// Hilfe - 1200
	public static final int HILFE = 1201;
	public static final int UEBER = 1202;

	public static final String AKTIVIERUNG_HILFE = "alt H";
	public static final String AKTIVIERUNG_UEBER = "alt A";

	// Bearbeiten - 1300
//	private static final int KOPIEREN = 1301;
//	private static final int EINFUEGEN = 1302;
	private static final int BILD_ZWISCHENABLAGE = 1303;

//	private static final String AKTIVIERUNG_KOPIEREN = "ctrl C";
//	private static final String AKTIVIERUNG_EINFUEGEN = "ctrl V";

	// ANSICHT - 1400
	public static final int VERGROESSERUNG_NORMAL = 1401;
	public static final int VERGROESSERUNG_VARIABEL = 1402;
	public static final int VERGROESSERUNG_PLUS = 1403;
	public static final int VERGROESSERUNG_MINUS = 1404;

	public static final String AKTIVIERUNG_VERGROESSERUNG_NORMAL = "ctrl 0";
	public static final String AKTIVIERUNG_VERGROESSERUNG_VARIABEL = "ctrl Z";
	public static final String AKTIVIERUNG_VERGROESSERUNG_PLUS = "ctrl PLUS";
	public static final String AKTIVIERUNG_VERGROESSERUNG_MINUS = "ctrl MINUS";

	// Teiler
	public static final String TEILERFARBE = "HELLGRAU";

	// RADIO-Anzeigeart - 1500
	public static final String R_ANZEIGE_FARBE = "HELLGRAU";
	public static final int R_AKTION_ANZEIGE = 1501;
	public static final int R_AKTION_WERT = 1502;
	// aktuelle Anzeigeart
	public static int anzeigeart = R_AKTION_WERT;

	// Schieberegler - 1600
	// wird direkt gelesen
	// public static final int REGLER_FLUSSVERZOEGERUNG = 1601;

	// Anzeige Gitter - 1700
	// wird direkt gelesen
	public static final int GRIDANZEIGE_MENU = 1701;
	public static final int GRIDANZEIGE = 1702;
	public static final int GRIDANZEIGELOESCHEN = GRIDANZEIGE + 1;
	private static final String AKTIVIERUNG_GRIDANZEIGE = "alt G";

	// Drucken - 1800
	public static final int DRUCKEN = 1801;
	private static final String AKTIVIERUNG_DRUCKEN = "ctrl P";
	private static final int DRUCKVORSCHAU = 1802;
	// private static final String AKTIVIERUNG_DRUCKVORSCHAU = "ctrl R";
	public static final int BILDERSTELLEN = 1803;
	private static final String AKTIVIERUNG_BILDERSTELLEN = "ctrl B";
	public static final int VORSCHAU_DRUCKEN = 1811;
	public static final int VORSCHAU_ABBRECHEN = 1812;
	public static int VORSCHAU_DRUCKERAUSWAHL = 1813; // ID in
														// DruckDialogVorschau

	// POPUP - 2000
	public static final int POP_COPY = 2001;
	public static final int POP_DELETE = 2002;
	public static final int POP_EDIT = 2003;
	public static final int POP_CopyToolstring = 2004;
	public static final int POP_CopyTabellenkalkulatorstring = 2005 ;

	private static DFD_G_Menue menu;

	public static DFD_G_Menue getMenu() {

		if (menu == null) {
			menu = new DFD_G_Menue();
		}
		return menu;
	}

	private DFD_G_Menue() {
		erzeugeMenue();
	}

	private void erzeugeMenue() {
		MenueLeiste menueleiste = new MenueLeiste();
		Menue m_Datei = new Menue("Datei", StartUmgebung.getFont());
		m_Datei.setzeMnemonik('d');
		m_Datei.setzeBeschreibung("Datei-Menü");
		menueleiste.menueEintragHinzufuegen(m_Datei);

		MenueEintrag mDatei_Neu = new MenueEintrag("Neu ... ", StartUmgebung.getFont());
		mDatei_Neu.setzeMnemonik('n');
		mDatei_Neu.setzeAktivierung(AKTIVIERUNG_NEU);
		mDatei_Neu.setzeLink(this, NEU);
		mDatei_Neu.setzeBeschreibung("neues Datenflussdiagramm");
		m_Datei.menueEintragHinzufuegen(mDatei_Neu);

		MenueEintrag mDatei_Oeffnen = new MenueEintrag("Oeffnen ... ", StartUmgebung.getFont());
		mDatei_Oeffnen.setzeMnemonik('o');
		mDatei_Oeffnen.setzeAktivierung(AKTIVIERUNG_OEFFNEN);
		mDatei_Oeffnen.setzeLink(this, OEFFNEN);
		mDatei_Oeffnen.setzeBeschreibung("Datenflussdiagramm oeffnen");
		m_Datei.menueEintragHinzufuegen(mDatei_Oeffnen);

		m_Datei.adSeparator();

		MenueEintrag mDatei_Speichern = new MenueEintrag("Speichern", StartUmgebung.getFont());
		mDatei_Speichern.setzeMnemonik('s');
		mDatei_Speichern.setzeAktivierung(AKTIVIERUNG_SPEICHERN);
		mDatei_Speichern.setzeLink(this, SPEICHERN);
		mDatei_Speichern.setzeBeschreibung("Datenflussdiagramm speichern");
		m_Datei.menueEintragHinzufuegen(mDatei_Speichern);

		MenueEintrag mDatei_SpeichernUnter = new MenueEintrag("Speichern unter ... ", StartUmgebung.getFont());
		mDatei_SpeichernUnter.setzeMnemonik('u');
		mDatei_SpeichernUnter.setzeAktivierung(AKTIVIERUNG_SPEICHERNUNTER);
		mDatei_SpeichernUnter.setzeLink(this, SPEICHERNUNTER);
		mDatei_SpeichernUnter.setzeBeschreibung("Datenflussdiagramm speichern unter ... ");
		m_Datei.menueEintragHinzufuegen(mDatei_SpeichernUnter);

		m_Datei.adSeparator();
		MenueEintrag mDatei_Drucken = new MenueEintrag("Drucken ", StartUmgebung.getFont());
		mDatei_Drucken.setzeMnemonik('D');
		mDatei_Drucken.setzeAktivierung(AKTIVIERUNG_DRUCKEN);
		mDatei_Drucken.setzeLink(this, DRUCKEN);
		mDatei_Drucken.setzeBeschreibung("Drucken");
		m_Datei.menueEintragHinzufuegen(mDatei_Drucken);

		// MenueEintrag mDatei_Druckvorschau = new MenueEintrag("Druckvorschau
		// ", StartUmgebung.getFont());
		// mDatei_Druckvorschau.setzeMnemonik('V');
		// mDatei_Druckvorschau.setzeAktivierung(AKTIVIERUNG_DRUCKVORSCHAU);
		// mDatei_Druckvorschau.setzeLink(this, DRUCKVORSCHAU);
		// mDatei_Druckvorschau.setzeBeschreibung("Druckvorschau");
		// m_Datei.menueEintragHinzufuegen(mDatei_Druckvorschau);

		MenueEintrag mDatei_BildErzeugen = new MenueEintrag("Als Bild speichern", StartUmgebung.getFont());
		mDatei_BildErzeugen.setzeMnemonik('B');
		mDatei_BildErzeugen.setzeAktivierung(AKTIVIERUNG_BILDERSTELLEN);
		mDatei_BildErzeugen.setzeLink(this, BILDERSTELLEN);
		mDatei_BildErzeugen.setzeBeschreibung("Drucken");
		m_Datei.menueEintragHinzufuegen(mDatei_BildErzeugen);

		m_Datei.adSeparator();
		MenueEintrag mDatei_Programmeigenschaften = new MenueEintrag("Optionen", StartUmgebung.getFont());
		mDatei_Programmeigenschaften.setzeMnemonik('t');
		mDatei_Programmeigenschaften.setzeAktivierung(AKTIVIERUNG_OPTIONEN);
		mDatei_Programmeigenschaften.setzeLink(this, OPTIONEN);
		mDatei_Programmeigenschaften.setzeBeschreibung("Optionen für das Programm Datenflussdiagramm");
		m_Datei.menueEintragHinzufuegen(mDatei_Programmeigenschaften);

		m_Datei.adSeparator();

		MenueEintrag mDatei_Beenden = new MenueEintrag("Beenden", StartUmgebung.getFont());
		mDatei_Beenden.setzeMnemonik('B');
		mDatei_Beenden.setzeAktivierung(AKTIVIERUNG_BEENDEN);
		mDatei_Beenden.setzeLink(this, BEENDEN);
		mDatei_Beenden.setzeBeschreibung("Programm DFD beenden");
		m_Datei.menueEintragHinzufuegen(mDatei_Beenden);

		// Menue me2 = new Menue("SubMenue");
		// me2.setzeMnemonik('s');
		// m_Datei.menueEintragHinzufuegen(me2);
		//
		// MenueCheckBox mc = new MenueCheckBox("chk");
		// mc.setzeMnemonik('C');
		// mc.setzeAktivierung("alt C");
		// mc.setzeLink(this, 10);
		// me2.menueEintragHinzufuegen(mc);

		// me2.setzeDeaktiviert();

		menueleiste.abstandHinzufuegen(20);

		Menue m_Bearbeiten = new Menue("Bearbeiten", StartUmgebung.getFont());
		m_Bearbeiten.setzeMnemonik('B');

		MenueEintrag mBearbeiten_Zwischenablage = new MenueEintrag("Kopieren->Zwischenablage", StartUmgebung.getFont());
		mBearbeiten_Zwischenablage.setzeMnemonik('K');
		mBearbeiten_Zwischenablage.setzeLink(this, BILD_ZWISCHENABLAGE);
		mBearbeiten_Zwischenablage.setzeBeschreibung("Kopieren eines Moduls");
		m_Bearbeiten.menueEintragHinzufuegen(mBearbeiten_Zwischenablage);

		// MenueEintrag mBearbeiten_Kopieren = new
		// MenueEintrag("Kopieren->Zwischenablage", StartUmgebung.getFont());
		// mBearbeiten_Kopieren.setzeMnemonik('K');
		// mBearbeiten_Kopieren.setzeAktivierung(AKTIVIERUNG_KOPIEREN);
		// mBearbeiten_Kopieren.setzeLink(this, KOPIEREN);
		// mBearbeiten_Kopieren.setzeBeschreibung("Kopieren eines Moduls");
		// m_Bearbeiten.menueEintragHinzufuegen(mBearbeiten_Kopieren);

		// MenueEintrag mBearbeiten_Einfuegen = new MenueEintrag("Einfügen",
		// StartUmgebung.getFont());
		// mBearbeiten_Einfuegen.setzeMnemonik('E');
		// mBearbeiten_Einfuegen.setzeAktivierung(AKTIVIERUNG_EINFUEGEN);
		// mBearbeiten_Einfuegen.setzeLink(this, EINFUEGEN);
		// mBearbeiten_Einfuegen.setzeBeschreibung("Einfüghen eines Moduls");
		// m_Bearbeiten.menueEintragHinzufuegen(mBearbeiten_Einfuegen);

		menueleiste.menueEintragHinzufuegen(m_Bearbeiten);
		menueleiste.abstandHinzufuegen(20);

		Menue m_Ansicht = new Menue("Ansicht", StartUmgebung.getFont());
		m_Ansicht.setzeMnemonik('A');

		MenueEintrag mAnsicht_Plus = new MenueEintrag("Vergrößern", StartUmgebung.getFont());
		mAnsicht_Plus.setzeMnemonik('g');
		mAnsicht_Plus.setzeAktivierung(AKTIVIERUNG_VERGROESSERUNG_PLUS);
		mAnsicht_Plus.setzeLink(this, VERGROESSERUNG_PLUS);
		mAnsicht_Plus.setzeBeschreibung("Vergrößern der Anssicht");
		m_Ansicht.menueEintragHinzufuegen(mAnsicht_Plus);

		MenueEintrag mAnsicht_Minus = new MenueEintrag("Verkleinern", StartUmgebung.getFont());
		mAnsicht_Minus.setzeMnemonik('k');
		mAnsicht_Minus.setzeAktivierung(AKTIVIERUNG_VERGROESSERUNG_MINUS);
		mAnsicht_Minus.setzeLink(this, VERGROESSERUNG_MINUS);
		mAnsicht_Minus.setzeBeschreibung("Verkleinern der Anssicht");
		m_Ansicht.menueEintragHinzufuegen(mAnsicht_Minus);

		MenueEintrag mAnsicht_Normal = new MenueEintrag("Normal", StartUmgebung.getFont());
		mAnsicht_Normal.setzeMnemonik('N');
		mAnsicht_Normal.setzeAktivierung(AKTIVIERUNG_VERGROESSERUNG_NORMAL);
		mAnsicht_Normal.setzeLink(this, VERGROESSERUNG_NORMAL);
		mAnsicht_Normal.setzeBeschreibung("Normale Anssicht");
		m_Ansicht.menueEintragHinzufuegen(mAnsicht_Normal);

		MenueEintrag mAnsicht_Variabel = new MenueEintrag("Zoom ...", StartUmgebung.getFont());
		mAnsicht_Variabel.setzeMnemonik('Z');
		mAnsicht_Variabel.setzeAktivierung(AKTIVIERUNG_VERGROESSERUNG_VARIABEL);
		mAnsicht_Variabel.setzeLink(this, VERGROESSERUNG_VARIABEL);
		mAnsicht_Variabel.setzeBeschreibung("Zoom einstellen");
		m_Ansicht.menueEintragHinzufuegen(mAnsicht_Variabel);

		m_Ansicht.adSeparator();

		MenueEintrag mAnsicht_Gitter = new MenueEintrag("Gitter ein/aus", StartUmgebung.getFont());
		mAnsicht_Gitter.setzeMnemonik('G');
		mAnsicht_Gitter.setzeAktivierung(AKTIVIERUNG_GRIDANZEIGE);
		mAnsicht_Gitter.setzeLink(this, GRIDANZEIGE_MENU);
		mAnsicht_Gitter.setzeBeschreibung("Gitter ein/aus");
		m_Ansicht.menueEintragHinzufuegen(mAnsicht_Gitter);

		menueleiste.menueEintragHinzufuegen(m_Ansicht);
		menueleiste.abstandHinzufuegen(20);

		// MenueRadioBehaelter mb = new MenueRadioBehaelter();
		//
		// MenueRadioTaste mr1 = new MenueRadioTaste("RT1");
		// mr1.setzeLink(this, 30);
		// mr1.setzeAktivierung("alt O");
		// MenueRadioTaste mr2 = new MenueRadioTaste("RT2");
		// mr2.setzeLink(this, 40);
		// mr2.setzeAktivierung("alt P");
		// mb.addMenueRadioTaste(mr1);
		// mb.addMenueRadioTaste(mr2);
		//
		// m_Bearbeiten.menueEintragHinzufuegen(mr1);
		// m_Bearbeiten.menueEintragHinzufuegen(mr2);

		menueleiste.fuellerHinzufuegen();

		Menue m_Hilfe = new Menue("Hilfe", StartUmgebung.getFont());
		m_Hilfe.setzeMnemonik('h');

		MenueEintrag m_Hilfe_Hilfe = new MenueEintrag("Hilfe", StartUmgebung.getFont());
		m_Hilfe_Hilfe.setzeMnemonik('h');
		m_Hilfe_Hilfe.setzeAktivierung(AKTIVIERUNG_HILFE);
		m_Hilfe.menueEintragHinzufuegen(m_Hilfe_Hilfe);

		MenueEintrag m_Hilfe_Ueber = new MenueEintrag("Über", StartUmgebung.getFont());
		m_Hilfe_Ueber.setzeMnemonik('b');
		m_Hilfe_Ueber.setzeAktivierung(AKTIVIERUNG_UEBER);
		m_Hilfe_Ueber.setzeLink(this, UEBER);
		m_Hilfe.menueEintragHinzufuegen(m_Hilfe_Ueber);

		menueleiste.menueEintragHinzufuegen(m_Hilfe);

		menueleiste.aktiviereMenue();

	}

	@Override
	public void tuWas(int ID) {
		switch (ID) {
		case NEU:
			if (LOCK_MENUE.testeLock()) {
				if (CHANGE.isChanged()) {
					FunktionsWriter.schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN_JaNein();
				}
				DFD__FModulverwalter.getModulverwalter().loescheAlleModule();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}
			break;
		case OEFFNEN:
			if (LOCK_MENUE.testeLock()) {
				if (CHANGE.isChanged()) {
					FunktionsWriter.schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN_JaNein();
				}
				DFD__FModulverwalter.getModulverwalter().loescheAlleModule();
				FunktionsReader.leseDFDMODULEundVERKNUEPFUNGEN();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case SPEICHERN:
			if (LOCK_MENUE.testeLock()) {
				FunktionsWriter.schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAME();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case SPEICHERNUNTER:
			if (LOCK_MENUE.testeLock()) {
				FunktionsWriter.schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case DRUCKEN:
			if (LOCK_MENUE.testeLock()) {
				dfdDrucken.getDrucken(dfdBildErzeugen.getBilderzeugen().bildErzeugen()).drucken();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case DRUCKVORSCHAU:
			if (LOCK_MENUE.testeLock()) {
				DruckDialogVorschau.oeffneDruckvorschau((dfdBildErzeugen.getBilderzeugen().bildErzeugen()));

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case BILDERSTELLEN:
			if (LOCK_MENUE.testeLock()) {
				dfdBildErzeugen.getBilderzeugen().bildSchreiben();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case OPTIONEN:
			if (LOCK_MENUE.testeLock()) {
				Dialogoptionen.oeffneOptionenDialog();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case BILD_ZWISCHENABLAGE:
			if (LOCK_MENUE.testeLock()) {
				BufferedImage bild = dfdBildErzeugen.getBilderzeugen().bildErzeugen();
				dfdZurZwischenablage.setClipboard(bild);
				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case VERGROESSERUNG_NORMAL:
			if (LOCK_MENUE.testeLock()) {
				DialogZoom.normal();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case VERGROESSERUNG_PLUS:
			if (LOCK_MENUE.testeLock()) {
				DialogZoom.plus();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case VERGROESSERUNG_MINUS:
			if (LOCK_MENUE.testeLock()) {
				DialogZoom.minus();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case VERGROESSERUNG_VARIABEL:
			if (LOCK_MENUE.testeLock()) {
				DialogZoom.zoom();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case R_AKTION_ANZEIGE:
			if (LOCK_MENUE.testeLock()) {
				DFD__FModulverwalter.getModulverwalter().setzeAlleModuleAnzeigeAnzeige();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case R_AKTION_WERT:
			if (LOCK_MENUE.testeLock()) {
				DFD__FModulverwalter.getModulverwalter().setzeAlleModuleAnzeigeWert();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case GRIDANZEIGE_MENU:
			if (LOCK_MENUE.testeLock()) {
				if (DFD_G_Hauptfenster.getHauptfenster().leseMitGitter()) {
					DFD_G_Hauptfenster.getHauptfenster().setzeOhneGitter();
				} else {
					DFD_G_Hauptfenster.getHauptfenster().setzeMitGitter();
				}

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

		case GRIDANZEIGE:
		case GRIDANZEIGELOESCHEN:
			if (LOCK_MENUE.testeLock()) {
				Zoom__Verwalter.getZoomverwalter().zoomAnpassen();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case HILFE:
			if (LOCK_MENUE.testeLock()) {

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case UEBER:
			if (LOCK_MENUE.testeLock()) {
				DialogUeber.oeffneDialog();

				// Lock Lösen
				LOCK_MENUE.releaseLock();
			}

			break;
		case BEENDEN:
			ExitSave.exit();
			break;

		default:
			break;
		}

	}

}
