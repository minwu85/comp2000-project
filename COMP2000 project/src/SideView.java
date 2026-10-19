import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.Optional;

// One of the two left-hand views (State pattern). SidePanel holds exactly one
// at a time and passes every draw and mouse call to it, so switching view
// changes behaviour without any "if table is open" checks in SidePanel.
public interface SideView {

    int MINUTES_BETWEEN_STOPS = 3;

    Map<String, Color> LINE_COLOURS = Map.of(
            "T1", new Color(255, 0, 0),    // Red Line
            "T2", new Color(0, 0, 255),    // Blue Line
            "T3", new Color(191, 0, 255),  // Purple Line
            "T4", new Color(0, 160, 0));   // Green Line

    void draw(Graphics g, int panelHeight, Vehicles[] trains, Time time);

    // x of the view's right edge; the tabs sit just outside it.
    int rightEdge();

    // Only the train list scrolls, so the other view keeps these no-ops.
    default boolean setPointer(int x, int y) {
        return false;
    }

    default boolean isOverScrollbar(int x, int y) {
        return false;
    }

    default void dragScrollTo(int y) {
    }

    default void scrollBy(int amount) {
    }

    default void setThumbDragging(boolean dragging) {
    }

    // The colour a train shares with its line on the map.
    static Color lineColour(String trainName) {
        return LINE_COLOURS.getOrDefault(trainName, Color.gray);
    }

    // "-" when the train has no next stop.
    static String nextStopName(Vehicles train) {
        return Optional.ofNullable(train.getNextStop()).map(Stops::getName).orElse("-");
    }
}
