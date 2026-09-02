import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SidePanel {

    int topHeight = 60;
    int leftWidth = 240;

    int buttonSize = 40;
    int buttonMargin = 20;

    int cardSpacing = 124;   // vertical gap from one train card to the next
    int scrollbarWidth = 10; // width of the left-bar scrollbar

    // Vertical scroll state for the train list.
    int scrollY = 0;         // pixels scrolled down
    int contentHeight = 0;   // total height of all cards (set while drawing)
    int viewHeight = 0;      // visible height of the left bar (set while drawing)

    Color darkBar = new Color(45, 45, 45);
    Color panelBody = new Color(120, 120, 120);
    Color cardBackground = new Color(225, 225, 225);
    Color accentLine = new Color(30, 144, 255);
    Color scrollTrack = new Color(90, 90, 90);
    Color scrollThumb = new Color(205, 205, 205);

    public int getTopHeight() {
        return topHeight;
    }

    public int getLeftWidth() {
        return leftWidth;
    }

    // True if (x, y) is over the fixed top bar or left bar, not the map.
    public boolean isOverPanel(int x, int y, int panelWidth, int panelHeight) {
        if (y < topHeight) {
            return true;
        }
        if (x < leftWidth) {
            return true;
        }
        return false;
    }

    // Draws the top bar (date + clock + pause/continue) and the left bar (one card per train).
    // Always called on an untranslated Graphics, so it stays fixed while the map is dragged.
    // Takes Vehicles[] (not Train[]) so any vehicle subtype can be shown here.
    public void display(Graphics g, int panelWidth, int panelHeight, Time time, Vehicles[] trains) {
        drawTopBar(g, panelWidth, time);
        drawLeftBar(g, panelHeight, trains);
    }

    private void drawTopBar(Graphics g, int panelWidth, Time time) {
        g.setColor(darkBar);
        g.fillRect(0, 0, panelWidth, topHeight);
        g.setColor(accentLine);
        g.fillRect(0, topHeight - 3, panelWidth, 3);

        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.BOLD, 26));
        g.drawString(time.getClockText(), 30, 38);

        // Date sits to the right of the clock, in a smaller font.
        g.setFont(new Font("SansSerif", Font.PLAIN, 15));
        g.drawString(time.getDateText(), 250, 38);

        drawPauseButton(g, panelWidth, time.isRunning());
    }

    private void drawLeftBar(Graphics g, int panelHeight, Vehicles[] trains) {
        int barTop = topHeight;
        viewHeight = panelHeight - barTop;
        contentHeight = trains.length * cardSpacing + 12;
        scrollY = clampScroll(scrollY); // keep valid if the window resized

        g.setColor(panelBody);
        g.fillRect(0, barTop, leftWidth, viewHeight);

        // Clip to the left bar so scrolled cards never paint over the top bar
        // or past the bottom of the window.
        Graphics clipped = g.create();
        clipped.clipRect(0, barTop, leftWidth, viewHeight);
        int cardY = barTop + 12 - scrollY;
        for (Vehicles train : trains) {
            drawTrainCard(clipped, cardY, train);
            cardY += cardSpacing;
        }
        clipped.dispose();

        drawScrollbar(g, barTop);
    }

    // --- Scrolling ------------------------------------------------------------

    private int maxScroll() {
        return Math.max(0, contentHeight - viewHeight);
    }

    private int clampScroll(int value) {
        if (value < 0) return 0;
        if (value > maxScroll()) return maxScroll();
        return value;
    }

    private int thumbHeight() {
        if (contentHeight <= 0) return 30;
        int h = (int) ((long) viewHeight * viewHeight / contentHeight);
        return Math.max(30, Math.min(h, viewHeight));
    }

    // Wheel notch scrolling (positive amount = scroll down).
    public void scrollBy(int amount) {
        scrollY = clampScroll(scrollY + amount);
    }

    // True if (x, y) is on the left-bar scrollbar.
    public boolean isOverScrollbar(int x, int y) {
        return x >= leftWidth - scrollbarWidth && x <= leftWidth && y >= topHeight;
    }

    // Jump the scroll so the thumb centres on mouseY (used while dragging the thumb).
    public void dragScrollTo(int mouseY) {
        int travel = viewHeight - thumbHeight();
        if (travel <= 0) {
            scrollY = 0;
            return;
        }
        int rel = mouseY - topHeight - thumbHeight() / 2;
        scrollY = clampScroll((int) ((long) rel * maxScroll() / travel));
    }

    private void drawScrollbar(Graphics g, int barTop) {
        if (maxScroll() == 0) {
            return; // everything fits, no scrollbar needed
        }
        int trackX = leftWidth - scrollbarWidth;
        g.setColor(scrollTrack);
        g.fillRect(trackX, barTop, scrollbarWidth, viewHeight);

        int thumbH = thumbHeight();
        int thumbY = barTop + (int) ((long) (viewHeight - thumbH) * scrollY / maxScroll());
        g.setColor(scrollThumb);
        g.fillRect(trackX, thumbY, scrollbarWidth, thumbH);
        g.setColor(Color.black);
        g.drawRect(trackX, thumbY, scrollbarWidth - 1, thumbH - 1);
    }

    // Card: a mini version of the on-map train icon, next to its info.
    private void drawTrainCard(Graphics g, int cardY, Vehicles train) {
        int cardX = 10;
        int cardWidth = leftWidth - 20;
        int cardHeight = 112;

        g.setColor(cardBackground);
        g.fillRect(cardX, cardY, cardWidth, cardHeight);
        g.setColor(Color.black);
        g.drawRect(cardX, cardY, cardWidth, cardHeight);

        drawTrainIcon(g, cardX + 10, cardY + 10, 46, train.getName(), lineColour(train.getName()));

        int textX = cardX + 10 + 46 + 10;
        int textY = cardY + 20;

        g.setColor(Color.black);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString(train.getName() + " (" + train.route.name + ")", textX, textY);

        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("\u2022 On train: " + train.getPassengers().size(), textX, textY + 20);
        g.drawString("\u2022 Current: " + train.getCurStop().getName(), textX, textY + 38);

        Stops next = train.getNextStop();
        String nextName = (next == null) ? "-" : next.getName();
        g.drawString("\u2022 Next: " + nextName, textX, textY + 56);
    }

    // Same box/label look as the train on the map, just small and in its line colour.
    private void drawTrainIcon(Graphics g, int x, int y, int size, String label, Color colour) {
        g.setColor(colour);
        g.fillRect(x, y, size, size);
        g.setColor(Color.black);
        g.drawRect(x, y, size, size);

        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString(label, x + 8, y + size / 2 + 5);
    }

    // Matches each train to the colour its line is drawn with on the map.
    private Color lineColour(String trainName) {
        if (trainName.equals("T1")) return new Color(255, 0, 0);   // Red Line
        if (trainName.equals("T2")) return new Color(0, 0, 255);   // Blue Line
        if (trainName.equals("T3")) return new Color(191, 0, 255); // Purple Line
        if (trainName.equals("T4")) return new Color(0, 160, 0);   // Green Line
        return Color.gray;
    }

    // True if (mouseX, mouseY) is inside the pause/continue button.
    public boolean isButtonClicked(int mouseX, int mouseY, int panelWidth) {
        int x = buttonX(panelWidth);
        int y = buttonY();
        return mouseX >= x && mouseX <= x + buttonSize && mouseY >= y && mouseY <= y + buttonSize;
    }

    private int buttonX(int panelWidth) {
        return panelWidth - buttonSize - buttonMargin;
    }

    private int buttonY() {
        return (topHeight - buttonSize) / 2;
    }

    // Two bars while running (pause), triangle while paused (continue).
    private void drawPauseButton(Graphics g, int panelWidth, boolean running) {
        int x = buttonX(panelWidth);
        int y = buttonY();

        g.setColor(Color.white);
        g.fillRect(x, y, buttonSize, buttonSize);
        g.setColor(Color.black);
        g.drawRect(x, y, buttonSize, buttonSize);

        if (running) {
            g.fillRect(x + 10, y + 8, 7, buttonSize - 16);
            g.fillRect(x + buttonSize - 17, y + 8, 7, buttonSize - 16);
        } else {
            int[] xPoints = {x + 12, x + 12, x + buttonSize - 12};
            int[] yPoints = {y + 8, y + buttonSize - 8, y + buttonSize / 2};
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }
}