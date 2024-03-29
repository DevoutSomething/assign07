package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains several methods for solving problems on generic, directed,
 * unweighted, sparse graphs.
 * 
 * @author CS 2420 instructors
 * @version Feb 28, 2024
 */
public class GraphUtility {

	/**
	 * Uses a depth-first search to determine if there is a path from the vertex
	 * with srcData to the vertex with dstData in the graph constructed from
	 * sources and destinations
	 * 
	 * @param sources
	 * @param destinations
	 * @param srcData
	 * @param dstData
	 * @return true if connected, false otherwise
	 * @throws IllegalArgumentException if there is no vertex with srcData or no
	 *                                  vertex with dstData
	 */
	public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)
			throws IllegalArgumentException {
		Graph<Type> graph = new Graph<>(sources, destinations);

		if (!graph.getVertices().containsKey(srcData))
			throw new IllegalArgumentException("Graph does not contain srcData");
		if (!graph.getVertices().containsKey(dstData))
			throw new IllegalArgumentException("Graph does not contain dstData");

		return graph.DFS(srcData, dstData);
	}

	/**
	 * Uses a breadth-first search to find the shortest path between the vertex the
	 * vertex
	 * with srcData to the vertex with dstData in the graph constructed from
	 * sources and destinations
	 * 
	 * @param sources
	 * @param destinations
	 * @param srcData
	 * @param dstData
	 * @return list describing the shortest path between the two vertices
	 * @throws IllegalArgumentException if there is no vertex with srcData or no
	 *                                  vertex with dstData or if no path exists
	 */
	public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData,
			Type dstData)
			throws IllegalArgumentException {
		Graph<Type> graph = new Graph<>(sources, destinations);

		if (!graph.getVertices().containsKey(srcData))
			throw new IllegalArgumentException("Graph does not contain srcData");
		if (!graph.getVertices().containsKey(dstData))
			throw new IllegalArgumentException("Graph does not contain dstData");

		return graph.reconstructPath(graph.getVertices().get(srcData), graph.BFS(srcData, dstData),
				graph.getVertices().get(dstData));
	}

	/**
	 * Use topological sort to generate a sorted ordering of the vertices in the
	 * graph constructed from sources and destinations
	 * 
	 * @param sources
	 * @param destinations
	 * @return ordered list of the vertices in the graph
	 * @throws IllegalArgumentException if the graph contains a cycle
	 */
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
		Graph<Type> graph = new Graph<>(sources, destinations);

		return graph.topoSort();
	}

	/**
	 * Builds "sources" and "destinations" lists according to the edges
	 * specified in the given DOT file (e.g., "a -> b"). Assumes that the vertex
	 * data type is String.
	 * 
	 * Accepts many valid "digraph" DOT files (see examples posted on Canvas).
	 * --accepts \\-style comments
	 * --accepts one edge per line or edges terminated with ;
	 * --does not accept attributes in [] (e.g., [label = "a label"])
	 * 
	 * @param filename     - name of the DOT file
	 * @param sources      - empty ArrayList, when method returns it is a valid
	 *                     "sources" list that can be passed to the public methods
	 *                     in this
	 *                     class
	 * @param destinations - empty ArrayList, when method returns it is a valid
	 *                     "destinations" list that can be passed to the public
	 *                     methods in
	 *                     this class
	 */
	public static void buildListsFromDot(String filename, ArrayList<String> sources, ArrayList<String> destinations) {

		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		scan.useDelimiter(";|\n");

		// Determine if graph is directed (i.e., look for "digraph id {").
		String line = "", edgeOp = "";
		while (scan.hasNext()) {
			line = scan.next();

			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");

			if (line.indexOf("digraph") >= 0) {
				edgeOp = "->";
				line = line.replaceFirst(".*\\{", "");
				break;
			}
		}
		if (edgeOp.equals("")) {
			System.out.println("DOT graph must be directed (i.e., digraph).");
			scan.close();
			System.exit(0);

		}

		// Look for edge operator -> and determine the source and destination
		// vertices for each edge.
		while (scan.hasNext()) {
			String[] substring = line.split(edgeOp);

			for (int i = 0; i < substring.length - 1; i += 2) {
				// remove " and trim whitespace from node string on the left
				String vertex1 = substring[0].replace("\"", "").trim();
				// if string is empty, try again
				if (vertex1.equals("")) {
					continue;
				}

				// do the same for the node string on the right
				String vertex2 = substring[1].replace("\"", "").trim();
				if (vertex2.equals("")) {
					continue;
				}

				// indicate edge between vertex1 and vertex2
				sources.add(vertex1);
				destinations.add(vertex2);
			}

			// do until the "}" has been read
			if (substring[substring.length - 1].indexOf("}") >= 0) {
				break;
			}

			line = scan.next();

			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");
		}

		scan.close();
	}
}
