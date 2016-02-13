package cell;

import graph.Graph;
import vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yaokaibin on 16-2-10.
 */
public class Cell {
    protected int time;
    protected final int id;
    protected final int link;
    protected final int volume;
    protected final int maxFlow;
    protected final double delta;
    protected final List<Vehicle> vehicles;
    protected final static VehicleComparator comparator = new VehicleComparator();
    protected final static StringBuffer buffer = new StringBuffer("time,link,index,location,dwell\n");

    public Cell(int id, int link, int volume, int maxFlow, double delta) {
        time = 0;
        this.id = id;
        this.link = link;
        this.volume = volume;
        this.maxFlow = maxFlow;
        this.delta = delta;
        vehicles = new ArrayList<>();
    }

    public int id() {
        return id;
    }

    public int link() {
        return link;
    }

    public int volume() {
        return volume;
    }

    public int maxFlow() {
        return maxFlow;
    }

    public double delta() {
        return delta;
    }

    public int size() {
        return vehicles.size();
    }

    public int getMaxSend() {
        int size = size();
        return size < maxFlow ? size : maxFlow;
    }

    public int getMaxReceive() {
        int compare = (int) Math.floor(delta * (volume - size()));
        return compare < maxFlow ? compare : maxFlow;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Vehicle> getVehicles(int amount) {
        return vehicles.stream()
                .sorted(comparator)
                .limit(amount)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehicles(int amount, int next) {
        return vehicles.stream()
                .filter(veh -> next == veh.next())
                .sorted(comparator)
                .limit(amount)
                .collect(Collectors.toList());
    }

    private static class VehicleComparator implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o2.getDwell() - o1.getDwell();
        }
    }

    public void addVehicles(List<Vehicle> vehicles) {
        this.vehicles.addAll(vehicles);
    }

    public void removeVehicles(List<Vehicle> vehicles) {
        this.vehicles.removeAll(vehicles);
    }

    public void stuckVehicles() {
        vehicles.forEach(Vehicle::stuck);
    }

    public void initiate(Graph graph) {
        throw new UnsupportedOperationException("unsupported operation");
    }

    public void storage() {
        throw new UnsupportedOperationException("unsupported operation");
    }

    public void iterate() {
        throw new UnsupportedOperationException("unsupported operation");
    }

    public static String getRecord() {
        return buffer.toString();
    }
}
