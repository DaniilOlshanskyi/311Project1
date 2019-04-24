import java.util.LinkedList;
import java.util.List;

/**
 * The ComputerNode class represents the nodes of the graph G, which are pairs (Ci, t).
 *
 * @author Daniil Olshanskyi
 * @author Kenneth Lange
 * @author Max Medberry
 */
public class ComputerNode {

	public int id;
	public int timestamp;
	//List of nodes to which this one has and outgoing edge
	public List<ComputerNode> list;
	public int color;
	public ComputerNode pred;
	
	
	public ComputerNode(int id, int timestamp){
		this.list = new LinkedList<ComputerNode>();
		this.id=id;
		this.timestamp=timestamp;
		this.color=0;
		this.pred=null;
	}
	
    /**
     * Returns the ID of the associated computer.
     *
     * @return Associated Computer's ID
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the timestamp associated with this node.
     *
     * @return Timestamp for the node
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a list of ComputerNode objects to which there is outgoing edge from this ComputerNode object.
     *
     * @return a list of ComputerNode objects that have an edge from this to the nodes in the list.
     */
    public List<ComputerNode> getOutNeighbors() {
        return list;
    }

    public void addNeighbor(ComputerNode nd){
    	list.add(nd);
    }
    
    public String toString(){
    	return "("+id+","+timestamp+")";
    }
    
    public void setColor(int c){
    	color=c;
    }
    
    public int getColor(){
    	return color;
    }
    
    public void setPred(ComputerNode c){
    	pred=c;
    }
    
    public ComputerNode getPred(){
    	return pred;
    }
}
