package cell;

import java.util.TreeMap;

public class SourceCellBuilder {
    private int id;
    private int link;
    private int volume;
    private double delta;
    private TreeMap<Integer, Integer> flows;

    public SourceCellBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public SourceCellBuilder setLink(int link) {
        this.link = link;
        return this;
    }

    public SourceCellBuilder setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public SourceCellBuilder setFlows(TreeMap<Integer, Integer> flows) {
        if (flows == null) {
            throw new NullPointerException("null flows");
        }
        if (0 != flows.firstKey()) {
            throw new IllegalArgumentException("initial flow must equal to zero.");
        }
        this.flows = flows;
        return this;
    }

    public SourceCellBuilder setDelta(double delta) {
        this.delta = delta;
        return this;
    }

    public SourceCell createSourceCell() {
        return new SourceCell(id, link, volume, delta, flows);
    }
}