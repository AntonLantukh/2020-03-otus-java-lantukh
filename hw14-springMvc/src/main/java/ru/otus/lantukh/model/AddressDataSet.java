package ru.otus.lantukh.model;


import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity(name = "Address")
@Table(name = "address")
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Expose
    @Column(name = "street", nullable = false)
    private String street;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private User user;

    public AddressDataSet() {
    }

    public AddressDataSet(long id, String street, User user) {
        this.id = id;
        this.street = street;
        this.user = user;
    }

    public AddressDataSet(long id, String street) {
        this.id = id;
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.setAddress(this);
    }

    @Override
    public String toString() {
        return street;
    }
}
