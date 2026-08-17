public class Counter {
    private int value = 0;
    Counter(int v) { value = v; }
    void add(Counter other) { value = value + other.value; }
    int getValue() { return value; }

    public static void main(String[] args){
        Counter first = new Counter(10);
        Counter second = new Counter(20);

        System.out.println(first.add(second.value));
    }

  



}
