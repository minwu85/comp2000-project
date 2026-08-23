import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Vehicles {
    int capacity; 
    String name;
    Routes route;
    ArrayList<Passenger> onBoard = new ArrayList<>();

    int stationIndex = 0; // position along route.stations
    int width = 30;
    int height = 16;

    public Vehicles(){
        
    }

    public Vehicles(int capacity, String name, Routes route){
        this.capacity = capacity;
        this.name = name;
        this.route = route;
    }

    public Stops getCurStop(){
        return route.stations.get(stationIndex);
    }

    public void addPassangers(){
        if(onBoard.size() == capacity){
            //Move to next station
            //
        } else{
            // get passangers from station and place them in the train arraylist 
        }
    }

    public void disembarkPassangers(){
       //call the passangers and ask them if this is there stop once they are all disembarked
       // 
    }

    // Steps forward one stop, looping back to the start after the last stop.
    public void moveVehicle(){
        int lastIndex = route.stations.size() - 1;
        if(stationIndex < lastIndex){
            stationIndex = stationIndex + 1;
        } else if(stationIndex == lastIndex){
            stationIndex = 0;
        }
    }

    // White rectangle, black outline, sitting on the route at getCurStop().
    public void displayVehicle(Graphics g){
        Stops curStop = getCurStop();
        int x = curStop.x - width / 2;
        int y = curStop.y - height / 2;

        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);

        if(onBoard.size() > 0){
            int passengerSize = 8;
            int passengerX = x + (width - passengerSize) / 2;
            int passengerY = y + (height - passengerSize) / 2;
            g.setColor(Color.orange);
            g.fillOval(passengerX, passengerY, passengerSize, passengerSize);
        }
    }

    public void reverseRoute(){
        //if getCurStop == route.size()
        //reverse route
    }




}