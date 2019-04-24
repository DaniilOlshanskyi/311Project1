import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * The CommunicationsMonitor class represents the graph G built to answer
 * infection queries.
 *
 * @author
 * @author
 * @author
 */
public class CommunicationsMonitor {

	// A list to store unsorted triplets
	public List<Triplet> triplets;
	// An array to store sorted triplets
	public Triplet[] triplets_sorted;
	// Map to associate computers with lists of once they communicate with
	public HashMap<Integer, List<ComputerNode>> map;
	// Graph has been created
	private boolean graphCreated;
	//Storage for all nodes;
	List<ComputerNode> allNodes;
	boolean contained = false;
	LinkedList<ComputerNode> returnList;
	

	/**
	 * Constructor with no parameters
	 */
	public CommunicationsMonitor() {
		this.triplets = new LinkedList<Triplet>();
		// Instantiate the map now
		map = new HashMap<Integer, List<ComputerNode>>();
		allNodes=new LinkedList<ComputerNode>();
	}

	/**
	 * Takes as input two integers c1, c2, and a timestamp. This triple
	 * represents the fact that the computers with IDs c1 and c2 have
	 * communicated at the given timestamp. This method should run in O(1) time.
	 * Any invocation of this method after createGraph() is called will be
	 * ignored.
	 *
	 * @param c1
	 *            First ComputerNode in the communication pair.
	 * @param c2
	 *            Second ComputerNode in the communication pair.
	 * @param timestamp
	 *            Time the communication took place.
	 */
	public void addCommunication(int c1, int c2, int timestamp) {
		// Add a newly-created triplet to our list to be sorted and processed
		// later.
		if (!graphCreated) {
			triplets.add(new Triplet(c1, c2, timestamp));
		}
	}

	/**
	 * Constructs the data structure as specified in the Section 2. This method
	 * should run in O(n + m log m) time.
	 */
	public void createGraph() {
		graphCreated = true;
		// Instantiate an array of the size we already know
		triplets_sorted = new Triplet[triplets.size()];
		// Copy triplets from list to array for more efficient use.
		triplets.toArray(triplets_sorted);
		// Merge-sort the triplets by the timestamp
		triplets_sorted = triSort(triplets_sorted);
		// Scan the triplets:
		for (int i = 0; i < triplets_sorted.length; i++) {
			// Be ready to store lists associated with first and second
			// computers
			List<ComputerNode> first_list;
			List<ComputerNode> second_list;

			ComputerNode first = null;
			ComputerNode second = null;
			// If there is no mapping for the first computer - create it
			if (!map.containsKey(triplets_sorted[i].first)) {
				// Create new array-list to store nodes
				map.put(triplets_sorted[i].first, new ArrayList<ComputerNode>());
				// create new node to represent the first one in this triplet
				first = new ComputerNode(triplets_sorted[i].first, triplets_sorted[i].timestamp);
				allNodes.add(first);
				// Obtain the list from the map
				first_list = map.get(triplets_sorted[i].first);
				// add first node to the map
				first_list.add(first);
			} else {// If there is mapping for this node - check if our first is
					// the same as last in list(since list is sorted)
				// Obtain the list from the map
				first_list = map.get(triplets_sorted[i].first);
				// If same timestamp - than they are equal, just get the last
				// one from the list
				if (first_list.get(first_list.size()-1).getTimestamp() == triplets_sorted[i].timestamp) {
					first = first_list.get(first_list.size()-1);
				} else {// If different timestamp(i.e. last in list has lower
						// timestamp than the new one) - create a new one and
						// append to the list
					first = new ComputerNode(triplets_sorted[i].first, triplets_sorted[i].timestamp);
					allNodes.add(first);
					first_list.add(first);
				}
			}

			// If there is no mapping for the second computer - create it
			if (!map.containsKey(triplets_sorted[i].second)) {
				// Create new array-list to store nodes
				map.put(triplets_sorted[i].second, new ArrayList<ComputerNode>());
				// create new node to represent the second one in this triplet
				second = new ComputerNode(triplets_sorted[i].second, triplets_sorted[i].timestamp);
				allNodes.add(second);
				// Obtain the list from the map
				second_list = map.get(triplets_sorted[i].second);
				// add second node to the map
				second_list.add(second);
			} else {// If there is mapping for this node - check if our second
					// is
					// the same as last in list(since list is sorted)
				// Obtain the list from the map
				second_list = map.get(triplets_sorted[i].second);
				// If same timestamp - than they are equal, just get the last
				// one from the list
				if (second_list.get(second_list.size()-1).getTimestamp() == triplets_sorted[i].timestamp) {
					second = second_list.get(second_list.size()-1);
				} else {// If different timestamp(i.e. last in list has lower
						// timestamp than the new one) - create a new one and
						// append to the list
					second = new ComputerNode(triplets_sorted[i].second, triplets_sorted[i].timestamp);
					allNodes.add(second);
					second_list.add(second);
				}
			}

			/*
			 * !!! At this point first_list holds list of nodes associated with
			 * first id, first holds the node associated with this id and this
			 * timestamp. second_list and second same for the second id
			 * respectively.
			 */

			// Add directed edges respectively.
			first.addNeighbor(second);;
			second.addNeighbor(first);;

			// If there was another one in the list - add a reference to the new
			// one to the neighbors list of the previously last in the list
			// (last-1 now)
			if (first_list.size() > 1) {
				first_list.get((first_list.size() - 2)).addNeighbor(first);;
			}
			if (second_list.size() > 1) {
				second_list.get((second_list.size() - 2)).addNeighbor(second);;
			}
			
		}
	}

