package assign07;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphTest {

    ArrayList<Integer> sources = new ArrayList<>();
    ArrayList<Integer> destinations = new ArrayList<>();
    Graph<Integer> graph = null;
    ArrayList<String> sources7 = new ArrayList<>();
    ArrayList<String> destinations7 = new ArrayList<>();
    Graph<String> example7 = null;
    ArrayList<String> sources8 = new ArrayList<>();
    ArrayList<String> destinations8 = new ArrayList<>();
    Graph<String> cities = null;

    public static void main(String[] args) {
        // ArrayList<Integer> sources = new ArrayList<>();
        // ArrayList<Integer> destinations = new ArrayList<>();
        // sources.add(1); destinations.add(2);
        // sources.add(2); destinations.add(3);
        // sources.add(1); destinations.add(3);
        // sources.add(3); destinations.add(4);
        // sources.add(4); destinations.add(2);
        // sources.add(1); destinations.add(4);
        // Graph<Integer> graph = new Graph<>(sources, destinations);

        // for (Vertex<Integer> v : graph.getVertices().values()) {
        //     for (Edge<Integer> e : v.getAdjacents())
        //         System.out.println(v.getVertexData() + "-->" + e.getDestinationValue());
        // }

        // for (Vertex<Integer> v : graph.getVertices().values()) {
        //     System.out.println(v.getVertexData() + " has " + v.getIndegrees() + " indegrees");
        // }

        // ArrayList<Integer> Asources = new ArrayList<>();
        // ArrayList<Integer> Adestinations = new ArrayList<>();
        // Asources.add(1); Adestinations.add(0);
        // Asources.add(2); Adestinations.add(0);
        // Asources.add(3); Adestinations.add(0);
        // Asources.add(4); Adestinations.add(0);
        // Asources.add(5); Adestinations.add(0);
        // List<Integer> lst = GraphUtility.sort(Asources, Adestinations);
        // System.out.println(lst.toString());

        // ArrayList<Integer> g4gsources = new ArrayList<>();
        // ArrayList<Integer> g4gdestinations = new ArrayList<>();
        // g4gsources.add(5); g4gdestinations.add(0);
        // g4gsources.add(5); g4gdestinations.add(2);
        // g4gsources.add(4); g4gdestinations.add(0);
        // g4gsources.add(4); g4gdestinations.add(1);
        // g4gsources.add(2); g4gdestinations.add(3);
        // g4gsources.add(3); g4gdestinations.add(1);
        // List<Integer> g4glst = GraphUtility.sort(g4gsources, g4gdestinations);
        // System.out.println(g4glst.toString());

        ArrayList<String> sources7 = new ArrayList<>();
        ArrayList<String> destinations7 = new ArrayList<>();
        GraphUtility.buildListsFromDot("C:\\Users\\alexd\\OneDrive\\Desktop\\CS 2420\\assign07\\examplegraph7.dot", sources7, destinations7);
        List<String> lst7 = GraphUtility.sort(sources7, destinations7);
        System.out.println(lst7.toString());
    }

    @BeforeEach
    void setup() {
        sources.clear();
        destinations.clear();
        sources.add(1); destinations.add(2);
        sources.add(2); destinations.add(3);
        sources.add(1); destinations.add(3);
        sources.add(3); destinations.add(4);
        sources.add(4); destinations.add(2);
        sources.add(1); destinations.add(4);
        graph = new Graph<>(sources, destinations);

        sources7 = new ArrayList<>();
        destinations7 = new ArrayList<>();
        GraphUtility.buildListsFromDot("C:\\\\Users\\\\alexd\\\\OneDrive\\\\Desktop\\\\CS 2420\\\\assign07\\\\examplegraph7.dot", sources7, destinations7);
        example7 = new Graph<>(sources7, destinations7);

        sources8 = new ArrayList<>();
        destinations8 = new ArrayList<>();
        GraphUtility.buildListsFromDot("C:\\Users\\alexd\\OneDrive\\Desktop\\CS 2420\\assign07\\examplegraph8.dot", sources8, destinations8);
        cities = new Graph<>(sources8, destinations8);
    }

    @Test
    public void testDFS() {
        assertTrue(graph.DFS(1, 4));
    }

    @Test
    public void testAreConnected() {
        assertTrue(GraphUtility.areConnected(sources, destinations, 1, 3));
    }

    @Test
    public void testBFS() {
        assertEquals((Integer)3, (Integer)graph.BFS(1, 3).getVertexData());
    }

    @Test
    public void testShortestPath() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1); expected.add(3);
        assertEquals(expected, GraphUtility.shortestPath(sources, destinations, 1, 3));
    }

    @Test
    public void testShortestPathThrows() {
        assertThrows(IllegalArgumentException.class, () -> GraphUtility.shortestPath(sources, destinations, 4, 1));
    }

    @Test
    public void testTopoThrows() {
        assertThrows(IllegalArgumentException.class, () -> GraphUtility.sort(sources, destinations));
    }

    @Test
    public void testExample5() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(2); expected.add(3); expected.add(4); expected.add(5);

        ArrayList<Integer> sources5 = new ArrayList<>();
        ArrayList<Integer> destinations5 = new ArrayList<>();
        sources5.add(1); destinations5.add(2);
        sources5.add(2); destinations5.add(3);
        sources5.add(3); destinations5.add(4);
        sources5.add(4); destinations5.add(5);
        List<Integer> actual = GraphUtility.shortestPath(sources5, destinations5, 2, 5);
        assertEquals(expected, actual);
    }

    @Test
    public void testShortest7() {
        List<String> expected = new ArrayList<>();
        expected.add("CS 1410"); expected.add("CS 2420"); expected.add("CS 3500"); expected.add("CS 4400");
        List<String> actual = GraphUtility.shortestPath(sources7, destinations7, "CS 1410", "CS 4400");
        assertEquals(expected, actual);
    }

    @Test
    public void testAreConnected7() {
        assertTrue(GraphUtility.areConnected(sources7, destinations7, "CS 1410", "CS 4400"));
    }

    @Test
    public void testTopoThrowsCities() {
        assertThrows(IllegalArgumentException.class, () -> GraphUtility.sort(sources8, destinations8));
    }

    @Test
    public void testDFSCities() {
        assertTrue(cities.DFS("Salt Lake City", "San Diego"));
    }

    @Test
    public void testDFSFalse() {
        assertFalse(example7.DFS("MATH 2250", "CS 4400"));
    }

    @Test
    public void testAreConnectedCycle() {
        assertTrue(GraphUtility.areConnected(sources8, destinations8, "Washington DC", "Atlanta"));
    }

    @Test
    public void testShortestCycle() {
        List<String> expected = new ArrayList<>();
        expected.add("Atlanta"); expected.add("San Diego"); expected.add("Salt Lake City"); expected.add("Dallas");
        List<String> actual = GraphUtility.shortestPath(sources8, destinations8, "Atlanta", "Dallas");
        assertEquals(expected, actual);
    }
}
