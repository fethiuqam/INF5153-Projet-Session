package com.uqam.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tAntecedents")
public class Antecedent {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    @Column(name = "biginning")
    private Date beginning;

    @Column(name = "end_")
    private Date end;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="treatment")
    private Treatment treatment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="diagnostic")
    private Diagnostic diagnostic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="prescriber")
    private Doctor prescriber;

    public Date getBeginning() {
        return beginning;
    }

    public Date getEnd() {
        return end;
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public Doctor getPrescriber() {
        return prescriber;
    }

    @Override
    public String toString() {
        return "Antecedent{" +
                "beginning=" + beginning +
                ", end=" + end +
                ", treatment=" + treatment +
                ", diagnostic=" + diagnostic +
                ", prescriber=" + prescriber +
                '}';
    }
}
