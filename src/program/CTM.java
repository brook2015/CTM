package program;

import cell.Cell;
import graph.Graph;
import graph.RoadNet;
import rw.FileOperation;

import java.io.File;
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
        try {
            if (args.length != 4) {
                throw new IllegalArgumentException("invalid arguments length");
            }
            Graph graph = new RoadNet(args[0], args[1]);
            int iterateCount = Integer.parseInt(args[3]);
            CTM ctm = new CTM(iterateCount, graph);
            ctm.start();
            boolean isSuccess = FileOperation.writeFile(ctm.getRecord(),
                    "utf-8", new File(args[2]));
            System.out.println(isSuccess ? "status: ok" : "status: fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}