import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SidePanel {

    int topHeight = 60;
    int leftWidth = 240;

    int buttonSize = 40;
    int buttonMargin = 20;

    Color darkBar = new Color(45, 45, 45);
    Color panelBody = new Color(120, 120, 120);
    Color cardBackground = new Color(225, 225, 225);
    Color accentLine = new Color(30, 144, 255);

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

    // Draws the top bar (clock + pause/continue) and the left bar (one card per train).
    // Always called on an untranslated Graphics, so it stays fixed while the map is dragged.
    // Takes Vehicles[] (not Train[]) so any vehicle subtype can be shown here.
    public void display(Graphics g, int panelWidth, int panelHeight, Time time, int totalPassengers, Vehicles[] trains) {
        drawTopBar(g, panelWidth, time);
        drawLeftBar(g, panelHeight, totalPassengers, trains);
    }

    private void drawTopBar(Graphics g, int panelWidth, Time time) {
        g.setColor(darkBar);
        g.fillRect(0, 0, panelWidth, topHeight);
        g.setColor(accentLine);
        g.fillRect(0, topHeight - 3, panelWidth, 3);
       
        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.BOLD, 28));
        g.drawString(time.getClockText(), 30, 40);
        
        drawPauseButton(g, panelWidth, time.isRunning());
    }

    private void drawLeftBar(Graphics g, int panelHeight, int totalPassengers, Vehicles[] trains) {
        g.setColor(panelBody);
        g.fillRect(0, topHeight, leftWidth, panelHeight - topHeight);

        // One compact card per train (T1..T4), stacked down the left bar.
        int cardY = topHeight + 12;
        for (Vehicles train : trains) {
            drawTrainCard(g, cardY, train);
            cardY += 124;
        }
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