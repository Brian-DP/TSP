import Algorithm.SimulatedAnnealing;
import Algorithm.TwoOpt;
import IO.TSPReader;
import IO.TSPWriter;
import Structure.Node;
import Structure.Tour;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String[] files ={"../ALGO_cup_2018_problems/ch130",
                "../ALGO_cup_2018_problems/d198",
                "../ALGO_cup_2018_problems/eil76",
                "../ALGO_cup_2018_problems/fl1577",
                "../ALGO_cup_2018_problems/kroA100",
                "../ALGO_cup_2018_problems/lin318",
                "../ALGO_cup_2018_problems/pcb442",
                "../ALGO_cup_2018_problems/pr439",
                "../ALGO_cup_2018_problems/rat783",
                "../ALGO_cup_2018_problems/u1060"};

        TSPWriter writer = new TSPWriter();
        Random random = new Random();

        int[] best = new int[10];
        int[] newBest = new int[10];

        // Read the current best from solutions
        for(int i=0; i<best.length; i++){
            TSPReader reader = new TSPReader(files[i] + ".opt.tour");
            reader.read();
            best[i] = reader.getLength();
        }

        // Seeding
        for(;;) {
            for (int i = 0; i < files.length; i++) {

                double startTime = System.currentTimeMillis();

                TSPReader reader = new TSPReader(files[i] + ".tsp");
                reader.read();
                List<Node> nodes = reader.getNodes();


                if (best[i] < (reader.getBestKnown() + (0.8 / 100.0 * reader.getBestKnown()))) {
                    System.out.println(reader.getName() + " skipped");
                    System.out.println();
                    continue;
                }

                Tour tour = new Tour(nodes);
                tour.setBestKnown(reader.getBestKnown());
                System.out.println(reader.getName());
                System.out.println("Best known: " + tour.getBestKnown());
                int temp = (int) (Math.random() * 1900) + 100;
                double coolingRate = (Math.random() * 0.499) + 0.5;
                long seed = random.nextLong();
                System.out.println("Temperature: " + temp);
                System.out.println("Cooling Rate: " + coolingRate);
                System.out.println("Seed: " + seed);

                SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(temp, coolingRate, seed);

                tour = simulatedAnnealing.apply(tour, startTime);

                newBest[i] = tour.getTourLength();

                if (newBest[i] <= best[i]) {
                    System.out.println("New best for " + reader.getName());
                    writer.write(files[i] + ".opt.tour",
                            tour,
                            simulatedAnnealing.getRestartTemp(),
                            simulatedAnnealing.getCoolingRate(),
                            simulatedAnnealing.getSeed());
                    best[i] = newBest[i];
                }

                System.out.println("Time for " + reader.getName() + ": " + (System.currentTimeMillis() - startTime) / 1000 + "s");
                System.out.println();

            }
        }
    }

}
