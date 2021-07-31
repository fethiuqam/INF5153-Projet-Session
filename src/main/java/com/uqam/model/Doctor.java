package com.uqam.model;

public class Doctor {

    private String firstname;
    private String lastname;
    private String permit;
    private String specialty;
    private Establishment establishment;


    //Method to validate both first and last name. Length of name must be higher than 1
    public boolean validName(String name){
        return firstname.length()>1;
    }

    //method to validate the speciality of the doctor. Length must be at least 6.
    public boolean validSpeciality(String specialty){
        return specialty.length()>7;
    }

    public Doctor(String firstname, String lastname, String permit, String specialty, Establishment establishment) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.permit = permit;
        this.specialty = specialty;
        this.establishment = establishment;
    }

    //getters
    public Establishment getEstablishment() {
        return establishment;
    }
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
