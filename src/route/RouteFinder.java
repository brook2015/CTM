package route;

import cell.Cell;
import connector.Link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by yaokaibin on 16-2-10.
 */
public class RouteFinder {
    private final static Random randomIndex = new Random();
    private final Map<Cell, List<Link>> graph;

    public RouteFinder(List<Link> links) {
        if (links == null) {
            throw new NullPointerException("null links");
        }
        if (links.isEmpty()) {
            throw new IllegalArgumentException("invalid links");
        }
        graph = new HashMap<>();
        for (Link link : links) {
            Cell head = link.getHead();
            if (!graph.containsKey(head)) {
                graph.put(head, new ArrayList<>(1));
            }
            graph.get(head).add(link);
        }
    }

    public Route find(Cell origin) {
        if (!graph.containsKey(origin)) {
            throw new IllegalArgumentException("invalid origin " + origin);
        }
        List<Integer> sequence = new ArrayList<>();
        Cell current = origin;
        sequence.add(current.getId());
        do {
            List<Link> linked = graph.get(current);
            int index = randomIndex.nextInt(linked.size());
            current = linked.get(index).getTail();
            sequence.add(current.getId());
        } while (graph.containsKey(current));
        return new Route(sequence);
    }
}
