package connector;

import cell.Cell;
import vehicle.Vehicle;

import java.util.List;

/**
 * Created by yaokaibin on 16-2-10.
 */
public class Link implements Connector {
    private final Cell head;
    private final Cell tail;
    private List<Vehicle> vehicles;

    public Link(Cell head, Cell tail) {
        this.head = head;
        this.tail = tail;
    }

    public int amount() {
        int maxSend = head.getMaxSend();
        int maxReceive = tail.getMaxReceive();
        return maxSend < maxReceive ? maxSend : maxReceive;
    }

    public Cell getHead() {
        return head;
    }

    public Cell getTail() {
        return tail;
    }

    public int size() {
        return vehicles == null ? 0 : vehicles.size();
    }

    @Override
    public void storage() {
        vehicles = head.getVehicles(amount());
    }

    @Override
    public void removeFromHead() {
        head.removeVehicles(vehicles);
    }

    @Override
    public void addToTail() {
        vehicles.forEach(Vehicle::move);
        tail.addVehicles(vehicles);
    }

    public void setVehicles(List<Vehicle> vehicles) {
        if (vehicles == null) {
            throw new NullPointerException("null vehicles");
        }
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return head.id() + " --> " + tail.id();
    }
}
