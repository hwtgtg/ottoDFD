package drucken;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import dfd_gui.DFD_Start;
import global.StartUmgebung;
import drucken.DFD_Drucken;;

public class dfdDrucken {

	Boolean Vollbild = true;
	PrinterJob printJob = null;

	BufferedImage bild = null;

	public static dfdDrucken getDrucken(BufferedImage bild) {
		return new dfdDrucken(bild);
	}

	private dfdDrucken(BufferedImage bild) {
		this.bild = bild;
	}

	public void drucken() {
		printJob = PrinterJob.getPrinterJob();

		String dateiname = DFD_Start.getDateiname();

		printJob.setJobName("DFD: " + dateiname);
		DFD_Drucken dr = new DFD_Drucken(bild);
		printJob.setPrintable(dr);

		if (printJob.printDialog()) {

			if (StartUmgebung.pageFormat == null) {
				StartUmgebung.pageFormat = printJob.getPageFormat(null);
			}
//			PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
			StartUmgebung.pageFormat = printJob.pageDialog(StartUmgebung.pageFormat);
			dr.setPageFormat(StartUmgebung.pageFormat);
			try {
				printJob.print();
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
