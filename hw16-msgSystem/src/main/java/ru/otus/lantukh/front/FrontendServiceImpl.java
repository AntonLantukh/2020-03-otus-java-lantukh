package ru.otus.lantukh.front;

import ru.otus.lantukh.dto.UserDto;
import ru.otus.lantukh.messagesystem.client.MessageCallback;
import ru.otus.lantukh.messagesystem.client.MsClient;
import ru.otus.lantukh.messagesystem.message.Message;
import ru.otus.lantukh.messagesystem.message.MessageType;

public class FrontendServiceImpl implements FrontendService {
    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void saveUserData(UserDto user, MessageCallback<UserDto> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, user,
                MessageType.USER_DATA_SAVE, dataConsumer);
        msClient.sendMessage(outMsg);
    };

}
