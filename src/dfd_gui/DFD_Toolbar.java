package dfd_gui;

import global.StartUmgebung;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import dfd_gui.zoom.DialogZoom;
import jtoolbox.Behaelter;
import jtoolbox.BehaelterBorderlayout;
import jtoolbox.Bilddatei;
import jtoolbox.RadioBehaelter;
import jtoolbox.RadioTaste;
import jtoolbox.Rechteck;
import jtoolbox.Schieberegler;
import jtoolbox.Taste;
import jtoolbox.UmschaltTaste;

public class DFD_Toolbar extends BehaelterBorderlayout {

	private static int bf(int wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	DFD_ToolbarHilfe tbHilfe;
	DFD_ToolbarLinks tbLinks;

	public DFD_Toolbar(int breite, int hoehe) {
		super(0, 0, bf(breite), bf(hoehe));

		tbHilfe = new DFD_ToolbarHilfe(this, DFD_Start.toolbarHoehe, DFD_Start.toolbarHoehe);

		verschiebeOst(tbHilfe);

		tbLinks = new DFD_ToolbarLinks(this, DFD_Start.toolbarBreite, DFD_Start.toolbarHoehe);
		// verschiebeWest(tbLinks);
	}

}

class DFD_ToolbarLinks extends Behaelter {

	Taste neu;
	Taste oeffnen;
	Taste speichern;
	Taste speichernUnter;

	Taste drucken;
	Taste bildErstellen ;

	Rechteck teiler01;

	RadioBehaelter b_anzeige;
	RadioTaste r_anzeige;
	RadioTaste r_wert;

	Rechteck teiler02;

	Schieberegler anzeigeVerzoegerunsregler;

	Rechteck teiler03;

	UmschaltTaste grid_schalter;

	private static int bf(float wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	private static int bfRed(float wert) {
		return StartUmgebung.bildschirmFaktor(wert / 1.2F);
	}

	public DFD_ToolbarLinks(Behaelter behaelter, int breite, int hoehe) {
		super(behaelter, 0, 0, bf(breite), bf(hoehe));
		((JPanel) this.getBasisComponente()).setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		Bilddatei anz;

		// Mit und ohne Gitter
		grid_schalter = new UmschaltTaste(this, "", 0, 0, bf(hoehe), bf(hoehe) - 4);

		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_grid.png");

		anz.einpassen(bfRed(hoehe) - 3, bfRed(hoehe) - 3);
		grid_schalter.setzeIcon(anz);
		grid_schalter.setzeGewaehlt();
		grid_schalter.setzeLink(DFD_G_Menue.getMenu());
		grid_schalter.setzeID(DFD_G_Menue.GRIDANZEIGE);

		// schalter.setzeLink(DFD_G_Menue.getMenu(),
		// DFD_G_Menue.SPEICHERNUNTER);

		teiler03 = new Rechteck(this);
		teiler03.setzeGroesse(bf(hoehe / 8), bf(hoehe) - 4);
		teiler03.setzeFarbe(DFD_G_Menue.TEILERFARBE);

		anzeigeVerzoegerunsregler = new Schieberegler(this, 'H');
		anzeigeVerzoegerunsregler.setzeDimensionen(0, bf(hoehe) / 8, 3 * bf(hoehe), bf(hoehe * 3.0F / 4) - 4);
		anzeigeVerzoegerunsregler.setzeBereich(0, 5, 0);
		anzeigeVerzoegerunsregler.setzeTeilung(5);
		// wird direkt gelesen
		// anzeigeVerzoegerunsregler.setzeLink(DFD_G_Menue.getMenu(),
		// DFD_G_Menue.REGLER_FLUSSVERZOEGERUNG);

		teiler02 = new Rechteck(this);
		teiler02.setzeGroesse(bf(hoehe / 8), bf(hoehe) - 4);
		teiler02.setzeFarbe(DFD_G_Menue.TEILERFARBE);

		// Wert- oder Funktionsanzeige
		b_anzeige = new RadioBehaelter(this);
		b_anzeige.setzeGroesse(3 * bf(hoehe), bf(hoehe) - 4);

		r_anzeige = new RadioTaste(b_anzeige, "A", 0, 0, bf(hoehe * 1.5F), bf(hoehe) - 4);
		r_anzeige.setzeSchriftgroesse(StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
		r_anzeige.setzeSchriftStilFett();
		r_anzeige.setzeHintergrundfarbe(DFD_G_Menue.R_ANZEIGE_FARBE);
		r_anzeige.setzeLink(DFD_G_Menue.getMenu());
		r_anzeige.setzeID(DFD_G_Menue.R_AKTION_ANZEIGE);
		r_anzeige.setzeTooltip(DialogZoom.getTootipZoom("Funktionsanzeige"));

		r_wert = new RadioTaste(b_anzeige, "W", bf(hoehe * 1.5F), 0, bf(hoehe * 1.5F), bf(hoehe) - 4);
		r_wert.setzeSchriftgroesse(StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
		r_wert.setzeSchriftStilFett();
		r_wert.setzeHintergrundfarbe(DFD_G_Menue.R_ANZEIGE_FARBE);
		r_wert.setzeLink(DFD_G_Menue.getMenu());
		r_wert.setzeID(DFD_G_Menue.R_AKTION_WERT);
		r_wert.setzeTooltip(DialogZoom.getTootipZoom("Wertanzeige"));

		r_wert.setzeGewaehlt();

		teiler01 = new Rechteck(this);
		teiler01.setzeGroesse(bf(hoehe / 8), bf(hoehe) - 4);
		teiler01.setzeFarbe(DFD_G_Menue.TEILERFARBE);

		bildErstellen = new Taste(this);
		bildErstellen.setzeGroesse(bf(hoehe), bf(hoehe) - 4);
		bildErstellen.setzeAusgabetext("");
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_bild.png");

		anz.einpassen(bfRed(hoehe), bfRed(hoehe));
		bildErstellen.setzeIcon(anz);
		bildErstellen.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.BILDERSTELLEN);

		
		
		
		drucken = new Taste(this);
		drucken.setzeGroesse(bf(hoehe), bf(hoehe) - 4);
		drucken.setzeAusgabetext("");
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_druck.png");

		anz.einpassen(bfRed(hoehe), bfRed(hoehe));
		drucken.setzeIcon(anz);
		drucken.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.DRUCKEN);

		speichernUnter = new Taste(this);
		speichernUnter.setzeGroesse(bf(hoehe), bf(hoehe) - 4);
		speichernUnter.setzeAusgabetext("");
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_speichern_unter.png");

		anz.einpassen(bfRed(hoehe), bfRed(hoehe));
		speichernUnter.setzeIcon(anz);
		speichernUnter.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.SPEICHERNUNTER);

		speichern = new Taste(this);
		speichern.setzeGroesse(bf(hoehe), bf(hoehe) - 4);
		speichern.setzeAusgabetext("");
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_speichern.png");
		anz.einpassen(bfRed(hoehe), bfRed(hoehe));
		speichern.setzeIcon(anz);
		speichern.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.SPEICHERN);

