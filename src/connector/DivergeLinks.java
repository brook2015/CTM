package connector;

import cell.Cell;

import java.util.List;

/**
 * Created by yaokaibin on 16-2-11.
 */
public class DivergeLinks implements Connector {
    private final Cell head;
    private final List<Link> links;

    public DivergeLinks(List<Link> links) {
        if (links == null) {
            throw new NullPointerException("null links");
        }
        if (links.isEmpty()) {
            throw new IllegalArgumentException("invalid links");
        }
        Cell head = links.get(0).getHead();
        boolean isValid = links.stream().allMatch(link -> head == link.getHead());
        if (!isValid) {
            throw new IllegalArgumentException("invalid links");
        }
        this.head = head;
        this.links = links;
    }

    @Override
    public void storage() {
        for (Link link : links) {
            int receive = link.getTail().getMaxReceive();
            int id = link.getTail().getId();
            link.setVehicles(head.getVehicles(receive, id));
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
}
