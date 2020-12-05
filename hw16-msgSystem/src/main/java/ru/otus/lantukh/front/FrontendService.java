package ru.otus.lantukh.front;

import ru.otus.lantukh.dto.UserDto;
import ru.otus.lantukh.messagesystem.client.MessageCallback;

public interface FrontendService {
    void saveUserData(UserDto user, MessageCallback<UserDto> dataConsumer);
}

