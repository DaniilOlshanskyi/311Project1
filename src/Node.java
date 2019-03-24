/**
 * Team members:
 * @author John Doe
 * @author Jane Doe
 * 
 * Node class for RBTree.
 */
public class Node {
	

	private int key;
	private int p;
	private int val;
	private int maxval;
	private Endpoint emax;
	
	public Node parent;
	public Node left;
	public Node right;
	public int color;
	
	
	
	
	public Node(int key, int p) {
		this.key = key;
		this.p = p;
		this.color = 0;
		this.parent = null;
		this.left = null;
		this.right = null;
	}

	/**
	 * Returns the parent of this node.
	 * @return
	 */
	public Node getParent() {
		return parent;
	}
	
	/**
	 * Returns the left child.
	 * @return
	 */
	public Node getLeft() {
		return left;
	}
	
	/**
	 * Returns the right child.
	 * @return
	 */
	public Node getRight() {
		return right;
	}
	
	/**
	 * Returns the endpoint value, which is an integer.
	 * @return
	 */
	public int getKey() {
		return key;
	}
	
	/**
	 * Returns the value of the function p based on this endpoint.
	 * @return
	 */
	public int getP() {
		return p;
	}
	
	/**
	 * Returns the val of the node as described in this assignment.
	 * @return
	 */
	public int getVal() {
		return val;
	}
	
	/**
	 * Returns themaxvalof the node as described in this assignment.
	 * @return
	 */
	public int getMaxVal() {
		return maxval;
	}
	
	/**
	 * Returns theEndpointobject that this node represents.
	 * @return
	 */
	public Endpoint getEndpoint() {
		//TODO: Modify it accordingly.
		return null;
	}
	
	/**
	 * Returns anEndpointobject that represents emax. 
	 * Calling this method on the root node will give the End point object whose getValue() 
	 * provides a point of maximum overlap.
	 * @return
	 */
	public Endpoint getEmax() {
		return emax;
	}
	
	/**
	 * Returns 0 if red. Returns 1 if black.
	 * @return
	 */
	public int getColor() {
		return color;
	}
	
	//Add more functions as  you see fit.
}
