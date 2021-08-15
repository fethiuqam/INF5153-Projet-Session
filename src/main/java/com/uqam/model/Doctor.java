package com.uqam.model;

import javax.persistence.*;
import java.util.Objects;

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
    public boolean validName(String name) throws AppException {
        return firstname.length() > 1;
    }

    //method to validate the speciality of the doctor. Length must be at least 6.
    public boolean validSpeciality(String specialty) throws AppException {
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
    protected Doctor clone() throws CloneNotSupportedException {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id && Objects.equals(firstname, doctor.firstname) && Objects.equals(lastname, doctor.lastname) && Objects.equals(permit, doctor.permit) && Objects.equals(speciality, doctor.speciality) && Objects.equals(establishment, doctor.establishment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, permit, speciality, establishment);
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
