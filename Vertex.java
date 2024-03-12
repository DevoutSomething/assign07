package assign07;

import java.util.LinkedList;

public class Vertex <T> {

    private T data;
    private LinkedList<Edge<T>> adjacents;
    int indegrees;

    public Vertex(T data) {
        this.data = data;
        this.adjacents = new LinkedList<>();
        this.indegrees = 0;
    }

    public T getVertexData() {
        return this.data;
    }

    public LinkedList<Edge<T>> getAdjacents() {
        return this.adjacents;
    }

    public void addEdge(Vertex<T> other) {
        this.adjacents.add(new Edge<T>(other));
    }

    public int getIndegrees() {
        return this.indegrees;
    }

    public void incrementIndegrees() {
        this.indegrees++;
    }

    public void decrementIndegrees() {
        this.indegrees--;
    }
}
