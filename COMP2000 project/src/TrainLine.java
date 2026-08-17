import java.util.ArrayList;

public class TrainLine {
    String name;
    ArrayList<Station> stations;
    ArrayList<Station> firstLine = new ArrayList<>();
    //firstLine.add(station1);
    TrainLine line1 = new TrainLine("Line1",firstLine);
   
    TrainLine(String name, ArrayList<Station> stations){
        this.name = name;
        this.stations = stations;

    }
}
