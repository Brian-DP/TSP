import Algorithm.SimulatedAnnealing;
import Algorithm.TwoOpt;
import IO.TSPReader;
import IO.TSPWriter;
import Structure.Node;
import Structure.Tour;

import java.util.*;

public class TSPSolver {

    static double startTime;

    public static void main(String[] args) {

        startTime = System.currentTimeMillis();

        switch(args[0]){
            case "-c":
                solve(args[1]);
                break;
            case "-s":
                solve(args[1], args[2], args[3], args[4]);
                break;
            case "-seed":
                seed();
                break;
            default:
                break;
        }

    }

    private static void solve(String filename, String stringTemp, String stringCoolingRate, String stringSeed) {

        TSPWriter writer = new TSPWriter();

        TSPReader reader = new TSPReader(filename);
        reader.readConfig();
        reader.read();
        List<Node> nodes = reader.getNodes();

        Tour tour = new Tour(nodes);
        tour.setBestKnown(reader.getBestKnown());
        System.out.println(reader.getName());

        double temp = Double.parseDouble(stringTemp);
        double coolingRate = Double.parseDouble(stringCoolingRate);
        long seed = Long.parseLong(stringSeed);

        System.out.println("Temperature: " + temp);
        System.out.println("Cooling Rate: " + coolingRate);
        System.out.println("Seed: " + seed);

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(temp, coolingRate, seed);
        tour = simulatedAnnealing.apply(tour, startTime);

        writer.write("../Solutions/" + filename + ".opt.tour", tour);


        if(tour.getTourLength() < reader.getCurrentBest()) {
            System.out.println("New best: " + tour.getTourLength());
            writer.write("../Solutions/" + filename + ".opt.tour", tour);
            writer.writeConfig(filename, "../Config/" + filename + ".config", tour, temp, coolingRate, seed);
        }

        System.out.println("Result: " + tour.getTourLength());
        System.out.println("Time for " + reader.getName() + ": " + (System.currentTimeMillis() - startTime) / 1000 + "s");
        System.out.println();

    }

    public static void solve(String filename){

        TSPWriter writer = new TSPWriter();

        TSPReader reader = new TSPReader(filename);
        reader.readConfig();
        reader.read();
        List<Node> nodes = reader.getNodes();

        Tour tour = new Tour(nodes);
        tour.setBestKnown(reader.getBestKnown());
        System.out.println(reader.getName());

        double temp = reader.getTemperature();
        double coolingRate = reader.getCoolingRate();
        long seed = reader.getSeed();

        System.out.println("Temperature: " + temp);
        System.out.println("Cooling Rate: " + coolingRate);
        System.out.println("Seed: " + seed);

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(temp, coolingRate, seed);
        tour = simulatedAnnealing.apply(tour, startTime);

        writer.write("../Solutions/" + filename + ".opt.tour", tour);

        System.out.println("Result: " + tour.getTourLength());
        System.out.println("Time for " + reader.getName() + ": " + (System.currentTimeMillis() - startTime) / 1000 + "s");
        System.out.println();

    }

    public static void seed(){
        String[] files ={"ch130",
                "d198",
                "eil76",
                "fl1577",
                "kroA100",
                "lin318",
                "pcb442",
                "pr439",
                "rat783",
                "u1060"};

        TSPWriter writer = new TSPWriter();
        Random random = new Random();

        for (int i = 0; i < files.length; i++) {

            startTime = System.currentTimeMillis();

            TSPReader reader = new TSPReader(files[i]);
            reader.readConfig();
            reader.read();

            if(reader.getCurrentBest() == reader.getBestKnown()){
                System.out.println("Skipped");
                continue;
            }

            List<Node> nodes = reader.getNodes();

            Tour tour = new Tour(nodes);
            tour.setBestKnown(reader.getBestKnown());
            System.out.println(reader.getName());
            double temp = (Math.random() * 1900) + 100;
            double coolingRate = (Math.random() * 0.499) + 0.5;
            long seed = random.nextLong();

            SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(temp, coolingRate, seed);

            tour = simulatedAnnealing.apply(tour, startTime);

            System.out.println("Current best: " + reader.getCurrentBest());
            System.out.println("Result: " + tour.getTourLength());

            if(tour.getTourLength() < reader.getCurrentBest()) {
                System.out.println("New best: " + tour.getTourLength());
                writer.write("../Solutions/" + files[i] + ".opt.tour", tour);
                writer.writeConfig(files[i], "../Config/" + files[i] + ".config", tour, temp, coolingRate, seed);
            }

            System.out.println("Time for " + reader.getName() + ": " + (System.currentTimeMillis() - startTime) / 1000 + "s");
        }
    }
}
