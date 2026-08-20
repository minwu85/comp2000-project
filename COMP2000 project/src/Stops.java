public class Stops {
    int x;
    int y;
    String name;
    int capacity;
    int totalComuters =0;

    Stops(int x, int y, String name, int capacity){
        this.x = x;
        this.y = y;
        this.name = name;
        this.capacity = capacity;
    }

    public int checkCapacity(){
        int boardingTime =0;
        if(totalComuters >=  capacity/3){
            
        }
        return boardingTime;
    }


}
