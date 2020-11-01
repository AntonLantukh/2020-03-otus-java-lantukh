package ru.otus.lantukh.model;


import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Expose
    private long id;

    @Column(name = "name", nullable = false)
    @Expose
    private String name;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @Expose
    private AddressDataSet address;

    @OneToMany(targetEntity = PhoneDataSet.class, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @Expose
    private Set<PhoneDataSet> phones = new HashSet<>();

    public User() {}

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id, String name, AddressDataSet address, PhoneDataSet phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones.add(phone);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public void addPhone(PhoneDataSet phone) {
        this.phones.add(phone);
        phone.setUser(this);
    }

    public void removePhone(PhoneDataSet phone) {
        this.phones.remove(phone);
        phone.setUser(null);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
