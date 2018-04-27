package Structure;

import java.util.List;

public class Tour {

    private int[] tour;
    private int[][] adjacencyMatrix;


    public Tour(List<Node> list) {
        tour = new int[list.size()];
        for(int i=0; i<list.size(); i++){
            tour[i] = list.get(i).getId();
        }
        adjacencyMatrix = new int[list.size()][list.size()];
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.size(); j++){
                adjacencyMatrix[i][j] = computeDistance(list.get(i), list.get(j));
            }
        }
    }

    public Tour(int[] newTour, int[][] newMatrix){
        tour = new int[newTour.length];
        for(int i=0; i<newTour.length; i++){
            tour[i] = newTour[i];
        }
        adjacencyMatrix = new int[newTour.length][newTour.length];
        for(int i = 0; i < newTour.length; i++){
            for(int j = 0; j < newTour.length; j++){
                adjacencyMatrix[i][j] = newMatrix[i][j];
            }
        }
    }

    private int computeDistance(Node n1, Node n2){
        double x = n1.getX() - n2.getX();
        double y = n1.getY() - n2.getY();
        int result = (int) Math.round(Math.sqrt(x*x + y*y));
        return result;
    }

    public int computeDistanceWithMatrix(int indexN1, int indexN2){
        return adjacencyMatrix[indexN1][indexN2];
    }


    public int getTourLength(){
        int distance = 0;
        for(int i=1; i<tour.length; i++){
            distance += computeDistanceWithMatrix(tour[i-1], tour[i]);
        }
        distance += computeDistanceWithMatrix(tour[tour.length-1], tour[0]);
        return distance;
    }

    public int getSize(){
        return tour.length;
    }

    public int getCity(int index){
        return tour[index];
    }

    public void setCity(int index, int city){
        tour[index] = city;
    }

    public int[] getTour() {
        return tour;
    }

    public void setTour(int[] tour) {
        this.tour = tour;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void setAdjacencyMatrix(int[][] adjacencyMatrix){
        this.adjacencyMatrix = adjacencyMatrix;
    }

    /*
    static public Tour copy(Tour tourCopy){
        Tour tour = new Tour();
        tour.setTour(tourCopy.getTour());
        tour.setAdjacencyMatrix(tourCopy.getAdjacencyMatrix());
        return tour;
    }
    */
}
