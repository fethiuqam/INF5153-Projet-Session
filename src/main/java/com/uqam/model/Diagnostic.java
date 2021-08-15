package com.uqam.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tDiagnostics")
public class Diagnostic implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "designation")
    private String designation;

    //methode to validate the length of the diagnostic name
    public boolean validDesignation(String designation) throws AppException {
        if(!(designation.length() > 2)){
            throw new AppException("Le diagnostic est trop court. Veuillez etre plus precis.");
        }
        return true;
    }

    public Diagnostic() {
    }

    public Diagnostic(String designation) {
        this.designation = designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public String getDesignation() {
        return designation;
    }

    @Override
    protected Diagnostic clone() throws CloneNotSupportedException {
        try {
            return (Diagnostic) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diagnostic that = (Diagnostic) o;
        return id == that.id && Objects.equals(designation, that.designation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation);
    }

    @Override
    public String toString() {
        return "Diagnostic{" +
                "designation='" + designation + '\'' +
                '}';
    }
}
