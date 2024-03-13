package assign07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Assign07Timing extends TimerTemplate {
    ArrayList<String> sources = new ArrayList<>();
    ArrayList<String> destinations = new ArrayList<>();

    public Assign07Timing(int[] problemSizes, int timesToLoop) {
        super(problemSizes, timesToLoop);
    }

    public static void main(String[] args) {
        int[] problemSizes = new int[20];
        for (int i=0; i<problemSizes.length; i++){
            problemSizes[i] = i*50 + 50;
        }

        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 10000000000L) { // warm up loop

        }
        
        Assign07Timing plot = new Assign07Timing(problemSizes, 100);
        Result[] results = plot.run();
        for (int i = 0; i < results.length; i++){
            System.out.println(results[i].n() + ", " + results[i].avgNanoSecs());
        }
    }

    @Override
    protected void setup(int n) {
        sources.clear();
        destinations.clear();
        generateRandomGraph(sources, destinations, n);
    }

    @Override
    protected void timingIteration(int n) {
        GraphUtility.areConnected(sources, destinations, "v1", destinations.get(n - 1));
    }

    @Override
    protected void compensationIteration(int n) {
    }

    public static <T> void generateRandomGraph(List<String> sources, List<String> destinations, int vertexCount) {

        Random rng = new Random();

        // generate a list of vertices
        String[] vertex = new String[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            vertex[i] = "v" + i;
        }

        // randomly connect the vertices using 2 * |V| edges
        for (int i = 0; i < 2 * vertexCount; i++) {
            sources.add(vertex[rng.nextInt(vertexCount)]); destinations.add(vertex[rng.nextInt(vertexCount)]);
        }
    }
}
