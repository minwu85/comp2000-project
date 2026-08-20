import java.util.ArrayList;

public class Vehicles {
    int capacity; 
    String name;
    Routes route;
    ArrayList<Passenger> onBoard;


    //Train train1 = new Train(200, "Choo Choo", line1);

    //train1.setBounds(width/2 -normalStation/2 + 100, height/2 -normalStation/2, normalStation, normalStation);
    public Vehicles(){
        
    }

    public Vehicles(int capacity, String name, Routes route){
        this.capacity = capacity;
        this.name = name;
        this.route = route;
    }

    public void getCurStop(){
        
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

    public void moveVehicle(){
        // get arrayList of routes called route and direct it to the next station
    }

    public void displayVehicle(Vehicles type, int x, int y){
        //
        
    }

    public void reverseRoute(){
        //if getCurStop == route.size()
        //reverse route
    }




}

