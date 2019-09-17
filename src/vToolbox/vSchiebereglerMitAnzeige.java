package vToolbox;

import jtoolbox.Ausgabe;
import jtoolbox.Behaelter;
import jtoolbox.IContainer;
import jtoolbox.Schieberegler;
import jtoolbox.Zeichnung;

public class vSchiebereglerMitAnzeige extends Behaelter {

	private Schieberegler regler;

	Ausgabe min;
	Ausgabe max;
	Ausgabe wert;

	public vSchiebereglerMitAnzeige() {
		this(Zeichnung.gibZeichenflaeche(), 0, 0, 200, 50);
	}

	public vSchiebereglerMitAnzeige(IContainer behaelter, int x, int y,
			int breite, int hoehe) {
		super(behaelter, x, y, breite, hoehe);
		regler = new Schieberegler(this, 'H');
		regler.setzeDimensionen(0, 0, breite, hoehe / 2);

		min = new Ausgabe(this, " min", 0, hoehe / 2, 100, hoehe / 2);
		min.setzeAusrichtung(0);
		max = new Ausgabe(this, "max ", breite - 100, hoehe / 2, 100, hoehe / 2);
		max.setzeAusrichtung(2);
		wert = new Ausgabe(this, "" + hoehe, breite / 2 - 50, hoehe / 2, 100,
				hoehe / 2);
		wert.setzeAusrichtung(1);
		
		regler.setzeLink(auftrag);
	}

	public void setzeBereich(int neuesMin, int neuesMax, int neuerWert) {
		 regler.setzeBereich(neuesMin, neuesMax, neuerWert);
		 min.setzeAusgabetext(" "+neuesMin);
		 max.setzeAusgabetext(""+neuesMax+" ");
		 wert.setzeAusgabetext(""+neuerWert);
	}

	public void setzeTeilung(int teilung) {
		 regler.setzeTeilung(teilung);
	}
	
	@Override
	public void ausfuehren(int ID){
		 wert.setzeAusgabetext(""+regler.leseIntWert());
	}
	
	public void setzeWert(float wert){
		regler.setzeWert(wert);
	}
	
	public double leseWert(){
		return regler.leseDoubleWert();
	}
	
	
}
