package jpToolbox;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class BaumKlasse implements TreeModel {
	
	DefaultMutableTreeNode root = null ;
	public BaumKlasse() {
		
	}
	

	@Override
	public Object getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getChild(Object parent, int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getChildCount(Object parent) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isLeaf(Object node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}
	
//	public static void  main( String[] args){
//		// new BaumImScrollfenster( new BaumKlasse(), 200 , 300);
//	}

	
}

