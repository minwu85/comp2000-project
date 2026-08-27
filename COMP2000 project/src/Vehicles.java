import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

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

    // What moveVehicle() will step to next, including the bounce at the end of the line.
    public Stops getNextStop(){
        int lastIndex = route.stations.size() - 1;
        if(stationIndex < lastIndex){
            return route.stations.get(stationIndex + 1);
        }
        if(lastIndex > 0){
            return route.stations.get(lastIndex - 1); // where index 1 will be after the reverse
        }
        return null;
    }

    public ArrayList<Passenger> getPassengers(){
        return onBoard;
    }

    public String getName(){
        return name;
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

    // Steps forward one stop; reverses direction once it reaches the end of the line.
    public void moveVehicle(){
        int lastIndex = route.stations.size() - 1;
        if(stationIndex < lastIndex){
            stationIndex = stationIndex + 1;
        } else {
            Collections.reverse(route.stations);
            stationIndex = 0;
        }
    }

    // Translucent red rectangle, thin outline, labelled with the vehicle's name.
    public void displayVehicle(Graphics g){
        Stops curStop = getCurStop();
        int x = curStop.x-width/2;
        int y = curStop.y-height/2;

        Graphics2D g2d=(Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        g.setColor(new Color(255, 0, 0)); // solid red
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);

        g.setFont(new Font("SansSerif", Font.BOLD, 12));
        g.drawString(name, x, y-4);
        //passonger display
        if(onBoard.size() > 0){
            int passengerSize = 8;
            int passengerX = x +(width-passengerSize)/2;
            int passengerY = y +(height-passengerSize)/2;
            g.setColor(Color.orange);
            g.fillOval(passengerX, passengerY, passengerSize, passengerSize);
        }
    }



}
