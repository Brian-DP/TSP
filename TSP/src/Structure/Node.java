package Structure;

public class Node {

    private int id;
    private double x, y;

    public Node(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void print(){
        System.out.println(id + " : " + x + ", " + y);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
