// Thrown when the simulated clock leaves the allowed service window
// (the trains only run from 06:00 AM to 12:00 PM). It is a checked
// exception on purpose: advance() declares "throws SimulationTimeException"
// so the tick loop is forced to decide what to do when service ends.
public class SimulationTimeException extends Exception {

    public SimulationTimeException(String message) {
        super(message);
    }
}
