/*
@author
Adam Brandt 
*/
package cs228hw4.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cs228hw4.graph.CS228DiGraph.Vertex;



public class CS228DiGraph<V> implements DiGraph<V> {
	
	/*
	 * Class the contains a map of all the costs of the vertexes
	 */
	public class Vertex {
		
		public Map<V, Integer> edges = new HashMap<V, Integer>();
		
		public Vertex() {

		}
		
	}
	private Map<V, Vertex> adjList = new HashMap<V, Vertex>();	
	
	public CS228DiGraph() {
		
	}
	
	
	/*
	 * Adds a vertex into the graph
	 */
	public void addVertex(V v) {
		adjList.put(v, new Vertex());
	}
	
	/*
	 * Adds an edge into the graph
	 */
	public void addEdge(V src, V dest, int cost) {
		adjList.get(src).edges.put(dest,cost);
	}
	
	@Override
	public Set getNeighbors(Object vertex) {
		return adjList.get(vertex).edges.keySet();
	}

	@Override
	public int getEdgeCost(Object start, Object end) {
		return adjList.get(start).edges.get(end);
	}

	@Override
	public int numVertices() {
		return adjList.size();
	}

	@Override
	public Iterator iterator() {
		Iterator<V> itr = adjList.keySet().iterator();
		return itr; 
		
	}

}
