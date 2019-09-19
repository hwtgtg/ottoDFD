package modul;

import java.awt.geom.Area;
import java.util.Vector;

import dfd_gui.DFD_Arbeitsfenster;

public class DFD__GModulverwalter {

	private static DFD__GModulverwalter mverwalter = null;

	private Vector<DFD__IG_Modul> gModule;

	public static DFD__GModulverwalter getModulverwalter() {
		if (mverwalter == null) {
			mverwalter = new DFD__GModulverwalter();
		}

		return mverwalter;
	}

	private DFD__GModulverwalter() {
		gModule = new Vector<DFD__IG_Modul>();
	}

	public void add_GModul(DFD__IG_Modul gmodul) {
		gModule.addElement(gmodul);
	}

	public void removeGModul(DFD__IG_Modul gmodul) {
		gModule.remove(gmodul);
	}

	// ------------------------------ Maus
	public void setzeAlleModuleNichtAusgewaehlt() {
		setzeAlleAnderenModule_NichtAusgewaehlt(null);
	}

	public synchronized void setzeAlleAnderenModule_NichtAusgewaehlt(DFD__IG_Modul ziel) {
		DFD_Arbeitsfenster.getArbeitsfenster().setzeFokus();
		for (DFD__IG_Modul aktuell : gModule) {
			if (aktuell != ziel) {
				aktuell.setzeNichtAusgewaehlt();
			}
		}
	}

	public DFD__IG_Modul testeMitAusgaengen(DFD__IG_Modul modulZuPfeil, Area eingangsArea) {

		for (DFD__IG_Modul aktuell : gModule) {
			if (aktuell != modulZuPfeil) {
				Area arAusgang = aktuell.getAusgangArea();
				if (arAusgang == null) {
					continue;
				}
				DFD__IG_Modul erg = aktuell;
				try {
					// EingangsArea nicht verwenden! Würde verändert
					arAusgang.intersect(eingangsArea);
					if (arAusgang.isEmpty()) {
						erg = null;
					}
				} catch (Exception e) {
					erg = null;
				}
				if (erg != null) {
					return aktuell;
				}
			}
		}
		return null;
	}

//	final Lock lockZeichnen = new ReentrantLock();
//	public void zeichne(Graphics2D g2) {
//		@SuppressWarnings("unused")
//		boolean mitRaster = false;
//		if (!lockZeichnen.tryLock()) {
//			return;
//		} else {
//			DFD_Arbeitsfenster.getArbeiter().resetArbeitsbehaelter();
//
//			DFD__GUIKONST.zoomAnpassen();
//
//			DFD__GUIKONST.rasterAnpassen();
//
//			for (DFD__IG_Modul aktuell : gModule) {
//				aktuell.bildZeichnen(g2);
//			}
//			lockZeichnen.unlock();
//		}
//	}

}
