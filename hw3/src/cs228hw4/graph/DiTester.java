package cs228hw4.graph;

public class DiTester {
	public static void main(String[] args) {
		CS228DiGraph<String> myGraph = new CS228DiGraph<String>();
		myGraph.addVertex("A");
		myGraph.addVertex("B");
		myGraph.addVertex("C");
		myGraph.addVertex("D");
		myGraph.addVertex("E");
		myGraph.addVertex("F");
		myGraph.addEdge("A", "B", 2);
		myGraph.addEdge("A", "C", 2);
		myGraph.addEdge("B", "D", 6);
		myGraph.addEdge("D", "B", 6);
		myGraph.addEdge("B", "E", 2);
		myGraph.addEdge("C", "E", 1);
		myGraph.addEdge("D", "F", 1);
		myGraph.addEdge("E", "D", 1);
		myGraph.addEdge("E", "F", 20);
		CS228Dijkstra<String> myDi = new CS228Dijkstra<String>(myGraph); 
		myDi.run("A");
		System.out.println(myDi.getShortestDistance("F"));
		System.out.println(myDi.getShortestPath("F"));
		//System.out.println(myDi.getShortestDistance("B"));
		//System.out.println(myDi.getShortestPath("B"));
	}
}
