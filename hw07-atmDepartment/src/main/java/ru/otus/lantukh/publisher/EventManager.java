package ru.otus.lantukh.publisher;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import ru.otus.lantukh.listener.EventListener;

public class EventManager {
    final Map<Event, List<EventListener>> listeners = new HashMap<>();

    public EventManager(Event[] operations) {
        for (Event operation: operations) {
            listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(Event eventType, EventListener listener) {
        List<EventListener> list = listeners.get(eventType);
        list.add(listener);
    }

    public void unsubscribe(Event eventType, EventListener listener) {
        List<EventListener> list = listeners.get(eventType);
        list.remove(listener);
    }

    public void notify(Event eventType) {
        List<EventListener> list = listeners.get(eventType);
        for (EventListener element: list) {
            element.performOperation();
        }
    }
}
