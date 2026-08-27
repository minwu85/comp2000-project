import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Time {

    int ticks = 0; // 1 real second = 5 ticks
    Timer timer;

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

    // Space (or clicking the button) toggles between running and paused.
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
}
