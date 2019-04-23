/**
 * Team members:
 * @author Max Medberry
 * @author Kenneth Lange
 * @author Daniil Olshanskyi
 * 
 * Node class for RBTree.
 */
public class Node {
	

	private int key;
	private int p;
	public int val;
	public int maxval;
	public Endpoint emax;
	
	public Node parent;
	public Node left;
	public Node right;
	//1 is black, 0 is red
	public int color;
	public int height;
	
	public Endpoint ep;
	
	
	public Node(int key, int p, Endpoint ep) {
		this.key = key;
		this.p = p;
		this.color = 0;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.height=0;
		this.ep=ep;
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
		return this.ep;
	}
	
	/**
	 * Returns anEndpointobject that represents emax. 
	 * Calling this method on the root node will give the End point object whose getValue() 
	 * provides a point of maximum overlap.
	 * @return
	 */
	public Endpoint getEmax() {
		return this.emax;
	}
	
	public void setEmax(Endpoint e) {
		this.emax = e; 
	}
	
	/**
	 * Returns 0 if red. Returns 1 if black.
	 * @return
	 */
	public int getColor() {
		return color;
	}
	
	public String toString(){
		return this.key+"";
	}
	//Add more functions as  you see fit.
}
