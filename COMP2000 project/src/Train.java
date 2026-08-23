public class Train extends Vehicles{

    public Train(){
        super();
    }

    public Train(int capacity, String name, Routes route){
        super(capacity, name, route);
    }

    // The example T1 train, running the full red line.
    public static Train t1() {
        return new Train(50, "T1", Routes.redLine());
    }
}
