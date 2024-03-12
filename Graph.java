package assign07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Graph <T> {

    private HashMap<T, Vertex<T>> vertices;

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

    
    public boolean DFS(T start, T goal) {
        Set<T> unvisitedSet = new HashSet<>(vertices.keySet());
        if (vertices.isEmpty())
            return false;
        
        return DFSrecursive(start, goal, unvisitedSet);
    }


    private boolean DFSrecursive(T current, T goal, Set<T> unvisitedSet) {
        if(current.equals(goal))
            return true;

        unvisitedSet.remove(current);
        if(unvisitedSet.isEmpty())
            return false;

        for (Edge<T> edge : vertices.get(current).getAdjacents()) {
            if(!unvisitedSet.contains(edge.getDestinationValue())) {
                continue;
            } else {
                return DFSrecursive(edge.getDestinationValue(), goal, unvisitedSet);
            }
        }

        return false;
    }

    public HashMap<T, Vertex<T>> getVertices() {
        return vertices;
    }

    public List<T> BFS(T start, T goal) {
        Queue<T> queue = new LinkedList<>();
        ArrayList<T> visits = new ArrayList<>(vertices.size());
        Set<T> unvisitedSet = new HashSet<>(vertices.keySet());
        queue.offer(start);
        
        int i = 0;
        T current;
        while (!queue.isEmpty()) {
            current = queue.poll();
            visits.set(i++, current);
            for (Edge<T> edge : vertices.get(current).getAdjacents()) {
                if(unvisitedSet.contains(edge)) {
                    queue.offer(edge.getDestinationValue());
                    unvisitedSet.remove(edge.getDestinationValue());
                }
            }
        }

        return visits;
    }

    // public ArrayList<T> topoSort() {

    // }
}
