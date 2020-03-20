
/*
@author
Adam Brandt 
*/
package cs228hw4.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CS228Dijkstra<V> implements Dijkstra<V> {
	private DiGraph<V> myGraph = null;
	private Map<V, Node> nodes = new HashMap<V, Node>();
	
	/*
	 * Takes in a graph and finds the shortest path to each element
	 * @param 
	 * Graph
	 */
	public CS228Dijkstra(DiGraph<V> graph) {
		myGraph = graph;
		Iterator<V> iter = myGraph.iterator();

		while (iter.hasNext()) {
			for (int i = 0; i < myGraph.numVertices(); i++) {
				Node n = new Node();
				nodes.put(iter.next(), n);
			}
		}
	}

	public class Node {
		private int cost = 0;
		private boolean visited = false;
		private ArrayList<V> Path = new ArrayList<V>();

		public Node() {

		}

		public void Init() {
			cost = Integer.MAX_VALUE;
			Path.clear();
			visited = false;
		}

		public int getCost() {
			return cost;
		}

		public void setCost(int c) {
			cost = c;
		}

		public void incCost(int c) {
			cost += c;
		}

		public boolean getVisited() {
			return visited;
		}

		public void setVisited() {
			visited = true;
		}

		public void setUnvisited() {
			visited = false;
		}

		public ArrayList<V> getPath() {
			return Path;
		}

		public void addPath(ArrayList<V> p) {
			Path.addAll(p);
		}

		public void setPath(ArrayList<V> p) {
			Path.clear();
			Path.addAll(p);
		}
	}
	/*
	 * Finds the shortest distance to a neighbor 
	 */
	public V minCost() {
		V index = null;
		int minCost = Integer.MAX_VALUE;
		for (Map.Entry<V, Node> entry : nodes.entrySet()) {
			if (entry.getValue().getVisited() == false && entry.getValue().getCost() < minCost) {
				minCost = entry.getValue().getCost();
				index = entry.getKey();
			}
		}
		return index;
	}

	@Override
	public void run(Object start) {
		for (Map.Entry<V, Node> entry : nodes.entrySet()) {
			entry.getValue().Init();
		}
		nodes.get(start).cost = 0;
		nodes.get(start).Path.add((V) start);
		for (int i = 0; i < myGraph.numVertices(); i++) {
			V curr = minCost();
			Node cNode = nodes.get(curr);
			cNode.visited = true;
			Set<? extends V> edges = myGraph.getNeighbors(curr);
			for (V entry : edges) {
				Node n = nodes.get(entry);
				if (!n.getVisited()) {
					int edgeCost = myGraph.getEdgeCost(curr, entry);
					if (edgeCost + cNode.getCost() < n.getCost()) {
						n.setCost( edgeCost + cNode.getCost() );
						ArrayList<V> temp = new ArrayList<V>();
						for(V e :cNode.getPath()) {
							temp.add(e);
						}
						temp.add(entry);
						n.setPath(temp);
					}
				}
			}
		}
	}

	@Override
	public List getShortestPath(Object vertex) {
		return nodes.get(vertex).getPath();
	}

	@Override
	public int getShortestDistance(Object vertex) {
		return nodes.get(vertex).getCost();
	}

}
