package ru.otus.lantukh.dto;

import java.io.Serializable;

public class UserDto implements Serializable {
    private String name;
    private String address;
    private String phone;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
