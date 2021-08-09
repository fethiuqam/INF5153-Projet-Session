package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tDoctors")
public class Doctor implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;
    private String lastname;
    private String permit;
    private String speciality;

    @ManyToOne
    @JoinColumn(name = "establishment")
    private Establishment establishment;

    public Doctor() {
    }

    public Doctor(String firstname, String lastname, String permit, String specialty, Establishment establishment) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.permit = permit;
        this.speciality = specialty;
        this.establishment = establishment;
    }


    //Method to validate both first and last name. Length of name must be higher than 1
    public boolean validName(String name) {
        return firstname.length() > 1;
    }

    //method to validate the speciality of the doctor. Length must be at least 6.
    public boolean validSpeciality(String specialty) {
        return specialty.length() > 7;
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
        return speciality;
    }

    @Override
    protected Doctor clone() {
        try {
            Doctor clone = (Doctor) super.clone();
            clone.establishment = this.establishment.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", permit='" + permit + '\'' +
                ", specialty='" + speciality + '\'' +
                ", establishment=" + establishment +
                '}';
    }
}
