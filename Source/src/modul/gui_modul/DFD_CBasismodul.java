package modul.gui_modul;

import dfd_gui.zoom.DFD__CMausBehaelter;

@SuppressWarnings("serial")
public abstract class DFD_CBasismodul extends DFD__CMausBehaelter  {
	boolean ausgewaehlt = false;

	public void setzeAusgewaehlt() {
		ausgewaehlt = true;
		repaint();
	}

	public void setzeNichtAusgewaehlt() {
		ausgewaehlt = false;
		repaint();
	}

}
