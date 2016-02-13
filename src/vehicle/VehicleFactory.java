package vehicle;

import cell.Cell;
import route.RouteFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class VehicleFactory {
    private static int index;
    private final RouteFinder finder;

    public VehicleFactory(RouteFinder finder) {
        if (finder == null) {
            throw new NullPointerException("null route finder");
        }
        index = 1;
        this.finder = finder;
    }

    public Vehicle produce(Cell origin) {
        return new Vehicle(index++, finder.find(origin));
    }

    public List<Vehicle> produce(Cell origin, int amount) {
        ArrayList<Vehicle> output = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            output.add(produce(origin));
        }
        return output;
    }

    public static void main(String[] args) {

    }
}
