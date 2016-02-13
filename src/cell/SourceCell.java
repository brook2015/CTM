package cell;

import connector.Connector;
import graph.Graph;
import vehicle.VehicleFactory;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class SourceCell extends Cell {
    private Connector out;
    private static VehicleFactory factory;

    public SourceCell(int id, int link, int volume, int maxFlow, double delta) {
        super(id, link, volume, maxFlow, delta);
    }

    @Override
    public void initiate(Graph graph) {
        if (graph == null) {
            throw new NullPointerException("null graph");
        }
        out = graph.getOutConnector(this);
        if (factory == null) {
            factory = new VehicleFactory(graph.getRouteFinder());
        }
    }

    @Override
    public void storage() {
        addVehicles2Cell();
        out.storage();
    }

    @Override
    public void iterate() {
        time++;
        out.removeFromHead();
    }

    private void addVehicles2Cell() {
        if (factory == null) {
            throw new NullPointerException("null factory");
        }
        addVehicles(factory.produce(this, addition()));
    }

    private int addition() {
        return maxFlow;
    }

    @Override
    public String toString() {
        return "source " + id;
    }
}
