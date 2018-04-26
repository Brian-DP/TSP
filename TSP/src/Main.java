import Algorithm.SimulatedAnnealing;
import Algorithm.TwoOpt;
import IO.TSPReader;
import Structure.Node;
import Structure.Tour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String[] files = {"../ALGO_cup_2018_problems/ch130.tsp",
                "../ALGO_cup_2018_problems/d198.tsp",
                "../ALGO_cup_2018_problems/eil76.tsp",
                "../ALGO_cup_2018_problems/fl1577.tsp",
                "../ALGO_cup_2018_problems/kroA100.tsp",
                "../ALGO_cup_2018_problems/lin318.tsp",
                "../ALGO_cup_2018_problems/pcb442.tsp",
                "../ALGO_cup_2018_problems/pr439.tsp",
                "../ALGO_cup_2018_problems/rat783.tsp",
                "../ALGO_cup_2018_problems/u1060.tsp"};

        List<TSPReader> readers = new ArrayList<>();

        int[] best = new int[10];
        int[] temp = new int[10];
        int[] coolingRate = new int[10];
        long[] seeds = new long[10];

        for(int i=0; i<files.length; i++) {

            TSPReader reader = new TSPReader(files[i]);
            reader.read();
            List<Node> nodes = reader.getNodes();

            Tour tour = new Tour(nodes);

            System.out.println(reader.getName());
            System.out.println("Best Known: " + reader.getBestKnown());
            SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(500, 0.95, 9694, reader.getBestKnown());

            tour = simulatedAnnealing.apply(tour);

            best[i] = tour.getTourLength();

            System.out.println("Parameters: " + simulatedAnnealing.getTemp() + ", " + simulatedAnnealing.getCoolingRate() + ", " + simulatedAnnealing.getSeed());
        }

        for(int i=0; i<files.length; i++){
            System.out.println("Best for " + files[i] + ": " + best[i]);
        }
    }

}
