package Dateioperationen;

import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import modul.DFD_FModul_Anzeige;
import modul.DFD_FModul_Beschreibung;
import modul.DFD_FModul_Eingabe;

import modul.DFD_FModul_Funktion;
import modul.DFD_FModul_Konstante;
import modul.DFD__FModulverwalter;
import modul.DFD__IF_Modul;
import ottoxml.OTTOElement;
import ottoxml.OTTOXML;

import dfd_gui.DFD_Start;
import global.CHANGE;
import jtoolbox.D_Bestaetigung;
import jtoolbox.Zeichnung;

public class FunktionsWriter {

	/**
	 * Schreiben
	 */

	public static void schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN_JaNein() {
		// Abbrechen -> return false
		JOptionPane dlg = new JOptionPane();

		@SuppressWarnings("static-access")
		int result = dlg.showConfirmDialog(Zeichnung.gibJFrame(), "Aktuellen Stand speichern?", "Speichern?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN();
		}

	}

	public static void schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN() {
		JFileChooser auswahlDatei = new JFileChooser(".\\");

		global.StartUmgebung.groesseAendernFileChooser(auswahlDatei);

		auswahlDatei.setDialogTitle("Datei schreiben");
		auswahlDatei.setSelectedFile(new File(DFD_Start.getDateinameMitPfad()));
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Datenflussdiagramm (*.dml)", "DML");
		auswahlDatei.setFileFilter(ff);

		boolean weiter = true;
		String dateiname = DFD_Start.getDateinameMitPfad();
		if (dateiname.equals("")) {
			dateiname = "DFD/DFDBeispiel.dml";
		}
		do {
			int erg = auswahlDatei.showSaveDialog(null);

			if (erg == JFileChooser.APPROVE_OPTION) {
				String path = auswahlDatei.getCurrentDirectory().getAbsolutePath();
				dateiname = auswahlDatei.getSelectedFile().getName();
				String newPath = "";
				StringTokenizer pathTokens = new StringTokenizer(path, "\\");
				while (pathTokens.hasMoreTokens()) {
					newPath = newPath + pathTokens.nextToken() + "/";
				}
				dateiname = newPath + dateiname;
				if (!dateiname.toLowerCase().endsWith(".dml")) {
					dateiname = dateiname + ".dml";
				}

				File datei = new File(dateiname);
				if (datei.exists()) {
					D_Bestaetigung db = new D_Bestaetigung();
					db.setzeTitel("Datei existiert");
					db.setzeMeldungstext("Datei existiert! Ersetzen?");
					db.typ_typJaNein();
					db.zeigeMeldung();
					if (db.leseErgebnis() == 'N') {
						weiter = false;
					} else {
						weiter = true;
					}

				}
			} else {
				return;
			}
		} while (!weiter);
		DFD_Start.setDateinameMitPfad(dateiname);
		schreibeDFDMODULEundVERKNUEPFUNGEN(dateiname);
		DFD_Start.setzeTitel(dateiname);
	}

	public static void schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAME() {
		if (DFD_Start.getDateinameMitPfad().equals("")) {
			schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN();
		} else {
			schreibeDFDMODULEundVERKNUEPFUNGEN(DFD_Start.getDateinameMitPfad());
		}
	}

