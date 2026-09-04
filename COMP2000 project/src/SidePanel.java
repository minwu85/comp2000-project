import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// Coordinates the fixed UI: the top bar (Time|Day box, pause button) and the
// two left views - SideTrain (list of trains) and SideTable (timetable).
// Two chevron tabs on the right edge switch between the views: "Train curr"
// always sits on top, "Train time table" always sits below it, so their
// positions never swap - only which one is highlighted as active changes.
// While the timetable is open, SideTrain is drawn dark grey.
public class SidePanel {

    int topHeight = 70;
    int leftWidth = 320;   // width of the SideTrain panel
    int tableWidth = 960;  // right edge of the SideTable panel when it is open

    int pauseSize = 56;
    int pauseMargin = 20;

    // Tab sizing: snug by default, a little wider while the mouse hovers over it.
    int tabShortWidth = 100; 
    int tabFullWidth = 135; //mouse over
    int tabHeight = 40;
    int tabPoint = 14;   // length of the pointed tip
    int tabGap = 10;     // vertical gap between the two tabs

    boolean tableOpen = false;
    boolean trainTabHover = false;
    boolean tableTabHover = false;
    boolean pauseHover = false;

    Color darkBar = new Color(45, 45, 45);
    Color accentLine = new Color(30, 144, 255);
    Color boxFill = new Color(245, 245, 245);
    Color buttonFill = new Color(245, 245, 245);
    Color buttonHoverFill = new Color(150, 200, 255);
    Color tabActiveFill = new Color(225, 225, 225);
    Color tabInactiveFill = new Color(90, 90, 90);

    SideTrain sideTrain = new SideTrain(leftWidth, topHeight);
    SideTable sideTable = new SideTable(leftWidth, topHeight, tableWidth);

    public int getTopHeight() {
        return topHeight;
    }

    public int getLeftWidth() {
        return leftWidth;
    }

    public boolean isTableOpen() {
        return tableOpen;
    }

    // True if (x, y) is over the fixed UI rather than the draggable map.
    public boolean isOverPanel(int x, int y, int panelWidth, int panelHeight) {
        if (y < topHeight) {
            return true;
        }
        int leftEdge;
        if (tableOpen) {
            leftEdge = tableWidth;
        } else {
            leftEdge = leftWidth;
        }
        if (x < leftEdge) {
            return true;
        }
        return isOverAnyTab(x, y);
    }

    // --- drawing ----------------------------------------------------------

    public void display(Graphics g, int panelWidth, int panelHeight, Time time, Vehicles[] trains) {
        drawTopBar(g, panelWidth, time);

        if (tableOpen) {
            sideTrain.draw(g, panelHeight, trains, true, time); // dark grey stub
            sideTable.draw(g, panelHeight, trains, time);
        } else {
            sideTrain.draw(g, panelHeight, trains, false, time);
        }

        drawTabs(g);
    }

    private void drawTopBar(Graphics g, int panelWidth, Time time) {
        g.setColor(darkBar);
        g.fillRect(0, 0, panelWidth, topHeight);
        g.setColor(accentLine);
        g.fillRect(0, topHeight - 3, panelWidth, 3);

        // Big box: clock on top, date underneath ("Time | Day").
        int boxX = 12;
        int boxY = 8;
        int boxW = 300;
        int boxH = topHeight - 16;
        g.setColor(boxFill);
        g.fillRect(boxX, boxY, boxW, boxH);
        g.setColor(Color.black);
        g.drawRect(boxX, boxY, boxW, boxH);

        g.setFont(new Font("Times New Roman", Font.BOLD, 24));
        g.drawString(time.getClockText(), boxX + 14, boxY + 26);
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.drawString(time.getDateText(), boxX + 14, boxY + 45);

        drawPauseButton(g, panelWidth, time.isRunning());
    }

    // --- top-bar pause button --------------------------------------------

    private int pauseX(int panelWidth) {
        return panelWidth - pauseSize - pauseMargin;
    }

    private int pauseY() {
        return (topHeight - pauseSize) / 2;
    }

    public boolean isPauseClicked(int mouseX, int mouseY, int panelWidth) {
        return inRect(mouseX, mouseY, pauseX(panelWidth), pauseY(), pauseSize, pauseSize);
    }

    public boolean setButtonHover(int mouseX, int mouseY, int panelWidth) {
        boolean nowHover = isPauseClicked(mouseX, mouseY, panelWidth);
        boolean changed = nowHover != pauseHover;
        pauseHover = nowHover;
        return changed;
    }

