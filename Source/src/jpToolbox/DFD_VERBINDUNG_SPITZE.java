package jpToolbox;


public class DFD_VERBINDUNG_SPITZE {

	public int xl;
	public int yl;
	public int xr;
	public int yr;
	public int x;
	public int y;

	@SuppressWarnings("unused")
	private DFD_VERBINDUNG_SPITZE() {

	}

	public DFD_VERBINDUNG_SPITZE(int x, int y, int vb_SPITZE) {
		this.x=x;
		this.y=y;
		xl=x-vb_SPITZE;
		yl=y-vb_SPITZE;
		xr=x+vb_SPITZE;
		yr=y-vb_SPITZE;
	}

}
