package com.uqam.model;

import java.util.Date;
import java.util.List;

public class Patient {

    private String firstname;
    private String lastname;
    private Gender gender;
    private Date dateOfBirth;
    private String birthCity;
    private String insuranceNumber;
    private Contact contact;
    private List<Patient> parents;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public Contact getContact() {
        return contact;
    }
}
