package modul.gui_modul;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import dfd_gui.zoom.DialogZoom;
import dfd_gui.zoom.Zoom_Textfenster;
import dfd_gui.zoom.Zoom__FunktionTool;
import dfd_gui.zoom.Zoom__Verwalter;
import drucken.DFD_Drucken;
import global.CHANGE;
import global.DFD__GUIKONST;
import global.StartUmgebung;
import jtoolbox.IContainer;
import jtoolbox.StaticTools;
import modul.DFD_GModul_Beschreibung;
import modul.DFD__Daten;
import modul.DFD__IG_Modul;

public class DFD_GUI_BeschreibungText extends Zoom_Textfenster
		implements DFD_I_SENSORAction_Link, DFD_I_GUI_Basismodul {

	DFD_GModul_Beschreibung dfd_beschreibung = null;

	DFD_Sensor modulsensor;
	DFD_Sensor_Groesse groessesensor;

	// Klassenfabrik
	public static DFD_GUI_BeschreibungText getDFD_GUI_BeschreibungText(IContainer behaelter, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		return getDFD_GUI_BeschreibungText(behaelter, neuesX, neuesY, neueBreite, neueHoehe, false);
	}

	public static DFD_GUI_BeschreibungText getDFD_GUI_BeschreibungText(IContainer behaelter, int neuesX, int neuesY,
			int neueBreite, int neueHoehe, boolean ZENTRIERT) {
		if (ZENTRIERT) {
			neuesX = neuesX - neueBreite / 2;
			neuesY = neuesY - neueHoehe / 2;
		}
		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);

		DFD_GUI_BeschreibungText ausgabe = new DFD_GUI_BeschreibungText(zoom, behaelter, neuesX, neuesY, neueBreite,
				neueHoehe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen

	protected DFD_GUI_BeschreibungText(Zoom__FunktionTool zoom, IContainer behaelter, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		super(zoom, behaelter, neuesX, neuesY, neueBreite, neueHoehe);

		modulsensor = DFD_Sensor.getDFD_Sensor(behaelter, neuesX, neuesY, neueBreite, neueHoehe);
		modulsensor.setzeSensorLink(this);
		modulsensor.sichtbarMachen();

		groessesensor = DFD_Sensor_Groesse.getDFD_Sensor_Groesse(
				neuesX + neueBreite - DFD__GUIKONST.SENSOR_GroesseAendern,
				neuesY + neueHoehe - DFD__GUIKONST.SENSOR_GroesseAendern, DFD__GUIKONST.SENSOR_GroesseAendern,
				DFD__GUIKONST.SENSOR_GroesseAendern);
		groessesensor.unsichtbarMachen();
		groessesensor.setzeSensorLink(this);

		setzeScrollbarDimension(StartUmgebung.bildschirmFaktor(StartUmgebung.scrollbarBreite));
		setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
		setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
	}

	// @Override
	// public void setzeZoomverhalten(int beiZoomAnpassen) {
	// zoom.setzeZoomverhalten(beiZoomAnpassen);
	// modulsensor.zoom.setzeZoomverhalten(beiZoomAnpassen);
	// }

	@Override
	public void modulLoeschen() {
		modulsensor.modulLoeschen();
		groessesensor.modulLoeschen();
		Zoom__Verwalter.getZoomverwalter().removeZoomkomponente(this);
		super.entfernen();
	}

	public void recall(DFD_GModul_Beschreibung dfd_GModul_Beschreibung) {
		this.dfd_beschreibung = dfd_GModul_Beschreibung;
		modulsensor.aktivierePopup();
	}

	@Override
	public void sensorAktionDRAGG(int x, int y, int ID, DFD_Sensor sensor) {
		if (sensor == modulsensor) {
			CHANGE.setChanged();
			this.setzePosition(x, y);
		} else if (sensor == groessesensor) {
			int gr = DialogZoom.intZoomWert(DFD__GUIKONST.SENSOR_GroesseAendern);
			int neueBreite = x - this.xPos + gr;
			if (neueBreite <= DialogZoom.intZoomWert(DFD__GUIKONST.BeschreibungText_Minimum)) {
				neueBreite = DialogZoom.intZoomWert(DFD__GUIKONST.BeschreibungText_Minimum);
			}
			int neueHoehe = y - this.yPos + gr;
			if (neueHoehe <= DialogZoom.intZoomWert(DFD__GUIKONST.BeschreibungText_Minimum)) {
				neueHoehe = DialogZoom.intZoomWert(DFD__GUIKONST.BeschreibungText_Minimum);
			}
			this.setzeGroesse(neueBreite, neueHoehe);
			modulsensor.setzeGroesse(neueBreite, neueHoehe);
		} else {

		}
	}

	@Override
	public void sensorAktionPRESS(int x, int y, int ID, DFD_Sensor sensor) {
		dfd_beschreibung.andereModuleDeaktivieren();
	}

	@Override
	public void sensorAktionRELEASE(int x, int y, int ID, DFD_Sensor sensor) {
		if (sensor == modulsensor) {
			int gr = DialogZoom.intZoomWert(DFD__GUIKONST.SENSOR_GroesseAendern);
			groessesensor.setzeDimensionen(x + breite - gr, y + hoehe - gr, gr, gr);
		}

	}

	@Override
	public void sensorAktionClick(int x, int y, int ID, DFD_Sensor sensor, int clicks) {
	}

	@Override
	public void setzeAusgewaehlt() {
		CHANGE.setChanged();
		if (modulsensor.isSichtbar()) {
			modulsensor.unsichtbarMachen();
		}
		if (!groessesensor.isSichtbar())
			groessesensor.sichtbarMachen();

		this.setzeEditierbar();
		this.setzeHintergrundfarbe("weiss");
		setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeBearbeiten);
		setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeBearbeiten);

		if (dfd_beschreibung != null) {
			dfd_beschreibung.setzeAusgewaehlt();
		}
	}

	@Override
	public void setzeNichtAusgewaehlt() {
		if (!modulsensor.isSichtbar()) {
			modulsensor.sichtbarMachen();
		}
		if (groessesensor.isSichtbar()) {
			groessesensor.unsichtbarMachen();
		}
		this.setzeNurAnzeige();
		setzeHintergrundfarbe(DFD__GUIKONST.HintergrundfarbeAnzeige);
		setzeSchriftfarbe(DFD__GUIKONST.SchriftfarbeAnzeige);
	}

	public int lesePosX() {
		return zoom.leseDimensionOhneZoom().x;
	}

	public int lesePosY() {
		return zoom.leseDimensionOhneZoom().y;
	}

	public int leseBreite() {
		return zoom.leseDimensionOhneZoom().width;
	}

	public int leseHoehe() {
		return zoom.leseDimensionOhneZoom().height;
	}

	public void setzeModulPosition(int posX, int posY) {
		DFD_setzePosition(posX, posY);
	}

	@Override
	public Rectangle leseRechteck() {
		return zoom.leseDimensionOhneZoom();
	}

	@Override
	public DFD__IG_Modul getGmodul() {
		return dfd_beschreibung;
	}

	public void setzeAusgangsBezeichnungen(DFD__Daten daten) {
	}

	@Override
	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD) {

		Rectangle dimGuiBeschreibung = leseDimensionOhneZoom();

		String[] zeilen = leseZeilen();

		dimGuiBeschreibung.x -= dimDFD.x;
		dimGuiBeschreibung.y -= dimDFD.y;

		// Hintergrund
		g2.setColor(DFD__GUIKONST.paintHintergrund);
		g2.fillRoundRect(DFD_Drucken.drX(dimGuiBeschreibung.x), DFD_Drucken.drY(dimGuiBeschreibung.y),
				DFD_Drucken.drF(dimGuiBeschreibung.width - 1), DFD_Drucken.drF(dimGuiBeschreibung.height - 1),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));

		g2.setColor(DFD__GUIKONST.paintVordergrund);
		g2.drawRoundRect(DFD_Drucken.drX(dimGuiBeschreibung.x), DFD_Drucken.drY(dimGuiBeschreibung.y),
				DFD_Drucken.drF(dimGuiBeschreibung.width - 1), DFD_Drucken.drF(dimGuiBeschreibung.height - 1),
				DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS), DFD_Drucken.drF(DFD__GUIKONST.FM_RECHTECK_RADIUS));

		g2.setColor(StaticTools.getColor(DFD__GUIKONST.SchriftfarbeAnzeige));
		g2.setBackground(StaticTools.getColor(DFD__GUIKONST.HintergrundfarbeAnzeige));

		Font font = g2.getFont();

		Font fontNeu = font.deriveFont( DFD_Drucken.drFF(DFD__GUIKONST.FAKTOR_FONTGROSSBild * DFD__GUIKONST.BILD_FONT_GROESSE));
		g2.setFont(fontNeu);

		FontRenderContext frc = g2.getFontRenderContext();
		TextLayout layout = new TextLayout("AWgZ", fontNeu, frc);
		Rectangle2D bounds = layout.getBounds();
		int strHoehe = (int) bounds.getHeight();

		int links = DFD_Drucken.drX( dimGuiBeschreibung.x + DFD__GUIKONST.BILDBESCHREIBUNG_RANDX);
		int oben =  DFD_Drucken.drY(dimGuiBeschreibung.y + DFD__GUIKONST.BILDBESCHREIBUNG_RANDY);

		for (int i = 0; i < zeilen.length; i++) {
			if ((zeilen[i] == null) || (zeilen[i].length() == 0))
				break;
			g2.drawString(zeilen[i], links, oben + strHoehe);
			oben += strHoehe +  DFD_Drucken.drF(DFD__GUIKONST.BILDBESCHREIBUNG_ZEILENABSTAND);
		}

		g2.setFont(font);
	}

}
