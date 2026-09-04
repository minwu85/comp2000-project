import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// The wide panel that opens on top of the map to show a simple timetable:
// one card per train with its line, current stop, next stop and rough ETAs,
// plus a footer naming the busiest train. Times come from the Time class.
public class SideTable {

    private final int leftX;   // x where the table content starts (right of the SideTrain stub)
    private final int top;     // y where the panel starts (below the top bar)
    private final int rightX;  // x of the panel's right edge

    private static final int MINUTES_BETWEEN_STOPS = 3;

    Color body = new Color(222, 224, 227);
    Color cardFill = new Color(248, 248, 248);
    Color cardBorder = new Color(90, 90, 90);

    public SideTable(int leftX, int top, int rightX) {
        this.leftX = leftX;
        this.top = top;
        this.rightX = rightX;
    }

    public void draw(Graphics g, int panelHeight, Vehicles[] trains, Time time) {
        int w = rightX - leftX;
        int h = panelHeight - top;

        g.setColor(body);
        g.fillRect(leftX, top, w, h);

        g.setColor(new Color(30, 30, 30));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("Train Time Table", leftX + 24, top + 38);

        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("Estimated times, about " + MINUTES_BETWEEN_STOPS
                + " min between stops. Clock: " + time.getClockText(), leftX + 24, top + 58);

        int cardX = leftX + 20;
        int cardWidth = w - 40;
        int cardHeight = 70;
        int cardGap = 14;
        int cardY = top + 80;

        for (Vehicles train : trains) {
            drawTrainCard(g, cardX, cardY, cardWidth, cardHeight, train, time);
            cardY += cardHeight + cardGap;
        }

        drawBusiestFooter(g, cardX, cardY + 6, trains);
    }

    private void drawTrainCard(Graphics g, int x, int y, int width, int height, Vehicles train, Time time) {
        g.setColor(cardFill);
        g.fillRoundRect(x, y, width, height, 12, 12);
        g.setColor(cardBorder);
        g.drawRoundRect(x, y, width, height, 12, 12);

        // Coloured stripe matching the line on the map.
        g.setColor(SideTrain.lineColour(train.getName()));
        g.fillRoundRect(x, y, 10, height, 12, 12);
        g.fillRect(x + 5, y, 5, height); // square off the stripe's right edge

        Stops next = train.getNextStop();
        String nextName;
        if (next == null) {
            nextName = "-";
        } else {
            nextName = next.getName();
        }

        // Bundle each label + value with a generic Pair before drawing it.
        Pair<String, String> current = new Pair<>("Current", train.getCurStop().getName());
        Pair<String, String> upcoming = new Pair<>("Next", nextName);

        int textX = x + 24;
        g.setColor(Color.black);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.drawString(train.getName() + "  -  " + train.route.name, textX, y + 24);

        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString(current.getFirst() + ": " + current.getSecond()
                + "     Time: " + time.clockAt(0), textX, y + 44);
        g.drawString(upcoming.getFirst() + ": " + upcoming.getSecond()
                + "     ETA: " + time.clockAt(MINUTES_BETWEEN_STOPS), textX, y + 62);
    }

    // Finds the train with the most people aboard using a bounded generic
    // method (GenericUtil.max needs a type that implements Comparable<T>;
    // the int counts are autoboxed to Integer to satisfy that).
    private void drawBusiestFooter(Graphics g, int x, int y, Vehicles[] trains) {
        if (trains.length == 0) {
            return;
        }
        Vehicles busiest = trains[0];
        for (Vehicles train : trains) {
            int busiestCount = busiest.getPassengers().size();
            int trainCount = train.getPassengers().size();
            Integer larger = GenericUtil.max(busiestCount, trainCount);
            if (larger.equals(trainCount)) {
                busiest = train;
            }
        }

        g.setColor(new Color(60, 60, 60));
        g.setFont(new Font("SansSerif", Font.ITALIC, 12));
        g.drawString("Busiest right now: " + busiest.getName()
                + " (" + busiest.getPassengers().size() + " aboard)", x, y + 14);
    }
}
