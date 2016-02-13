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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class RoadNet implements Graph {
    private final List<Link> links;
    private final List<Cell> cells;

    public RoadNet(String linkPath, String cellPath) {
        links = new ArrayList<>();
        cells = new ArrayList<>();
        loadCells(cellPath);
        loadLinks(linkPath);
        initiateCells();
    }

    @SuppressWarnings("resource")
    private void loadCells(String path) {
        Scanner scanner = null;
        try {
            File file = new File(path);
            scanner = new Scanner(file).useDelimiter("\\n|,");
            int count = scanner.nextInt();
            for (int i = 0; i < count; i++) {
                int id = scanner.nextInt();
                int type = scanner.nextInt();
                int link = scanner.nextInt();
                int volume = scanner.nextInt();
                int maxFlow = scanner.nextInt();
                double delta = scanner.nextDouble();
                Cell cell;
                if (type == 1) {
                    cell = new SourceCellBuilder().setId(id).setLink(link)
                            .setVolume(volume).setMaxFlow(maxFlow)
                            .setDelta(delta)
                            .createSourceCell();
                } else if (type == 2) {
                    cell = new OrdinaryCellBuilder().setId(id).setLink(link)
                            .setVolume(volume).setMaxFlow(maxFlow)
                            .setDelta(delta)
                            .createOrdinaryCell();
                } else if (type == 3) {
                    cell = new SinkCellBuilder().setId(id).setLink(link)
                            .setVolume(volume).setMaxFlow(maxFlow)
                            .setDelta(delta)
                            .createSinkCell();
                } else {
                    throw new IllegalStateException("invalid cell type");
                }
                cells.add(cell);
            }
        } catch (FileNotFoundException | IllegalStateException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    @SuppressWarnings("resource")
    private void loadLinks(String path) {
        Scanner scanner = null;
        try {
            File file = new File(path);
            scanner = new Scanner(file).useDelimiter("\\n|,");
            int count = scanner.nextInt();
            for (int i = 0; i < count; i++) {
                int headId = scanner.nextInt();
                int tailId = scanner.nextInt();
                Cell head = getCellByIndex(headId);
                Cell tail = getCellByIndex(tailId);
                if (head == null || tail == null) {
                    throw new NullPointerException("null head or tail");
                }
                Link link = new Link(head, tail);
                links.add(link);
            }
        } catch (FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private Cell getCellByIndex(int id) {
        for (Cell cell : cells) {
            if (id == cell.id()) {
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
            throw new IllegalArgumentException("invalid links");
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
            throw new IllegalArgumentException("invalid links");
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
