public class Passenger {
    /*set the end and start stops for the passenger
        eg let passager 1 as a type (student): Stop A - C
        make a check point to see if the passenger has reached the end stop
        if not then keep moving to the next stop until the end stop is reached
        or once the passager dectets the station name matching the passenger's end stop 
        therefore the passenger exit the train, once exit is display a correct message for 2 sec
        let a passenger pass1 = new Passenger(Stops.C, Stops.A);
        Passenger pass1 = new Passenger(Stops.Central, Stops.East1);
        Passenger pass2 = new Passenger(Stops.East1, Stops.Central);
        Let a passage in a different colour shape display over the train stop once enter click then the pass1 
        pass1 will move to the next stop until it reaches the end stop

        once it reaches East1 then display a message "pass1 reached destination" for 2 sec
        then remove the passenger from the train stop and let it disappear from the screen
        */

    private Stops start;
    private Stops end;
    private Stops curStop;

    public Passenger(Stops start, Stops end) {
        this.start = start;
        this.end = end;
        this.curStop = start;
    }
        
    public boolean hasReachedDestination() {
        return curStop == end;
    }

    public Stops getCurrentStop() {
        return curStop;
    }

    public Stops getStart() {
        return start;
    }

    public Stops getEnd() {
        return end;
    }

    
}
    

