public class Train {
    int capacity; 
    String name;
    TrainLine route;

    //Train train1 = new Train(200, "Choo Choo", line1);

    //train1.setBounds(width/2 -normalStation/2 + 100, height/2 -normalStation/2, normalStation, normalStation);



    Train(int capacity, String name, TrainLine route){
        this.capacity = capacity;
        this.name = name;
        this.route = route;
    }


}
