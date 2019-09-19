package dfd_gui;

import Dateioperationen.DFD_INI;
import jpToolbox.BaumImScrollfenster;
import jtoolbox.BehaelterBorderlayout;
import jtoolbox.BehaelterSplit;
import jtoolbox.Bilddatei;
import jtoolbox.Zeichnung;
import funktionenBaum.FW_Module_ImBaum;
import global.StartUmgebung;

public class DFD_G_Hauptfenster {

	private static DFD_G_Hauptfenster hauptfenster;

	public static DFD_G_Hauptfenster getHauptfenster() {
		if (hauptfenster == null) {
			hauptfenster = new DFD_G_Hauptfenster();
		}
		return hauptfenster;
	}

	private BehaelterBorderlayout behaelterMitStatusbar;

	BehaelterSplit hauptfensterSplit;

	@SuppressWarnings("unused")
	private DFD_Arbeitsfenster dfdAnzeige;
	
	DFD_Toolbar toolbar ;
	
	
	private DFD_G_Hauptfenster() {
		hauptfenster = this ;
		basisaufbau();
	}

	private void basisaufbau() {
		
		Bilddatei bild = new Bilddatei("images" + global.StartUmgebung.fileseparator + "toolbox.png");
		Zeichnung.setzeIcon(bild);

		Zeichnung.setzeFenstergroesse(StartUmgebung.bildschirmFaktor(1000), StartUmgebung.bildschirmFaktor(700));

		// ---------------------------------------------------- Toolbar erzeugen
		toolbar = new DFD_Toolbar(DFD_Start.toolbarBreite, DFD_Start.toolbarHoehe);
		Zeichnung.setzeToolbar(toolbar.getBasisComponente());

		Zeichnung.expand();

		// ----------------------------------------------------Menue erzeugen
		DFD_G_Menue.getMenu();

		// ---------------------------------------------------- Hauptfenster
		// erzeugen mit Statusbar
		behaelterMitStatusbar = new BehaelterBorderlayout(0, 0, 100, 100);

		DFD_Statusbar statusbar = new DFD_Statusbar(behaelterMitStatusbar, DFD_Start.toolbarBreite,
				DFD_Start.toolbarHoehe);
		// Statusbar nach unten

		behaelterMitStatusbar.verschiebeSued(statusbar);
		statusbar.setzeGroesse(10, 30);

		// Hauptfenster in die Mitte
		hauptfensterSplit = new BehaelterSplit(behaelterMitStatusbar, 0, 0, 500, 500);
		hauptfensterSplit.panelLO.expand();
		hauptfensterSplit.panelRU.expand();
		
		dfdAnzeige = DFD_Arbeitsfenster.getArbeitsfenster();

		funktionsBaumAnzeigen();

		DFD_INI.splitPosition = DFD_INI.dfd_INIwerte.leseInteger("GUI", "splitPosition", 150);
		hauptfensterSplit.setDividerLocation(StartUmgebung.bildschirmFaktor(DFD_INI.splitPosition));
		hauptfensterSplit.setDividerSize(StartUmgebung.bildschirmFaktor(DFD_Start.deviderSize));
		DFD_INI.dfd_INIwerte.setzeInteger("GUI", "splitPosition", DFD_INI.splitPosition);

	}

	BaumImScrollfenster auswahlBaum;

	public void funktionsBaumAnzeigen() {
		auswahlBaum = new BaumImScrollfenster(hauptfensterSplit.getBehaelterLO(), FW_Module_ImBaum.root, 0, 0, 200,
				300);
		auswahlBaum.setzeFont(StartUmgebung.getFont());
		// Funktionen aufmachen
		auswahlBaum.getjBaum().expandRow(1);
	}

	public int leseSplitPosition() {
		return StartUmgebung.bildschirmFaktorReverse(getHauptfenster().hauptfensterSplit.getDividerLocation());
	}

	public int leseAnzeigeverzoegerungsregler() {
		return toolbar.tbLinks.anzeigeVerzoegerunsregler.leseIntWert() ;
	}

	public boolean leseMitGitter(){
		return toolbar.tbLinks.grid_schalter.istGewaehlt();
	}

	public void setzeOhneGitter() {
		toolbar.tbLinks.grid_schalter.setzeNichtGewaehlt();
	}

	public void setzeMitGitter() {
		toolbar.tbLinks.grid_schalter.setzeGewaehlt();		
	}
}
