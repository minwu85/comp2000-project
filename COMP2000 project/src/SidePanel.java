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

    // Draws the top bar (clock + pause/continue) and the left bar (train card)
    // Always called on an untranslated Graphics, so it stays fixed while the map is dragged
    public void display(Graphics g, int panelWidth, int panelHeight, Time time, int totalPassengers, Vehicles train) {
        drawTopBar(g, panelWidth, time);
        drawLeftBar(g, panelHeight, totalPassengers, train);
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
    private void drawLeftBar(Graphics g, int panelHeight, int totalPassengers, Vehicles train) {
        g.setColor(panelBody);
        g.fillRect(0, topHeight, leftWidth, panelHeight - topHeight);
        //no need, can be replace with other info 
        //g.setColor(Color.white);
        //g.setFont(new Font("SansSerif", Font.BOLD, 16));
        //g.drawString("Passengers: " + totalPassengers, 20, topHeight + 30);

        drawTrainCard(g, topHeight + 45, train);
    }

    // Card: a mini version of the on-map train icon, next to its info.
    private void drawTrainCard(Graphics g, int cardY, Vehicles train) {
        int cardX = 10;
        int cardWidth = leftWidth - 20;
        int cardHeight = 150;

        g.setColor(cardBackground);
        g.fillRect(cardX, cardY, cardWidth, cardHeight);
        g.setColor(Color.black);
        g.drawRect(cardX, cardY, cardWidth, cardHeight);

        drawTrainIcon(g, cardX + 12, cardY + 12, 60, train.getName());

        int textX = cardX + 12 + 60 + 12;
        int textY = cardY + 26;

        g.setColor(Color.black);
        g.setFont(new Font("Times New Roman", Font.BOLD, 14));
        g.drawString(train.getName() + " (" + train.route.name + ")", textX, textY);

        g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        g.drawString("\u2022 On train: " + train.getPassengers().size(), textX, textY + 22);
        g.drawString("\u2022 Current: " + train.getCurStop().getName(), textX, textY + 40);

        Stops next = train.getNextStop();
        String nextName = (next == null) ? "-" : next.getName();
        g.drawString("\u2022 Next: " + nextName, textX, textY + 58);
    }

    // Same red/black look as the train on the map, just small and labelled.
    private void drawTrainIcon(Graphics g, int x, int y, int size, String label) {
        g.setColor(new Color(255, 0, 0));
        g.fillRect(x, y, size, size);
        g.setColor(Color.black);
        g.drawRect(x, y, size, size);

        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.BOLD, 14));
        g.drawString(label, x + 10, y + size / 2 + 5);
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
        // Draw  button
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
