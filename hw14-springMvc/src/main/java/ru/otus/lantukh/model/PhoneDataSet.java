package ru.otus.lantukh.model;


import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity(name = "Phone")
@Table(name = "phone")
public class PhoneDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Expose
    @Column(name = "number")
    private String number;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(long id, String number) {
        this.id = id;
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }
}
