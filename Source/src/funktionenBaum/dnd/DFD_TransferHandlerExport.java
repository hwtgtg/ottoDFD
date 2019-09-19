package funktionenBaum.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;

import funktionenBaum.FW__FunktionsKlasse;

public class DFD_TransferHandlerExport extends TransferHandler {

	private static final long serialVersionUID = -3850066853964543215L;

	static DataFlavor dfdFlavor = DFD_Transferable.dfdFlavor;

	public DFD_TransferHandlerExport() {
	}

	public int getSourceActions(JComponent c) {
		return COPY;
	}

	public Transferable createTransferable(JComponent c) {
//		LOG.outln(
//				((DefaultMutableTreeNode) ((JTree) c).getLastSelectedPathComponent()).getUserObject()
//						// ).leseKlassenbezeichnung()
//						.getClass().getName());

		if (((DefaultMutableTreeNode) ((JTree) c).getLastSelectedPathComponent()).getUserObject().getClass().getName()
				.equals("java.lang.String")) {
			return null;
		} else {
			return new DFD_Transferable(
					(FW__FunktionsKlasse) ((DefaultMutableTreeNode) ((JTree) c).getLastSelectedPathComponent())
							.getUserObject());
		}

	}

	public void exportDone(JComponent c, Transferable t, int action) {
		if (action == MOVE) {
			;
		}
	}

}
