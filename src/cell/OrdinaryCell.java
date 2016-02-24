package cell;

import connector.Connector;
import graph.Graph;
import vehicle.Vehicle;
import vehicle.VehicleFactory;

import java.util.Map;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class OrdinaryCell extends Cell {
    private Connector in;
    private Connector out;
    private final int initialVehicleCount;
    private static VehicleFactory factory;

    public OrdinaryCell(int id, int link, int volume, double delta,
                        int initialVehicleCount, Map<Integer, Integer> flows) {
        super(id, link, volume, delta, flows);
        this.initialVehicleCount = initialVehicleCount;
    }

    @Override
    public void initiate(Graph graph) {
        if (graph == null) {
            throw new NullPointerException("null graph");
        }
        if (factory == null) {
            factory = new VehicleFactory(graph.getRouteFinder());
        }
        in = graph.getInConnector(this);
        out = graph.getOutConnector(this);
        addVehicles(factory.produce(this, initialVehicleCount));
    }

    @Override
    public void storage() {
        out.storage();
    }

    @Override
    public void iterate() {
        super.iterate();
        out.removeFromHead();
        stuckVehicles();
        in.addToTail();
        for (Vehicle vehicle : vehicles) {
            String record = time + "," + link + "," + vehicle.getInformation();
            buffer.append(record).append("\n");
        }
    }

    @Override
    public String toString() {
        return "ordinary " + id;
    }
}
