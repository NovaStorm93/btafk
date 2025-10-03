package novasmods.btafk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import novasmods.btafk.interfaces.IEvent;

public class EventScheduler {
    private class Event{
        IEvent event;
        int tickDelay;
        List args;
        public Event(IEvent event, int tickDelay, List args){
            this.event = event;
            this.tickDelay = tickDelay;
            this.args = args;
        }
        
        
        public void runEvent(){
            this.event.runEvent(args);
        }
        
    }
    
    
    List<Event> events = new ArrayList<Event>();
    
    public void scheduleEvent(IEvent event, int tickDelay, List args){
        Event scheduledEvent = new Event(event, tickDelay, args);
        events.add(scheduledEvent);
        
        
        
    }
    
    public void onServerTick(){
        if(events.size() <= 0){
            return;
        }
        Iterator<Event> i = events.iterator();
        while(i.hasNext()){
            Event event = i.next();
            event.tickDelay--;
            
            if(event.tickDelay <= 0){
                event.runEvent();
                i.remove();
            }
            
            
        }
        
        
        
        
    }
    
    
}
