import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import javax.swing.Timer;

public class Time {

    int ticks = 0;          // 1 real second = 5 ticks (still drives the train step)
    long simSeconds = 0;    // simulated seconds elapsed on the clock

    // The simulated clock counts up from this fixed moment (1 Sep 2029, 6am).
    // It is NOT the real wall-clock time; it only moves while the sim runs.
    private static final LocalDateTime START = LocalDateTime.of(2029, 9, 1, 6, 0, 0);

    // Base speed: each real second pushes the clock 5 simulated minutes forward,
    // so it steps 06:00:00 -> 06:05:00 -> 06:10:00 ... like the old MM:SS clock did.
    private static final int STEP_SECONDS = 5 * 60;

    private static final DateTimeFormatter CLOCK_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH); // e.g. 06:05:07 AM
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH); // Monday, 1 September 2029

    private final Random random = new Random();

    Timer timer;

    public Time(ActionListener onTick) {
        timer = new Timer(200, onTick); // fires every 200ms, 5 times a second
    }

    public void advance() {
        ticks = ticks + 1;
        if (ticks % 5 == 0) {
            // One real second finished: jump the clock ~5 minutes, plus a small
            // random wobble so the seconds field looks realistic instead of :00.
            int wobble = random.nextInt(31) - 15; // -15..+15 seconds
            simSeconds = simSeconds + STEP_SECONDS + wobble;
        }
    }

    public boolean isOnSecond() {
        return ticks % 5 == 0;
    }

    // Simulated date-time = start + the seconds accumulated in advance().
    private LocalDateTime simNow() {
        return START.plusSeconds(simSeconds);
    }

    // 12-hour clock with AM/PM, e.g. "06:05:07 AM".
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
