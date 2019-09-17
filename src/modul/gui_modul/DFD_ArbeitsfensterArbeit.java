package modul.gui_modul;


import dfd_gui.DFD_Arbeitsfenster;
import dfd_gui.zoom.Zoom__IModul;

public class DFD_ArbeitsfensterArbeit {

	private DFD_Arbeitsfenster arbeit;


	public DFD_ArbeitsfensterArbeit(DFD_Arbeitsfenster arbeit) {
		this.arbeit = arbeit;
	}

	// ------------------------------ Arbeisfensterscroller anpassen

	private int breiteArbeitsfenster = 0;
	private int hoeheArbeitsfenster = 0;
	private int tmpBreite = 0;
	private int tmpHoehe = 0;

	public void resetArbeitsbehaelter() {
		breiteArbeitsfenster = 0;
		hoeheArbeitsfenster = 0;
		tmpBreite = 0;
		tmpHoehe = 0;
	}

	
	public boolean testeArbeitsfensterNeu(Zoom__IModul modul, int x, int y, int breite , int hoehe) {

		if ((x < 0) || (y < 0)) {
			return false;
		} else {
			tmpBreite = Math.max(tmpBreite, x+breite);
			tmpHoehe = Math.max(tmpHoehe, y+hoehe);

			if (tmpHoehe > hoeheArbeitsfenster || tmpBreite > breiteArbeitsfenster) {
				hoeheArbeitsfenster = tmpHoehe;
				breiteArbeitsfenster = tmpBreite;
				arbeit.setzeDimensionen(0, 0, breiteArbeitsfenster, hoeheArbeitsfenster);
			}
			return true;
		}
	}


}
