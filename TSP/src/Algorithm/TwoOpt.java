package Algorithm;

import Structure.Node;
import Structure.Tour;

public class TwoOpt {

    double startTime;

    public TwoOpt(double startTime){
        this.startTime = startTime;
    }

    // Reverse nodes two by two
    private void reverse(Tour tour, int i, int j){
        while(i < j){
            int temp = tour.getCity(i);
            tour.setCity(i, tour.getCity(j));
            tour.setCity(j, temp);
            i++;
            j--;
        }
    }

    public Tour apply(Tour tour){   //2-opt "first-best" improvement
        int bestI = 0, bestJ = 0, gain, bestGain = -1, d1, d2;
        while(bestGain < 0 && System.currentTimeMillis() - 175000 < startTime){     //Until it's done or time limit
            bestGain = 0;
            for(int i=0; i<tour.getSize()-1; i++){
                for(int j=i+2; j<tour.getSize(); j++){
                    if(j == tour.getSize()-1){  //Last arc (circular array)
                        d1 = tour.computeDistanceWithMatrix(tour.getCity(i), tour.getCity(i+1))
                                + tour.computeDistanceWithMatrix(tour.getCity(j), tour.getCity(0));
                        d2 = tour.computeDistanceWithMatrix(tour.getCity(i), tour.getCity(j))
                                + tour.computeDistanceWithMatrix(tour.getCity(i+1), tour.getCity(0));
                    }
                    else {
                        d1 = tour.computeDistanceWithMatrix(tour.getCity(i), tour.getCity(i + 1))
                                + tour.computeDistanceWithMatrix(tour.getCity(j), tour.getCity(j + 1));
                        d2 = tour.computeDistanceWithMatrix(tour.getCity(i), tour.getCity(j))
                                + tour.computeDistanceWithMatrix(tour.getCity(i + 1), tour.getCity(j + 1));
                    }
                    gain = d2 - d1;
                    if(gain < bestGain){
                        bestGain = gain;
                        bestI = i;
                        bestJ = j;
                        break;
                    }
                }
            }
            if(bestGain < 0) {
                reverse(tour,bestI + 1, bestJ);

            }
        }
        return tour;
    }
}
