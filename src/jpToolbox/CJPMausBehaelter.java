package jpToolbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JComponent;

import jtoolbox.BasisComponente;
import jtoolbox.IContainer;
import jtoolbox.ITuWas;
import jtoolbox.StaticTools;
import jtoolbox.Zeichnung;

@SuppressWarnings("serial")
public class CJPMausBehaelter extends BasisComponente implements IContainer,
		MouseListener, MouseMotionListener, MouseWheelListener {
	
	private boolean					mitRaster			= false;
	private int						deltaX				= 50;
	private int						deltaY				= 50;
	
	private Vector<BasisComponente>	unterComponenten	= new Vector<BasisComponente>();
	private String					anzeige				= null;
	private boolean					mitRand				= false;
	
	int								anzeigenbreite		= 0;
	int								anzeigenhoehe		= 0;
	int								ascend				= 0;
	
	private String					hintergrundFarbe	= null;
	Color							hfarbe;
	
	public Component add(Component comp, int index) {
		Component erg = super.add(comp, index);
		unterComponenten.addElement((BasisComponente) comp);
		return erg;
	}
	
	/**
	 * liefert den Zoomfaktor fuer den Behaelter
	 * 
	 * @return
	 */
	public double getBehaelterZoom() {
		return zoomFaktor * ((IContainer) this.getParent()).getBehaelterZoom();
	}
	
	public double setzeZoomfaktor(double zf) {
		zoomFaktor = zf;
		bzf = ((IContainer) this.getParent()).getBehaelterZoom();
		fontGroesse = (int) Math.round(originalFontGroesse * zoomFaktor * bzf);
		setzeSchriftgroesse(fontGroesse);
		
		originalXPos = (int) (originalXPos / zoomFaktor);
		originalYPos = (int) (originalYPos / zoomFaktor);
		
		for (int i = 0; i < unterComponenten.size(); i++) {
			unterComponenten.get(i).zommfaktorAnpassen();
		}
		
		zoomen();
		if (sichtbar) {
			((IContainer) this.getParent()).setzeKomponentenKoordinaten(this,
					xPos, yPos, breite, hoehe);
		} else {
			((IContainer) this.getParent()).setzeKomponentenKoordinaten(this,
					xPos, yPos, 0, 0);
		}
		return zoomFaktor;
	}
	
	public void setzeMitRand(boolean mitRand) {
		this.mitRand = mitRand;
		Zeichnung.gibZeichenflaeche().repaint();
	}
	
	public void setzeSchriftgroesse(int i) {
		setFontsize(i);
		repaint();
	}
	
	public void setText(String s) {
		setzeMitRand(true);
		anzeige = s;
	}
	
	public void setzeSchriftFarbe(String farbname) {
		farbe = StaticTools.getColor(farbname);
		repaint();
	}
	
	public void setzeHintergrundfarbe(String neueFarbe) {
		hintergrundFarbe = neueFarbe;
		if (hintergrundFarbe != null)
			hfarbe = StaticTools.getColor(hintergrundFarbe);
		repaint();
	}
	
	public void setzeMitRaster(boolean mitRaster) {
		this.mitRaster = mitRaster;
		// Zeichnung.gibJFrame().repaint();
		Zeichnung.gibZeichenflaeche().repaint();
	}
	
	public void setzeDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}
	
	public void setzeDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}
	
	public CJPMausBehaelter() {
		/*
		 * this.setBorder(BorderFactory.createCompoundBorder(BorderFactory
		 * .createRaisedBevelBorder(), BorderFactory
		 * .createLoweredBevelBorder()));
		 */
		this.setLayout(null);
	}
	
	// Bereits in Basiscomponente definiert
	// ITuWas linkObj;
	// int id = 0; // Basis-ID der Komponente fuer Callback
	
	public void setzeLink(ITuWas linkObj, int ID) {
		this.linkObj = linkObj;
		id = ID;
	}
	
	private int		mX			= 0;
	private int		mY			= 0;
	private int		click		= 0;
	private int		button		= 0;
	private boolean	shift		= false;
	private boolean	ctrl		= false;
	private boolean	alt			= false;
	private int		rotation	= 0;
	
	private boolean	action		= false;
	
	public int getMX() {
		return mX;
	}
	
	public int getMY() {
		return mY;
	}
	
	public int getClickCount() {
		return click;
	}
	
	public int getButton() {
		return button;
	}
	
	public boolean getShift() {
		return shift;
	}
	
	public boolean getCtrl() {
		return ctrl;
	}
	
	public boolean getAlt() {
		return alt;
	}
	
	public int getRotation() {
		return rotation;
	}
	
	public boolean isAction() {
		boolean wert = action;
		action = false;
		return wert;
	}
	

	
	public void tuWas(int ID, MouseEvent e) {
		final int IDrunnable = ID;
		action = true;
		mX = e.getX();
		mY = e.getY();
		click = e.getClickCount();
		button = e.getButton();
		shift = e.isShiftDown();
		ctrl = e.isControlDown();
		alt = e.isAltDown();
		if (linkObj != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					linkObj.tuWas(id + IDrunnable);
				};
			}).start();
		}
	}
	
	public void setMouseAction(int action) {
		mouseAction = action;
		if ((mouseAction & 0x1F) != 0) {
			addMouseListener(this);
		} else {
			removeMouseListener(this);
		}
		if ((mouseAction & 0x30) != 0) {
			addMouseMotionListener(this);
		} else {
			removeMouseMotionListener(this);
		}
		if ((mouseAction & (1 << JPMausBehaelter.WHEEL)) != 0) {
			addMouseWheelListener(this);
		} else {
			removeMouseWheelListener(this);
		}
	}
	
	public int getMouseAction() {
		return mouseAction;
	}
	
	private int	mouseAction	= 0;
	
	public void mouseClicked(MouseEvent e) {
		if ((mouseAction & (1 << JPMausBehaelter.CLICK)) != 0) {
			tuWas(JPMausBehaelter.CLICK, e);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if ((mouseAction & (1 << JPMausBehaelter.PRESS)) != 0) {
			tuWas(JPMausBehaelter.PRESS, e);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if ((mouseAction & (1 << JPMausBehaelter.RELEASE)) != 0) {
			tuWas(JPMausBehaelter.RELEASE, e);
		}
		
	}
	
	public void mouseEntered(MouseEvent e) {
		if ((mouseAction & (1 << JPMausBehaelter.ENTER)) != 0) {
			tuWas(JPMausBehaelter.ENTER, e);
		}
	}
	
	public void mouseExited(MouseEvent e) {
		if ((mouseAction & (1 << JPMausBehaelter.EXIT)) != 0) {
			tuWas(JPMausBehaelter.EXIT, e);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if ((mouseAction & (1 << JPMausBehaelter.DRAGGED)) != 0) {
			tuWas(JPMausBehaelter.DRAGGED, e);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if ((mouseAction & (1 << JPMausBehaelter.MOVED)) != 0) {
			tuWas(JPMausBehaelter.MOVED, e);
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if ((mouseAction & (1 << JPMausBehaelter.WHEEL)) != 0) {
			rotation = e.getWheelRotation();
			tuWas(JPMausBehaelter.WHEEL, e);
		}
	}
	
	public void setzeSichtbarkeit(boolean sichtbar) {
		this.getParent().setVisible(sichtbar);
	}
	
	public void setzeKomponentenKoordinaten(JComponent obj, int x, int y,
			int width, int height) {
		obj.setBounds(x, y, width, height);
		repaint();
		validate();
		// Zeichnung.gibJFrame().validate();
		Zeichnung.gibZeichenflaeche().validate();
	}
	
	public void setzeKomponentenGroesse(JComponent obj, int width, int height) {
		obj.setSize(width, height);
		obj.repaint();
		repaint();
		validate();
		// Zeichnung.gibJFrame().validate();
		Zeichnung.gibZeichenflaeche().validate();
	}
	
	public void setzeKomponentenPosition(JComponent obj, int x, int y) {
		obj.setLocation(x, y);
		obj.repaint();
		repaint();
		validate();
		// Zeichnung.gibJFrame().validate();
		Zeichnung.gibZeichenflaeche().validate();
	}
	
	/**
	 * Die Darstellung der Komponente wird hier programmiert.
	 */
	public void paintComponentSpezial(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Graphik-Abmessungen
		int breite = getSize().width - 1;
		int hoehe = getSize().height - 1;
		
		if (mitRand) {
			
			if (hintergrundFarbe != null) {
				g2.setColor(hfarbe);
				g2.fill3DRect(0, 0, breite, hoehe, true);
			}
			
			g2.setColor(farbe);
			if (anzeige != null) {
				Rectangle2D rec = (g2.getFontMetrics()).getStringBounds(" "
						+ anzeige + " ", g2);
				anzeigenbreite = (int) rec.getWidth();
				anzeigenhoehe = (int) rec.getHeight();
				ascend = g2.getFontMetrics().getMaxAscent();
			}
			
			if (anzeige != null) {
				g2.drawString(anzeige, 6, ascend);
				g2.drawLine(0, anzeigenhoehe / 2, 4, anzeigenhoehe / 2);
				g2.drawLine(anzeigenbreite + 1, anzeigenhoehe / 2, breite,
						anzeigenhoehe / 2);
				g2.drawLine(0, anzeigenhoehe / 2, 0, hoehe);
				g2.drawLine(breite, anzeigenhoehe / 2, breite, hoehe);
				g2.drawLine(0, hoehe, breite, hoehe);
				
			} else {
				g2.draw3DRect(0, 0, breite, hoehe, true);
			}
		}
		
		if (mitRaster) {
			Color farbe = StaticTools.getColor("schwarz");
			g.setColor(farbe);
			
			int hor = deltaX;
			while (hor < breite) {
				g2.drawLine(hor, 0, hor, hoehe);
				hor += deltaX;
			}
			
			int ver = deltaY;
			while (ver < hoehe) {
				g2.drawLine(0, ver, breite, ver);
				ver += deltaY;
			}
		}
	}
	
}
