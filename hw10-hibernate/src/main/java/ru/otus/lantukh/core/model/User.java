package ru.otus.lantukh.core.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    @OneToMany(targetEntity = PhoneDataSet.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private Set<PhoneDataSet> phone = new HashSet<PhoneDataSet>();

    public User() {}

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id, String name, AddressDataSet address, PhoneDataSet phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone.add(phone);
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

    public Set<PhoneDataSet> getPhone() {
        return phone;
    }

    public void setPhone(Set<PhoneDataSet> phone) {
        this.phone = phone;
    }

    public void addPhone(PhoneDataSet phone) {
        this.phone.add(phone);
        phone.setUser(this);
    }

    public void removePhone(PhoneDataSet phone) {
        this.phone.remove(phone);
        phone.setUser(null);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phone=" + phone +
                '}';
    }
}
