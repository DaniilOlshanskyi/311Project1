import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

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
	private List<Triplet> triplets;
	// An array to store sorted triplets
	private Triplet[] triplets_sorted;
	// Map to associate computers with lists of once they communicate with
	private HashMap<Integer, List<ComputerNode>> map;

	/**
	 * Constructor with no parameters
	 */
	public CommunicationsMonitor() {
		this.triplets = new LinkedList<Triplet>();
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
		triplets.add(new Triplet(c1, c2, timestamp));
	}

	/**
	 * Constructs the data structure as specified in the Section 2. This method
	 * should run in O(n + m log m) time.
	 */
	public void createGraph() {
		// Instantiate an array of the size we already know
		triplets_sorted = new Triplet[triplets.size()];
		//Copy triplets from list to array for more efficient use.
		triplets.toArray(triplets_sorted);
		// Merge-sort the triplets by the timestamp
		triplets_sorted = triSort(triplets_sorted);
		// Instantiate the map now
		map = new HashMap<Integer, List<ComputerNode>>();
		// Scan the triplets:
		for (int i = 0; i < triplets_sorted.length; i++) {
			// Be ready to store lists associated with first and second
			// computers
			List<ComputerNode> first_list;
			List<ComputerNode> second_list;
			// If there is no mapping for the first computer - create it
			if (!map.containsKey(triplets_sorted[i].first)) {
				map.put(triplets_sorted[i].first, new ArrayList<ComputerNode>());
			}
			// Obtain the list from the map
			first_list = map.get(triplets_sorted[i].first);

			// If there is no mapping for the second computer - create it
			if (!map.containsKey(triplets_sorted[i].second)) {
				map.put(triplets_sorted[i].second, new ArrayList<ComputerNode>());
			}
			// Obtain the list from the map
			second_list = map.get(triplets_sorted[i].second);
			
			//Search for the first node 
			ComputerNode first = null;
			//For each node in the associated list,
			for (int j = 0; j < first_list.size(); j++) {
				//Get the node from the list
				ComputerNode temp = first_list.get(j);
				//If node in list has the same id and timestamp - no need to create a new one, take it and break the loop
				if (temp.getID()==triplets_sorted[i].first && temp.getTimestamp()==triplets_sorted[i].timestamp){
					first = temp;
					break;
				}
			}
			//If such node does not exist in the list - create it and add to the list
			if (first==null){
				first = new ComputerNode(triplets_sorted[i].first,triplets_sorted[i].timestamp);
				first_list.add(first);
				//If this was not the first in the list - add reference to it for other nodes in the list.
				//!!! INCLUDING ITSELF !!!
				if (first_list.size()>1){
					for (int j = 0; j<first_list.size()-1; j++){
						first_list.get(j).list.add(first);
					}
				}
			}
			
			
			//Search for the second node 
			ComputerNode second = null;
			//For each node in the associated list,
			for (int j = 0; j < second_list.size(); j++) {
				//Get the node from the list
				ComputerNode temp = second_list.get(j);
				//If node in list has the same id and timestamp - no need to create a new one, take it and break the loop
				if (temp.getID()==triplets_sorted[i].second && temp.getTimestamp()==triplets_sorted[i].timestamp){
					second = temp;
					break;
				}
			}
			//If such node does not exist in the list - create it and add to the list
			if (second==null){
				second = new ComputerNode(triplets_sorted[i].second,triplets_sorted[i].timestamp);
				second_list.add(first);
				//If this was not the first in the list - add reference to it for other nodes in the list.
				//!!! INCLUDING ITSELF !!!
				if (second_list.size()>1){
					for (int j = 0; j<second_list.size()-1; j++){
						second_list.get(j).list.add(second);
					}
				}
			}
			
			
			/*!!!
			 * At this point first_list holds list of nodes associated with first id, first holds the node
			 * associated with this id and this timestamp. second_list and second same for the second id respectively.
			 */
			
			//Add directed edges respectively.
			first.list.add(second);
			second.list.add(first);
			
		}
	}

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
		return null;
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
		return null;
	}

	// A short helper-container to hold triplets before they are processed
	private class Triplet {

		public int first, second, timestamp;

		public Triplet(int first, int second, int timestamp) {
			this.first = first;
			this.second = second;
			this.timestamp = timestamp;
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
			for (int m = j; k < right.length; k++) {
				arr[k] = right[m];
				k++;
			}
		} else { // Else, add leftovers from left
			for (int m = i; k < left.length; k++) {
				arr[k] = left[m];
				k++;
			}
		}
		return arr;
	}

}
