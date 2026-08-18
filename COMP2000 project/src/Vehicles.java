public class Vehicles {
    int capacity; 
    String name;
    Routes route;

    //Train train1 = new Train(200, "Choo Choo", line1);

    //train1.setBounds(width/2 -normalStation/2 + 100, height/2 -normalStation/2, normalStation, normalStation);
    public Vehicles(){
        
    }

    public Vehicles(int capacity, String name, Routes route){
        this.capacity = capacity;
        this.name = name;
        this.route = route;
    }


}

