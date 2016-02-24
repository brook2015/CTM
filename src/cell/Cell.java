package cell;

import graph.Graph;
import vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by yaokaibin on 16-2-10.
 */
public class Cell {
    protected int time;
    protected int maxFlow;
    protected final int id;
    protected final int link;
    protected final int volume;
    protected final double delta;
    protected final List<Vehicle> vehicles;
    protected final static VehicleComparator comparator = new VehicleComparator();
    protected final static StringBuffer buffer = new StringBuffer("time,link,index,location,dwell\n");
    private FlowSequence flowSequence;

    private class FlowSequence {
        private int index;
        private final int size;
        private final List<Map.Entry<Integer, Integer>> flows;

        public FlowSequence(Map<Integer, Integer> flows) {
            index = 0;
            TreeMap<Integer, Integer> copy = new TreeMap<>(flows);
            maxFlow = copy.firstEntry().getValue();
            this.flows = copy.entrySet().stream().collect(Collectors.toList());
            size = this.flows.size();
        }

        private boolean updateOrNot() {
            return index + 1 < size && time > flows.get(index + 1).getKey();
        }

        public void update() {
            if (updateOrNot()) {
                index++;
                maxFlow = flows.get(index).getValue();
            }
        }
    }

    public Cell(int id, int link, int volume, double delta, Map<Integer, Integer> flows) {
        time = 0;
        this.id = id;
        this.link = link;
        this.volume = volume;
        this.delta = delta;
        vehicles = new ArrayList<>();
        flowSequence = new FlowSequence(flows);
    }

    public int getId() {
        return id;
    }

    public int getLink() {
        return link;
    }

    public int getVolume() {
        return volume;
    }

    public int getMaxFlow() {
        return maxFlow;
    }

    public double getDelta() {
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
        time++;
        flowSequence.update();
    }

    public static String getRecord() {
        return buffer.toString();
    }
}
