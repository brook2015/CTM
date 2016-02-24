package cell;

import java.util.TreeMap;

public class OrdinaryCellBuilder {
    private int id;
    private int link;
    private int volume;
    private int initialVehicleCount;
    private double delta;
    private TreeMap<Integer, Integer> flows;

    public OrdinaryCellBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public OrdinaryCellBuilder setLink(int link) {
        this.link = link;
        return this;
    }

    public OrdinaryCellBuilder setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public OrdinaryCellBuilder setFlows(TreeMap<Integer, Integer> flows) {
        if (flows == null) {
            throw new NullPointerException("null flows");
        }
        if (0 != flows.firstKey()) {
            throw new IllegalArgumentException("initial flow must equal to zero.");
        }
        this.flows = flows;
        return this;
    }

    public OrdinaryCellBuilder setDelta(double delta) {
        this.delta = delta;
        return this;
    }

    public OrdinaryCellBuilder setInitialVehicleCount(int count) {
        this.initialVehicleCount = count;
        return this;
    }

    public OrdinaryCell createOrdinaryCell() {
        return new OrdinaryCell(id, link, volume, delta, initialVehicleCount, flows);
    }
}