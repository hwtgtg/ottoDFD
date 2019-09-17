package funktionenBaum.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import funktionenBaum.FW__FunktionsKlasse;

public class DFD_Transferable implements Transferable {

	FW__FunktionsKlasse klasse = null;

	public static DataFlavor dfdFlavor = new DataFlavor(FW__FunktionsKlasse.class, "DFD_FUNKTION");

	public DFD_Transferable(FW__FunktionsKlasse klasse) {
		this.klasse = klasse;
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return klasse;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { dfdFlavor };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return dfdFlavor.equals(flavor);
	}
}
