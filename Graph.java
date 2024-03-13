package assign07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Class reprenting a graph data structure with BFS, DFS, and topoSort methods
 * 
 * @author Alex Dean and Archer Fox
 * @version 3/12/2023
 */
public class Graph<T> {

    private HashMap<T, Vertex<T>> vertices;

    /**
     * Constructs a graph from the sources and destinations lists
     * 
     * @param sources
     * @param destinations
     * @throws IllegalArgumentException if sources and destinations are not the same
     *                                  size
     */
    public Graph(List<T> sources, List<T> destinations) throws IllegalArgumentException {
        if (sources.size() != destinations.size())
            throw new IllegalArgumentException("sources and destinations need to be the same size");

        this.vertices = new HashMap<>();
        for (int i = 0; i < sources.size(); i++) {
            if (!vertices.containsKey(sources.get(i))) {
                vertices.put(sources.get(i), new Vertex<T>(sources.get(i)));
            }

            vertices.get(sources.get(i)).addEdge(new Vertex<T>(destinations.get(i)));

            if (!vertices.containsKey(destinations.get(i))) {
                vertices.put(destinations.get(i), new Vertex<T>(destinations.get(i)));
            }

            vertices.get(destinations.get(i)).incrementIndegrees();
        }
    }

    /**
     * Driver method for depth-first search
     * 
     * @param start
     * @param goal
     * @return true if there is a path from start to goal, false otherwise
     */
    public boolean DFS(T start, T goal) {
        Set<T> unvisitedSet = new HashSet<>(vertices.keySet());
        if (vertices.isEmpty())
            return false;

        return DFSrecursive(start, goal, unvisitedSet);
    }

    /**
     * Recursive method for depth-first search
     * 
     * @param current
     * @param goal
     * @param unvisitedSet
     * @return true if there is a path from start to goal, false otherwise
     */
    private boolean DFSrecursive(T current, T goal, Set<T> unvisitedSet) {
        if (current.equals(goal))
            return true;

        unvisitedSet.remove(current);
        if (unvisitedSet.isEmpty())
            return false;

        for (Edge<T> edge : vertices.get(current).getAdjacents()) {
            if (!unvisitedSet.contains(edge.getDestinationValue())) {
                continue;
            } else {
                return DFSrecursive(edge.getDestinationValue(), goal, unvisitedSet);
            }
        }

        return false;
    }

    /**
     * Getter method for the hashmap of vertices
     * 
     * @return vertices
     */
    public HashMap<T, Vertex<T>> getVertices() {
        return vertices;
    }

    /**
     * Breadth-first search algorithm that traverses the graph from start to goal
     * while setting the visitedFrom values
     * 
     * @param start
     * @param goal
     * @return end vertex of the BFS
     * @throws IllegalArgumentException if there is no path from start to goal
     */
    public Vertex<T> BFS(T start, T goal) throws IllegalArgumentException {
        Queue<T> queue = new LinkedList<>();
        queue.offer(start);

        T current = start;

        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.equals(goal)) {
                return vertices.get(current);
            }
            if (vertices.get(current).getVisited()) {
                current = queue.poll();
                continue;
            }

            vertices.get(current).setVisited(true);

            for (T val : queue) {
                if (val.equals(goal)) {
                    return vertices.get(val);
                }
            }
            for (Edge<T> edge : vertices.get(current).getAdjacents()) {
                if (!edge.getDestination().getVisited()) {
                    queue.offer(edge.getDestinationValue());
                    vertices.get(edge.getDestinationValue()).setVisitedFrom(vertices.get(current));
                }
            }
        }
        return vertices.get(current);
    }

    /**
     * Reconstructs the path taken in BFS as a list
     * 
     * @param start
     * @param end
     * @param initialGoal
     * @return list describing the path taken from start to end
     * @throws IllegalArgumentException if the end vertex is not equal to the initial goal of the BFS
     */
    public List<T> reconstructPath(Vertex<T> start, Vertex<T> end, Vertex<T> initialGoal)
            throws IllegalArgumentException {
        if (end == null) {
            throw new IllegalArgumentException("There is no valid path between vertices");
        }
        if (!end.getVertexData().equals(initialGoal.getVertexData()))
            throw new IllegalArgumentException("There is no valid path between vertices");

        ArrayList<T> returnList = new ArrayList<>();
        Vertex<T> temp = end;

        while (true) {
            returnList.add(temp.getVertexData());
            if (temp.getVertexData().equals(start.getVertexData()))
                break;
            temp = temp.getVisitedFrom();
        }

        Collections.reverse(returnList);
        return returnList;
    }

    /**
     * Topological sorting algorithm that returns an ordered list of the vertices in the graph
     * 
     * @return ordered list
     * @throws IllegalArgumentException if there is a cycle in the graph
     */
    public List<T> topoSort() throws IllegalArgumentException {
        Queue<T> queue = new LinkedList<>();
        List<T> returnList = new LinkedList<>();
        int numVisited = 0;

        for (Vertex<T> v : vertices.values()) {
            if (v.getIndegrees() == 0)
                queue.offer(v.getVertexData());
        }
        if (queue.isEmpty())
            throw new IllegalArgumentException("Graph has a cycle");

        while (!queue.isEmpty()) {
            returnList.add(queue.peek());
            Vertex<T> u = vertices.get(queue.poll());
            numVisited++;
            for (Edge<T> e : u.getAdjacents()) {
                vertices.get(e.getDestinationValue()).decrementIndegrees();
                if (vertices.get(e.getDestinationValue()).getIndegrees() == 0) {
                    queue.offer(e.getDestinationValue());
                }
            }
        }

        if (numVisited < vertices.size())
            throw new IllegalArgumentException("Graph has a cycle");

        return returnList;
    }

}
