package cell;

import connector.Connector;
import graph.Graph;
import vehicle.VehicleFactory;

import java.util.Map;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class SourceCell extends Cell {
    private Connector out;
    private static VehicleFactory factory;

    public SourceCell(int id, int link, int volume, double delta, Map<Integer,Integer> flows) {
        super(id, link, volume, delta, flows);
    }

    @Override
    public void initiate(Graph graph) {
        if (graph == null) {
            throw new NullPointerException("null graph");
        }
        if (factory == null) {
            factory = new VehicleFactory(graph.getRouteFinder());
        }
        out = graph.getOutConnector(this);
    }

    @Override
    public void storage() {
        addVehicles(factory.produce(this, addition()));
        out.storage();
    }

    @Override
    public void iterate() {
        super.iterate();
        out.removeFromHead();
    }

    private int addition() {
        return maxFlow;
    }

    @Override
    public String toString() {
        return "source " + id;
    }
}
