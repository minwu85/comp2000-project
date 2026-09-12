import java.util.ArrayList;

public class Routes {
    String name;
    ArrayList<Stops> stations;

    public Routes(String name, ArrayList<Stops> stations) {
        this.name = name;
        this.stations = stations;
    }

    // The full red line, running past Percy Port to its actual end.
    public static Routes redLine() {
        ArrayList<Stops> stops = new ArrayList<>();
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
        stops.add(Stops.DaisyHill);
        stops.add(Stops.Reeds);
        stops.add(Stops.PercyPort);
        stops.add(Stops.Bakerville);
        stops.add(Stops.Jubilee);
        stops.add(Stops.Central);
        stops.add(Stops.SherieGrove);
        stops.add(Stops.Prudence);
        stops.add(Stops.TigerBay);
        stops.add(Stops.TrollUponBridge);
        stops.add(Stops.Celeste);
        stops.add(Stops.Hailstone);
        stops.add(Stops.WindyJunction);
        return new Routes("Blue Line", stops);
    }
        public static Routes purpleLine() {
        ArrayList<Stops> stops = new ArrayList<>();
        stops.add(Stops.TownHall);
        stops.add(Stops.Eagleston);
        stops.add(Stops.Rosebury);
        stops.add(Stops.Central);
        stops.add(Stops.SherieGrove);
        stops.add(Stops.Prudence);
        stops.add(Stops.TigerBay);
        stops.add(Stops.TrollUponBridge);
        return new Routes("Purple Line", stops);
    }
    public static Routes greenLine() {
        ArrayList<Stops> stops = new ArrayList<>();
        stops.add(Stops.Central);
        stops.add(Stops.SherieGrove);
        stops.add(Stops.AndiePark);
        stops.add(Stops.MountPresley);
        return new Routes("Green Line", stops);
    }
}
