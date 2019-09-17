package jpToolbox;

import global.StartUmgebung;
import jtoolbox.*;

/**
 * KomponenteImScrollfenster kapselt JScrollPane
 * 
 * Der Komponente wird beim Erzezgen ein GUI-Element übergeben, z.B. ein Behälter.
 *  
 * @author Hans Witt
 * 
 * Version: 1 (26.2.2009) 
 * 
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import funktionenBaum.dnd.DFD_TransferHandlerExport;

public class BaumImScrollfenster implements IComponente {

	protected CBaumImScrollfenster obj;
	protected int breite = 100;
	protected int hoehe = 100;
	protected int xPos = 0;
	protected int yPos = 0;

	protected boolean sichtbar = true;

	protected double zoomInhalt = 1;

	/**
	 * Konstuktor für Hauptfenster
	 */
	public BaumImScrollfenster(DefaultMutableTreeNode baum) {
		this(Zeichnung.gibZeichenflaeche(), baum);
	}

	/**
	 * Konstruktor für Hauptfenster
	 * 
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public BaumImScrollfenster(DefaultMutableTreeNode baum, int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), baum, 0, 0, neueBreite, neueHoehe);
	}

	/**
	 * Konstruktor für Hauptfenster
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public BaumImScrollfenster(DefaultMutableTreeNode baum, int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), baum, neuesX, neuesY, neueBreite, neueHoehe);
	}

	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 */
	public BaumImScrollfenster(IContainer behaelter, DefaultMutableTreeNode baum) {
		this(behaelter, baum, 0, 0, 100, 100);
	}

	/**
	 * allgemeiner Konstuktor
	 * 
	 * @param behaelter
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public BaumImScrollfenster(IContainer behaelter, DefaultMutableTreeNode baum, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {

		// innen.getBasisComponente().setzePosition(0, 0);
		// innen.getBasisComponente().ausContainerEntfernen();
		obj = new CBaumImScrollfenster(baum);
		if (behaelter != null) {
			behaelter.add(obj, 0);
			setzeDimensionen(neuesX, neuesY, neueBreite, neueHoehe);
			behaelter.validate();
		}
	}

	public void setzeZoomfaktor(double zf) {
		zoomInhalt = obj.setzeZoomfaktor(zf);
	}

	public double getBehaelterZoom() {
		return obj.getBehaelterZoom();
	}

	/**
	 * Das Interface IComponente fordert eine Methode die eine BasisComponente
	 * zurückliefert. Sie wird benötigt, um ein Objekt zu einem anderen Behälter
	 * hinzuzufügen
	 */
	public BasisComponente getBasisComponente() {
		return obj;
	}

	public JTree getjBaum() {
		return obj.getjBaum();
	}

	public void addTreeWillExpandListener(TreeWillExpandListener twl) {
		obj.addTreeWillExpandListener(twl);
	}

	public void addTreeSelectionListener(TreeSelectionListener tsl) {
		obj.addTreeSelectionListener(tsl);
	}

	public void addTreeExpansionListener(TreeExpansionListener tel) {
		obj.addTreeExpansionListener(tel);
	}

	/**
	 * Größe des Anzeigefelds ändern *
	 */
	public void setzeGroesse(int neueBreite, int neueHoehe) {
		breite = neueBreite;
		hoehe = neueHoehe;
		obj.setzeGroesse((int) (breite / zoomInhalt), (int) (hoehe / zoomInhalt));
	}

	/**
	 * neue Position
	 * 
	 * @param x
	 * @param y
	 */
	public void setzePosition(int neuesX, int neuesY) {
		xPos = neuesX;
		yPos = neuesY;
		obj.setzePosition((int) (xPos / zoomInhalt), (int) (yPos / zoomInhalt));
	}

	/**
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public void setzeDimensionen(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		xPos = neuesX;
		yPos = neuesY;
		breite = neueBreite;
		hoehe = neueHoehe;
		obj.setzeDimensionen((int) (xPos / zoomInhalt), (int) (yPos / zoomInhalt), (int) (breite / zoomInhalt),
				(int) (hoehe / zoomInhalt));
	}

	/**
	 * Mache sichtbar.
	 */
	public void sichtbarMachen() {
		sichtbar = true;
		obj.sichtbarMachen();
	}

	/**
	 * Mache unsichtbar.
	 */
	public void unsichtbarMachen() {
		sichtbar = false;
		obj.unsichtbarMachen();
	}

	public void setzeFont(Font font) {
		obj.setzeFont(font);
	}

}

@SuppressWarnings("serial")
class JTreeExpandable extends JTree {

	public JTreeExpandable(DefaultMutableTreeNode root) {
		super(root);
	}

	// Kann einen TreePath expandieren
	public void setExpanded(TreePath tFunktion) {
		this.setExpandedState(tFunktion, true);
	}
}

