package assign07;

import java.util.LinkedList;

/**
 * Vertex class to be used in Graph
 * 
 * @author Alex Dean and Archer Fox
 * @version 3/12/2023
 */
public class Vertex<T> {

    private T data;
    private LinkedList<Edge<T>> adjacents;
    private int indegrees;
    private Vertex<T> visitedFrom;
    private boolean visited;

    /**
     * Constructs a Vertex object
     * 
     * @param data
     */
    public Vertex(T data) {
        this.data = data;
        this.adjacents = new LinkedList<>();
        this.indegrees = 0;
    }

    /**
     * Gets the visited value of the vertex
     * 
     * @return true if visited, false otherwise
     */
    public boolean getVisited() {
        return visited;
    }

    /**
     * Sets the visited value of the vertex
     * 
     * @param choice
     */
    public void setVisited(boolean choice) {
        visited = choice;
    }

    /**
     * Gets the vertex that this vertex was visited from
     * 
     * @return visitedFrom
     */
    public Vertex<T> getVisitedFrom() {
        return visitedFrom;
    }

    /**
     * Sets the vertex that this vertex was visited from
     * 
     * @param vertex
     */
    public void setVisitedFrom(Vertex<T> vertex) {
        visitedFrom = vertex;
    }

    /**
     * Gets the value of this vertex
     * 
     * @return data
     */
    public T getVertexData() {
        return this.data;
    }

    /**
     * Gets the list of adjacent vertices
     * 
     * @return adjacency list
     */
    public LinkedList<Edge<T>> getAdjacents() {
        return this.adjacents;
    }

    /**
     * Adds an edge to this vertex
     * 
     * @param other
     */
    public void addEdge(Vertex<T> other) {
        this.adjacents.add(new Edge<T>(other));
    }

    /**
     * Gets the number of indegrees this vertex has
     * 
     * @return num of indegrees as an int
     */
    public int getIndegrees() {
        return this.indegrees;
    }

    /**
     * Increments the amount of indegrees
     */
    public void incrementIndegrees() {
        this.indegrees++;
    }

    /**
     * Decrements the amount of indegrees
     */
    public void decrementIndegrees() {
        this.indegrees--;
    }
}