	/* DFS implementation, Stack overflow damger.
	public List<ComputerNode> queryInfection1(int c1, int c2, int x, int y) {
		//c1 = node infected at time x
		//c2 = node to be checked at time y
		int size = allNodes.size();
		Iterator<ComputerNode> iter = allNodes.iterator();
		
		for (int i = 0; i<size; i++){
			ComputerNode temp = iter.next();
			temp.setColor(0);
			temp.setPred(null);
		}
		returnList = new LinkedList<ComputerNode>();
		if (!map.containsKey(c1) || !map.containsKey(c2)) {
			return null;
		}
		ComputerNode start = null;
		for (int i=0; i<map.get(c1).size(); i++) {
			if (map.get(c1).get(i).getTimestamp()>=x){
				start = map.get(c1).get(i);
				break;
			}
		}
		if (start == null) {
			return null;
		}
		start.setColor(1);
		DFS(start, c2, y);
		if (returnList.isEmpty()) {
			return null;
		}
		return returnList;
	}
	// Works, but if I try using it twice the colors aren't reset so breaks... :(
	private void DFS(ComputerNode start, int end, int y){
		for (int i=0; i<start.getOutNeighbors().size(); i++) {
			ComputerNode check = start.getOutNeighbors().get(i);
			if(check.getColor()==0) {
				check.setColor(1);
				check.setPred(start);
				if (check.getID()==end && check.getTimestamp()<=y){
					while (check.getPred()!=null) {
						returnList.addFirst(check);
						check=check.getPred();
					}
					returnList.addFirst(check);
				} else {
					DFS(check, end, y);
				}
			}
		}
	}
	
	
	*/
	/**
	 * Determines whether computer c2 could be infected by time y if computer c1
	 * was infected at time x. If so, the method returns an ordered list of
	 * ComputerNode objects that represents the transmission sequence. This
	 * sequence is a path in graph G. The first ComputerNode object on the path
	 * will correspond to c1. Similarly, the last ComputerNode object on the
	 * path will correspond to c2. If c2 cannot be infected, return null.
	 * <p>
	 * Example 3. In Example 1, an infection path would be (C1, 4), (C2, 4),
	 * (C2, 8), (C4, 8), (C3, 8)
	 * <p>
	 * This method can assume that it will be called only after createGraph()
	 * and that x <= y. This method must run in O(m) time. This method can also
	 * be called multiple times with different inputs once the graph is
	 * constructed (i.e., once createGraph() has been invoked).
	 *
	 * @param c1
	 *            ComputerNode object to represent the Computer that is
	 *            hypothetically infected at time x.
	 * @param c2
	 *            ComputerNode object to represent the Computer to be tested for
	 *            possible infection if c1 was infected.
	 * @param x
	 *            Time c1 was hypothetically infected.
	 * @param y
	 *            Time c2 is being tested for being infected.
	 * @return List of the path in the graph (infection path) if one exists,
	 *         null otherwise.
	 */
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y) {
		int size = allNodes.size();
		Iterator<ComputerNode> iter = allNodes.iterator();

		if (!map.containsKey(c2)){
			return null;
		}

		if (!map.containsKey(c1)){
			return null;
		}
		for (int i = 0; i<size; i++){
			ComputerNode temp = iter.next();
			temp.setColor(0);
			temp.setPred(null);
			
		}
		
		ComputerNode start = null;
		ComputerNode end = null;
		List<ComputerNode> startList = map.get(c1);
		size = startList.size();
		for (int i = 0; i<size; i++){
			if (startList.get(i).getTimestamp()>=x){
				start = startList.get(i);
				break;
			}
		}
		if (start==null){
			return null;
		}
		return BFS(start, c2, y);
		
	}
	
	private List<ComputerNode> BFS(ComputerNode start, int end, int y){
		LinkedList<ComputerNode> q = new LinkedList<ComputerNode>();
		
		start.setColor(1);
		q.add(start);
		
		while (!q.isEmpty()){
			ComputerNode u = q.peek();
			Iterator<ComputerNode> iter = u.getOutNeighbors().iterator();
			int size = u.getOutNeighbors().size();
			for (int i=0; i<size; i++){
				ComputerNode v = iter.next();
				if (v.getColor()==0){
					v.setColor(1);
					v.setPred(u);
					if (v.getID()==end && v.getTimestamp()<=y){
						LinkedList<ComputerNode> returnList = new LinkedList<ComputerNode>();
						while (v.getPred()!=null){
							returnList.addFirst(v);
							v=v.getPred();
						}
						returnList.addFirst(v);
						return returnList;
					}
					q.addLast(v);
				}
			}
			q.remove();
			u.setColor(2);
		}
		
		return null;
	}
	
	
	/**
	 * Returns a HashMap that represents the mapping between an Integer and a
	 * list of ComputerNode objects. The Integer represents the ID of some
	 * computer Ci, while the list consists of pairs (Ci, t1),(Ci, t2),..., (Ci,
	 * tk), represented by ComputerNode objects, that specify that Ci has
	 * communicated with other computers at times t1, t2,...,tk. The list for
	 * each computer must be ordered by time; i.e., t1\<t2\<...\<tk.
	 *
	 * @return HashMap representing the mapping of an Integer and ComputerNode
	 *         objects.
	 */
	public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
		return map;
	}

	/**
	 * Returns the list of ComputerNode objects associated with computer c by
	 * performing a lookup in the mapping.
	 *
	 * @param c
	 *            ID of computer
	 * @return ComputerNode objects associated with c.
	 */
	public List<ComputerNode> getComputerMapping(int c) {
		if (map.containsKey(c)){
			return map.get(c);
		}
		return null;
	}

	// A short helper-container to hold triplets before they are processed
	public class Triplet {

		public int first, second, timestamp;

		public Triplet(int first, int second, int timestamp) {
			this.first = first;
			this.second = second;
			this.timestamp = timestamp;
		}
		
		public String toString(){
			return "("+first+","+second+","+timestamp+")";
		}
	}

	/**
	 * MergeSort to sort triplets
	 * 
	 * @param arr
	 *            - (sub)array to be sorted
	 * @return - a sorted (sub)array
	 */
	private Triplet[] triSort(Triplet[] arr) {
		if (arr.length == 1) {
			return arr;
		}
		// Middle point to divide an array in two
		int mid = (int) (Math.floor(arr.length / 2.0));
		// Divide arr into two arrays
		Triplet[] arr_left = new Triplet[mid];
		Triplet[] arr_right = new Triplet[arr.length - mid];
		for (int i = 0; i < mid; i++) {
			arr_left[i] = arr[i];
		}
		for (int i = mid; i < arr.length; i++) {
			arr_right[i - mid] = arr[i];
		}
		arr = triMerge(triSort(arr_left), triSort(arr_right));
		return arr;
	}

	/**
	 * Merge function of mergesort to merge arrays of triplets
	 * 
	 * @param left
	 *            - first array to merge
	 * @param right
	 *            - second array to merge
	 * @return - result of merging both arrays i.e. sorted array
	 */
	private Triplet[] triMerge(Triplet[] left, Triplet[] right) {
		Triplet[] arr = new Triplet[left.length + right.length];
		int i = 0;
		int j = 0;
		int k = 0;
		// Merge while one of the arrays is not depleted
		while (i < left.length && j < right.length) {
			if (left[i].timestamp <= right[j].timestamp) {
				arr[k] = left[i];
				k++;
				i++;
			} else {
				arr[k] = right[j];
				k++;
				j++;
			}
		}
		// If left depleted, add leftovers from right
		if (i >= left.length) {
			for (int m = j; m < right.length; m++) {
				arr[k] = right[m];
				k++;
			}
		} else { // Else, add leftovers from left
			for (int m = i; m < left.length; m++) {
				arr[k] = left[m];
				k++;
			}
		}
		return arr;
	}

}
