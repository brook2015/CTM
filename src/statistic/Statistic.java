package statistic;

import rw.FileOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by yaokaibin on 16-2-23.
 */
public class Statistic {
    private Set<Integer> locations;
    private List<Record> records;

    public Statistic(String path) {
        List<String> lines = FileOperation.readFileToList(new File(path));
        assert lines != null;
        int size = lines.size();
        records = new ArrayList<>(size - 1);
        for (int i = 1; i < size; i++) {
            records.add(stringToRecord(lines.get(i)));
        }
        locations = records.stream()
                .map(Record::getLocation)
                .collect(Collectors.toCollection(TreeSet<Integer>::new));
    }

    private Record stringToRecord(String input) {
        String[] strings = input.split(",");
        if (strings.length != 5) {
            throw new IllegalArgumentException("illegal argument: " + input);
        }
        int time = Integer.parseInt(strings[0]);
        int link = Integer.parseInt(strings[1]);
        int index = Integer.parseInt(strings[2]);
        int location = Integer.parseInt(strings[3]);
        double dwell = Double.parseDouble(strings[4]);
        return new Record(time, link, index, location, dwell);
    }

    public static <R, T> Map<R, List<T>> group(Collection<T> source, Function<T, R> function) {
        return source.stream().collect(Collectors.groupingBy(function));
    }

    public String statistic() {
        StringBuilder builder = new StringBuilder("time,location,size\n");
        Map<Integer, List<Record>> groupByTime = group(records, Record::getTime);
        for (Map.Entry<Integer, List<Record>> timeGroup : groupByTime.entrySet()) {
            int time = timeGroup.getKey();
            Map<Integer, List<Record>> groupByLocation = group(timeGroup.getValue(), Record::getLocation);
            for (Map.Entry<Integer, List<Record>> locationGroup : groupByLocation.entrySet()) {
                int location = locationGroup.getKey();
                int size = 0;
                if (locations.contains(location)) {
                    size = locationGroup.getValue().size();
                }
                String line = time + "," + location + "," + size + "\n";
                builder.append(line);
            }
        }
        return builder.toString();
    }

    private static class Record {
        private final int time;
        private final int link;
        private final int index;
        private final int location;
        private final double dwell;

        public Record(int time, int link, int index, int location, double dwell) {
            this.time = time;
            this.link = link;
            this.index = index;
            this.location = location;
            this.dwell = dwell;
        }

        public int getTime() {
            return time;
        }

        public int getLink() {
            return link;
        }

        public int getIndex() {
            return index;
        }

        public int getLocation() {
            return location;
        }

        public double getDwell() {
            return dwell;
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("invalid inputs");
        }
        Statistic statistic = new Statistic(args[0]);
        boolean status = FileOperation.writeFile(
                statistic.statistic(),
                "utf-8",
                new File(args[1]));
        System.out.println("status: " + (status ? "ok" : "fail"));
    }
}
