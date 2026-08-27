public class App {
    public static void main(String[] args) throws Exception {
        Routes line1 = Routes.line1();

        Passenger pass1 = new Passenger("pass1", Stops.Central, Stops.Jubilee);
        Passenger pass2 = new Passenger("pass2", Stops.Jubilee, Stops.Central);

        runPassenger(pass1, line1);
        runPassenger(pass2, line1);
    }
    //get passenger
    private static void runPassenger(Passenger p, Routes route) throws InterruptedException {
        System.out.println(p.getName() + " boards at " + p.getStart());

        while (!p.moveToNextStop(route)) {
            System.out.println(p.getName() + " moves to " + p.getCurStop());
        }

        // message stays "on screen" for 2 seconds, then the passenger disappears
        System.out.println(p.getMessage());
        Thread.sleep(2000);
    }
}
