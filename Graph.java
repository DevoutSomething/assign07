package assign07;

import java.util.ArrayList;
import java.util.Collections;
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

    public Vertex<T> BFS(T start, T goal) throws IllegalArgumentException {
        Queue<T> queue = new LinkedList<>();
        queue.offer(start);
        
        T current = start;

        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.equals(goal)){
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

    public List<T> reconstructPath(Vertex<T> start, Vertex<T> end, Vertex<T> initialGoal) throws IllegalArgumentException {
        if(end == null){
            throw new IllegalArgumentException("There is no valid path between vertices");
        }
        if(!end.getVertexData().equals(initialGoal.getVertexData()))
            throw new IllegalArgumentException("There is no valid path between vertices");
        
        ArrayList<T> returnList = new ArrayList<>();
        Vertex<T> temp = end;
    
        while (temp != null) {
            returnList.add(temp.getVertexData());
            temp = temp.getVisitedFrom();
        }

        Collections.reverse(returnList);
        return returnList;
    }

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
