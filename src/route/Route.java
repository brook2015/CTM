package route;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaokaibin on 16-2-10.
 */
public class Route {
    private int index;
    private final int size;
    private final int[] sequence;

    public Route(List<Integer> sequence) {
        if (sequence == null) {
            throw new NullPointerException("null sequence");
        }
        if (sequence.isEmpty()) {
            throw new IllegalArgumentException("illegal size of sequence: 0");
        }
        index = 0;
        size = sequence.size();
        this.sequence = new int[size];
        for (int i = 0; i < size; i++) {
            this.sequence[i] = sequence.get(i);
        }
    }

    public int getSize() {
        return size;
    }

    public int current() {
        return sequence[index];
    }

    public int next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("out of bounds");
        }
        return sequence[index + 1];
    }

    public int forward() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("out of bounds");
        }
        return sequence[++index];
    }

    public boolean hasNext() {
        return index < size - 1;
    }

    public int origin() {
        return sequence[0];
    }

    public int destination() {
        return sequence[size - 1];
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder().append(origin());
        for (int i = 1; i < size; i++) {
            buffer.append("->").append(sequence[i]);
        }
        return buffer.toString();
    }

    public static Route demo() {
        ArrayList<Integer> sequence = new ArrayList<>();
        int size = 6;
        for (int i = 0; i < size; i++) {
            sequence.add(i);
        }
        return new Route(sequence);
    }

    public static void main(String[] args) {
        Route route = Route.demo();
        System.out.println(route);
    }
}
