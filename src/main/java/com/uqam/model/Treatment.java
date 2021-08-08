package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tTreatments")
public class Treatment {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String designation;

    public Treatment() {}

    public Treatment(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "designation='" + designation + '\'' +
                '}';
    }
}
