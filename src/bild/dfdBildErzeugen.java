package bild;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import dfd_gui.DFD_Start;
import drucken.DFD_Drucken;
import global.DFD__GUIKONST;
import jtoolbox.D_Bestaetigung;
import modul.DFD__FModulverwalter;

public class dfdBildErzeugen {

	BufferedImage dfdBild = null;

	public static dfdBildErzeugen getBilderzeugen() {
		return new dfdBildErzeugen();
	}

	public dfdBildErzeugen() {

	}

	public boolean bildSchreiben(BufferedImage bild) {

		JFileChooser auswahlDatei = new JFileChooser(".\\");

		global.StartUmgebung.groesseAendernFileChooser(auswahlDatei);

		auswahlDatei.setDialogTitle("Bilddatei schreiben");
		String bilddateiname = DFD_Start.getDateinameMitPfad();
		if (bilddateiname.toLowerCase().endsWith(".dml")) {
			bilddateiname = bilddateiname.substring(0, bilddateiname.length() - 4) + ".png";
		}

		auswahlDatei.setSelectedFile(new File(bilddateiname));
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Bild - DFD (*.png)", "PNG");
		auswahlDatei.setFileFilter(ff);

		String dateinamePNG = "";
		int erg = auswahlDatei.showSaveDialog(null);

		if (erg == JFileChooser.APPROVE_OPTION) {
			String path = auswahlDatei.getCurrentDirectory().getAbsolutePath();
			dateinamePNG = auswahlDatei.getSelectedFile().getName();
			String newPath = "";
			StringTokenizer pathTokens = new StringTokenizer(path, "\\");
			while (pathTokens.hasMoreTokens()) {
				newPath = newPath + pathTokens.nextToken() + "/";
			}
			dateinamePNG = newPath + dateinamePNG;
			if (!dateinamePNG.toLowerCase().endsWith(".png")) {
				dateinamePNG = dateinamePNG + ".png";
			}
		} else {
			return false;
		}

		if (bild == null) {
			return false;
		}

		try {
			File outputfile = new File(dateinamePNG);
			if (outputfile.exists()) {
				D_Bestaetigung db = new D_Bestaetigung();
				db.setzeTitel("Datei existiert");
				db.setzeMeldungstext("Datei existiert! Ersetzen?");
				db.typ_typJaNein();
				db.zeigeMeldung();
				if (db.leseErgebnis() == 'N') {
					return false ;
				} 
			}
	
			ImageIO.write(bild, "png", outputfile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public BufferedImage bildErzeugen() {
		
		Rectangle dimDFD = DFD__FModulverwalter.getModulverwalter().bestimmeDimensionen();
		if (dimDFD == null) {
			return null;
		} else {
			DFD_Drucken.druckbildFaktor=1.0F;
			
			DFD_Drucken.posX = 0;
			DFD_Drucken.posY = 0;
			DFD_Drucken.paperWidth = DFD_Drucken.drFF(dimDFD.width);
			DFD_Drucken.paperHeight = DFD_Drucken.drFF(dimDFD.height);
			DFD_Drucken.zumDrucker = false;
			
			// +1 als Reserve für Runden beim Skalieren
			dfdBild = new BufferedImage(DFD_Drucken.drF(dimDFD.width)+1, DFD_Drucken.drF(dimDFD.height)+1, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g2 = dfdBild.createGraphics();
			
			Font font = g2.getFont();
			font = font.deriveFont(Font.BOLD);
			g2.setFont(font);
			font = g2.getFont();
			font = font.deriveFont(DFD__GUIKONST.BILD_FONT_GROESSE*1.0F);
			g2.setFont(font);

			g2.setColor(DFD__GUIKONST.paintHintergrund);
			g2.fillRect(0, 0, DFD_Drucken.drF(dimDFD.width)+1, DFD_Drucken.drF(dimDFD.height)+1);
			g2.setColor(DFD__GUIKONST.paintVordergrund);
			
			// Moduldarstellungen zeichnen
			DFD__FModulverwalter.getModulverwalter().zeichne(g2, dimDFD);
			// Verbindungen vor den Moduldarstellungen zeichnen 
			DFD__FModulverwalter.getModulverwalter().zeichneVerbindungen(g2, dimDFD);


			return dfdBild;
		}
	}

	public void bildSchreiben() {
		BufferedImage bild = bildErzeugen();
		if ( bild !=null ){
			bildSchreiben(bild);
		}
	}

}
