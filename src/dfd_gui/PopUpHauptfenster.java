package dfd_gui;

import global.StartUmgebung;
import jtoolbox.*;

public class PopUpHauptfenster {

	public MenuePopup popmenu ;
	
	MenueEintrag popme ;
	MenueEintrag pop_abbrechen ;

	/**
	 * 
	 */
	public PopUpHauptfenster() {
		popmenu = new MenuePopup();
		
		popme = new MenueEintrag("DFD-Hauptfenster",StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
		popme.setzeBeschreibung("DFD-Hauptfenster",
				StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2), true);

		popmenu.menueEintragHinzufuegen(popme);
		popmenu.adSeparator();
		pop_abbrechen = new MenueEintrag("abbrechen",StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
		popmenu.menueEintragHinzufuegen(pop_abbrechen);

// TODO
		popmenu.aktivierePopupMenue(DFD_Arbeitsfenster.getArbeitsfenster().getBasisComponente());
		
	}
	
}
