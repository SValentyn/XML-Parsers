package SAX;

import java.util.ArrayList;

class FunctionCreator {

    private double k;
    private double b;

    public FunctionCreator() {
        this.k = 0;
        this.b = 0;
    }

    private double getK() {
        return k;
    }

    private double getB() {
        return b;
    }

    public void calculation(ArrayList<DataNode> nodes) {
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumX2 = 0; // x squared

        for (DataNode node : nodes) {
            sumX += node.getX();
            sumY += node.getY();
            sumXY += node.getX() * node.getY();
            sumX2 += node.getX() * node.getX();
        }

        int count = nodes.size() * 2;
        k = ((sumXY - sumX * sumY / count) / (sumX2 - sumX * sumX / count));
        b = (sumY / count - getK() * sumX / count);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nEquation: ").append("y(x) = ").append(getK()).append(" * x");

        if (getB() > 0) builder.append(" + ").append(getB());
        else builder.append(" ").append(getB()).append("\n");

        return builder.toString();
    }

}
