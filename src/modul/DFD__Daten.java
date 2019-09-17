/**
 * 
 */
package modul;

import jpToolbox.Daten_Variant;

/**
 * @author otto
 *
 */
public class DFD__Daten extends Daten_Variant {
	
	// Spezialrückmeldungen, z.B. bei Wenn über berücksichtigten Eingang
	int [] spezial ;
	
	public int[] getSpezial() {
		return spezial;
	}

	public void setSpezial( int []spezial) {
		this.spezial =spezial;
	}

	
	DFD__IG_Modul.Eingangsstatus eStatus =  DFD__IG_Modul.Eingangsstatus.Verbunden;
	public DFD__IG_Modul.Eingangsstatus getEingangsStatus(){
		return eStatus;
	}
	
	public void setEingangsStatus(DFD__IG_Modul.Eingangsstatus eStatus){
		this.eStatus=eStatus;
	}
	
	boolean bearbeitet = false ;
	public boolean isBearbeitet() {
		return bearbeitet;
	}
	
	public void setBearbeitet(boolean bearbeitet) {
		this.bearbeitet = bearbeitet;
	}
	
	boolean gueltig = false ;
	public boolean isGueltig() {
		return gueltig;
	}

	public void setGueltig(boolean gueltig) {
		this.gueltig = gueltig;
	}

	String eingangBezeichnung = null ;
	public String getEingangBezeichnung() {
		return eingangBezeichnung ;
	}

	public void setEingangBezeichnung(String eingangBezeichnung) {
		this.eingangBezeichnung = eingangBezeichnung;
	}

	String ausgangbezeichnung = null ;
	public String getAusgangbezeichnung() {
		return ausgangbezeichnung;
	}

	public void setAusgangbezeichnung(String ausgangbezeichnung) {
		this.ausgangbezeichnung = ausgangbezeichnung;
	}

	String tabellenkalkulatorString = null ;
	public String getTabellenkalkulatorString() {
		return tabellenkalkulatorString;
	}

	public void setTabellenkalkulatorString(String tabellenkalkulatorString) {
		this.tabellenkalkulatorString = tabellenkalkulatorString;
	}


	String toolstring = null ;
	public String getToolstring() {
		return toolstring;
	}

	public void setToolstring(String toolstring) {
		this.toolstring = toolstring;
	}


	
	/**
	 * 
	 */
	public DFD__Daten() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public DFD__Daten(String s) {
		super(s);
		spezial = null ;
	}

	/**
	 * Copy-Constructor
	 * 
	 * @param daten
	 */
	public DFD__Daten(DFD__Daten daten) {
		super(daten);
		if (daten.spezial != null ){
			spezial = new int[ daten.spezial.length];
			for (int i = 0 ; i < daten.spezial.length; i++ ){
				spezial[i]=daten.spezial[i];
			}
		} else {
			spezial = null;
		}
		this.gueltig=daten.gueltig;
		this.eingangBezeichnung=daten.eingangBezeichnung;
		this.ausgangbezeichnung=daten.ausgangbezeichnung;
		this.tabellenkalkulatorString=daten.tabellenkalkulatorString;
		this.toolstring=daten.toolstring;
		if(daten.istError()) this.setzeError();
	}


	public static DFD__Daten getDatenUngueltig(){
		DFD__Daten daten = new DFD__Daten();
		daten.setGueltig(false);
		daten.setAusgangbezeichnung("");
		daten.setzeWert("");
		daten.setToolstring("");
		
		return daten;
	}

	public void fuerTabellenkalkulatorAufbereiten() {
		if(istDezimalzahl()) {
			setTabellenkalkulatorString(getDatenString());
		} else if(istBoolean()) {
			if(getBoolean()) {
				setTabellenkalkulatorString("WAHR");
			} else {
				setTabellenkalkulatorString("FALSE");
			}
		} else {
			if(!istDezimalzahl()) {
				// Nicht boolean und keine Dezimalzahl > nur String
				String tmp = getDatenString();
				if(!tmp.startsWith("\"")) {
					tmp = "\"" + tmp ;
				}
				if(!tmp.endsWith("\"")) {
					tmp = tmp +"\"" ;
				}
				setTabellenkalkulatorString(tmp);
			}
		}
	}
	
}
