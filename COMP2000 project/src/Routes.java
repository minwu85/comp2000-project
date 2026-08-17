import java.util.ArrayList;

public class Routes {
    String name;
    ArrayList<Station> stations;
    ArrayList<Station> firstLine = new ArrayList<>();
    //firstLine.add(station1);
    Routes line1 = new Routes("Line1",firstLine);
   
    Routes(String name, ArrayList<Station> stations){
        this.name = name;
        this.stations = stations;

    }
}
