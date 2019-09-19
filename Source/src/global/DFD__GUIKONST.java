package global;

import java.awt.Color;

import dfd_gui.DFD_Arbeitsfenster;
import dfd_gui.DFD_G_Hauptfenster;
import dfd_gui.zoom.DialogZoom;
import jtoolbox.StaticTools;

/**
 * Konstanten der Projekts Funktional
 * 
 * @author Witt
 * 
 */
public class DFD__GUIKONST {

	// Hauptfenster

	public static final int F_BREITE = 1000;
	public static final int F_HOEHE = 750;

	public static final int F_TRENNERX = 150;
	int F_TRENNER_BREIT = 7;

	public static int dfd_ausgabeStellen = 15;

	public static final int DFD_FONT_GROESSE = 16;
	public static final float FAKTOR_FONTGROSS = 1.5F;
	public static final String SchriftfarbeAnzeige = "dfdSchriftAnzeige";
	public static final String HintergrundfarbeAnzeige = "dfdHintergrundAnzeige";
	public static final String SchriftfarbeBearbeiten = "dfdSchriftBearbeiten";
	public static final String HintergrundfarbeBearbeiten = "dfdHintergrundBearbeiten";
	public static final String SchriftfarbeAusgabe = "dfdSchriftAusgabe";
	public static final String HintergrundfarbeAusgabe = "dfdHintergrundAusgabe";

	public static final String DFD_HINTERGRUND_ERROR = "rot";
	public static final String DFD_HINTERGRUND_NORMAL = "dfdHintergrundAnzeige";

	public static final String Rasterfarbe = "dfdRasterfarbe";
	public static final String Auswahlfarbe = "dfdAuswahlfarbe";

	// Modul
	public static final Color paintHintergrund = Color.WHITE;
	public static final Color paintVordergrund = Color.BLACK;
	public static final Color paintEingang = Color.RED;
	public static final Color paintAusgang = Color.GREEN;

	public static Color modulAusgewaehlt = null;

	// Modul Eingabe
	public static final String LeereEingabe = "'";

	// VerschiebeSensor
	public static final int PFEILSENSOR_DURCHMESSER = 7;
	public static int pfeilsensor_Durchmesser = PFEILSENSOR_DURCHMESSER;
	public static Color PFEILSENSOR_NORMAL = Color.ORANGE;
	public static Color PFEILSENSOR_TREFFER = Color.RED;

	public static final int SENSOR_GroesseAendern = 12;

	// Bilddatei
	public static final int bildrand = 10;
	// BeschreibungTextmodul
	// Farben gesetzt in Startumgebung
	public static final int BeschreibungText_Minimum = 30;
	public static final int BILDBESCHREIBUNG_RANDX = 5;
	public static final int BILDBESCHREIBUNG_RANDY = 5;
	public static final float FAKTOR_FONTGROSSBild = 1.2F;
	public static final int BILDBESCHREIBUNG_ZEILENABSTAND = 5;
	public static final float BILD_FONT_GROESSE = 15;

	// Modul
	public static final int FM_RECHTECK_RADIUS = 5;
	public static final int FM_HORIZONTAL_RASTER = (int) (DFD_FONT_GROESSE * 4.5);
	public static final int FM_VERTIKAL_RASTER = (int) (DFD_FONT_GROESSE * 1.5);
	public static final int FM_ZEILEN_ENG = 1;
	public static final int FM_ZEILEN_EIN_WEIT = 2;

	public static final int FM_ZEILEN_WEIT = 3;

	public static final int FM_KONNEKTOR_X = DFD_FONT_GROESSE * 3;
	public static final int FM_KONNECTOR_Y = DFD_FONT_GROESSE * 3 / 4;

	public static int fm_RECHTECK_RADIUS = 5;
	public static int fm_Horizontal_RASTER = 60;
	public static int fm_Vertikal_RASTER = 18;

	public static int fm_KONNEKTOR_X = 40;
	public static int fm_KONNECTOR_Y = 10;

	// Verteiler
	public static final int FV_DURCHMESSER = FM_HORIZONTAL_RASTER * 1 / 2;

	public static int fv_DURCHMESSER = FM_HORIZONTAL_RASTER * 1 / 2;

