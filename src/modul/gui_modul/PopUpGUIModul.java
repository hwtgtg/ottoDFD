package modul.gui_modul;

import dfd_gui.DFD_G_Menue;
import global.StartUmgebung;
import jtoolbox.*;

public class PopUpGUIModul implements ITuWas {

	public MenuePopup popmenu;

	MenueEintrag popme;
	MenueEintrag pop_kopieren;
	MenueEintrag pop_bearbeiten;
	MenueEintrag pop_loesschen;
	MenueEintrag pop_abbrechen;
	MenueEintrag pop_CopyToolstring;
	MenueEintrag pop_CopyTabellenkalkulatorstring;

	DFD_I_SENSORAction_Link sensorlink = null;

	/**
	 * @param sensorlink
	 * 
	 */
	public PopUpGUIModul(IComponente modul, DFD_I_SENSORAction_Link sensorlink) {

		popmenu = new MenuePopup();
		setzeSensorlink(sensorlink);
		popmenu.aktivierePopupMenue(modul.getBasisComponente());

	}

	public void setzeSensorlink(DFD_I_SENSORAction_Link sensorlink) {
		this.sensorlink = sensorlink;
		if (sensorlink != null) {

			popme = new MenueEintrag(sensorlink.getGmodul().getFmodul().getPopupBezeichnung(),
					StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
			popme.setzeBeschreibung(sensorlink.getGmodul().getFmodul().getPopupBezeichnung(),
					StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2), true);
			popmenu.menueEintragHinzufuegen(popme);
			popmenu.adSeparator();

			if (sensorlink.getGmodul().getFmodul().isPopupWithCopy()) {
				pop_kopieren = pop_abbrechen = new MenueEintrag("Kopieren",
						StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
				pop_kopieren.setzeLink(this, DFD_G_Menue.POP_COPY);
				popmenu.menueEintragHinzufuegen(pop_kopieren);

			}

			if (sensorlink.getGmodul().getFmodul().isPopupWithEdit()) {
				pop_bearbeiten = pop_abbrechen = new MenueEintrag("Bearbeiten",
						StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
				pop_bearbeiten.setzeLink(this, DFD_G_Menue.POP_EDIT);
				popmenu.menueEintragHinzufuegen(pop_bearbeiten);
			}

			pop_CopyToolstring = new MenueEintrag("Copy Toolstring",
					StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
			pop_CopyToolstring.setzeLink(this, DFD_G_Menue.POP_CopyToolstring);
			popmenu.menueEintragHinzufuegen(pop_CopyToolstring);

			pop_CopyTabellenkalkulatorstring = new MenueEintrag("für Tabellenkalkulator",
					StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
			pop_CopyTabellenkalkulatorstring.setzeLink(this, DFD_G_Menue.POP_CopyTabellenkalkulatorstring);
			popmenu.menueEintragHinzufuegen(pop_CopyTabellenkalkulatorstring);

			popmenu.adSeparator();
		
			
			pop_loesschen = pop_abbrechen = new MenueEintrag("Löschen",
					StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
			pop_loesschen.setzeLink(this, DFD_G_Menue.POP_DELETE);
			popmenu.menueEintragHinzufuegen(pop_loesschen);
			popmenu.adSeparator();
			pop_abbrechen = new MenueEintrag("abbrechen", StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize + 2));
			popmenu.menueEintragHinzufuegen(pop_abbrechen);

		}

	}

	@Override
	public void tuWas(int ID) {
		switch (ID) {
		case DFD_G_Menue.POP_COPY:
			sensorlink.getGmodul().pop_copy();
			break;
		case DFD_G_Menue.POP_DELETE:
			sensorlink.getGmodul().pop_delete();
			break;
		case DFD_G_Menue.POP_EDIT:
			sensorlink.getGmodul().pop_edit();
			break;
		case DFD_G_Menue.POP_CopyToolstring:
			sensorlink.getGmodul().pop_CopyToolstring();
			break;
		case DFD_G_Menue.POP_CopyTabellenkalkulatorstring:
			sensorlink.getGmodul().pop_CopyTabellenkalkulatorstring();
			break;
		default:
		}

	}

}
