package dfd_gui.zoom;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingConstants;

import dfd_gui.DFD_Arbeitsfenster;
import drucken.DFD_Drucken;
import global.DFD__GUIKONST;
import global.StartUmgebung;
import jtoolbox.Eingabefeld;
import jtoolbox.IContainer;
import jtoolbox.StaticTools;

public class Zoom_Eingabe extends Eingabefeld implements Zoom__IModul {
	Zoom__FunktionTool zoom;

	// Klassenfabrik
	public static Zoom_Eingabe getZoomEingabe(IContainer behaelter, String neuerText, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		Zoom__FunktionTool zoom = new Zoom__FunktionTool(neuesX, neuesY, neueBreite, neueHoehe, StartUmgebung.fontSize);
		Zoom_Eingabe ausgabe = new Zoom_Eingabe(zoom, behaelter, neuerText, neuesX, neuesY, neueBreite, neueHoehe);
		return ausgabe;
	}

	// Konstruktor, wird nur über Fabrik aufgerufen
	protected Zoom_Eingabe(Zoom__FunktionTool zoom, IContainer behaelter, String neuerText, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		super(behaelter, neuerText, zoom.getX(), zoom.getY(), zoom.getBreite(), zoom.getHoehe());
		this.zoom = zoom;
		zoom.setzeZoomobjekt(this);

		super.setzeSchriftgroesse(zoom.getFontgroesse());
		addZoomfenster();
	}

	@Override
	public void addZoomfenster() {
		Zoom__Verwalter.getZoomverwalter().addZoomkomponente(this);
	}

	@Override
	public void modulLoeschen() {
		Zoom__Verwalter.getZoomverwalter().removeZoomkomponente(this);
		entfernen();
	}

	@Override
	public void setzeSchriftgroesse(int neueFontgroesse) {
		zoom.setzeFontgroesse(neueFontgroesse);
		super.setzeSchriftgroesse(zoom.getFontgroesse());
	}

	@Override
	public void setzeAnzeigezoomNeu() {
		setzeDimensionen(zoom.getX(), zoom.getY(), zoom.getBreite(), zoom.getHoehe());
		super.setzeSchriftgroesse(zoom.getFontgroesse());
	}

	@Override
	public void setzePosition(int posX, int posY) {
		if (posX < 0) {
			posX = 0;
		}
		if (posY < 0) {
			posY = 0;
		}

		if (DFD_setzePosition(posX, posY)) {
			super.setzePosition(posX, posY);
		} else {
		}
	}

	@Override
	public boolean DFD_setzePosition(int posX, int posY) {
		if (DFD_Arbeitsfenster.getArbeiter().testeArbeitsfensterNeu(this, posX, posY, zoom.getBreite(),
				zoom.getHoehe())) {
			zoom.neueOriginalPositionAusObjekt(posX, posY);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setzeGroesse(int breite, int hoehe) {
		if (DFD_setzeGroesse(breite, hoehe)) {
			super.setzeGroesse(breite, hoehe);
		}
	}

	@Override
	public boolean DFD_setzeGroesse(int breite, int hoehe) {
		zoom.neueOriginalGroesseAusObjekt(breite, hoehe);
		DFD_Arbeitsfenster.getArbeiter().testeArbeitsfensterNeu(this, super.xPos, super.yPos, breite, hoehe);
		return true;
	}

	@Override
	public void setzeDimensionen(int posX, int posY, int breite, int hoehe) {
		if (DFD_SetzeDimensionen(posX, posY, breite, hoehe)) {
			super.setzeDimensionen(posX, posY, breite, hoehe);
		} else {
			if (posX < 0) {
				posX = 0;
			}
			if (posY < 0) {
				posY = 0;
			}
			setzeDimensionen(0, 0, breite, hoehe);

		}
	}

	@Override
	public boolean DFD_SetzeDimensionen(int posX, int posY, int breite, int hoehe) {
		return DFD_Arbeitsfenster.getArbeiter().testeArbeitsfensterNeu(this, posX, posY, breite, hoehe);
	}

	@Override
	public Rectangle leseDimensionInclZoom() {
		return new Rectangle(super.xPos, super.yPos, super.breite, super.hoehe);
	}

	@Override
	public Rectangle leseDimensionOhneZoom() {
		return zoom.leseDimensionOhneZoom();
	}

	@Override
	public void setzeAusgewaehlt() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setzeNichtAusgewaehlt() {

	}

	public void mitRand() {
		super.mitRand();
	}

	public void bildZeichnen(Graphics2D g2, Rectangle dimDFD, Rectangle dimEingabe) {
		
		String anzeigetext = leseText();
		if ((anzeigetext==null)||(anzeigetext.length()==0)) return ;
		
		g2.setColor(StaticTools.getColor(DFD__GUIKONST.SchriftfarbeAnzeige));
		g2.setBackground(StaticTools.getColor(DFD__GUIKONST.HintergrundfarbeAnzeige));

		Font font = g2.getFont();
		
		Font fontNeu = font.deriveFont(DFD_Drucken.drF(DFD__GUIKONST.BILD_FONT_GROESSE)*1.0F);
		fontNeu=fontNeu.deriveFont(Font.BOLD);
		g2.setFont(fontNeu);

		FontRenderContext frc = g2.getFontRenderContext();
		TextLayout layout = new TextLayout(anzeigetext, font, frc);
		Rectangle2D bounds = layout.getBounds();

		int strBreite = (int) bounds.getWidth();
		int strHoehe = (int)bounds.getHeight();
		int ausrichtung = getHorizontalAlignment();

		
		Rectangle local = leseDimensionOhneZoom(); 
		
		int links = -dimDFD.x+dimEingabe.x+local.x;
		int oben = -dimDFD.y+dimEingabe.y+local.y;
		int txtBreite = local.width;
		int textHoehe = local.height;

		if (ausrichtung == SwingConstants.LEFT) {
			g2.drawString(anzeigetext, DFD_Drucken.drX(links), DFD_Drucken.drY(oben + textHoehe /2)+ strHoehe / 2);
		} else if (ausrichtung == SwingConstants.CENTER) {
			g2.drawString(anzeigetext, DFD_Drucken.drX(links + txtBreite / 2)-strBreite/2, DFD_Drucken.drY(oben + textHoehe / 2)+strHoehe/2);
		} else if (ausrichtung == SwingConstants.RIGHT) {
			g2.drawString(anzeigetext, DFD_Drucken.drX(links + txtBreite )- strBreite, DFD_Drucken.drY(oben + textHoehe / 2)+strHoehe/2);
		}
		
		g2.setFont(font);
	}


}
