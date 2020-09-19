package ru.otus.processor;

import ru.otus.Message;

import java.time.Clock;
import java.time.LocalDateTime;

public class ProcessorThrowError implements Processor {
    private final Clock clock;

    public ProcessorThrowError(Clock clock) {
        this.clock = clock;
    }

    public ProcessorThrowError() {
        this.clock = Clock.systemDefaultZone();
    }

    @Override
    public Message process(Message message) {
        if (LocalDateTime.now(clock).getSecond() % 2 == 0) {
            throw new SecondException();
        }

        return message;
    }
}
