import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SidePanel {

    int topHeight = 60;
    int leftWidth = 200;

    int buttonSize = 40;
    int buttonMargin = 20;

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

    // Draws the top bar (clock + pause/continue) and the left bar (passenger counts).
    // Always called on an untranslated Graphics, so it stays fixed while the map is dragged.
    public void display(Graphics g, int panelWidth, int panelHeight, Time time, int passengerCount, int onTrainCount) {
        drawTopBar(g, panelWidth, time);
        drawLeftBar(g, panelHeight, passengerCount, onTrainCount);
    }

    private void drawTopBar(Graphics g, int panelWidth, Time time) {
        g.setColor(new Color(230, 230, 230));
        g.fillRect(0, 0, panelWidth, topHeight);
        g.setColor(Color.black);
        g.drawRect(0, 0, panelWidth, topHeight);

        g.setFont(new Font("Times New Roman", Font.BOLD, 28));
        g.drawString(time.getClockText(), 30, 40);

        drawPauseButton(g, panelWidth, time.isRunning());
    }

    private void drawLeftBar(Graphics g, int panelHeight, int passengerCount, int onTrainCount) {
        g.setColor(new Color(230, 230, 230));
        g.fillRect(0, topHeight, leftWidth, panelHeight - topHeight);
        g.setColor(Color.black);
        g.drawRect(0, topHeight, leftWidth, panelHeight - topHeight);

        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Passengers: " + passengerCount, 40, 190);
        g.drawString("On Train: " + onTrainCount, 40, 210);
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
