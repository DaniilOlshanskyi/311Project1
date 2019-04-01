/**
 * Team members:
 * @author Max Medberry
 * @author Kenneth Lange
 * @author Daniil Olshanskyi
 * 
 * RBTree class, maintains operations on RBTree.
 */
public class RBTree {
	//Color: 1 is black, 0 is red
	private Node nil;
	private Node root;
	public int size=0;
	
	/**
	 * RB Tree constructor. It initializes nil node as well.
	 */
	public RBTree() {
		//TODO: FIGURE OUT IF 0 AS NIL KEY IS OK and 0 AS NIL P OK
		nil = new Node(0,0);
		nil.color=1;
		nil.height=-1;
		root=nil;
	}
	
	/**
	 * Returns the root of the tree.
	 * @return
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * Returns reference for the nil node, for the rbTree.
	 * @return
	 */
	public Node getNILNode() {
		return nil;
	}
	
	/**
	 * Returns the number of internal nodes in the tree.
	 * @return
	 */
	public int getSize() {
		//TODO: Modify it accordingly.
		return size;
	}
	
	
	/**
	 * Returns the height of the tree.
	 * @return
	 */
	public int getHeight() {
		//TODO: Modify it accordingly.
		return 0;
	}
	
	public void Insert(Node z){
		//TODO: update val, maxval, emax accordingly to project specifications
		Node y = nil;
		Node x = this.root;
		while (!x.equals(nil)){
			y=x;
			if (z.getKey()<x.getKey()){
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;
		if (y.equals(nil)){
			this.root = z;
		} else if (z.getKey()<y.getKey()){
			y.left = z;
		} else {
			y.right = z;
		}
		z.left=nil;
		z.right=nil;
		z.color=0;
		InsertFixup(z);
		Climb(z);
		ClimbHeight(z);
	}
	
	private void InsertFixup(Node z){
		while (z.parent.color==0){
			if (z.parent.equals(z.parent.parent.left)){
				Node y = z.parent.parent.right;
				if (y.color==0){
					z.parent.color = 1;
					y.color = 1;
					z.parent.parent.color = 0;
					z = z.parent.parent;
				} else {
					if (z.equals(z.parent.right)){
						z = z.parent;
						LeftRotate(z);
					}
					z.parent.color = 1;
					z.parent.parent.color = 0;
					RightRotate(z.parent.parent);
				}
			} else {
				Node y = z.parent.parent.left;
				if (y.color==0){
					z.parent.color = 1;
					y.color = 1;
					z.parent.parent.color = 0;
					z = z.parent.parent;
				} else {
					if (z.equals(z.parent.left)){
						z = z.parent;
						RightRotate(z);
					}
					z.parent.color = 1;
					z.parent.parent.color = 0;
					LeftRotate(z.parent.parent);
				}
			}
		}
		this.root.color = 1;
	}
	
	private void LeftRotate(Node z){
		z.height--;
		Node y = z.right;
		z.right = y.left;
		if (!y.left.equals(nil)){
			y.left.parent = z;
		}
		y.parent = z.parent;
		if (z.parent.equals(nil)){
			root = y;
		} else if (z.equals(z.parent.left)){
			z.parent.left = y;
		} else {
			z.parent.right = y;
		}
		y.left = z;
		z.parent = y;
		//recalculate for Z, Z.right, and Z.left
		RotationCalculation(z);
	}
	
	private void RightRotate(Node z){
		z.height--;
		Node y = z.left;
		z.left = y.right;
		if (!y.right.equals(nil)){
			y.right.parent = z;
		}
		y.parent = z.parent;
		if (z.parent.equals(nil)){
			root = y;
		} else if (z.equals(z.parent.right)){
			z.parent.right = y;
		} else {
			z.parent.left = y;
		}
		y.right = z;
		z.parent = y;
		//recalculate for Z, Z.right, and Z.left
		RotationCalculation(z);
		//recalculate emax
		
	}
	
	private void RotationCalculation(Node z) {
		if (z.right != nil) {
			//do calculation for z.right
			CalculateValue(z.right);
			CalculateMaxValue(z.right);
		} if (z.left != nil) {
			//do calculation for z.left
			CalculateValue(z.left);
			CalculateMaxValue(z.left);
		}
		//do calculation for z
		CalculateValue(z);
		CalculateMaxValue(z);
	}
	
	/**
	 * Calculates and returns the value of the node
	 * @param z
	 */
	private void CalculateValue(Node z) {
		z.val =  z.getP() + z.left.getVal() + z.right.getVal();
	}
	
	/**
	 * Calculates and returns the max value of the node
	 * @param z
	 */
	private void CalculateMaxValue(Node z) {
		int max =  Math.max(z.left.getMaxVal(), Math.max(z.left.getVal() + z.getP(), z.left.getVal() + z.getP() + z.right.getMaxVal()));
		if (max == z.left.getMaxVal()) {
			z.setEmax(z.getLeft().getEmax());
		}
		else if (max == z.left.getVal() + z.getP()) {
			z.setEmax(z.getKey());
		}
		else if (max == z.left.getVal() + z.getP() + z.right.getMaxVal()) {
			z.setEmax(z.getRight().getEmax());
		}
		z.maxval = max;
	}
	 
	private void Transplant(Node u, Node v) {
		if (u.parent.equals(this.nil)) {
			this.root = v;
		} else if (u.equals(u.parent.left)) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}
	 
	public void Delete(Node z) {
		Node y = z;
		Node x;
		int yOrigColor = y.color;
		if (z.left.equals(nil)) {
			 x = z.right;
			Transplant(z,z.right);
		} else if (z.right.equals(nil)) {
			 x = z.left;
			 Transplant(z,z.left);
		} else {
			y = Minimum(z.right);
			yOrigColor = y.color;
			x = y.right;
			if (y.parent.equals(z)) {
				x.parent = y;
			} else {
				Transplant(y,y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			Transplant(z,y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (yOrigColor==1) {//black
			DeleteFixup(x);
		}
		Climb(x);
	}
	
	private void DeleteFixup(Node x) {
		while (!x.equals(root) && x.color==1) {
			if (x.equals(x.parent.left)) {
				Node w = x.parent.left;
				if (w.color==0) {
					w.color = 1;
					x.parent.color = 0;
					LeftRotate(x.parent);
					w = x.parent.right;
				}
				if (w.left.color==1 && w.right.color==1) {
					w.color = 0;
					x = x.parent;
				} else {
					if (w.right.color==1) {
						w.left.color = 1;
						w.color = 0;
						RightRotate(w);
						w = x.parent.right;
					}
					w.color = x.parent.color;
					x.parent.color = 1;
					w.right.color = 1;
					LeftRotate(x.parent);
					x = root;
				}
			} else {
				Node w = x.parent.right;
				if (w.color==0) {
					w.color = 1;
					x.parent.color = 0;
					RightRotate(x.parent);
					w = x.parent.left;
				}
				if (w.right.color==1 && w.left.color==1) {
					w.color = 0;
					x = x.parent;
				} else {
					if (w.left.color==1) {
						w.right.color = 1;
						w.color = 0;
						LeftRotate(w);
						w = x.parent.left;
					}
					w.color = x.parent.color;
					x.parent.color = 1;
					w.left.color = 1;
					RightRotate(x.parent);
					x = root;
				}
				
			}
		}
		x.color = 1;
	}
	
	private Node Minimum(Node x) {
		while (!x.left.equals(nil)) {
			x = x.left;
		}
		return x;
	}
	private void Climb(Node z) {
		while (!z.equals(nil)) {
			CalculateMaxValue(z);
			CalculateValue(z);
			z = z.parent;
		}
	}
	
	private void ClimbHeight(Node z){
			while (!z.equals(nil)){
				z.height=Math.max(z.left.height, z.right.height)+1;
				z=z.parent;
//				if (!z.left.equals(nil)){
//					z.height=z.left.height+1;
//				}
//				if (!z.right.equals(nil)){
//					z.height=z.right.height+1;
//				}
			}

	}
	//Add more functions as  you see fit.
}
