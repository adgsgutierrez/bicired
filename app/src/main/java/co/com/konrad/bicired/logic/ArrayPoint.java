package co.com.konrad.bicired.logic;

import java.util.ArrayList;

public class ArrayPoint {

    private ArrayList<Point> points;

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "{" +
                "points=" + points +
                '}';
    }
}
