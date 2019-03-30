/**
 * Team members:
 * @author John Doe
 * @author Jane Doe
 * 
 * Endpoint class for Node.
 */
public class Endpoint {
	public Node n;
	public Endpoint(Node n) {
		this.n = n;
	}
	/**
	 * returns the endpoint value.  For example if the
	 * End point object represents the left end point of the 
	 * interval [1,3], this would return 1.
	 * @return
	 */
	public int getValue() {
		//TODO: Modify it accordingly.
		return n.getKey();
	}
}
