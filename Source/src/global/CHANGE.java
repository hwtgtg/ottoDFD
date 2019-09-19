package global;

public class CHANGE {

	private static boolean changed = false ; 

	public CHANGE() {
		// TODO Auto-generated constructor stub
	}

	
	public synchronized static boolean isChanged(){
		return changed ;
	}
	
	public synchronized static void reset(){
		changed = false;
	}

	public synchronized static void setChanged(){
		changed = true;
	}
	
}
