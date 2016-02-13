package connector;

import cell.Cell;
import graph.Graph;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class MergeLinks implements Connector {
    private final Cell tail;
    private final List<Link> links;
    private final static ToIntFunction<Link> headMaxSend = link -> link.getHead().getMaxSend();
    private final static ToIntFunction<Link> headVehicleCount = link -> link.getHead().size();

    public MergeLinks(List<Link> links) {
        if (links == null) {
            throw new NullPointerException("null links");
        }
        if (links.isEmpty()) {
            throw new IllegalArgumentException("invalid links");
        }
        Cell tail = links.get(0).getTail();
        boolean isValid = links.stream().allMatch(link -> tail == link.getTail());
        if (!isValid) {
            throw new IllegalArgumentException("invalid links");
        }
        this.tail = tail;
        this.links = links;
    }

    @Override
    public void storage() {
        if (headMaxSend() > tail.getMaxReceive()) {
            int amount = headVehicleCount();
            for (Link link : links) {
                int maxSend = link.getHead().getMaxSend();
                int receive = link.getHead().size() * tail.getMaxReceive() / amount;
                int count = maxSend < receive ? maxSend : receive;
                link.setVehicles(link.getHead().getVehicles(count));
            }
        } else {
            for (Link link : links) {
                int maxSend = link.getHead().getMaxSend();
                link.setVehicles(link.getHead().getVehicles(maxSend));
            }
        }
    }

    @Override
    public void removeFromHead() {
        links.forEach(Link::removeFromHead);
    }

    @Override
    public void addToTail() {
        links.forEach(Link::addToTail);
    }

    private int headMaxSend() {
        return links.stream().mapToInt(headMaxSend).sum();
    }

    private int headVehicleCount() {
        return links.stream().mapToInt(headVehicleCount).sum();
    }
}
