package program;

import cell.Cell;
import graph.Graph;
import graph.RoadNet;

import java.util.List;

/**
 * Created by yaokaibin on 16-2-13.
 */
public class CTM {
    private final int iterateCount;
    private final List<Cell> cells;

    public CTM(int iterateCount, Graph graph) {
        this.iterateCount = iterateCount;
        cells = graph.getCells();
    }

    public void iterate() {
        cells.forEach(Cell::storage);
        cells.forEach(Cell::iterate);
    }

    public void start() {
        for (int i = 0; i < iterateCount; i++) {
            iterate();
        }
    }

    public String getRecord() {
        return Cell.getRecord();
    }

    public static void main(String[] args) {
        Graph graph = RoadNet.demo();
        CTM ctm = new CTM(10, graph);
        ctm.start();
        System.out.println(ctm.getRecord());
    }
}

