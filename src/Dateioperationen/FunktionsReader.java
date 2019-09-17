package Dateioperationen;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import dfd_gui.DFD_Start;
import modul.DFD_FModul_Anzeige;
import modul.DFD_FModul_Beschreibung;
import modul.DFD_FModul_Eingabe;
import modul.DFD_FModul_Funktion;
import modul.DFD_FModul_Konstante;
import modul.DFD_FModul_Verteiler;
import modul.DFD__FModulverwalter;
import modul.fw_modul.DFD__FunktionWork;
import ottoxml.OTTOElement;
import ottoxml.OTTOXML;

public class FunktionsReader {

	/**
	 * read settings from file data/einstellungen.xml
	 */
	public static void leseDFDMODULEundVERKNUEPFUNGEN() {
		 // Bei Änderung auch in Funktionsreader anpassen !


		JFileChooser auswahlDatei = new JFileChooser(".\\");

		global.StartUmgebung.groesseAendernFileChooser(auswahlDatei);

		// chooser.setPreferredSize(new Dimension(800,600));

		auswahlDatei.setFileSelectionMode(JFileChooser.FILES_ONLY);

		auswahlDatei.setDialogTitle("Datei öffnen");
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Datenflussdiagramm (*.dml)", "DML");
		auswahlDatei.setFileFilter(ff);


		auswahlDatei.setSelectedFile(new File(DFD_Start.getDateinameMitPfad()));
		int erg = auswahlDatei.showOpenDialog(null);

		String dateiname = DFD_Start.getDateinameMitPfad();
		if (dateiname.equals("")) {
			dateiname = "DFD/DFDBeispiel.dml";
		}

		if (erg == JFileChooser.APPROVE_OPTION) {
			File f = auswahlDatei.getSelectedFile().getAbsoluteFile();
			dateiname = f.getPath();
		} else {
			return;
		}

		DFD_Start.setDateinameMitPfad(dateiname); 

		DFD_Start.setzeTitel(DFD_Start.getDateinameMitPfad());

		try {

			OTTOXML ottodml = OTTOXML.leseOTTOXML(dateiname);

			OTTOElement root = ottodml.getRoot();

			if (root.getBezeichnung().compareTo("DATENFLUSSDIAGRAMM") != 0) {
				throw new Exception("Element \"DATENFLUSSDIAGRAMM\" Eingabedatei erwartet.");
			}

			OTTOElement dfd = root;

			/**
			 * Module
			 */
			OTTOElement module = ottodml.getElement(dfd, "MODULE");

			Vector<OTTOElement> modul = ottodml.getElements(module, "MODUL");
			for (OTTOElement aktuellesModul : modul) {
				OTTOElement eTyp = ottodml.getElement(aktuellesModul, "MODUL_TYP");
				String modultyp = eTyp.getInhalt();

				OTTOElement eID = ottodml.getElement(aktuellesModul, "MODUL_ID");
				int modulID = eID.getInhaltInt(-1);

				if (modultyp.equalsIgnoreCase("DFD_FModul_Eingabe")) {
					// ************************************************************
					// Eingabemodul
					// Bezeichnung Eingabe
					OTTOElement eBeschreibung = ottodml.getElement(aktuellesModul, "MODUL_BEZEICHNUNG");
					String modulbezeichnung = eBeschreibung.getInhaltTrim();

					// Wert Eingabe
					OTTOElement eWert = ottodml.getElement(aktuellesModul, "MODUL_WERT");
					String modulwert = eWert.getInhaltTrim();

					OTTOElement modulposition = ottodml.getElement(aktuellesModul, "MODULPOSITION");
					// X
					OTTOElement eX = ottodml.getElement(modulposition, "X");
					int posX = eX.getInhaltInt(-1);
					// Y
					OTTOElement eY = ottodml.getElement(modulposition, "Y");
					int posY = eY.getInhaltInt(-1);
			
					DFD_FModul_Eingabe dfdmodul = new DFD_FModul_Eingabe(posX, posY);
					dfdmodul.setzeEingangswert(modulwert);
					dfdmodul.setzeModulbezeichnung(modulbezeichnung); 
					dfdmodul.setDfd_fModulNummer(modulID);
				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Konstante")||
						// WG. Schreibfehler in früherer Version
						modultyp.equalsIgnoreCase("DFD_FModul_Konstannte")) {
					// ************************************************************
					// Eingabemodul - Konstante
					// Wert Eingabe
					OTTOElement eWert = ottodml.getElement(aktuellesModul, "MODUL_WERT");
					String modulwert = eWert.getInhaltTrim();

					OTTOElement modulposition = ottodml.getElement(aktuellesModul, "MODULPOSITION");
					// X
					OTTOElement eX = ottodml.getElement(modulposition, "X");
					int posX = eX.getInhaltInt(-1);
					// Y
					OTTOElement eY = ottodml.getElement(modulposition, "Y");
					int posY = eY.getInhaltInt(-1);

					DFD_FModul_Konstante dfdmodul = new DFD_FModul_Konstante(posX, posY);
					dfdmodul.setzeEingangswert(modulwert);
					dfdmodul.setDfd_fModulNummer(modulID);
				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Beschreibung")) {
					// ************************************************************
					// Wert Eingabe
					OTTOElement eZeilen = ottodml.getElement(aktuellesModul, "ZEILEN");
					OTTOElement eAnzahl = ottodml.getElement(eZeilen, "ANZAHL");
					int anzahlZeilen = eAnzahl.getInhaltInt(-1);

					OTTOElement eZeile = null;
					Vector<String> zeilen = new Vector<String>();
					for (int i = 0; i < anzahlZeilen; i++) {
						eZeile = ottodml.getElement(eZeilen, "ZEILE_" + i);
						zeilen.add(eZeile.getInhalt());
					}

					OTTOElement modulposition = ottodml.getElement(aktuellesModul, "MODULPOSITION");
					// X
					OTTOElement eX = ottodml.getElement(modulposition, "X");
					int posX = eX.getInhaltInt(-1);
					// Y
					OTTOElement eY = ottodml.getElement(modulposition, "Y");
					int posY = eY.getInhaltInt(-1);
					// Breite
					OTTOElement eBreite = ottodml.getElement(modulposition, "BREITE");
					int breite = eBreite.getInhaltInt(-1);
					// Hoehe
					OTTOElement eHoehe = ottodml.getElement(modulposition, "HOEHE");
					int hoehe = eHoehe.getInhaltInt(-1);

					DFD_FModul_Beschreibung dfdmodul = new DFD_FModul_Beschreibung(posX, posY, breite, hoehe);
					for (int i = 0; i < anzahlZeilen; i++) {
						if (zeilen.get(i) != null) {
							if(i==(anzahlZeilen-1)){
								dfdmodul.print(zeilen.get(i));
							} else {
								dfdmodul.println(zeilen.get(i));
							}
						}
					}
					dfdmodul.setDfd_fModulNummer(modulID);
				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Anzeige")) {
					// ************************************************************
					// Anzeigemodul
					// Bezeichnung Ausgabe
					OTTOElement eBeschreibung = ottodml.getElement(aktuellesModul, "MODUL_BEZEICHNUNG");
					String modulbezeichnung = eBeschreibung.getInhaltTrim();

					OTTOElement modulposition = ottodml.getElement(aktuellesModul, "MODULPOSITION");
					// X
					OTTOElement eX = ottodml.getElement(modulposition, "X");
					int posX = eX.getInhaltInt(-1);
					// Y
					OTTOElement eY = ottodml.getElement(modulposition, "Y");
					int posY = eY.getInhaltInt(-1);

					DFD_FModul_Anzeige dfdmodul = new DFD_FModul_Anzeige(posX, posY);
					dfdmodul.setzeModulbezeichnung(modulbezeichnung); // Stört ersetzen der Eingabe für Tooltipp
					dfdmodul.setDfd_fModulNummer(modulID);
				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Verteiler")) {
					// ************************************************************
					// Verteilermodul
					// Keine Bezeichnungen
					OTTOElement modulposition = ottodml.getElement(aktuellesModul, "MODULPOSITION");
					// X
					OTTOElement eX = ottodml.getElement(modulposition, "X");
					int posX = eX.getInhaltInt(-1);
					// Y
					OTTOElement eY = ottodml.getElement(modulposition, "Y");
					int posY = eY.getInhaltInt(-1);

					DFD_FModul_Verteiler dfdmodul = new DFD_FModul_Verteiler(posX, posY);
					dfdmodul.setDfd_fModulNummer(modulID);
				} else if (modultyp.equalsIgnoreCase("DFD_FModul_Funktion")) {
					// Allgemein - über DFD__FunktionWork
					// DFD__FunktionWork-Klassenbezeichnung

					OTTOElement eWork = ottodml.getElement(aktuellesModul, "MODUL_WORK_KLASSE");
					String work_bezeichnung = eWork.getInhaltTrim();

					// Anzahl Eingaenge
					OTTOElement eAnzahl = ottodml.getElement(aktuellesModul, "EINGAENGE");
					int anzahlEingaenge = eAnzahl.getInhaltInt(-1);

					String[] eingangsbezeichnungen = null;
					if (anzahlEingaenge > 0) {
						eingangsbezeichnungen = new String[anzahlEingaenge];
						for (int i = 0; i < anzahlEingaenge; i++) {
							// Bezeichnung der Eingänge
							OTTOElement eEin = ottodml.getElement(aktuellesModul, "MODUL_EINGANG_" + i);
							eingangsbezeichnungen[i] = eEin.getInhaltTrim();
						}
					}

					// Bezeichnung Ausgang
					OTTOElement eAusgang = ottodml.getElement(aktuellesModul, "MODUL_AUSGANG");
					String ausgangsbezeichnung = eAusgang.getInhaltTrim();

					// Bezeichnung der Funktion - Fakultativ

					OTTOElement eBeschreibung = ottodml.getElement(aktuellesModul, "MODUL_BEZEICHNUNG");
					String modulbezeichnung = eBeschreibung.getInhaltTrim();

					OTTOElement modulposition = ottodml.getElement(aktuellesModul, "MODULPOSITION");
					// X
					OTTOElement eX = ottodml.getElement(modulposition, "X");
					int posX = eX.getInhaltInt(-1);
					// Y
					OTTOElement eY = ottodml.getElement(modulposition, "Y");
					int posY = eY.getInhaltInt(-1);

					DFD__FunktionWork wmodul = null;

					try {
						Class<?> c3 = Class.forName("modul.fw_modul." + work_bezeichnung);
						@SuppressWarnings("unchecked")
						Constructor<DFD__FunktionWork> constructor = (Constructor<DFD__FunktionWork>) c3
								.getConstructor();
						wmodul = constructor.newInstance();

					} catch (SecurityException e) {
					} catch (IllegalArgumentException e) {
					} catch (ClassNotFoundException e) {
					} catch (NoSuchMethodException e) {
					} catch (InstantiationException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}

					if (wmodul != null) {
						DFD_FModul_Funktion fmodul = DFD_FModul_Funktion.erzeugeFunktionsmodul(wmodul, posX, posY);
						fmodul.setDfd_fModulNummer(modulID);
						// fmodul.setzeModulbezeichnung(modulbezeichnung);  // Stört ersetzen der Eingabe für Tooltipp
						fmodul.setzeAusgangsbezeichnung(ausgangsbezeichnung);
						for (int i = 0; i < anzahlEingaenge; i++) {
							fmodul.setzeEingangsbezeichnung(i, eingangsbezeichnungen[i]);
						}

					}
				}

				OTTOElement verknuefungen = ottodml.getElement(dfd, "VERKNUEPFUNGEN");
				Vector<OTTOElement> verknuepfung = ottodml.getElements(verknuefungen, "VERKNUEPFUNG");

				for (OTTOElement aktuelleVerknuepfung : verknuepfung) {
					OTTOElement eModul = ottodml.getElement(aktuelleVerknuepfung, "EINGANGSMODUL");
					OTTOElement eEingangsmodulID = ottodml.getElement(eModul, "ID");
					OTTOElement eEingangsNr = ottodml.getElement(eModul, "EINGANG");
					OTTOElement eIdAusgang = ottodml.getElement(aktuelleVerknuepfung, "AUSGANGSMODUL");

					int eingangsmodulID = eEingangsmodulID.getInhaltInt(-1);
					int eingangsNr = eEingangsNr.getInhaltInt(-1);
					int idAusgang = eIdAusgang.getInhaltInt(-1);

					DFD__FModulverwalter.verknuepfe(eingangsmodulID, eingangsNr, idAusgang);

				}

			}

		} catch (Exception e) {
		} finally {

		}
		DFD__FModulverwalter.aktiviereAlleEingaben();
	}

}
