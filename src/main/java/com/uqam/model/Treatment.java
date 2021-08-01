package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tTreatments")
public class Treatment implements Cloneable{

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String designation;

    public Treatment(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    @Override
    protected Treatment clone() {
        try {
            return (Treatment) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "designation='" + designation + '\'' +
                '}';
    }
}
