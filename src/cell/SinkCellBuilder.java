package cell;

import graph.Graph;

public class SinkCellBuilder {
    private int id;
    private int link;
    private int volume;
    private int maxFlow;
    private double delta;
    private Graph graph;

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

    public SinkCellBuilder setMaxFlow(int maxFlow) {
        this.maxFlow = maxFlow;
        return this;
    }

    public SinkCellBuilder setDelta(double delta) {
        this.delta = delta;
        return this;
    }

    public SinkCell createSinkCell() {
        return new SinkCell(id, link, volume, maxFlow, delta);
    }
}