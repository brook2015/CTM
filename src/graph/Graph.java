package graph;

import cell.Cell;
import connector.Connector;
import connector.Link;
import route.RouteFinder;

import java.util.List;

/**
 * Created by yaokaibin on 16-2-11.
 */
public interface Graph {
    Connector getOutConnector(Cell cell);

    Connector getInConnector(Cell cell);

    List<Cell> getCells();

    List<Link> getLinks();

    RouteFinder getRouteFinder();
}
