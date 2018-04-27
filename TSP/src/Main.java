import Algorithm.SimulatedAnnealing;
import Algorithm.TwoOpt;
import IO.TSPReader;
import IO.TSPWriter;
import Structure.Node;
import Structure.Tour;

import java.util.*;

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

        TSPWriter writer = new TSPWriter();
        Random random = new Random();

        int[] best = {6110, 15781, 538, 23964, 21282, 42230, 51293, 107217, 9282, 239070}; // Best before seeding
        int[] newBest = new int[10];

        for(int i=0; i<files.length; i++) {

            double startTime = System.currentTimeMillis();

            TSPReader reader = new TSPReader(files[i]);
            reader.read();
            List<Node> nodes = reader.getNodes();

            if(best[i] < (reader.getBestKnown() + (1.0/100.0*reader.getBestKnown()))){
                System.out.println(reader.getName() + " skipped");
                continue;
            }

            Tour tour = new Tour(nodes);
            System.out.println(reader.getName());
            int temp = (int) (Math.random()*1900) + 100;
            double coolingRate = (Math.random()*0.495) + 0.5;
            long seed = random.nextLong();
            System.out.println("Temperature: " + temp);
            System.out.println("Cooling Rate: " + coolingRate);
            System.out.println("Seed: " + seed);

            SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(temp, coolingRate, seed, reader.getBestKnown());

            tour = simulatedAnnealing.apply(tour, startTime);

            newBest[i] = tour.getTourLength();

            if(newBest[i] < best[i]){
                System.out.println("New best for " + reader.getName());
                writer.write("../ALGO_cup_2018_problems/" + reader.getName() + ".opt.tour",
                        tour,
                        simulatedAnnealing.getRestartTemp(),
                        simulatedAnnealing.getCoolingRate(),
                        simulatedAnnealing.getSeed());
                best[i] = newBest[i];
            }

            System.out.println("Time for " + reader.getName() + ": " + (System.currentTimeMillis() - startTime)/1000 + "s");
            System.out.println();

        }
    }

}
