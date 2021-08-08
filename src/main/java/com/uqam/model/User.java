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

    public User() {
    }

    public User(String username, String password, Doctor doctor) {
        this.username = username;
        this.password = password;
        this.doctor = doctor;
    }

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

    public boolean validPassword(String password){
        if (password.length()<8){
            return false;
        }
        return password.matches("[A-Za-z0-9]+");
    }
}
