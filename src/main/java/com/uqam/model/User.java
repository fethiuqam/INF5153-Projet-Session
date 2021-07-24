package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tUsers")
public class User {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String username;
    private String password;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn( name="doctor")
    private Doctor doctor;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", doctor=" + doctor +
                '}';
    }
}
