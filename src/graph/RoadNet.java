package graph;

import cell.Cell;
import cell.OrdinaryCellBuilder;
import cell.SinkCellBuilder;
import cell.SourceCellBuilder;
import connector.Connector;
import connector.DivergeLinks;
import connector.Link;
import connector.MergeLinks;
import route.RouteFinder;
import rw.FileOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class RoadNet implements Graph {
    private final List<Link> links = new ArrayList<>();
    private final List<Cell> cells = new ArrayList<>();

    public RoadNet(String linkPath, String cellPath) {
        loadCells(cellPath);
        loadLinks(linkPath);
        initiateCells();
    }

    private void loadCells(String path) {
        List<String> source = FileOperation.readFileToList(new File(path));
        assert source != null;
        cells.addAll(source.stream().map(this::parseStringToCell).collect(Collectors.toList()));
    }

    private Cell parseStringToCell(String source) {
        String[] splitStrings = source.split(" ");
        if (splitStrings.length != 7 && splitStrings.length != 6) {
            throw new IllegalArgumentException("invalid arguments: " + source);
        }
        int id = Integer.parseInt(splitStrings[0]);
        int type = Integer.parseInt(splitStrings[1]);
        int link = Integer.parseInt(splitStrings[2]);
        int volume = Integer.parseInt(splitStrings[3]);
        double delta = Double.parseDouble(splitStrings[4]);
        TreeMap<Integer, Integer> flows = stringToTreeMap(splitStrings[splitStrings.length - 1]);
        if (type == 1) {
            return new SourceCellBuilder()
                    .setId(id)
                    .setLink(link)
                    .setVolume(volume)
                    .setFlows(flows)
                    .setDelta(delta)
                    .createSourceCell();
        } else if (type == 2) {
            int initialVehicleCount = Integer.parseInt(splitStrings[5]);
            return new OrdinaryCellBuilder()
                    .setId(id)
                    .setLink(link)
                    .setVolume(volume)
                    .setFlows(flows)
                    .setDelta(delta)
                    .setInitialVehicleCount(initialVehicleCount)
                    .createOrdinaryCell();
        } else if (type == 3) {
            return new SinkCellBuilder()
                    .setId(id)
                    .setLink(link)
                    .setVolume(volume)
                    .setFlows(flows)
                    .setDelta(delta)
                    .createSinkCell();
        } else {
            throw new IllegalStateException("illegal state");
        }
    }

    private TreeMap<Integer, Integer> stringToTreeMap(String source) {
        String[] splitStrings = source.split("\\:|\\/");
        int size = splitStrings.length / 2;
        TreeMap<Integer, Integer> result = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            int time = Integer.parseInt(splitStrings[2 * i]);
            int flow = Integer.parseInt(splitStrings[2 * i + 1]);
            result.put(time, flow);
        }
        return result;
    }

    private void loadLinks(String path) {
        List<String> source = FileOperation.readFileToList(new File(path));
        assert source != null;
        for (String s : source) {
            String[] splitStrings = s.split(" ");
            int headCellId = Integer.parseInt(splitStrings[0]);
            int tailCellId = Integer.parseInt(splitStrings[1]);
            Cell head = getCellByIndex(headCellId);
            Cell tail = getCellByIndex(tailCellId);
            links.add(new Link(head, tail));
        }
    }

    private Cell getCellByIndex(int id) {
        for (Cell cell : cells) {
            if (id == cell.getId()) {
                return cell;
            }
        }
        return null;
    }

    private List<Link> getInLinks(Cell cell) {
        if (cell == null) {
            throw new NullPointerException("null cell");
        }
        return links.stream()
                .filter(link -> cell == link.getTail())
                .collect(Collectors.toList());
    }

    private List<Link> getOutLinks(Cell cell) {
        if (cell == null) {
            throw new NullPointerException("null cell");
        }
        return links.stream()
                .filter(link -> cell == link.getHead())
                .collect(Collectors.toList());
    }

    @Override
    public Connector getInConnector(Cell cell) {
        return links2InConnector(getInLinks(cell));
    }

    @Override
    public Connector getOutConnector(Cell cell) {
        return links2OutConnector(getOutLinks(cell));
    }

    @Override
    public List<Link> getLinks() {
        return links;
    }

    @Override
    public List<Cell> getCells() {
        return cells;
    }

    @Override
    public RouteFinder getRouteFinder() {
        return new RouteFinder(links);
    }

    private static Connector links2InConnector(List<Link> links) {
        if (links == null) {
            throw new NullPointerException("null links");
        } else if (links.isEmpty()) {
            throw new IllegalArgumentException("empty links");
        } else if (1 == links.size()) {
            return links.get(0);
        } else {
            return new MergeLinks(links);
        }
    }

    private static Connector links2OutConnector(List<Link> links) {
        if (links == null) {
            throw new NullPointerException("null links");
        } else if (links.isEmpty()) {
            throw new IllegalArgumentException("empty links");
        } else if (1 == links.size()) {
            return links.get(0);
        } else {
            return new DivergeLinks(links);
        }
    }

    private void initiateCells() {
        for (Cell cell : cells) {
            cell.initiate(this);
        }
    }

    public static RoadNet demo() {
        return new RoadNet("doc/links.txt", "doc/cells.txt");
    }

    public static void main(String[] args) {
        RoadNet net = RoadNet.demo();
        net.getLinks().forEach(System.out::println);
        net.getCells().forEach(System.out::println);
    }
}
