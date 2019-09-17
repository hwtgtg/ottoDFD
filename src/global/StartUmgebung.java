package global;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.print.PageFormat;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import LOG.LOG;
import LOG.LogColorTextfeldBF;
import LOG.LogConsole;

public class StartUmgebung {

	public static boolean DEBUG = false;
	public static boolean MIT_LOG = false;

	public static boolean isJar = false;
	public static String fileseparator = "/";
	public static String startklasse = "";
	public static String startPfad = "";
	public static String homePfad = "";
	public static String workingPfad = "";

	public static String inidatei_basis = "";
	public static String inidatei_home = "";

	public static final int bildschimaufloesungStd = 96;
	public static int bildschimaufloesung = bildschimaufloesungStd;
	public static float bildFaktor = 1.0F;

	public static final int bildschirmBreiteStd = 1280;
	public static final int bildschirmHoeheStd = 1024;

	public static int bildschirmBreite = bildschirmBreiteStd;
	public static int bildschirmHoehe = bildschirmHoeheStd;
	public static float bildFaktorFenster = 1.0F;

	public static int fontSize = 12;
	public static Font fontMenu = null;

	
	public static PageFormat pageFormat = null ;
	
	
	
	public static Font getFont() {
		if (fontMenu == null)
			fontMenu = new Font("Arial", Font.PLAIN, StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
		return fontMenu;
	}

	public static int scrollbarBreite = 16;

	public static Dimension jOptionpaneDimension = null;

	public static void groesseAendernDialog(JOptionPane dlgBestaetigen) {
		recursiveSetFont(dlgBestaetigen, getFont());

		if (jOptionpaneDimension == null) {
			jOptionpaneDimension = dlgBestaetigen.getPreferredSize();
			jOptionpaneDimension.height = bildschirmFaktorFenster(jOptionpaneDimension.height);
			jOptionpaneDimension.width = bildschirmFaktorFenster(jOptionpaneDimension.width);
		}
		dlgBestaetigen.setPreferredSize(jFileChooserDimension);
	}

	public static Dimension jFileChooserDimension = null;

	public static void groesseAendernFileChooser(JFileChooser auswahlDatei) {
		recursiveSetFont(auswahlDatei, getFont());

		if (jFileChooserDimension == null) {
			jFileChooserDimension = auswahlDatei.getPreferredSize();
			jFileChooserDimension.height = bildschirmFaktorFenster(jFileChooserDimension.height);
			jFileChooserDimension.width = bildschirmFaktorFenster(jFileChooserDimension.width);
		}
		auswahlDatei.setPreferredSize(jFileChooserDimension);
	}

	public static void recursiveSetFont(JComponent comp, Font f) {
		synchronized (comp) {
			comp.setFont(f);
			for (int index = 0; index < comp.getComponentCount(); index++) {
				Component c = comp.getComponent(index);
				if (c instanceof JComponent) {
					recursiveSetFont((JComponent) c, f);
				} // Ends if
			}
		}
	}

	public static void startwerte(Object dfd, String startklasse) {

		DFD__GUIKONST.initialisieren();

		// Startpfad lesen
		StartUmgebung.startklasse = startklasse;
		startPfad = dfd.getClass().getClassLoader().getResource(startklasse + ".class").toString();
		startPfad = startPfad.substring(0, global.StartUmgebung.startPfad.lastIndexOf(startklasse + ".class"));

		if (startPfad.startsWith("file:")) {
			startPfad = startPfad.substring(5);
		} else {
			startPfad = startPfad.substring(9);
			isJar = true;
		}

		workingPfad = System.getProperty("user.dir");
		homePfad = System.getProperty("user.home");

		if (isJar) {
			fileseparator = "/";
		} else {
			fileseparator = System.getProperty("file.separator");
		}
		
		if (startPfad.indexOf(startklasse + ".jar!") != -1) {
			// JAR-Datei
			inidatei_basis = startPfad.substring(0, global.StartUmgebung.startPfad.lastIndexOf(startklasse + ".jar!"))
					+ startklasse + "-Global.cfg";

		} else {

			inidatei_basis = startPfad + startklasse + "-Global.cfg";
		}

		inidatei_home = homePfad + fileseparator + startklasse + "-Home.cfg";

		bildschimaufloesung = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
		bildFaktor = (float) (bildschimaufloesung * 1.0F / bildschimaufloesungStd);

		bildschirmHoehe = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		bildschirmBreite = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		bildFaktorFenster = (float) (bildschirmHoehe * 1.0 / bildschirmHoeheStd);

	}

	public static int bildschirmFaktorFenster(float wert) {
		return (int) Math.round(wert * StartUmgebung.bildFaktorFenster);
	}

	public static int bildschirmFaktor(float wert) {
		return (int) Math.round(wert * StartUmgebung.bildFaktor);
	}

	public static float bildschirmFaktorFloat(float wert) {
		return wert * StartUmgebung.bildFaktor;
	}

	public static int bildschirmFaktorReverse(float wert) {
		return (int) Math.round(wert / StartUmgebung.bildFaktor);
	}

	static boolean bspBildfaktor = false;

	public static void argsAuswerten(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-LOG")) {
				StartUmgebung.MIT_LOG = true;
			} else if (args[i].equalsIgnoreCase("-CFG")) {
				if (i < args.length) {
					inidatei_basis = args[i + 1];
				}
			} else if (args[i].equalsIgnoreCase("-DBG")) {
				StartUmgebung.DEBUG = true;
			} else if (args[i].equalsIgnoreCase("-BF")) {
				if (i < args.length) {
					bspBildfaktor = true;
					float erg = bildFaktor;
					try {
						erg = Float.parseFloat(args[i + 1]);
					} catch (Exception e) {
					}
					bildFaktor = erg;
				}
			}
		}

		if (StartUmgebung.MIT_LOG) {
			LOG.setLogOutput(new LogColorTextfeldBF("LOG-Fenster"));
		} else {
			LOG.setLogOutput(null);
		}
		LOG.setLogOutput(new LogConsole());

//		LOG.setLogOutput(new LogColorTextfeldBF("LOG-Fenster"));
//		for (int i = 0; i < args.length; i++) {
//			LOG.outln(args[i]);
//		}

	}

	public static void setzeBildFaktor(float bf) {
		if (!bspBildfaktor) {
			bildFaktor = bf;
		}
	}

}
