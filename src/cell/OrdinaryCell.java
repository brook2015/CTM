package cell;

import connector.Connector;
import graph.Graph;
import vehicle.Vehicle;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class OrdinaryCell extends Cell {
    private Connector in;
    private Connector out;

    public OrdinaryCell(int id, int link, int volume, int maxFlow, double delta) {
        super(id, link, volume, maxFlow, delta);
    }

    @Override
    public void initiate(Graph graph) {
        if (graph == null) {
            throw new NullPointerException("null graph");
        }
        in = graph.getInConnector(this);
        out = graph.getOutConnector(this);
    }

    @Override
    public void storage() {
        out.storage();
    }

    @Override
    public void iterate() {
        time++;
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