@SuppressWarnings("serial")
class CBaumImScrollfenster extends BasisComponente implements IContainer {

	JScrollPane scrollPane;
	JTreeExpandable jBaum = null; // Komponente im Scrollfenster

	/**
	 * liefert den Zoomfaktor für den Behälter
	 * 
	 * @return
	 */
	public double getBehaelterZoom() {
		return zoomFaktor * ((IContainer) this.getParent()).getBehaelterZoom();
	}

	public void setzeFont(Font font) {
		// TODO --- Test
		scrollPane.setFont(font);
		scrollPane.getHorizontalScrollBar()
				.setPreferredSize(new Dimension(10, StartUmgebung.bildschirmFaktor(StartUmgebung.scrollbarBreite)));
		scrollPane.getVerticalScrollBar()
				.setPreferredSize(new Dimension(StartUmgebung.bildschirmFaktor(StartUmgebung.scrollbarBreite), 10));

		StartUmgebung.recursiveSetFont(scrollPane, font);
		repaint();
		validate();
	}

	public void addTreeExpansionListener(TreeExpansionListener tel) {
		jBaum.addTreeExpansionListener(tel);
	}

	public void addTreeSelectionListener(TreeSelectionListener tsl) {
		jBaum.addTreeSelectionListener(tsl);

	}

	public void addTreeWillExpandListener(TreeWillExpandListener twl) {
		jBaum.addTreeWillExpandListener(twl);
	}

	public double setzeZoomfaktor(double zf) {
		zoomFaktor = zf;
		zoomen();
		if (sichtbar) {
			((IContainer) this.getParent()).setzeKomponentenKoordinaten(this, xPos, yPos, breite, hoehe);
		} else {
			((IContainer) this.getParent()).setzeKomponentenKoordinaten(this, xPos, yPos, 0, 0);
		}
		return zoomFaktor;
	}

	public CBaumImScrollfenster(DefaultMutableTreeNode baum) {
		this.setLayout(new BorderLayout());

		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scrollPane, BorderLayout.CENTER);

		jBaum = new JTreeExpandable(baum);

		// *********************************************************************************************************************************
		jBaum.setTransferHandler(new DFD_TransferHandlerExport());
		// TODO Einschränken auf Blätter
		// TODO Transferobjekt anpassen
		jBaum.setDragEnabled(true);

		// *********************************************************************************************************************************

		Bilddatei plus = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_dirclose2.png");
		plus.einpassen(StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize * 1.8F),
				StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize * 1.2F));
		Icon plusIcon = new ImageIcon((plus.leseBild()));

		Bilddatei minus = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_diropen2.png");
		minus.einpassen(StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize * 1.8F),
				StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize * 1.2F));
		Icon minusIcon = new ImageIcon((minus.leseBild()));

		Bilddatei blatt = new Bilddatei("images" + global.StartUmgebung.fileseparator + "wt_modul.png");
		blatt.einpassen(StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize * 1.8F),
				StartUmgebung.bildschirmFaktor(StartUmgebung.fontSize * 1.2F));
		Icon leafIcon = new ImageIcon((blatt.leseBild()));

		// Update only one tree instance
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jBaum.getCellRenderer();

		renderer.setClosedIcon(plusIcon);
		renderer.setOpenIcon(minusIcon);
		renderer.setLeafIcon(leafIcon);
		scrollPane.setViewportView(jBaum);

	}

	/**
	 * @return the jBaum
	 */
	public JTreeExpandable getjBaum() {
		return jBaum;
	}

	public void setzeSichtbarkeit(boolean sichtbar) {
		this.getParent().setVisible(sichtbar);
	}

	// Wird von der Graphikkomponente zum Positionieren im Behälter aufgerufen
	// position sollte ignoriert werden. Nur Beite und Höhe wichtig
	public void setzeKomponentenKoordinaten(JComponent obj, int x, int y, int width, int height) {
		obj.setBounds(x, y, width, height);
		obj.setPreferredSize(new Dimension(width, height));
		repaint();
		validate();
		// Zeichnung.gibJFrame().validate();
		Zeichnung.gibZeichenflaeche().validate();
	}

	public void setzeKomponentenGroesse(JComponent obj, int width, int height) {
		obj.setSize(width, height);
		obj.setPreferredSize(new Dimension(width, height));
		obj.repaint();
		repaint();
		validate();
		// Zeichnung.gibJFrame().validate();
		Zeichnung.gibZeichenflaeche().validate();
	}

	public void setzeKomponentenPosition(JComponent obj, int x, int y) {
		obj.setLocation(x, y);
		obj.setPreferredSize(new Dimension(obj.getWidth(), obj.getHeight()));
		obj.repaint();
		repaint();
		validate();
		// Zeichnung.gibJFrame().validate();
		Zeichnung.gibZeichenflaeche().validate();
	}

	public void paintComponentSpezial(Graphics g) {

	}

}

