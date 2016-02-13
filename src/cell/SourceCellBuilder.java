package cell;

import graph.Graph;

public class SourceCellBuilder {
    private int id;
    private int link;
    private int volume;
    private int maxFlow;
    private double delta;

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

    public SourceCellBuilder setMaxFlow(int maxFlow) {
        this.maxFlow = maxFlow;
        return this;
    }

    public SourceCellBuilder setDelta(double delta) {
        this.delta = delta;
        return this;
    }

    public SourceCell createSourceCell() {
        return new SourceCell(id, link, volume, maxFlow, delta);
    }
}