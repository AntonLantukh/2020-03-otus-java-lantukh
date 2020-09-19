package ru.otus.processor;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.Message;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.*;

class ProcessorThrowErrorTest {
    @Test
    @DisplayName("Тестируем исключение процессора для четной секунды")
    void processorEvenSecondExceptionTest() {
        var message = new Message.Builder().field7("field7").build();

        Clock clock = Clock.fixed(Instant.parse("2020-12-22T10:15:30.00Z"), ZoneId.of("UTC"));
        var processor = new ProcessorThrowError(clock);

        assertThatExceptionOfType(SecondException.class).isThrownBy(() -> processor.process(message));
    }

    @Test
    @DisplayName("Тестируем исключение процессора для нечетной секунды")
    void processorOddSecondExceptionTest() {
        var message = new Message.Builder().field7("field7").build();

        Clock clock = Clock.fixed(Instant.parse("2020-12-22T10:15:31.00Z"), ZoneId.of("UTC"));
        var processor = new ProcessorThrowError(clock);

        assertThatCode(() -> processor.process(message)).doesNotThrowAnyException();
    }
}