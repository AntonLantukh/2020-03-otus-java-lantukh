package ru.otus.lantukh.messagesystem;

import ru.otus.lantukh.messagesystem.client.ResultDataType;
import ru.otus.lantukh.messagesystem.message.MessageType;

public interface HandlersStore {
    RequestHandler<? extends ResultDataType> getHandlerByType(String messageTypeName);

    void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler);
}
