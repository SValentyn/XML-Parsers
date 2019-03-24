package SAX;

class DataNode {

    private String date;
    private double x;
    private double y;

    public DataNode(String date, double x, double y) {
        this.date = date;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "\ndate - " + date + "; x -" + x + "; y - " + y;
    }
}
