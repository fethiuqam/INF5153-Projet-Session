package com.uqam.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "tPatients")
public class Patient {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String firstname;
    private String lastname;
    private Gender gender;
    private Date dateOfBirth;
    private String birthCity;
    private String insuranceNumber;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn( name="contact")
    private Contact contact;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable( name = "tParents", joinColumns = @JoinColumn( name = "fils" ),
            inverseJoinColumns = @JoinColumn( name = "parent" ) )
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

    @Override
    public String toString() {
        return "Patient{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", birthCity='" + birthCity + '\'' +
                ", insuranceNumber='" + insuranceNumber + '\'' +
                ", contact=" + contact +
                ", parents=" + parents +
                '}';
    }
}
