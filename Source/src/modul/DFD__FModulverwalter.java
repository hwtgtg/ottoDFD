package modul;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Vector;

import dfd_gui.DFD_Arbeitsfenster;
import dfd_gui.DFD_G_Menue;
import drucken.DFD_Drucken;
import global.DFD__GUIKONST;
import jtoolbox.StaticTools;

public class DFD__FModulverwalter {

	private static DFD__FModulverwalter mverwalter = null;

	public Vector<DFD__IF_Modul> fModule;

	public static DFD__FModulverwalter getModulverwalter() {
		if (mverwalter == null) {
			mverwalter = new DFD__FModulverwalter();
		}
		return mverwalter;
	}

	private DFD__FModulverwalter() {
		fModule = new Vector<DFD__IF_Modul>();
	}

	public void add_FModul(DFD__IF_Modul fModul) {
		fModule.addElement(fModul);
	}

	public void removeFmodul(DFD__IF_Modul fModul) {
		fModule.remove(fModul);
	}

	public DFD__IF_Modul getModul(int modulID) {
		for (DFD__IF_Modul modul : fModule) {
			if (modul.getDfd_fModulNummer() == modulID) {
				return modul;
			}
		}
		return null;
	}

	public void setzeAlleModuleAnzeigeWert() {
		DFD_G_Menue.anzeigeart = DFD_G_Menue.R_AKTION_WERT;
		for (DFD__IF_Modul aktuell : fModule) {
			aktuell.setzeTooltipAnzeigeWert();
		}
	}

	public void setzeAlleModuleAnzeigeAnzeige() {
		DFD_G_Menue.anzeigeart = DFD_G_Menue.R_AKTION_ANZEIGE;
		for (DFD__IF_Modul aktuell : fModule) {
			aktuell.setzeTooltipAnzeigeAnzeige();
		}
	}

	public void loescheAlleModule() {

		// Der eigentliche Vektor wird verändert !
		Vector<DFD__IF_Modul> fModuleCopy = new Vector<DFD__IF_Modul>();
		for (DFD__IF_Modul fModul : fModule) {
			fModuleCopy.add(fModul);
		}

		for (DFD__IF_Modul fModul : fModuleCopy) {
			fModul.modulLoeschen();
		}
	}

	public static void nummeriereModuleNeu() {
		int ID = 1;
		for (DFD__IF_Modul aktuell : getModulverwalter().fModule) {
			aktuell.setDfd_fModulNummer(ID);
			ID++;
		}
	}
	
	public static void aktiviereAlleEingaben() {
		for (DFD__IF_Modul aktuell : getModulverwalter().fModule) {
			if( aktuell instanceof DFD_FModul_Eingabe ) {
				((DFD_FModul_Eingabe)aktuell).setzeAusgangGueltig();
			} else if(aktuell instanceof DFD_FModul_Konstante ) {
				((DFD_FModul_Konstante)aktuell).setzeAusgangGueltig();
			}
		}

	}
	
	
	
	
	

	public static void verknuepfe(int eingangsmodulID, int eingangsNr, int idAusgang) {
		DFD__IF_Modul modul = DFD__FModulverwalter.getModulverwalter().getModul(eingangsmodulID);
		DFD__IF_Modul modulAusgang = DFD__FModulverwalter.getModulverwalter().getModul(idAusgang);

		if ((modul != null) && (modulAusgang != null)) {
			modul.dateiReaderVerbindeFModulEingangMitFremdenFAusgang(eingangsNr, modulAusgang);
		}
	}

	public Rectangle bestimmeDimensionen() {
		Rectangle d_aktuell = null;
		int links = Integer.MAX_VALUE;
		int oben = Integer.MAX_VALUE;
		int rechts = -1;
		int unten = -1;

		int aRechts = 0;
		int aUntern = 0;

		for (DFD__IF_Modul aktuell : fModule) {
			d_aktuell = aktuell.leseRechteck();

			if ((d_aktuell.x >= 0) && (d_aktuell.x < links))
				links = d_aktuell.x;
			if ((d_aktuell.y >= 0) && (d_aktuell.y < oben))
				oben = d_aktuell.y;

			aRechts = d_aktuell.x + d_aktuell.width;
			if ((aRechts >= 0) && (aRechts > rechts))
				rechts = aRechts;

			aUntern = d_aktuell.y + d_aktuell.height;
			if ((aUntern >= 0) && (aUntern > unten))
				unten = aUntern;
		}
		if ((rechts == -1) || (unten == -1)) {
			return null;
		} else {
			if (links > DFD__GUIKONST.bildrand) {
				links = links - DFD__GUIKONST.bildrand;
			} else {
				links = 0;
			}
			if (oben > DFD__GUIKONST.bildrand) {
				oben = oben - DFD__GUIKONST.bildrand;
			} else {
				oben = 0;
			}
			rechts = rechts + DFD__GUIKONST.bildrand;
			unten = unten + DFD__GUIKONST.bildrand;

			return new Rectangle(links, oben, rechts - links, unten - oben);
		}
	}

	public void zeichne(Graphics2D g2, Rectangle dimDFD ) {

		g2.setColor(StaticTools.getColor(DFD__GUIKONST.HintergrundfarbeAnzeige));
		g2.setColor(Color.black);
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Font f = DFD_Arbeitsfenster.getArbeitsfenster().getBasisComponente().getFont();
		
		f = f.deriveFont(DFD_Drucken.drFF(DFD__GUIKONST.DFD_FONT_GROESSE));
		g2.setFont(f);
		
		for (DFD__IF_Modul aktuell : fModule) {

			g2.setBackground(Color.white);
			g2.setColor(Color.black);
			
			aktuell.bildZeichnen(g2, dimDFD);
		}
	}

	public void zeichneVerbindungen(Graphics2D g2, Rectangle dimDFD) {
		for (DFD__IF_Modul aktuell : fModule) {

			g2.setBackground(Color.white);
			g2.setColor(Color.black);
			
			aktuell.bildZeichneVerbindungen(g2, dimDFD);
		}
	}

}
