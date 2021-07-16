package com.uqam.model;

public class Doctor {

    private String firstname;
    private String lastname;
    private String permit;
    private String specialty;

    public Establishment getEstablishment() {
        return establishment;
    }

    private Establishment establishment;


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPermit() {
        return permit;
    }

    public String getSpecialty() {
        return specialty;
    }
}
