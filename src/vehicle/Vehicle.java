package vehicle;

import route.Route;

/**
 * Created by yaokaibin on 16-2-10.
 */
public class Vehicle {
    private final int index;
    private int location;
    private int dwell;
    private final Route route;

    public Vehicle(int index, Route route) {
        this.index = index;
        this.route = route;
        location = route.origin();
        dwell = 1;
    }

    public int getIndex() {
        return index;
    }

    public int getLocation() {
        return location;
    }

    public int getDwell() {
        return dwell;
    }

    public int origin() {
        return route.origin();
    }

    public int destination() {
        return route.destination();
    }

    public int next() {
        return route.next();
    }

    public void move() {
        dwell = 1;
        location = route.forward();
    }

    public void stuck() {
        dwell++;
    }

    public int size() {
        return route.getSize();
    }

    @Override
    public String toString() {
        return "veh" + index + " at " + location;
    }

    public String getInformation() {
        return index + "," + location + "," + dwell;
    }

    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle(2, Route.demo());
        System.out.println(vehicle);
    }
}
