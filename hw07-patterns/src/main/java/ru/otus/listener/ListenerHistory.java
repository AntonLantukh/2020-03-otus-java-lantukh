package ru.otus.listener;

import ru.otus.Message;
import ru.otus.listener.Listener;

import java.util.ArrayList;

public class ListenerHistory implements Listener {
    private ArrayList<String> history = new ArrayList<>();

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        String message = history.size() == 0 ? "oldMsg:%s" : "oldMsg:%s, newMsg:%s";
        history.add(message);
    }
}
