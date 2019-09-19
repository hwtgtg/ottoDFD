package funktionenBaum;

public class FunktionenAuswahl_Funktionenlesen {

	public FunktionenAuswahl_Funktionenlesen() {
		// TODO Auto-generated constructor stub
	}

	public static void funktionenLesen() {

		// Module aus dem Verzeichnis modul\fm_modul einlesen
		FW_Module_ImBaum.fwModulPfad = global.StartUmgebung.startPfad + "modul/fw_modul";
		FW_Module_ImBaum.leseFunktionen();
		FW_Module_ImBaum.erzeugeBaum();
	}

}
