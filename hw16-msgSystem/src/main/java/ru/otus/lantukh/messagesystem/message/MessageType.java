package ru.otus.lantukh.messagesystem.message;

public enum MessageType {
    USER_DATA_GET("UserDataGet"),

    USER_DATA_SAVE("UserDataSave");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
