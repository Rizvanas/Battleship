package battleship.services;

import battleship.models.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    Map<Event, List<EventListener>> listeners = new HashMap<>();

    public EventManager(Event... events) {
        for (var e : events) {
            this.listeners.put(e, new ArrayList<>());
        }
    }

    public void subscribe(EventListener listener, Event... events) {
        for (var event : events) {

            listeners.get(event).add(listener);
        }
    }

    public void unsubscribe(Event event, EventListener listener) {
        listeners.get(event).remove(listener);
    }

    public void notify(Event event) {
        for (var listener : listeners.get(event)) {
            listener.update(event);
        }
    }
}
