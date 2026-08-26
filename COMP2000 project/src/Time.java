import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Time {

    int ticks = 0; // 1 real second = 5 ticks
    Timer timer;

    int topPanelHeight = 60;
    int buttonSize = 40;
    int buttonMargin = 20;

    public Time(ActionListener onTick) {
        timer = new Timer(200, onTick); // fires every 200ms, 5 times a second
    }

    public void advance() {
        ticks = ticks + 1;
    }

    public boolean isOnSecond() {
        return ticks % 5 == 0;
    }

    public String getClockText() {
        int displayTicks = (ticks / 5) * 5; // only changes once per real second
        int minutes = displayTicks / 60;
        int seconds = displayTicks % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // Space toggles between running and paused.
    public void toggle() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    
    public int getTopPanelHeight() {
        return topPanelHeight;
    }

    // Draws the top panel: background bar, clock, and pause/continue button.
    public void display(Graphics g, int panelWidth) {
        g.setColor(new Color(230, 230, 230));
        g.fillRect(0, 0, panelWidth, topPanelHeight);
        g.setColor(Color.black);
        g.drawRect(0, 0, panelWidth, topPanelHeight);

        g.setFont(new Font("Times New Roman", Font.BOLD, 28));
        g.drawString(getClockText(), 30, 40);


        displayPauseButton(g, panelWidth);
    }

    // True if (mouseX, mouseY) is inside the pause/continue button.
    public boolean isButtonClicked(int mouseX, int mouseY, int panelWidth) {
        int x = buttonX(panelWidth);
        int y = buttonY();


        return mouseX >= x && mouseX <= x + buttonSize
         && mouseY >= y && mouseY <= y + buttonSize;
    }

    private int buttonX(int panelWidth) {
        return panelWidth - buttonSize - buttonMargin;
    }

    private int buttonY() {
        return (topPanelHeight - buttonSize) / 2;
    }


    // Two bars while running (pause), triangle while paused (continue).
    private void displayPauseButton(Graphics g, int panelWidth) {
        int x = buttonX(panelWidth);
        int y = buttonY();

        g.setColor(Color.white);
        g.fillRect(x, y, buttonSize, buttonSize);
        g.setColor(Color.black);
        g.drawRect(x, y, buttonSize, buttonSize);



        if (isRunning()) {
            g.fillRect(x + 10, y + 8, 7, buttonSize - 16);
            g.fillRect(x + buttonSize - 17, y + 8, 7, buttonSize - 16);
        } else {
            int[] xPoints = {x + 12, x + 12, x + buttonSize - 12};
            int[] yPoints = {y + 8, y + buttonSize - 8, y + buttonSize / 2};
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }



}
