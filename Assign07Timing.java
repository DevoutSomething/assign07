package assign07;

import java.util.ArrayList;

public class Assign07Timing extends TimerTemplate {
    ArrayList<Integer> sources = new ArrayList<>();
    ArrayList<Integer> destinations = new ArrayList<>();

    public Assign07Timing(int[] problemSizes, int timesToLoop) {
        super(problemSizes, timesToLoop);
    }

    public static void main(String[] args) {
        int[] problemSizes = new int[20];
        for (int i=0; i<problemSizes.length; i++){
            problemSizes[i] = i*500 + 500;
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
        
    }

    @Override
    protected void timingIteration(int n) {
        GraphUtility.areConnected(sources, destinations, null, null);
    }

    @Override
    protected void compensationIteration(int n) {

    }
}
