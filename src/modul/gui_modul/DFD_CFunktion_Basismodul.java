package modul.gui_modul;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class DFD_CFunktion_Basismodul extends DFD_CBasismodul {
	public int anzahlEingaenge = 0;

	// Test auf Rechteck fuer Eingang, liefet Nummer des Eingangs,
	// KEINEINGANG fuer keinen Treffer
	abstract int sucheEingangsNr(int posX, int posY);

	// Liefert den Eingangspunkt fuer Nr i, i aus 0,1,...
	abstract Point eingangMittelpunkt(int i);

	// Gibt den Ausgangspunkt eines Pfeils wieder
	abstract Point ausgangMittelpunkt();

	abstract boolean testAusgang(int posX, int posY);

}
