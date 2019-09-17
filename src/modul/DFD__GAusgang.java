package modul;

import java.awt.Point;

public class DFD__GAusgang {

	DFD__IG_Modul eigenesGModul = null;

	public DFD__IG_Modul getEigenesGModul() {
		return eigenesGModul;
	}

	public void setEigenesModul(DFD__IG_Modul eigenesModul) {
		this.eigenesGModul = eigenesModul;
	}

	DFD__GEingang fremderGEingang = null;
	int fremderEingangNummer = 0;

	public DFD__GAusgang(DFD__IG_Modul dfd_modul) {
		// TODO Auto-generated constructor stub
	}

	void recall(DFD__IG_Modul modul) {
		this.eigenesGModul = modul;
	}

	public void verschiebePfeil() {
		if (fremderGEingang != null) {
			fremderGEingang.verschiebePfeil(DFD__GEingang.keinVerschiebenDurchSensor);
		}
	}

	public Point getPointAusgangPixel(int ausgangsnr) {
		return eigenesGModul.getPointAusgangPixel(ausgangsnr);
	}

	public void verbindeMitNeuemEingang(DFD__GEingang neuerFremderEingang) {

		DFD__GEingang alterEingang = fremderGEingang;
		if ((alterEingang != null) && (alterEingang != neuerFremderEingang)) {
			alterEingang.loeseVonAusgang();
		}
		this.fremderGEingang = neuerFremderEingang;
		this.fremderEingangNummer = neuerFremderEingang.eingangsnummer;

	}

	public void loeseEingang(DFD__GEingang dfd__GEingang) {
		fremderGEingang = null;
	}

	public void modulLoeschen() {
		if (fremderGEingang != null) {
			fremderGEingang.loeseVonAusgang();
//			eigenesGModul.loeseFuGModulAusgangvomFEingang(fremderGEingang.getGmodul().getFmodul(),
//					fremderGEingang.eingangsnummer);
		}
	}

}
