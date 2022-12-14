package com.uqam.model;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tTreatments")
public class Treatment implements Cloneable{

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String designation;

    public Treatment() {
    }

    public Treatment(String designation) {
        this.designation = designation;
    }

    //methode to validate the length of the Treatment to make it suppose to be used
    public  boolean validTreatment(String designation) throws AppException{
        if( designation.length()<9){
            throw new AppException("La description du traitement est trop courte. Veuillez entrer une description d'au moins 10 caracteres.");
        }
        return true;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treatment treatment = (Treatment) o;
        return id == treatment.id && Objects.equals(designation, treatment.designation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation);
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "designation='" + designation + '\'' +
                '}';
    }
}
