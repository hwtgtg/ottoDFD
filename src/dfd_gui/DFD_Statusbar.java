package dfd_gui;

//import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import global.StartUmgebung;
import jtoolbox.Behaelter;
import jtoolbox.Taste;


// Funktioniert noch nicht



public class DFD_Statusbar extends Behaelter {
	private static int bf(int wert) {
		return StartUmgebung.bildschirmFaktor(wert);
	}

	public DFD_Statusbar( Behaelter behaelter, int breite, int hoehe) {
		super(behaelter,0, 0, bf(breite), bf(hoehe));
		((JPanel) this.getBasisComponente()).setLayout(new FlowLayout(
				FlowLayout.LEFT, 2, 2));

//		((JPanel) this.getBasisComponente()).setLayout(new BorderLayout(3,3));
		
		Taste rechts = new Taste(this);
		rechts.setzeAusgabetext("2");
		rechts.setzeGroesse(60, 20);
		

		Taste links = new Taste(this);
		links.setzeAusgabetext("1");
		links.setzeGroesse(60, 20);


	}

}
