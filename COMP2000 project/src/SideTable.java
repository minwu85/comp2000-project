import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// The wide panel that opens on top of the map to show a simple timetable:
// one row per train with its current stop, the time now, and the next stop
// with a rough ETA. Times come from the Time class.
public class SideTable {

    private final int leftX;   // x where the table content starts (right of the dark SideTrain stub)
    private final int top;     // y where the panel starts (below the top bar)
    private final int rightX;  // x of the panel's right edge

    private static final int MINUTES_BETWEEN_STOPS = 3;

    Color body = new Color(220, 220, 220);
    Color headerRow = new Color(190, 190, 190);
    Color gridLine = new Color(170, 170, 170);

    // Column x positions (absolute).
    private final int colTrain;
    private final int colLine;
    private final int colCurrent;
    private final int colTime;
    private final int colNext;
    private final int colEta;

    public SideTable(int leftX, int top, int rightX) {
        this.leftX = leftX;
        this.top = top;
        this.rightX = rightX;

        this.colTrain = leftX + 20;
        this.colLine = leftX + 90;
        this.colCurrent = leftX + 210;
        this.colTime = leftX + 380;
        this.colNext = leftX + 460;
        this.colEta = leftX + 620;
    }

    public void draw(Graphics g, int panelHeight, Vehicles[] trains, Time time) {
        int w = rightX - leftX;
        int h = panelHeight - top;

        g.setColor(body);
        g.fillRect(leftX, top, w, h);
        g.setColor(Color.black);
        g.drawRect(leftX, top, w, h);

        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString("Train Time Table", leftX + 20, top + 34);

        g.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g.drawString("Estimated times, about " + MINUTES_BETWEEN_STOPS
                + " min between stops. Clock: " + time.getClockText(), leftX + 20, top + 54);

        // Header row.
        int headerY = top + 78;
        g.setColor(headerRow);
        g.fillRect(leftX, headerY - 16, w, 24);
        g.setColor(Color.black);
        g.setFont(new Font("SansSerif", Font.BOLD, 12));
        g.drawString("TRAIN", colTrain, headerY);
        g.drawString("LINE", colLine, headerY);
        g.drawString("CURRENT STOP", colCurrent, headerY);
        g.drawString("TIME", colTime, headerY);
        g.drawString("NEXT STOP", colNext, headerY);
        g.drawString("ETA", colEta, headerY);

        // One row per train.
        int rowY = headerY + 40;
        int rowStep = 46;
        for (Vehicles train : trains) {
            drawRow(g, rowY, train, time);
            g.setColor(gridLine);
            g.drawLine(leftX + 12, rowY + 14, rightX - 12, rowY + 14);
            rowY += rowStep;
        }
    }

    private void drawRow(Graphics g, int y, Vehicles train, Time time) {
        // Line-colour swatch next to the train name.
        g.setColor(SideTrain.lineColour(train.getName()));
        g.fillRect(colTrain - 16, y - 11, 11, 11);
        g.setColor(Color.black);
        g.drawRect(colTrain - 16, y - 11, 11, 11);

        Stops next = train.getNextStop();
        String nextName = (next == null) ? "-" : next.getName();

        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString(train.getName(), colTrain, y);

        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.drawString(train.route.name, colLine, y);
        g.drawString(train.getCurStop().getName(), colCurrent, y);
        g.drawString(time.clockAt(0), colTime, y);
        g.drawString(nextName, colNext, y);
        g.drawString(time.clockAt(MINUTES_BETWEEN_STOPS), colEta, y);
    }
}
