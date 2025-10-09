package novasmods.btafk.events;


public abstract class Event{

    
    public Event(int tickDelay){
        if(tickDelay < 0){
            this.tickDelay = 0;
        }
        this.tickDelay = tickDelay;
    }
    
    protected int tickDelay;
    
    // Boolean return value flags if this event has completed and if this object should be freed;
    public boolean tickEvent() {
        tickDelay--;
        if(tickDelay <= 0){
            runEvent();
            return true;
        }
        return false;
        
    }
    
    
    // runEvent called when it's time to run this event, afterwards this object will be freed;
    protected abstract void runEvent();
    
}
