package funktionenBaum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import LOG.LOG;
import modul.fw_modul.DFD__FunktionWork;
import ottoxml.OTTOElement;
import ottoxml.OTTOXML;

class FM__Comparator implements Comparator<FW__FunktionsKlasse> {

	@Override
	public int compare(FW__FunktionsKlasse f1, FW__FunktionsKlasse f2) {
		if (f1.klassenbezeichnung.equals("DFD_FModul_Eingabe")) {
			return -1;
		} else if (f1.klassenbezeichnung.equals("DFD_FModul_Konstante")) {
			return -1;
		} else if (f1.klassenbezeichnung.equals("DFD_FModul_Anzeige")) {
			return -1;
		} else if (f1.klassenbezeichnung.equals("DFD_FModul_Verteiler")) {
			return -1;
		} else if (f1.klassenbezeichnung.equals("DFD_FModul_Beschreibung")) {
			return -1;
		} else if (f2.klassenbezeichnung.equals("DFD_FModul_Eingabe")) {
			return +1;
		} else if (f2.klassenbezeichnung.equals("DFD_FModul_Konstante")) {
			return +1;
		} else if (f2.klassenbezeichnung.equals("DFD_FModul_Anzeige")) {
			return +1;
		} else if (f2.klassenbezeichnung.equals("DFD_FModul_Verteiler")) {
			return +1;
		} else if (f2.klassenbezeichnung.equals("DFD_FModul_Beschreibung")) {
			return +1;
		} else if (f1.gruppe.equals("Zahlen") && !f2.gruppe.equals("Zahlen")) {
			return -1;
		} else if (!f1.gruppe.equals("Zahlen") && f2.gruppe.equals("Zahlen")) {
			return +1;
		} else if (f1.gruppe.equals("ganzzahlig") && !f2.gruppe.equals("ganzzahlig")) {
			return -1;
		} else if (!f1.gruppe.equals("ganzzahlig") && f2.gruppe.equals("ganzzahlig")) {
			return +1;
		} else if (f1.gruppe.equals("logisch") && !f2.gruppe.equals("Vergleich-Zahlen")) {
			return -1;
		} else if (!f1.gruppe.equals("logisch") && f2.gruppe.equals("Vergleich-Zahlen")) {
			return +1;
		} else if (f1.gruppe.equals("logisch") && !f2.gruppe.equals("Logisch")) {
			return -1;
		} else if (!f1.gruppe.equals("logisch") && f2.gruppe.equals("Logisch")) {
			return +1;
		} else if (f1.gruppe.equals("String") && !f2.gruppe.equals("String")) {
			return -1;
		} else if (!f1.gruppe.equals("String") && f2.gruppe.equals("String")) {
			return +1;
		} else if (f1.gruppe.compareTo(f2.gruppe) < 0) {
			return -1;
		} else if (f1.gruppe.compareTo(f2.gruppe) > 0) {
			return +1;
		} else if (f1.sortierung < f2.sortierung) {
			return -1;
		} else if (f1.sortierung > f2.sortierung) {
			return +1;
		} else {
			if (f1.bezeichnung.compareTo(f2.bezeichnung) < 0) {
				return -1;
			} else if (f1.bezeichnung.compareTo(f2.bezeichnung) > 0) {
				return +1;
			} else {
				return 0;
			}
		}
	}
}

public class FW_Module_ImBaum {

	public static String fwModulPfad = "";

	public static LinkedList<FW__FunktionsKlasse> funktionen = new LinkedList<FW__FunktionsKlasse>();

	public static DefaultMutableTreeNode root = null;

	public static void erzeugeBaum() {
		root = new DefaultMutableTreeNode("Auswahl");
		DefaultMutableTreeNode funktion = new DefaultMutableTreeNode("Funktionen");
		root.add(funktion);

		FM__Comparator fmComparator = new FM__Comparator();
		Collections.sort(funktionen, fmComparator);
		String gruppe = "Ein-Aus";
		DefaultMutableTreeNode tGruppe = null;
		for (FW__FunktionsKlasse f : funktionen) {
			if (f.gruppe.equals("Ein-Aus")) {
				funktion.add(new DefaultMutableTreeNode(f));
			} else {
				if (!gruppe.equals(f.gruppe)) {
					tGruppe = new DefaultMutableTreeNode(f.gruppe);
					funktion.add(tGruppe);
					gruppe = f.gruppe;
				}
				tGruppe.add(new DefaultMutableTreeNode(f));
			}
		}
	}

