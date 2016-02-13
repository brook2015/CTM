package cell;

public class OrdinaryCellBuilder {
    private int id;
    private int link;
    private int volume;
    private int maxFlow;
    private double delta;

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

    public OrdinaryCellBuilder setMaxFlow(int maxFlow) {
        this.maxFlow = maxFlow;
        return this;
    }

    public OrdinaryCellBuilder setDelta(double delta) {
        this.delta = delta;
        return this;
    }

    public OrdinaryCell createOrdinaryCell() {
        return new OrdinaryCell(id, link, volume, maxFlow, delta);
    }
}