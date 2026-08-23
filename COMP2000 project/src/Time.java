public class Time {

    int ticks = 0; // 1 real second = 5 ticks

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
}
