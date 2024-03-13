package assign07;

/**
 * Edge class to be used in Graph
 * 
 * @author Alex Dean and Archer Fox
 * @version 3/12/2023
 */
public class Edge<T> {

    private Vertex<T> destination;

    /**
     * Constructs an Edge object with a destination vertex
     * 
     * @param destination
     */
    public Edge(Vertex<T> destination) {
        this.destination = destination;
    }

    /**
     * Gets the value of the destination vertex
     * 
     * @return destination value
     */
    public T getDestinationValue() {
        return this.destination.getVertexData();
    }

    /**
     * Gets destination vertex
     * 
     * @return destination vertex
     */
    public Vertex<T> getDestination() {
        return this.destination;
    }
}
