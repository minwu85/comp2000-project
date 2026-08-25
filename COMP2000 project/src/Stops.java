import java.util.Objects;

public class Stops {
    int x;
    int y;
    String name;
    int capacity;
    int totalComuters = 0;

    public Stops(int x, int y, String name, int capacity) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.capacity = capacity;
    }

    // Named stops with pixel positions matching where Panel draws them.
    public static final Stops Central= new Stops(640, 360, "Central", 100);
    public static final Stops Jubilee = new Stops(740, 360, "East1", 50);
    public static final Stops Bakerville = new Stops(840, 360, "East2", 50);
    public static final Stops PercyPort = new Stops(940, 360, "Percy Port", 50);
    public static final Stops Merrybrook = new Stops(1040, 460, "East3", 50);
    public static final Stops Brookchester = new Stops(1040, 560, "East4", 50);

    public String getName() {
        return name;
    }

    public int checkCapacity() {
        int boardingTime = 0;
        if (totalComuters >= capacity / 3) {
            boardingTime = 5; // a busy stop takes a little longer to board
        }
        return boardingTime;
    }

    // Same name means same station, so curStop.equals(end) works reliably.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Stops)) return false;
        return name.equals(((Stops) obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
