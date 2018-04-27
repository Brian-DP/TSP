package Algorithm;

import Structure.Tour;

import java.util.Random;

import static java.lang.System.exit;

public class SimulatedAnnealing {

    // Initial temp
    private double temp;

    // Max temp, restarting value
    private double restartTemp;

    private double coolingRate;
    private long seed;
    private Random generator;

    // Best known solution for given problem
    private int bestKnown;

    public SimulatedAnnealing(double temp, double coolingRate, long seed, int bestKnown) {
        this.temp = temp;
        this.restartTemp = temp;
        this.coolingRate = coolingRate;
        this.seed = seed;
        generator = new Random(seed);
        this.bestKnown = bestKnown;
    }

    // Calculate the acceptance probability
    public double acceptanceProbability(int length, int newLength, double temperature) {

        if(newLength < length){
            return 1.0;
        }

        // If the new solution is worse, calculate an acceptance probability
        return Math.exp( (double) -(newLength - length) / temperature);
    }

    // Double bridge
    public void perturbation(Tour solution){

        int city1 = 0, city2 = 0, city3 = 0, city4 = 0, placeholder = 0;

        // Get a random positions in the tour
        while(city1+1 >= city2) {
            city1 = (int) (solution.getSize() / 2 * generator.nextDouble());                               // Get city 1/2 from the first half of the tour
            city2 = (int) (solution.getSize() / 2 * generator.nextDouble());
        }
        while(city3+1 >= city4) {
            city3 = (int) ((solution.getSize() / 2 * generator.nextDouble()) + solution.getSize() / 2);    // Get city 3/4 from the second half of the tour
            city4 = (int) ((solution.getSize() / 2 * generator.nextDouble()) + solution.getSize() / 2);
        }

        int[] oldTour = solution.getTour();
        int[] newTour = new int[oldTour.length];

        // Double bridge
        for(int i=0; i <= city1; i++){                      //->1
            newTour[placeholder] = oldTour[i];
            placeholder++;
        }
        for(int i=(city3+1); i <= city4; i++){              //3+1 -> 4
            newTour[placeholder] = oldTour[i];
            placeholder++;
        }
        for(int i=(city2+1); i <= city3; i++) {             //2+1 -> 3
            newTour[placeholder] = oldTour[i];
            placeholder++;
        }
        for(int i=(city1+1); i <= city2; i++) {             //1+1 -> 2
            newTour[placeholder] = oldTour[i];
            placeholder++;
        }
        for(int i=city4+1; i < oldTour.length; i++) {       //4 ->
            newTour[placeholder] = oldTour[i];
            placeholder++;
        }

        // Check for errors
        if(placeholder != oldTour.length){
            System.out.println(placeholder);
            exit(1);
        }

        solution.setTour(newTour);
    }

    public Tour apply(Tour tour, double startTime){

        // Set current solution as best
        Tour best = new Tour(tour.getTour(), tour.getAdjacencyMatrix());
        int currentLength = 0, newLength = 0;
        int iteration = 0;
        TwoOpt twoOpt = new TwoOpt(startTime);

        // Loop until time limit or until best known solution is reached
        while (System.currentTimeMillis() - 178000 < startTime && best.getTourLength() != bestKnown) {

            // Create new solution
            Tour newSolution = new Tour(tour.getTour(), tour.getAdjacencyMatrix());
            perturbation(newSolution);

            // Improve the new solution
            newSolution = twoOpt.apply(newSolution);

            // Get lengths of solutions
            currentLength = tour.getTourLength();
            newLength = newSolution.getTourLength();

            // Decide if we should accept the solution
            if (acceptanceProbability(currentLength, newLength, temp) > generator.nextDouble()) {
                tour = new Tour(newSolution.getTour(), newSolution.getAdjacencyMatrix());
            }

            // Keep track of the best solution found
            if (tour.getTourLength() < best.getTourLength()) {
                best = new Tour(tour.getTour(), tour.getAdjacencyMatrix());
                //System.out.println("New best: " + best.getTourLength());
                //System.out.println("Best known: " + bestKnown);
                //System.out.println("Time: " + (System.currentTimeMillis() - startTime)/1000 + "s");
                //System.out.println();
            }

            // Explore a bit before cooling down
            iteration++;
            if(iteration > 50) {
                temp *= coolingRate;
                iteration = 0;
            }

            // If the system cools down before time limit, restart the algorithm
            if(temp < 1){
                temp = restartTemp;
            }

        }

        return best;

    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getCoolingRate() {
        return coolingRate;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public double getRestartTemp() {
        return restartTemp;
    }

    public void setRestartTemp(double restartTemp) {
        this.restartTemp = restartTemp;
    }
}
