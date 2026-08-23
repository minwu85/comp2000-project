import java.util.ArrayList;

public class Routes {
    String name;
    ArrayList<Stops> stations;

    public Routes(String name, ArrayList<Stops> stations) {
        this.name = name;
        this.stations = stations;
    }

    // Builds the example line without the old self-constructing field bug.
    public static Routes line1() {
        ArrayList<Stops> firstLine = new ArrayList<>();
        firstLine.add(Stops.Central);
        firstLine.add(Stops.East1);
        firstLine.add(Stops.East2);
        firstLine.add(Stops.PercyPort);
        return new Routes("Line1", firstLine);
    }

    // Next stop walking from current towards destination, either direction. Null if arrived or not on route.
    public Stops getNextTowards(Stops current, Stops destination) {
        int curIndex = stations.indexOf(current);
        int destIndex = stations.indexOf(destination);
        if (curIndex == -1 || destIndex == -1 || curIndex == destIndex) {
            return null;
        }
        int step;
        if (destIndex > curIndex) {
            step = 1;
        } else {
            step = -1;
        }
        return stations.get(curIndex + step);
    }

    // Bounded generic: works for Stops or any subclass.
    public static <T extends Stops> boolean isOnRoute(Routes route, T stop) {
        return route.stations.contains(stop);
    }
}
