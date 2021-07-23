package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tPatients")
public class Diagnostic {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String designation;

    public String getDesignation() {
        return designation;
    }

    @Override
    public String toString() {
        return "Diagnostic{" +
                "designation='" + designation + '\'' +
                '}';
    }
}
