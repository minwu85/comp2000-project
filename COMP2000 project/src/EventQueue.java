import java.util.PriorityQueue;

// A queue of simulation events that always returns the earliest-due one
// first. "T extends SimulationEvent" means only real events can be added,
// while still letting different event subclasses share one queue.
public class EventQueue<T extends SimulationEvent> {

    private final PriorityQueue<T> events = new PriorityQueue<>();

    public void add(T event){
        events.add(event);
    }

    // True if the earliest queued event is due by the given step.
    public boolean hasDueEvent(long step){
        return !events.isEmpty() && events.peek().getStep() <= step;
    }

    public T nextEvent(){
        return events.poll();
    }
}
