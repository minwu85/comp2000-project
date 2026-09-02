import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.Timer;

public class Time {

    int ticks = 0; // 1 real second = 5 ticks = 1 simulated second

    // The simulated clock counts up from this fixed moment (a Monday morning).
    // It is NOT the real wall-clock time; it only moves while the sim runs.
    private static final LocalDateTime START = LocalDateTime.of(2029, 9, 1, 6, 0, 0);

    private static final DateTimeFormatter CLOCK_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH); // e.g. 06:00:00 AM
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH); // Monday, 1 September 2029

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

    // Simulated date-time: one simulated second per real second (ticks / 5).
    private LocalDateTime simNow() {
        return START.plusSeconds(ticks / 5);
    }

    // 12-hour clock with AM/PM, e.g. "06:00:00 AM". Changes once per simulated second.
    public String getClockText() {
        return simNow().format(CLOCK_FORMAT);
    }

    // Weekday, day, month and year, e.g. "Monday, 1 September 2029".
    public String getDateText() {
        return simNow().format(DATE_FORMAT);
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
