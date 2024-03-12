package assign07;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphTest {

    ArrayList<Integer> sources = new ArrayList<>();
    ArrayList<Integer> destinations = new ArrayList<>();
    Graph<Integer> graph = null;

    public static void main(String[] args) {
        ArrayList<Integer> sources = new ArrayList<>();
        ArrayList<Integer> destinations = new ArrayList<>();
        sources.add(1); destinations.add(2);
        sources.add(2); destinations.add(3);
        sources.add(1); destinations.add(3);
        sources.add(3); destinations.add(4);
        sources.add(4); destinations.add(2);
        sources.add(1); destinations.add(4);
        Graph<Integer> graph = new Graph<>(sources, destinations);

        for (Vertex<Integer> v : graph.getVertices().values()) {
            for (Edge<Integer> e : v.getAdjacents())
                System.out.println(v.getVertexData() + "-->" + e.getDestinationValue());
        }

        for (Vertex<Integer> v : graph.getVertices().values()) {
            System.out.println(v.getVertexData() + " has " + v.getIndegrees() + " indegrees");
        }
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
    }

    @Test
    public void testDFS() {
        assertTrue(graph.DFS(1, 4));
    }

    @Test
    public void testBFS() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1); expected.add(4);
        assertEquals(expected, graph.BFS(1, 4));
    }
}
