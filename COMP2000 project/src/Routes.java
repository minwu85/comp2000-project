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
        firstLine.add(Stops.TownHall);
        firstLine.add(Stops.Eagleston);
        firstLine.add(Stops.Rosebury); 
        firstLine.add(Stops.Central);
        firstLine.add(Stops.Jubilee);
        firstLine.add(Stops.Bakerville);
        firstLine.add(Stops.PercyPort);
        return new Routes("Line1", firstLine);
    }

    // The full red line, running past Percy Port to its actual end.
    public static Routes redLine() {
        ArrayList<Stops> stops = new ArrayList<>();
        //stops.add(Stops.blank);
        //stops.add(Stops.blank);
        //stops.add(Stops.template);
        stops.add(Stops.TownHall);
        stops.add(Stops.Eagleston);
        stops.add(Stops.Rosebury);
        stops.add(Stops.Central);
        stops.add(Stops.Jubilee);
        stops.add(Stops.Bakerville);
        stops.add(Stops.PercyPort);
        stops.add(Stops.Merrybrook);
        stops.add(Stops.Brookchester);
        return new Routes("Red Line", stops);
    }
    public static Routes blueLine() {
        ArrayList<Stops> stops = new ArrayList<>();
        stops.add(Stops.SunsetPoint);
        //stops.add(Stops.DaisyHill);
        //stops.add(Stops.Reeds);
        stops.add(Stops.PercyPort);
        stops.add(Stops.Bakerville);
        stops.add(Stops.Jubilee);
        stops.add(Stops.Central);
        //stops.add(Stops.SherieGrove);
        //stops.add(Stops.TigerBay);
        //stops.add(Stops.Troll-Upon-Bridge);
        //stops.add(Stops.Celeste);
        //stops.add(Stops.Hailstone);
        //stops.add(Stops.WindyJunction);

        return new Routes("Blue Line", stops);
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
