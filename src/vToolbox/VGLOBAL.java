package vToolbox;

import java.util.LinkedList;

/**
 * Globale Variablen für die Darstellung der Klassen
 * 
 * Klassen werden in Liste eingehängt, die bei Bedarf angepasst wird
 * 
 * Listenmitglieder implementieren IZOOM mit der Methode zoomen();
 * 
 * benötigte Parameter werden aus der Klasse VGLOBAL entnommen
 * 
 * @author witt
 *
 */
public class VGLOBAL {

	public static float zoomfaktor = 1.0F;
	public static float fontfaktor = 1.0F;

	private LinkedList<IZOOM> zoom;
	private static VGLOBAL global = null;

	public static void addZoom(IZOOM object) {
		if (global == null) {
			global = new VGLOBAL();
		}
		global.add(object);
	}

	public static void zoomen() {
		if (global != null) {
			global.zoom();
		}
	}

	public static void zoomenFont() {
		if (global != null) {
			global.fontZoom();
		}
	}

	public static void zoomenDimension() {
		if (global != null) {
			global.dimensionsZoom();
		}
	}

	private VGLOBAL() {
		zoom = new LinkedList<IZOOM>();
	}

	private void add(IZOOM object) {
		zoom.add(object);
	}

	private void zoom() {
		for (IZOOM zoomObject : zoom) {
			zoomObject.fontZoom();
			zoomObject.dimensionsZoom();

		}
	}

	private void fontZoom() {
		for (IZOOM zoomObject : zoom) {
			zoomObject.fontZoom();
		}
	}

	private void dimensionsZoom() {
		for (IZOOM zoomObject : zoom) {
			zoomObject.dimensionsZoom();
		}
	}

}
