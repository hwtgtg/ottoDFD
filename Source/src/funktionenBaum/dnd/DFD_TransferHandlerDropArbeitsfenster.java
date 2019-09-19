package funktionenBaum.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import funktionenBaum.FW__FunktionsKlasse;

public class DFD_TransferHandlerDropArbeitsfenster extends TransferHandler {

	private static final long serialVersionUID = 3914385559437550068L;

	static DataFlavor dfdFlavor = DFD_Transferable.dfdFlavor;

	public DFD_TransferHandlerDropArbeitsfenster() {
		
	}

	/**
	 * Overridden to import a Color if it is available.
	 * getChangesForegroundColor is used to determine whether the foreground or
	 * the background color is changed.
	 */
	public boolean importData(JComponent componente, Transferable t) {
	
		if (hasDFDFlavor(t.getTransferDataFlavors())) {
			try {
				FW__FunktionsKlasse dfdmodul = (FW__FunktionsKlasse) t.getTransferData(dfdFlavor);
//				LOG.outln(dfdmodul.leseKlassenbezeichnung());

				((DFD_I_DND) componente).bearbeiteFunktion(dfdmodul);
				return true;
			} catch (UnsupportedFlavorException e) {
			} catch (IOException ioe) {
			}
		}
		return false;
	}
	
	
	
	
	
	
	

	/**
	 * Does the flavor list have a Color flavor?
	 */
	protected boolean hasDFDFlavor(DataFlavor[] flavors) {
		if (dfdFlavor == null) {
			return false;
		}

		for (int i = 0; i < flavors.length; i++) {
			if (dfdFlavor.equals(flavors[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Overridden to include a check for a color flavor.
	 */
	public boolean canImport(JComponent c, DataFlavor[] flavors) {
		return hasDFDFlavor(flavors);
	}

}


