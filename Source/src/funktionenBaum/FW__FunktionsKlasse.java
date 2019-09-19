package funktionenBaum;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

public class FW__FunktionsKlasse implements Serializable , MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5013976026941288152L;
	public String klassenbezeichnung = "";
	public String gruppe = "";
	public String bezeichnung = "";
	public int sortierung = 99;
	public boolean intern = false ;

	/**
	 * @param name
	 * @param gruppe
	 * @param bezeichnung
	 */
	public FW__FunktionsKlasse(String name, String bezeichnung, String gruppe,
			int sortierung) {
		this(false,name,bezeichnung,gruppe,sortierung);
	}

	/**
	 * @param name
	 * @param gruppe
	 * @param bezeichnung
	 */
	public FW__FunktionsKlasse(boolean intern , String name, String bezeichnung, String gruppe,
			int sortierung) {
		this.intern = intern ;
		this.klassenbezeichnung = name;
		this.gruppe = gruppe;
		this.bezeichnung = bezeichnung;
		this.sortierung = sortierung;
	}

	public String toString() {
		return bezeichnung;
	}
	
	public String leseKlassenbezeichnung(){
		return klassenbezeichnung ;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
