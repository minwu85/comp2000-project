// A scheduled accident: once its step is reached, it stops one named train
// at its current stop for a number of steps.
public class AccidentEvent extends SimulationEvent {

    private final String trainName;
    private final int delaySteps;

    public AccidentEvent(long step, String trainName, int delaySteps){
        super(step);
        this.trainName = trainName;
        this.delaySteps = delaySteps;
    }

    public String getTrainName(){
        return trainName;
    }

    public int getDelaySteps(){
        return delaySteps;
    }

    @Override
    public void execute(Vehicles[] trains){
        for(Vehicles train : trains){
            if(train.getName().equals(trainName)){
                train.delay(delaySteps);
            }
        }
    }
}
