package test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by yaokaibin on 16-2-22.
 */
public class TreeMapTest {
    private int time;
    private int flow;
    private FlowSequence sequence;

    public TreeMapTest(Map<Integer, Integer> sequence) {
        time = 0;
        this.sequence = new FlowSequence(sequence);
    }

    public void iterate() {
        time++;
        sequence.update();
    }

    public int getFlow() {
        return flow;
    }

    private class FlowSequence {
        private int index;
        private final int size;
        private List<Map.Entry<Integer, Integer>> sequence;

        public FlowSequence(Map<Integer, Integer> sequence) {
            index = 0;
            TreeMap<Integer, Integer> copy = new TreeMap<>(sequence);
            flow = copy.firstEntry().getValue();
            this.sequence = copy.entrySet().stream().collect(Collectors.toList());
            size = this.sequence.size();
        }

        public void update() {
            if (updateOrNot()) {
                index++;
                flow = sequence.get(index).getValue();
            }
        }

        private boolean updateOrNot() {
            return index + 1 < size && time > sequence.get(index + 1).getKey();
        }
    }

    public static void main(String[] args) {
        Map<Integer, Integer> sequence = new TreeMap<>();
        sequence.put(8, 9);
        sequence.put(0, 6);
        sequence.put(7, 16);
        sequence.put(12, 20);
        TreeMapTest test = new TreeMapTest(sequence);
        int iterateCount = 20;
        for (int i = 0; i < iterateCount; i++) {
            test.iterate();
            System.out.print(test.getFlow() + "\t");
        }
    }
}
