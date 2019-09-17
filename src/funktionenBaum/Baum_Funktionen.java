package funktionenBaum;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import Dateioperationen.FunktionsReader;
import Dateioperationen.FunktionsWriter;

public class Baum_Funktionen implements TreeExpansionListener,
		TreeWillExpandListener, TreeSelectionListener {

	JTree tree;

	/**
	 * @param tree
	 */
	public Baum_Funktionen(JTree tree) {
		this.tree = tree;
		tree.addTreeWillExpandListener(this);
		tree.addTreeSelectionListener(this);
		
	}

	public void treeCollapsed(TreeExpansionEvent event) {
	}

	public void treeExpanded(TreeExpansionEvent event) {
	}

	public void treeWillCollapse(TreeExpansionEvent e)
			throws ExpandVetoException {
		TreePath p = e.getPath();
		// Der Hauptast und der Funktionsast müssen offen bleiben!
		if ((p.getPathCount() == 2) || (p.getPathCount() == 1))
			throw new ExpandVetoException(e, "Weil ich nicht will");
	}

	public void treeWillExpand(TreeExpansionEvent arg0)
			throws ExpandVetoException {
	}

	public void valueChanged(TreeSelectionEvent e) {

		TreePath p = e.getPath();
		if (tree.isPathSelected(p)) {
			int i = p.getPathCount();
			if( i == 2 ){
				String text = p.getLastPathComponent().toString();
				if (text.equals("Öffnen")){
					FunktionsReader.leseDFDMODULEundVERKNUEPFUNGEN();
				} else if (text.equals("Speichern")){
					FunktionsWriter.schreibeDFDMODULEundVERKNUEPFUNGEN_DATEINAMEFRAGEN();	
				}
			} else if( i > 2 ){
				@SuppressWarnings("unused")
				DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) p.getLastPathComponent();
				
			}
			tree.clearSelection();
		} else {
//			LOG.outln("Deselect --------------" + p);
		}
	}

}
