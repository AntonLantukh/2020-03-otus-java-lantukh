package ru.otus.lantukh.messagesystem;

import ru.otus.lantukh.messagesystem.client.MsClient;
import ru.otus.lantukh.messagesystem.message.Message;

public interface MessageSystem {

    void addClient(MsClient msClient);

    void removeClient(String clientId);

    boolean newMessage(Message msg);

    void dispose() throws InterruptedException;

    void dispose(Runnable callback) throws InterruptedException;

    void start();

    int currentQueueSize();
}

