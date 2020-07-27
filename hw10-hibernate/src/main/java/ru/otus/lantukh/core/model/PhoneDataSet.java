package ru.otus.lantukh.core.model;


import javax.persistence.*;

@Entity(name = "Phone")
@Table(name = "phone")
public class PhoneDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "number", nullable = false)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(long id, String number, User user) {
        this.id = id;
        this.number = number;
        this.user = user;
    }

    public PhoneDataSet(long id, String number) {
        this.id = id;
        this.number = number;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.addPhone(this);
    }

    @Override
    public String toString() {
        return number;
    }
}
