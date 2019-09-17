package dfd_gui.zoom;

public class PointFloat {
	public float x = 0;
	public float y = 0;
	
	public PointFloat( float x , float y){
		this.x=x;
		this.y=y;
	}
	
	public String toString(){
		return ""+x+","+y;
	}
}
