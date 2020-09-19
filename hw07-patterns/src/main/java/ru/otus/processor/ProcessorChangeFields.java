package ru.otus.processor;

import ru.otus.Message;

public class ProcessorChangeFields implements Processor {

    @Override
    public Message process(Message message) {
        var messageField11 = message.getField11();
        var messageField12 = message.getField12();

        return message.toBuilder().field11(messageField12).field12(messageField11).build();
    }
}
