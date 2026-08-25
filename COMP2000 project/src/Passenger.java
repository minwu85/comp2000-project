import java.util.ArrayList;

public class Passenger {
    // Walks a passenger from start to end, one stop at a time along a route.
    // Sets a "reached destination" message once curStop equals end.

    private String name;
    private Stops start;
    private Stops end;
    private Stops curStop;
    private String message;

    public Passenger(String name, Stops start, Stops end) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.curStop = start;
    }

    public boolean checkStop() {
        return curStop.equals(end);
    }

    // Moves one stop closer to the destination on the given route.
    // Returns true once the passenger has arrived (including if they were
    // already there), false if there's still more of the route to go.
    public boolean moveToNextStop(Routes route) {
        if (checkStop()) {
            message = name + " reached destination";
            return true;
        }

        Stops next = route.getNextTowards(curStop, end);
        if (next != null) {
            curStop = next;
        }

        if (checkStop()) {
            message = name + " reached destination";
            return true;
        }
        return false;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public Stops getCurStop() {
        return curStop;
    }

    public Stops getStart() {
        return start;
    }

    public Stops getEnd() {
        return end;
    }

    // Example passenger commuting between East1 and Percy Port.
    public static Passenger pass1() {
        return new Passenger("pass1", Stops.Jubilee, Stops.PercyPort);
    }

    // Boards this passenger when curStop matches start, disembarks at end.
    public void checkBoarding(Stops curStop, ArrayList<Passenger> onBoard) {
        if (curStop.equals(start) && !onBoard.contains(this)) {
            onBoard.add(this);
        } else if (curStop.equals(end) && onBoard.contains(this)) {
            onBoard.remove(this);
        }
    }
}
