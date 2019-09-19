package dfd_gui.zoom;

import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import dfd_gui.DFD_Arbeitsfenster;
import global.DFD__GUIKONST;

public class Zoom__Verwalter {

	private static Zoom__Verwalter zoomVerwalter = null;

	private Vector<Zoom__IModul> zModule;

	public static Zoom__Verwalter getZoomverwalter() {
		if (zoomVerwalter == null) {
			zoomVerwalter = new Zoom__Verwalter();
		}

		return zoomVerwalter;
	}

	private Zoom__Verwalter() {
		zModule = new Vector<Zoom__IModul>();
	}

	public void addZoomkomponente(Zoom__IModul zoomModul) {
		zModule.addElement(zoomModul);
	}

	public void removeZoomkomponente(Zoom__IModul zoomModul) {
		zModule.remove(zoomModul);
	}

	// ------------------------------ Zoom

	// Eingang ändert sich
	final Lock lockZoom = new ReentrantLock();
	public void zoomAnpassen() {
		if (!lockZoom.tryLock()) {
			return;
		} else {
			DFD_Arbeitsfenster.getArbeiter().resetArbeitsbehaelter();

			DFD__GUIKONST.zoomAnpassen();

			DFD__GUIKONST.rasterAnpassen();

			for (Zoom__IModul modul : zModule) {
				modul.setzeAnzeigezoomNeu();
			}
			lockZoom.unlock();
		}
	}

}
