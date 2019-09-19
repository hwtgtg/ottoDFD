package modul;

import java.awt.Point;
import java.util.Vector;

public class DFD__GAusgangVerteiler extends DFD__GAusgang {

	Vector<DFD__GEingang> fremderEingang;

	public DFD__GAusgangVerteiler(DFD__IG_Modul dfd_modul) {
		super(dfd_modul);
		fremderEingang = new Vector<DFD__GEingang>();
	}

	void recall(DFD__IG_Modul modul) {
		this.eigenesGModul = modul;
	}

	public void verschiebePfeil() {
		for (DFD__GEingang eingang : fremderEingang) {
			eingang.verschiebePfeil(DFD__GEingang.keinVerschiebenDurchSensor);
		}

	}

	public Point getPointAusgangPixel(int ausgangsnr) {
		return eigenesGModul.getPointAusgangPixel(ausgangsnr);
	}

	public void verbindeMitNeuemEingang(DFD__GEingang neuerFremderEingang) {
		fremderEingang.addElement(neuerFremderEingang);
	}

	public void loeseEingang(DFD__GEingang dfd__GEingang) {
		fremderEingang.remove(dfd__GEingang);
	}

	public synchronized void modulLoeschen() {
		
		// Der eigentliche Vektor wird verändert !
		Vector<DFD__GEingang> fremderEingangCopy;
		fremderEingangCopy = new Vector<DFD__GEingang>();
		for (DFD__GEingang eingang : fremderEingang){
			fremderEingangCopy.add(eingang);
		}
	
		for (DFD__GEingang eingang : fremderEingangCopy) {
			eingang.loeseVonAusgang();

//			eigenesGModul.loeseFuGModulAusgangvomFEingang(eingang.getGmodul().getFmodul(), eingang.eingangsnummer);
		}
	}

}
