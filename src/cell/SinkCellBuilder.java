package cell;

import java.util.TreeMap;

public class SinkCellBuilder {
    private int id;
    private int link;
    private int volume;
    private double delta;
    private TreeMap<Integer, Integer> flows;

    public SinkCellBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public SinkCellBuilder setLink(int link) {
        this.link = link;
        return this;
    }

    public SinkCellBuilder setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public SinkCellBuilder setFlows(TreeMap<Integer, Integer> flows) {
        if (flows == null) {
            throw new NullPointerException("null flows");
        }
        if (0 != flows.firstKey()) {
            throw new IllegalArgumentException("initial flow must equal to zero.");
        }
        this.flows = flows;
        return this;
    }

    public SinkCellBuilder setDelta(double delta) {
        this.delta = delta;
        return this;
    }

    public SinkCell createSinkCell() {
        return new SinkCell(id, link, volume, delta, flows);
    }
}