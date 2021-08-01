package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tEstablishments")
public class Establishment implements Cloneable{

    @Id @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    private String identification;
    private String designation ;

    public Establishment(){}

    public Establishment(String id, String designation) {
        this.identification = id;
        this.designation = designation;
    }

    //method to validate the length of the establishment
    public boolean validDesignation(String designation){
        return designation.length()>1;
    }

    //getters
    public String getId() {
        return identification;
    }
    public String getDesignation() {
        return designation;
    }

    @Override
    protected Establishment clone() {
        try {
            return (Establishment) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Establishment{" +
                "identification='" + identification + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