		oeffnen = new Taste(this);
		oeffnen.setzeGroesse(bf(hoehe), bf(hoehe) - 4);
		oeffnen.setzeAusgabetext("");
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_oeffnen.png");
		anz.einpassen(bfRed(hoehe), bfRed(hoehe));
		oeffnen.setzeIcon(anz);
		oeffnen.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.OEFFNEN);

		neu = new Taste(this);
		neu.setzeGroesse(bf(hoehe), bf(hoehe) - 4);
		neu.setzeAusgabetext("");
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_neu.png");

		anz.einpassen(bfRed(hoehe), bfRed(hoehe));
		neu.setzeIcon(anz);
		neu.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.NEU);

	}

}

class DFD_ToolbarHilfe extends Behaelter {

	Taste tasteHilfe;
	Taste tasteVergroesserungNeutral;
	Taste tasteVergroesserungMinus;
	Taste tasteVergroesserungPlus;

	private static int bf(int wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	private static int bfRed(int wert) {
		return StartUmgebung.bildschirmFaktor(wert / 1.2F);
	}

	private static int bfRed2(int wert) {
		return StartUmgebung.bildschirmFaktor(wert / 1.5F);
	}

	public DFD_ToolbarHilfe(Behaelter behaelter, int breite, int hoehe) {
		super(behaelter, 0, 0, bf(breite), bf(hoehe));
		((JPanel) this.getBasisComponente()).setLayout(new FlowLayout(FlowLayout.RIGHT, 1, 1));
		// this.setzeMitRand(true);
		// this.setzeDeltaX(10);
		// this.setzeDeltaY(10);
		// this.setzeMitRaster(true);

		Bilddatei anz;

		// Hilfetaste
		tasteHilfe = new Taste(this, "", bf(0) + 2, bf(0) + 2, bf(breite), bf(hoehe) - 4);
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_hilfe.png");

		anz.einpassen(bfRed(hoehe), bfRed(hoehe));
		tasteHilfe.setzeIcon(anz);
		tasteHilfe.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.HILFE);

		// Taste Normale Vergroesserung
		tasteVergroesserungNeutral = new Taste(this, "", bf(0) + 2, bf(0) + 2, bf(breite), bf(hoehe) - 4);
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_plusminus.png");

		anz.einpassen(bfRed2(hoehe), bfRed2(hoehe));
		tasteVergroesserungNeutral.setzeIcon(anz);
		tasteVergroesserungNeutral.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.VERGROESSERUNG_NORMAL);

		// Taste kleinere Vergroesserung
		tasteVergroesserungMinus = new Taste(this, "", bf(0) + 2, bf(0) + 2, bf(breite), bf(hoehe) - 4);
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_minus.png");

		anz.einpassen(bfRed2(hoehe), bfRed2(hoehe));
		tasteVergroesserungMinus.setzeIcon(anz);
		tasteVergroesserungMinus.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.VERGROESSERUNG_MINUS);

		// Taste groessere Vergroesserung
		tasteVergroesserungPlus = new Taste(this, "", bf(0) + 2, bf(0) + 2, bf(breite), bf(hoehe) - 4);
		anz = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_plus.png");

		anz.einpassen(bfRed2(hoehe), bfRed2(hoehe));
		tasteVergroesserungPlus.setzeIcon(anz);
		tasteVergroesserungPlus.setzeLink(DFD_G_Menue.getMenu(), DFD_G_Menue.VERGROESSERUNG_PLUS);
	}
}
