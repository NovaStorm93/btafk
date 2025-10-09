package novasmods.btafk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import novasmods.btafk.events.Event;


public class EventScheduler {
    
    
    List<Event> events = new ArrayList<Event>();
    
    public void scheduleEvent(Event event){
        events.add(event);
        
        
        
    }
    
    public void onServerTick(){
        if(events.size() <= 0){
            return;
        }
        Iterator<Event> i = events.iterator();
        while(i.hasNext()){
            Event event = i.next();
            boolean shouldRemove = event.tickEvent();
            if(shouldRemove){
                i.remove();
            }
            
            
            
        }
        
        
        
        
    }
    
    
}
