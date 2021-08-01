package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tDiagnostics")
public class Diagnostic implements Cloneable{

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    @Column(name = "designation")
    private String designation;

    public Diagnostic(String designation) {
        this.designation = designation;
    }


    public String getDesignation() {
        return designation;
    }

    @Override
    protected Diagnostic clone() {
        try {
            return (Diagnostic) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Diagnostic{" +
                "designation='" + designation + '\'' +
                '}';
    }
}
