package ru.otus.lantukh.messagesystem;

import java.util.Optional;

import ru.otus.lantukh.messagesystem.client.ResultDataType;
import ru.otus.lantukh.messagesystem.message.Message;

public interface RequestHandler<T extends ResultDataType> {
    Optional<Message> handle(Message msg);
}
