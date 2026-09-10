import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import javax.swing.Timer;

public class Time {

    int ticks = 0;          // 1 real second = 5 ticks (still drives the train step)
    long simSeconds = 0;    // simulated seconds elapsed on the clock
    private boolean serviceEnded = false;

    // The simulated clock counts up from this fixed moment (1 Sep 2029, 6am).
    // It is NOT the real wall-clock time; it only moves while the sim runs.
    private static final LocalDateTime START = LocalDateTime.of(2029, 9, 1, 6, 0, 0);

    // The trains only run 06:00 AM -> 12:00 PM. Past this the service is over.
    private static final LocalDateTime END = LocalDateTime.of(2029, 9, 1, 12, 0, 0);
    private static final long SERVICE_LENGTH_SECONDS = Duration.between(START, END).getSeconds();

    // Base speed: each real second pushes the clock 5 simulated minutes forward,
    // so it steps 06:00:00 -> 06:05:00 -> 06:10:00 ... like the old MM:SS clock did.
    private static final int STEP_SECONDS = 5 * 60;

    private static final DateTimeFormatter CLOCK_FORMAT =
    DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH); // e.g. 06:05:07 AM
    private static final DateTimeFormatter SHORT_CLOCK_FORMAT =
    DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH); // e.g. 06:05 AM
    private static final DateTimeFormatter DATE_FORMAT =
    DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH); // Monday, 1 September 2029

    private final Random random = new Random();

    Timer timer;

    public Time(ActionListener onTick) {
        timer = new Timer(200, onTick); // fires every 200ms, 5 times a second
    }

    // Advances the simulated clock. Throws when the clock leaves the
    // 06:00 AM - 12:00 PM service window, so the caller (the tick loop) has
    // to handle the end of service.
    public void advance() throws SimulationTimeException {
        if (serviceEnded) {
            timer.stop();
            return;
        }

        ticks = ticks + 1;
        if (ticks % 5 == 0) {
            // One real second finished: jump the clock ~5 minutes, plus a small
            // random wobble so the seconds field looks realistic instead of :00.
            int wobble = random.nextInt(31) - 15; // -15..+15 seconds
            simSeconds = simSeconds + STEP_SECONDS + wobble;

            if (simSeconds < 0) {
                // A bad wobble should never rewind past the 06:00 AM start.
                simSeconds = 0;
                throw new SimulationTimeException(
                        "Simulated time went before the 06:00 AM start of service.");
            }
            if (simNow().isAfter(END)) {
                simSeconds = SERVICE_LENGTH_SECONDS; // freeze exactly on 12:00 PM
                serviceEnded = true;
                timer.stop();
                throw new SimulationTimeException(
                        "Service ended at " + getClockText()
                        + " - trains only run 06:00 AM to 12:00 PM.");
            }
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

    // The simulated clock a given number of minutes ahead, e.g. "06:08 AM".
    // Used by the timetable to estimate stop times.
    public String clockAt(int minutesAhead) {
        return simNow().plusMinutes(minutesAhead).format(SHORT_CLOCK_FORMAT);
    }

    // Space (or clicking the button) toggles between running and paused.
    public void toggle() {
        if (serviceEnded) {
            return; // service is over for the day, nothing to start
        }
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    public boolean isEnded() {
        return serviceEnded;
    }
}