	// Pfeil
	public static final int PF_BREITE = 4;
	public static final int PF_LaengeAnfang = FM_VERTIKAL_RASTER * 2;
	public static int pf_Breite = 4;
	public static int pf_LaengeAnfang = 20;
	public static final int PF_LOESEN = FM_VERTIKAL_RASTER;
	public static int pf_Loesen;

	public static final String PF_FREI = "blau";
	public static final String PF_VERBUNDEN = "dfd_PF_verbunden";
	public static final String PF_OK = "gruen";
	public static final String PF_ERROR = "dfd_PF_Fehler";
	public static final String PF_HERVORHEBEN = "dfd_PF_hervorgehoben";
	public static final String PF_ABSCHWAECHEN = "dfd_PF_abschwaechen";

	public static int leseVerzoegerung() {
		return DFD_G_Hauptfenster.getHauptfenster().leseAnzeigeverzoegerungsregler();
	}

	public static void zoomAnpassen() {
		fm_RECHTECK_RADIUS = DialogZoom.intZoomWert(DFD__GUIKONST.FM_RECHTECK_RADIUS);
		fm_Horizontal_RASTER = DialogZoom.intZoomWert(DFD__GUIKONST.FM_HORIZONTAL_RASTER);
		fm_Vertikal_RASTER = DialogZoom.intZoomWert(DFD__GUIKONST.FM_VERTIKAL_RASTER);

		fm_KONNEKTOR_X = DialogZoom.intZoomWert(DFD__GUIKONST.FM_KONNEKTOR_X);
		fm_KONNECTOR_Y = DialogZoom.intZoomWert(DFD__GUIKONST.FM_KONNECTOR_Y);

		fv_DURCHMESSER = DialogZoom.intZoomWert(DFD__GUIKONST.FV_DURCHMESSER);

		pf_Breite = DialogZoom.intZoomWert(PF_BREITE);
		pf_LaengeAnfang = DialogZoom.intZoomWert(PF_LaengeAnfang);

		pf_Loesen = DialogZoom.intZoomWert(PF_LOESEN);

		pfeilsensor_Durchmesser = DialogZoom.intZoomWert(PFEILSENSOR_DURCHMESSER);
	}

	public static void initialisieren() {
		StaticTools.setzeFarbe("dfdRasterfarbe", 255, 204, 102);

		StaticTools.setzeFarbe("dfdAuswahlfarbe", 255, 204, 102);

		StaticTools.setzeFarbe("dfdSchriftAnzeige", 0, 0, 0);
		StaticTools.setzeFarbe("dfdHintergrundAnzeige", 255, 255, 200);
		StaticTools.setzeFarbe("dfdSchriftBearbeiten", 0, 0, 150);
		StaticTools.setzeFarbe("dfdHintergrundBearbeiten", 255, 255, 255);
		StaticTools.setzeFarbe("dfdSchriftAusgabe", 0, 0, 110);
		StaticTools.setzeFarbe("dfdHintergrundAusgabe", 220, 220, 220);
		StaticTools.setzeFarbe("dfdHintergrundWertAusgabe", 255, 255, 255);

		StaticTools.setzeFarbe("dfdAuswahlfarbeRand", 220, 220, 220);
		StaticTools.setzeFarbe("dfdAuswahlfarbeHintergrund", 220, 220, 220);

		StaticTools.setzeFarbe("dfd_PF_hervorgehoben", 255, 0, 255);
		StaticTools.setzeFarbe("dfd_PF_abschwaechen", 152, 227, 136);
		StaticTools.setzeFarbe("dfd_PF_verbunden", 255, 120, 0);
		StaticTools.setzeFarbe("dfd_PF_Fehler", 255, 0, 0);
		StaticTools.setzeFarbe("modulAusgewaehlt", 255, 165, 0);

		modulAusgewaehlt = StaticTools.getColor("modulAusgewaehlt");
	}

	@SuppressWarnings("static-access")
	public static void rasterAnpassen() {
		DFD_Arbeitsfenster.getArbeitsfenster().rasterBearbeiten(DFD_G_Hauptfenster.getHauptfenster().leseMitGitter());
	}

}
