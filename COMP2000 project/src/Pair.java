// A generic pair of two values that can be different types, e.g. a label and
// its value. T and U are type parameters: Pair<String, String> makes both
// getFirst() and getSecond() return String, Pair<String, Integer> makes
// getSecond() return an Integer, and so on - the compiler checks this for us.
public class Pair<T, U> {

    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}
