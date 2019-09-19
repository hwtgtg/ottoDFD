package drucken;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import modul.DFD__FModulverwalter;

public class DFD_Drucken implements Printable {

	BufferedImage bild = null;
	Image bildM = null;

	PageFormat pageFormat = null;

	public static double posX = 1;
	public static double posY = 1;
	public static double paperWidth = 100;
	public static double paperHeight = 100;

	public static float druckbildFaktor = 1.0F;
	public static boolean zumDrucker = false;

	public static float drFF(float wert) {
		return wert * druckbildFaktor;
	}

	public static int drF(float wert) {
		int erg = (int) (Math.round(wert * druckbildFaktor));
		return (erg == 0) ? 1 : erg;
	}

	public static int drX(float wert) {
		int erg = (int) (posX + Math.round(wert * druckbildFaktor))+1;
		return (erg == 0) ? 1 : erg;
	}

	public static int drY(float wert) {
		int erg = (int) (posY + Math.round(wert * druckbildFaktor))+1;
		return (erg == 0) ? 1 : erg;
	}

	public DFD_Drucken(BufferedImage bild) {
		this.bild = bild;
	}

	public void setPageFormat(PageFormat pageFormat) {
		this.pageFormat = pageFormat;
		DFD_Drucken.posX = pageFormat.getImageableX();
		DFD_Drucken.posY = pageFormat.getImageableY();
		DFD_Drucken.paperWidth = pageFormat.getImageableWidth();
		DFD_Drucken.paperHeight = pageFormat.getImageableHeight();
		DFD_Drucken.zumDrucker = true;

	}

	@Override
	public int print(Graphics g, PageFormat defaultPageFormat, int pageIndex) throws PrinterException {
		Rectangle dimDFD = DFD__FModulverwalter.getModulverwalter().bestimmeDimensionen();
		if (dimDFD == null) {
			return Printable.NO_SUCH_PAGE;
		}

		if (pageIndex > 0) {
			return Printable.NO_SUCH_PAGE;
		}
		Graphics2D g2 = (Graphics2D) g;

		if (zumDrucker) {
			int bBreite = dimDFD.width;
			int bHoehe = dimDFD.height;

			druckbildFaktor = (float) (paperWidth / (bBreite * 1.0));

			float faktor2 = (float) (paperHeight / (bHoehe * 1.0));

			if (faktor2 < druckbildFaktor) {
				druckbildFaktor = faktor2;
			}
		}
		// Moduldarstellungen zeichnen
		DFD__FModulverwalter.getModulverwalter().zeichne(g2, dimDFD);
		// Verbindungen vor den Moduldarstellungen zeichnen 
		DFD__FModulverwalter.getModulverwalter().zeichneVerbindungen(g2, dimDFD);

		// bildM = bild.getScaledInstance((int) Math.round(bild.getWidth() *
		// druckbildFaktor),
		// (int) Math.round(bild.getHeight() * druckbildFaktor),
		// Image.SCALE_SMOOTH);

		// g2.drawImage(bildM, (int) posX + 1, (int) posY + 1, null);

		return Printable.PAGE_EXISTS;
	}

}
