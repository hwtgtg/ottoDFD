package jpToolbox;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ScreenCapture {

	public ScreenCapture() {
		// TODO Auto-generated constructor stub
	}


	public static BufferedImage holeBackground(JPanel panel) {
		BufferedImage background = null ;
		Point pos = panel.getLocationOnScreen();
		int breite = panel.getWidth();
		int hoehe = panel.getHeight();
		
//		panel.setVisible(false);

		try {
			Robot rbt = new Robot();
			background = rbt.createScreenCapture(new Rectangle(pos.x, pos.y, breite, hoehe));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		panel.setVisible(true);
		
		return background ;
		
	}
	
}
