public class Train extends Vehicles{

    public Train(){
        super();
    }

    public Train(int capacity, String name, Routes route, Stops start){
        super(capacity, name, route);
    }

    // The example T1 train, running the full red line.
    public static Train t1() {
        return new Train(50, "T1", Routes.redLine(), Stops.Brookchester);
    }
    public static Train t2() {
        return new Train(50, "T2", Routes.blueLine(), Stops.SunsetPoint);
    }
    public static Train t3(){
        return new Train(50, "T3", Routes.purpleLine(), Stops.TownHall);
    }
    public static Train t4(){
        return new Train(50, "T4", Routes.greenLine(), Stops.MountPresley);
    }
}
