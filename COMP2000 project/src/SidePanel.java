import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// Coordinates the fixed UI: the top bar (Time box, HOME button, pause button)
// and the two left views - SideTrain (list of trains) and SideTable (timetable).
// Two chevron handles on the right edge switch between the views. While the
// timetable is open, SideTrain is drawn dark grey.
public class SidePanel {

    int topHeight = 64;
    int leftWidth = 240;   // width of the SideTrain panel
    int tableWidth = 960;  // right edge of the SideTable panel when it is open

    int buttonSize = 40;
    int buttonMargin = 20;
    int buttonGap = 10;

    // Chevron handle size.
    int handleWidth = 150;
    int handleHeight = 40;
    int handlePoint = 14;  // length of the pointed tip

    boolean tableOpen = false;

    // Hover state for the top-bar buttons (gives the "changes colour" effect).
    boolean homeHover = false;
    boolean pauseHover = false;

    Color darkBar = new Color(45, 45, 45);
    Color accentLine = new Color(30, 144, 255);
    Color boxFill = new Color(245, 245, 245);
    Color buttonFill = new Color(245, 245, 245);
    Color buttonHoverFill = new Color(150, 200, 255);
    Color handleActive = new Color(225, 225, 225);
    Color handleInactive = new Color(90, 90, 90);

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
        if (y < topHeight) return true;
        int leftEdge = tableOpen ? tableWidth : leftWidth;
        if (x < leftEdge) return true;
        // The switch handle sticks out past that edge.
        return isOverAnyHandle(x, y);
    }

    // --- drawing ----------------------------------------------------------

    public void display(Graphics g, int panelWidth, int panelHeight, Time time, Vehicles[] trains) {
        drawTopBar(g, panelWidth, time);

        if (tableOpen) {
            sideTrain.draw(g, panelHeight, trains, true); // dark grey stub
            sideTable.draw(g, panelHeight, trains, time);
        } else {
            sideTrain.draw(g, panelHeight, trains, false);
        }

        drawHandles(g);
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
        int boxH = topHeight - 18;
        g.setColor(boxFill);
        g.fillRect(boxX, boxY, boxW, boxH);
        g.setColor(Color.black);
        g.drawRect(boxX, boxY, boxW, boxH);

        g.setFont(new Font("Times New Roman", Font.BOLD, 22));
        g.drawString(time.getClockText(), boxX + 14, boxY + 24);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString(time.getDateText(), boxX + 14, boxY + 42);

        drawHomeButton(g, panelWidth);
        drawPauseButton(g, panelWidth, time.isRunning());
    }

    // --- top-bar buttons ------------------------------------------------

    private int pauseX(int panelWidth) {
        return panelWidth - buttonSize - buttonMargin;
    }

    private int homeX(int panelWidth) {
        return pauseX(panelWidth) - buttonSize - buttonGap;
    }

    private int buttonY() {
        return (topHeight - buttonSize) / 2;
    }

    public boolean isPauseClicked(int mouseX, int mouseY, int panelWidth) {
        return inRect(mouseX, mouseY, pauseX(panelWidth), buttonY(), buttonSize, buttonSize);
    }

    public boolean isHomeClicked(int mouseX, int mouseY, int panelWidth) {
        return inRect(mouseX, mouseY, homeX(panelWidth), buttonY(), buttonSize, buttonSize);
    }

    // Simple house icon. Reserved for later - for now it just hover-highlights.
    private void drawHomeButton(Graphics g, int panelWidth) {
        int x = homeX(panelWidth);
        int y = buttonY();

        g.setColor(homeHover ? buttonHoverFill : buttonFill);
        g.fillRect(x, y, buttonSize, buttonSize);
        g.setColor(Color.black);
        g.drawRect(x, y, buttonSize, buttonSize);

        int[] roofX = {x + 6, x + buttonSize / 2, x + buttonSize - 6};
        int[] roofY = {y + 20, y + 7, y + 20};
        g.setColor(new Color(60, 60, 60));
        g.fillPolygon(roofX, roofY, 3);
        g.fillRect(x + 11, y + 20, buttonSize - 22, 13);
        g.setColor(homeHover ? buttonHoverFill : buttonFill);
        g.fillRect(x + buttonSize / 2 - 3, y + 25, 6, 8); // door
    }

    private void drawPauseButton(Graphics g, int panelWidth, boolean running) {
        int x = pauseX(panelWidth);
        int y = buttonY();

        g.setColor(pauseHover ? buttonHoverFill : buttonFill);
        g.fillRect(x, y, buttonSize, buttonSize);
        g.setColor(Color.black);
        g.drawRect(x, y, buttonSize, buttonSize);

        g.setColor(Color.black);
        if (running) {
            g.fillRect(x + 10, y + 8, 7, buttonSize - 16);
            g.fillRect(x + buttonSize - 17, y + 8, 7, buttonSize - 16);
        } else {
            int[] xs = {x + 12, x + 12, x + buttonSize - 12};
            int[] ys = {y + 8, y + buttonSize - 8, y + buttonSize / 2};
            g.fillPolygon(xs, ys, 3);
        }
    }

    // --- view-switch handles -----------------------------------------

    private int handleRightEdge() {
        return tableOpen ? tableWidth : leftWidth;
    }

    private int topHandleY() {
        return topHeight + 16;
    }

    private int bottomHandleY() {
        return topHeight + 16 + handleHeight + 10;
    }

    private String activeLabel() {
        return tableOpen ? "Train time table" : "Train curr";
    }

    private String otherLabel() {
        return tableOpen ? "Train curr" : "Train time table";
    }

    private void drawHandles(Graphics g) {
        int x = handleRightEdge();
        drawHandle(g, x, topHandleY(), activeLabel(), handleActive, Color.black);
        drawHandle(g, x, bottomHandleY(), otherLabel(), handleInactive, Color.white);
    }

    private void drawHandle(Graphics g, int x, int y, String label, Color fill, Color textColour) {
        int[] xs = {x, x + handleWidth, x + handleWidth + handlePoint, x + handleWidth, x};
        int[] ys = {y, y, y + handleHeight / 2, y + handleHeight, y + handleHeight};
        g.setColor(fill);
        g.fillPolygon(xs, ys, 5);
        g.setColor(Color.black);
        g.drawPolygon(xs, ys, 5);

        g.setColor(textColour);
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.drawString(label, x + 12, y + handleHeight / 2 + 5);
    }

    private boolean inHandle(int mouseX, int mouseY, int handleY) {
        int x = handleRightEdge();
        return inRect(mouseX, mouseY, x, handleY, handleWidth + handlePoint, handleHeight);
    }

    private boolean isOverAnyHandle(int mouseX, int mouseY) {
        return inHandle(mouseX, mouseY, topHandleY()) || inHandle(mouseX, mouseY, bottomHandleY());
    }

    // Handles a click on either handle. Returns true if one was hit.
    // The bottom (inactive) handle switches views.
    public boolean handleTabClick(int mouseX, int mouseY, int panelWidth) {
        if (inHandle(mouseX, mouseY, bottomHandleY())) {
            tableOpen = !tableOpen;
            return true;
        }
        return inHandle(mouseX, mouseY, topHandleY()); // active handle: no-op but consume it
    }

    // --- pointer / scroll forwarding (only meaningful for SideTrain) ----

    public boolean setPointer(int x, int y) {
        boolean changed = false;
        // These need the real panel width; Panel passes screen coords, and the
        // buttons live near the right edge - recompute against a stored width is
        // overkill, so we accept the width via the same call used for clicks.
        // setPointer is called with raw coords; button hover is refreshed in
        // setButtonHover below.
        changed |= sideTrain.setPointer(x, y);
        return changed;
    }

    // Called from Panel's mouseMoved with the panel width so button hover works.
    public boolean setButtonHover(int x, int y, int panelWidth) {
        boolean nh = isHomeClicked(x, y, panelWidth);
        boolean np = isPauseClicked(x, y, panelWidth);
        boolean changed = (nh != homeHover) || (np != pauseHover);
        homeHover = nh;
        pauseHover = np;
        return changed;
    }

    public boolean isOverScrollbar(int x, int y) {
        return !tableOpen && sideTrain.isOverScrollbar(x, y);
    }

    public void dragScrollTo(int y) {
        if (!tableOpen) sideTrain.dragScrollTo(y);
    }

    public void scrollBy(int amount) {
        if (!tableOpen) sideTrain.scrollBy(amount);
    }

    public void setThumbDragging(boolean dragging) {
        sideTrain.setThumbDragging(dragging);
    }

    // --- helper ---------------------------------------------------------

    private boolean inRect(int px, int py, int x, int y, int w, int h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }
}
