package modul;

public class DFD_FAusgang {
	public DFD__IF_Modul fremderAusgang = null;
	public int eingangsnummer = -1;

	public DFD_FAusgang(DFD__IF_Modul fremderAusgang, int eingangsnummer) {
		this.fremderAusgang = fremderAusgang;
		this.eingangsnummer = eingangsnummer;
	}

}
