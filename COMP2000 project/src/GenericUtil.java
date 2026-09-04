// Small generics helper. "T extends Comparable<T>" is a bounded type
// parameter: it means T can be any type, as long as that type implements
// Comparable<T> - which guarantees a.compareTo(b) exists to call.
public class GenericUtil {

    public static <T extends Comparable<T>> T max(T a, T b) {
        if (a.compareTo(b) > 0) {
            return a;
        }
        return b;
    }
}
