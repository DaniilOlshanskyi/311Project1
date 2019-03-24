/**
 * Team members:
 * @author John Doe
 * @author Jane Doe
 * 
 * RBTree class, maintains operations on RBTree.
 */
public class RBTree {
	
	private Node nil;
	private Node root;
	
	/**
	 * RB Tree constructor. It initializes nil node as well.
	 */
	public RBTree() {
		//TODO: FIGURE OUT IF 0 AS NIL KEY IS OK
		this.nil = new Node(0,0);
		nil.color=1;
		this.root=nil;
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
		return 0;
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
		//TODO: update p, val, maxval, emax accordingly to project specifications
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
		Node y = z.right;
		z.right = y.left;
		if (y.left!=null){
			y.left.parent = z;
		}
		y.parent = z.parent;
		if (z.parent==null){
			root = y;
		} else if (z.equals(z.parent.left)){
			z.parent.left = y;
		} else {
			z.parent.right = y;
		}
		y.left = z;
		z.parent = y;
	}
	
	private void RightRotate(Node z){
		Node y = z.left;
		z.left = y.right;
		if (y.right!=null){
			y.right.parent = z;
		}
		y.parent = z.parent;
		if (z.parent==null){
			root = y;
		} else if (z.equals(z.parent.right)){
			z.parent.right = y;
		} else {
			z.parent.left = y;
		}
		y.right = z;
		z.parent = y;	
	}
	
	//Add more functions as  you see fit.
}
