package battleship.services;

import battleship.models.Event;

public interface EventListener {
    void update(Event event);
}
