package cell;

import connector.Connector;
import graph.Graph;
import vehicle.Vehicle;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class SinkCell extends Cell {
    private Connector in;

    public SinkCell(int id, int link, int volume, int maxFlow, double delta) {
        super(id, link, volume, maxFlow, delta);
    }

    @Override
    public void initiate(Graph graph) {
        if (graph == null) {
            throw new NullPointerException("null graph");
        }
        in = graph.getInConnector(this);
    }

    @Override
    public void storage() {

    }

    @Override
    public void iterate() {
        time++;
        stuckVehicles();
        in.addToTail();
        for (Vehicle vehicle : vehicles) {
            String record = time + "," + link + "," + vehicle.getInformation();
            buffer.append(record).append("\n");
        }
    }

    @Override
    public String toString() {
        return "sink " + id;
    }
}