	/**
	 * Lesen der Funktionen aus der XML-Datei
	 */
	public static void leseFunktionen() {

		// Interne Funktionen
		FW_Module_ImBaum.funktionen
				.add(new FW__FunktionsKlasse(true, "DFD_FModul_Beschreibung", "Beschreibung", "Ein-Aus", 1));
		FW_Module_ImBaum.funktionen
				.add(new FW__FunktionsKlasse(true, "DFD_FModul_Verteiler", "Verteiler", "Ein-Aus", 2));
		FW_Module_ImBaum.funktionen.add(new FW__FunktionsKlasse(true, "DFD_FModul_Anzeige", "Anzeige", "Ein-Aus", 3));
		FW_Module_ImBaum.funktionen
				.add(new FW__FunktionsKlasse(true, "DFD_FModul_Konstante", "Konstante", "Ein-Aus", 4));
		FW_Module_ImBaum.funktionen.add(new FW__FunktionsKlasse(true, "DFD_FModul_Eingabe", "Eingabe", "Ein-Aus", 5));

		// Weitere Funktionen
		OTTOXML ottoxml = null;
		OTTOElement root = null;

		String implFunktionenStart;

		try {
			InputStream stream = null;

			if (!global.StartUmgebung.isJar) {
				// Nicht in Jar - Entwicklung in Eclipse
				implFunktionenStart = global.StartUmgebung.startPfad + "funktionenBaum" + "/" + "fw_module_impl.xml";
				implFunktionenStart = implFunktionenStart.substring(1);

				File datei = new File(implFunktionenStart);
				FileReader reader = null;
				if (!datei.exists()) {
					stream = null;
				} else {
					String pfad = datei.getPath();
					try {
						reader = new FileReader(pfad);
					} catch (FileNotFoundException e) {
					}
					ottoxml = OTTOXML.leseOTTOXML(implFunktionenStart);
					try {
						if (reader != null)
							reader.close();
					} catch (Exception e) {
					}
				}

			} else {
				// Aus jar-Datei lesen
				implFunktionenStart = global.StartUmgebung.fileseparator + "funktionenBaum"
						+ global.StartUmgebung.fileseparator + "fw_module_impl.xml";
				stream = DFD__FunktionWork.class.getResourceAsStream(implFunktionenStart);
				if (stream != null) {
					ottoxml = OTTOXML.leseOTTOXMLStream(stream);
				}
			}

			root = ottoxml.getRoot();

			if (root.getBezeichnung().compareTo("DATENFLUSSDIAGRAMM") != 0) {
				throw new Exception("Element \"DATENFLUSSDIAGRAMM\" in der Datei fw_module_impl.xml erwartet.");
			}
		} catch (Exception e) {
			return;
		}

		OTTOElement dfd = root;

		/**
		 * Module
		 */
		OTTOElement module = ottoxml.getElement(dfd, "MODULE_IMPL");

		Vector<OTTOElement> modul = ottoxml.getElements(module, "MODUL_IMPL");
		for (OTTOElement m : modul) {
			OTTOElement m_filename = ottoxml.getElement(m, "MODUL_FILENAME");
			String filename = m_filename.getInhaltTrim();

			try {
				Class<?> c3 = Class.forName("modul.fw_modul." + filename);

				if (!c3.getSuperclass().toString().substring(6).equals("modul.fw_modul.DFD__FunktionWork")) {
					LOG.outln("Falsche Oberklasse: " + c3.getSuperclass().toString().substring(6));
					return; // Falsche Oberklasse
				} else {

				}

				String sBezeichnung = filename;
				String sGruppe = "-";
				int sortierung = 99;

				try {
					Field bez = c3.getField("bezeichnung");// throws
					// NoSuchFieldException
					sBezeichnung = bez.get(null).toString();
					Field gruppe = c3.getField("gruppe");// throws
					// NoSuchFieldException
					sGruppe = gruppe.get(null).toString();
					Field sort = c3.getField("sortierung");// throws
					// NoSuchFieldException
					sortierung = Integer.parseInt(sort.get(null).toString());
				} catch (Exception e) {
				}

				FW_Module_ImBaum.funktionen.add(new FW__FunktionsKlasse(filename, sBezeichnung, sGruppe, sortierung));

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

	public static boolean existiert(String typ) {

		for (FW__FunktionsKlasse f : funktionen) {
			if (f.klassenbezeichnung.equals(typ)) {
				return true;
			}
		}
		return false;
	}

}
