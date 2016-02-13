package cell;

import vehicle.Vehicle;

import java.util.List;

/**
 * Created by yaokaibin on 16-2-11.
 */
public interface Vertex {
    int id();

    int getMaxSend();

    int getMaxReceive();

    int size();

    void addVehicles(List<Vehicle> vehicles);

    void removeVehicles(List<Vehicle> vehicles);

    List<Vehicle> getVehicles();

    List<Vehicle> getVehicles(int amount);

    List<Vehicle> getVehicles(int amount, int next);

    void storage();

    void iterate();
}