    private void drawPauseButton(Graphics g, int panelWidth, boolean running) {
        int x = pauseX(panelWidth);
        int y = pauseY();

        if (pauseHover) {
            g.setColor(buttonHoverFill);
        } else {
            g.setColor(buttonFill);
        }
        g.fillRect(x, y, pauseSize, pauseSize);
        g.setColor(Color.black);
        g.drawRect(x, y, pauseSize, pauseSize);

        int barW = pauseSize / 6;
        if (running) {
            g.fillRect(x + pauseSize / 5, y + 10, barW, pauseSize - 20);
            g.fillRect(x + pauseSize - pauseSize / 5 - barW, y + 10, barW, pauseSize - 20);
        } else {
            int[] xs = {x + 16, x + 16, x + pauseSize - 14};
            int[] ys = {y + 10, y + pauseSize - 10, y + pauseSize / 2};
            g.fillPolygon(xs, ys, 3);
        }
    }

    // --- view-switch tabs -------------------------------------------------

    private int tabX() {
        if (tableOpen) {
            return tableWidth;
        }
        return leftWidth;
    }

    private int trainTabY() {
        return topHeight + 16;
    }

    private int tableTabY() {
        return topHeight + 16 + tabHeight + tabGap;
    }

    private int tabWidth(boolean hover) {
        if (hover) {
            return tabFullWidth;
        }
        return tabShortWidth;
    }

    private void drawTabs(Graphics g) {
        int x = tabX();
        drawTab(g, x, trainTabY(), "Train current", !tableOpen, trainTabHover);
        drawTab(g, x, tableTabY(), "Train table", tableOpen, tableTabHover);
    }

    // Same label at both sizes - only the tab's fill colour (active/inactive)
    // and its width (hovered/not) change; the label text and position stay put.
    private void drawTab(Graphics g, int x, int y, String label, boolean active, boolean hover) {
        int w = tabWidth(hover);
        int[] xs = {x, x + w, x + w + tabPoint, x + w, x};
        int[] ys = {y, y, y + tabHeight / 2, y + tabHeight, y + tabHeight};

        if (active) {
            g.setColor(tabActiveFill);
        } else {
            g.setColor(tabInactiveFill);
        }
        g.fillPolygon(xs, ys, 5);
        g.setColor(Color.black);
        g.drawPolygon(xs, ys, 5);

        if (active) {
            g.setColor(Color.black);
        } else {
            g.setColor(Color.white);
        }
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.drawString(label, x + 10, y + tabHeight / 2 + 5);
    }

    // Generous hit area (as wide as the tab ever gets) so hovering near a
    // short tab both grows it and is easy to click once it is grown.
    private boolean inTab(int mouseX, int mouseY, int tabY) {
        return inRect(mouseX, mouseY, tabX(), tabY, tabFullWidth + tabPoint, tabHeight);
    }

    private boolean isOverAnyTab(int mouseX, int mouseY) {
        return inTab(mouseX, mouseY, trainTabY()) || inTab(mouseX, mouseY, tableTabY());
    }

    // "Train curr" always opens the train list; "Train time table" always
    // opens the timetable - whichever tab you click is the view you get.
    public boolean handleTabClick(int mouseX, int mouseY, int panelWidth) {
        if (inTab(mouseX, mouseY, trainTabY())) {
            tableOpen = false;
            return true;
        }
        if (inTab(mouseX, mouseY, tableTabY())) {
            tableOpen = true;
            return true;
        }
        return false;
    }

    // --- pointer / scroll forwarding --------------------------------------

    public boolean setPointer(int x, int y) {
        boolean changed = sideTrain.setPointer(x, y);

        boolean trainHover = inTab(x, y, trainTabY());
        boolean tableHover = inTab(x, y, tableTabY());
        if (trainHover != trainTabHover) {
            changed = true;
        }
        if (tableHover != tableTabHover) {
            changed = true;
        }
        trainTabHover = trainHover;
        tableTabHover = tableHover;
        return changed;
    }

    public boolean isOverScrollbar(int x, int y) {
        if (tableOpen) {
            return false;
        }
        return sideTrain.isOverScrollbar(x, y);
    }

    public void dragScrollTo(int y) {
        if (!tableOpen) {
            sideTrain.dragScrollTo(y);
        }
    }

    public void scrollBy(int amount) {
        if (!tableOpen) {
            sideTrain.scrollBy(amount);
        }
    }

    public void setThumbDragging(boolean dragging) {
        sideTrain.setThumbDragging(dragging);
    }

    // --- helper -------------------------------------------------------

    private boolean inRect(int px, int py, int x, int y, int w, int h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }
}
