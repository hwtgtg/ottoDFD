package modul.gui_modul;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface DFD_I_GUI_Basismodul {


	void bildZeichnen(Graphics2D g2, Rectangle dimDFD);
	void setzeModulPosition(int posX, int posY);

	Rectangle leseRechteck();

}
