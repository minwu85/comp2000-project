import java.util.PriorityQueue;

// A queue of simulation events that always returns the earliest-due one
// first. "T extends SimulationEvent" means only real events can be added,
// while still letting different event subclasses share one queue.
//
// A PriorityQueue is not thread-safe. Panel adds the accidents on whichever
// thread builds the window while the Swing timer thread removes them, so every
// method locks the queue (synchronized). hasDueEvent() followed by
// nextEvent() is still two separate calls, which is safe only because a
// single thread (the timer) drains the queue.
public class EventQueue<T extends SimulationEvent> {

    private final PriorityQueue<T> events = new PriorityQueue<>();

    public synchronized void add(T event){
        events.add(event);
    }

    // True if the earliest queued event is due by the given step.
    public synchronized boolean hasDueEvent(long step){
        return !events.isEmpty() && events.peek().getStep() <= step;
    }

    public synchronized T nextEvent(){
        return events.poll();
    }
}
