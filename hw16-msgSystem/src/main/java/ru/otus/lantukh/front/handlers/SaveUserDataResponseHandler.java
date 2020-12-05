package ru.otus.lantukh.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

import ru.otus.lantukh.dto.UserDto;
import ru.otus.lantukh.messagesystem.RequestHandler;
import ru.otus.lantukh.messagesystem.client.CallbackRegistry;
import ru.otus.lantukh.messagesystem.client.MessageCallback;
import ru.otus.lantukh.messagesystem.client.ResultDataType;
import ru.otus.lantukh.messagesystem.message.Message;
import ru.otus.lantukh.messagesystem.message.MessageHelper;

public class SaveUserDataResponseHandler implements RequestHandler<UserDto> {
    private static final Logger logger = LoggerFactory.getLogger(SaveUserDataResponseHandler.class);
    private final CallbackRegistry callbackRegistry;

    public SaveUserDataResponseHandler(CallbackRegistry callbackRegistry) {
        this.callbackRegistry = callbackRegistry;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            MessageCallback<? extends ResultDataType> callback = callbackRegistry.getAndRemove(msg.getCallbackId());
            if (callback != null) {
                callback.accept(MessageHelper.getPayload(msg));
            } else {
                logger.error("callback for Id:{} not found", msg.getCallbackId());
            }
        } catch (Exception ex) {
            logger.error("msg:{}", msg, ex);
        }
        return Optional.empty();
    }
}