	public static void schreibeDFDMODULEundVERKNUEPFUNGEN(String dateiname) {

		DFD__FModulverwalter.nummeriereModuleNeu();

		OTTOXML ottodml;

		try {
			ottodml = OTTOXML.createEmptyOTTOXML();

			OTTOElement root = ottodml.addElement("DATENFLUSSDIAGRAMM");
			OTTOElement dfd = root;
			ottodml.addComment(root, " OTTO-DML ");

			// XMLWriter.addComment(root, " Titel ");
			// Element titel = XMLWriter.addElement("TITEL", dfd);
			// XMLWriter.addText(titel, G__Anzeige.gui.titel.leseText());

			ottodml.addComment(root, " Vorhandene Komponenten ");
			OTTOElement module = ottodml.addElement("MODULE", dfd);

			for (DFD__IF_Modul fm : DFD__FModulverwalter.getModulverwalter().fModule) {

				OTTOElement modul = ottodml.addElement("MODUL", module);

				OTTOElement eTyp = ottodml.addElement("MODUL_TYP", modul);
				String modultyp = fm.getClass().getSimpleName();
				ottodml.addText(eTyp, modultyp);

				OTTOElement eID = ottodml.addElement("MODUL_ID", modul);
				ottodml.addText(eID, "" + fm.getDfd_fModulNummer());

				if (modultyp.equalsIgnoreCase("DFD_FModul_Eingabe")) {
					// ************************************************************
					// Eingabemodul
					// Bezeichnung Eingabe
					OTTOElement eBeschreibung = ottodml.addElement("MODUL_BEZEICHNUNG", modul);
					ottodml.addText(eBeschreibung, ((DFD_FModul_Eingabe) fm).leseModulbezeichnung());

					// Wert Eingabe
					OTTOElement eWert = ottodml.addElement("MODUL_WERT", modul);
					ottodml.addText(eWert, ((DFD_FModul_Eingabe) fm).leseEingangswert());

					OTTOElement modulposition = ottodml.addElement("MODULPOSITION", modul);

					// X
					OTTOElement eX = ottodml.addElement("X", modulposition);
					ottodml.addText(eX, "" + fm.leseRechteck().x);
					// Y
					OTTOElement eY = ottodml.addElement("Y", modulposition);
					ottodml.addText(eY, "" + fm.leseRechteck().y);

				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Konstante")) {
					// ************************************************************
					// Eingabemodul
					// Bezeichnung Eingabe
					// Wert Eingabe
					OTTOElement eWert = ottodml.addElement("MODUL_WERT", modul);
					ottodml.addText(eWert, ((DFD_FModul_Konstante) fm).leseEingangswert());

					OTTOElement modulposition = ottodml.addElement("MODULPOSITION", modul);

					// X
					OTTOElement eX = ottodml.addElement("X", modulposition);
					ottodml.addText(eX, "" + fm.leseRechteck().x);
					// Y
					OTTOElement eY = ottodml.addElement("Y", modulposition);
					ottodml.addText(eY, "" + fm.leseRechteck().y);

				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Anzeige")) {
					// ************************************************************
					// Anzeigemodul
					// Bezeichnung Ausgabe
					OTTOElement eBeschreibung = ottodml.addElement("MODUL_BEZEICHNUNG", modul);
					ottodml.addText(eBeschreibung, ((DFD_FModul_Anzeige) fm).leseModulbezeichnung());

					OTTOElement modulposition = ottodml.addElement("MODULPOSITION", modul);

					// X
					OTTOElement eX = ottodml.addElement("X", modulposition);
					ottodml.addText(eX, "" + fm.leseRechteck().x);
					// Y
					OTTOElement eY = ottodml.addElement("Y", modulposition);
					ottodml.addText(eY, "" + fm.leseRechteck().y);

				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Beschreibung")) {
					// ************************************************************
					// Beschreibungsmodul

					String[] zeilen = ((DFD_FModul_Beschreibung) fm).leseZeilen();

					int anzahlZeilen = zeilen.length;

					OTTOElement eZeilen = ottodml.addElement("ZEILEN", modul);

					OTTOElement eAnzahl = ottodml.addElement("ANZAHL", eZeilen);
					ottodml.addText(eAnzahl, "" + anzahlZeilen);

					OTTOElement eBeschreibung = null;
					for (int i = 0; i < anzahlZeilen; i++) {
						// Bezeichnung der Zeilen
						eBeschreibung = ottodml.addElement("ZEILE_" + i, eZeilen);
						ottodml.addText(eBeschreibung, zeilen[i]);
					}

					OTTOElement modulposition = ottodml.addElement("MODULPOSITION", modul);
					// X
					OTTOElement eX = ottodml.addElement("X", modulposition);
					ottodml.addText(eX, "" + fm.leseRechteck().x);
					// Y
					OTTOElement eY = ottodml.addElement("Y", modulposition);
					ottodml.addText(eY, "" + fm.leseRechteck().y);
					// Breite
					OTTOElement eBreite = ottodml.addElement("BREITE", modulposition);
					ottodml.addText(eBreite, "" + fm.leseRechteck().width);
					// Hoehe
					OTTOElement eHoehe = ottodml.addElement("HOEHE", modulposition);
					ottodml.addText(eHoehe, "" + fm.leseRechteck().height);

				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Verteiler")) {
					// ************************************************************
					// Verteilermodul
					// Keine Bezeichnungen

					OTTOElement modulposition = ottodml.addElement("MODULPOSITION", modul);

					// X
					OTTOElement eX = ottodml.addElement("X", modulposition);
					ottodml.addText(eX, "" + fm.leseRechteck().x);
					// Y
					OTTOElement eY = ottodml.addElement("Y", modulposition);
					ottodml.addText(eY, "" + fm.leseRechteck().y);

				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Funktion")) {
					// Allgemein - über DFD__FunktionWork
					// DFD__FunktionWork-Klassenbezeichnung
					OTTOElement eBeschreibung = ottodml.addElement("MODUL_WORK_KLASSE", modul);
					ottodml.addText(eBeschreibung, ((DFD_FModul_Funktion) fm).arbeiter.getClass().getSimpleName());

					// Anzahl Eingaenge
					int anzahlEingaenge = ((DFD_FModul_Funktion) fm).leseAnzahlEingaenge();

					OTTOElement eAnzahl = ottodml.addElement("EINGAENGE", modul);
					ottodml.addText(eAnzahl, "" + anzahlEingaenge);

					for (int i = 0; i < anzahlEingaenge; i++) {
						// Bezeichnung der Eingänge
						eBeschreibung = ottodml.addElement("MODUL_EINGANG_" + i, modul);
						ottodml.addText(eBeschreibung, ((DFD_FModul_Funktion) fm).arbeiter.eingangsbezeichungen[i]);
					}
					// Bezeichnung Ausgang
					eBeschreibung = ottodml.addElement("MODUL_AUSGANG", modul);
					ottodml.addText(eBeschreibung, ((DFD_FModul_Funktion) fm).arbeiter.ausgangsbezeichnung);

					// Bezeichnung der Funktion - Fakultativ
					eBeschreibung = ottodml.addElement("MODUL_BEZEICHNUNG", modul);
					ottodml.addText(eBeschreibung, ((DFD_FModul_Funktion) fm).leseModulbezeichnung());

					OTTOElement modulposition = ottodml.addElement("MODULPOSITION", modul);

					// X
					OTTOElement eX = ottodml.addElement("X", modulposition);
					ottodml.addText(eX, "" + fm.leseRechteck().x);
					// Y
					OTTOElement eY = ottodml.addElement("Y", modulposition);
					ottodml.addText(eY, "" + fm.leseRechteck().y);

				}

			}

			ottodml.addComment(root, " Verknuepfungen ");
			OTTOElement verknuepfungen = ottodml.addElement("VERKNUEPFUNGEN", dfd);

			for (DFD__IF_Modul fm : DFD__FModulverwalter.getModulverwalter().fModule) {

				int anzahlEingaenge = fm.leseAnzahlEingaenge();

				for (int nr = 0; nr < anzahlEingaenge; nr++) {
					int ausgangsID = fm.getDfd_fModulNummerAmEingang(nr);

					if (ausgangsID > 0) {
						OTTOElement verknuepfung = ottodml.addElement("VERKNUEPFUNG", verknuepfungen);

						OTTOElement eModul = ottodml.addElement("EINGANGSMODUL", verknuepfung);

						OTTOElement eID = ottodml.addElement("ID", eModul);
						ottodml.addText(eID, "" + fm.getDfd_fModulNummer());

						OTTOElement eNr = ottodml.addElement("EINGANG", eModul);
						ottodml.addText(eNr, "" + nr);

						OTTOElement idAusgang = ottodml.addElement("AUSGANGSMODUL", verknuepfung);
						ottodml.addText(idAusgang, "" + ausgangsID);
					}
				}
			}

			ottodml.addComment(root, " Ende ");

			ottodml.schreibeOTTOXML(dateiname);

		} catch (Exception e) {
			e.printStackTrace();
		}
		CHANGE.reset();
	}
}
