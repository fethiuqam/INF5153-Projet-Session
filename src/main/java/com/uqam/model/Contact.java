package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tContacts")
public class Contact {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String address;
    private String phone;
    private String email;

    public Contact(){}

    public Contact(String address, String phone, String email) {
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
