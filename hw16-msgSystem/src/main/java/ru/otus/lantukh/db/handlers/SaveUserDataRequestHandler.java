package ru.otus.lantukh.db.handlers;

import java.util.Optional;

import ru.otus.lantukh.db.DBServiceUser;
import ru.otus.lantukh.dto.UserDto;
import ru.otus.lantukh.messagesystem.RequestHandler;
import ru.otus.lantukh.messagesystem.message.Message;
import ru.otus.lantukh.messagesystem.message.MessageBuilder;
import ru.otus.lantukh.messagesystem.message.MessageHelper;
import ru.otus.lantukh.model.AddressDataSet;
import ru.otus.lantukh.model.PhoneDataSet;
import ru.otus.lantukh.model.User;

public class SaveUserDataRequestHandler implements RequestHandler<UserDto> {
    private final DBServiceUser dbService;

    public SaveUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        UserDto userDto = MessageHelper.getPayload(msg);

        User user = new User(0, userDto.getName());
        user.setAddress(new AddressDataSet(0, userDto.getAddress()));
        user.addPhone(new PhoneDataSet(0, userDto.getPhone()));

        long id = dbService.saveUser(user);
        userDto.setId(id);

        return Optional.of(MessageBuilder.buildReplyMessage(msg, userDto));
    }
}
