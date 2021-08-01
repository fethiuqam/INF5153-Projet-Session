package com.uqam.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tAntecedents")
public class Antecedent implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "biginning")
    private Date beginning;

    @Column(name = "end_")
    private Date end;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "treatment")
    private Treatment treatment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "diagnostic")
    private Diagnostic diagnostic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescriber")
    private Doctor prescriber;

    public Antecedent() {
    }

    public Antecedent(Date debut, Date fin, Diagnostic diagnostic, Treatment treatment, Doctor prescriber) {
        this.beginning = debut;
        this.end = fin;
        this.diagnostic = diagnostic;
        this.treatment = treatment;
        this.prescriber = prescriber;
    }

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
    protected Antecedent clone() {
        try {
            Antecedent clone = (Antecedent) super.clone();
            clone.treatment = this.treatment.clone();
            clone.diagnostic = this.diagnostic.clone();
            clone.prescriber = this.prescriber.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
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
