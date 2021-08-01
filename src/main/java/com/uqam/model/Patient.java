package com.uqam.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tPatients")
public class Patient implements Cloneable {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String firstname;
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dateOfBirth;
    private String birthCity;
    private String insuranceNumber;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn( name="contact")
    private Contact contact;

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable( name = "tParents", joinColumns = @JoinColumn( name = "fils" ),
            inverseJoinColumns = @JoinColumn( name = "parent" ) )
    private Set<Patient> parents = new HashSet<>();

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
    protected Patient clone() {
        try {
            Patient clone = (Patient) super.clone();
            clone.contact = this.contact.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
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
