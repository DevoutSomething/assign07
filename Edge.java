package assign07;

public class Edge <T> {

    private Vertex<T> destination;

    public Edge(Vertex<T> destination) {
        this.destination = destination;
    }

    public T getDestinationValue() {
        return this.destination.getVertexData();
    }
}
