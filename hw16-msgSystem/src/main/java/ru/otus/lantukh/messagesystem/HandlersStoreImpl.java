package ru.otus.lantukh.messagesystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.otus.lantukh.messagesystem.client.ResultDataType;
import ru.otus.lantukh.messagesystem.message.MessageType;

public class HandlersStoreImpl implements HandlersStore {
    private final Map<String, RequestHandler<? extends ResultDataType>> handlers = new ConcurrentHashMap<>();

    @Override
    public RequestHandler<? extends ResultDataType> getHandlerByType(String messageTypeName) {
        return handlers.get(messageTypeName);
    }

    @Override
    public void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler) {
        handlers.put(messageType.getName(), handler);
    }


}
