package com.uqam.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Optional;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "treatment")
    private Treatment treatment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "diagnostic")
    private Diagnostic diagnostic;

    @ManyToOne
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

    //getters

    public Optional getBeginning() {
        return Optional.ofNullable(beginning);
    }

    public Optional getEnd() {
        return Optional.ofNullable(end);
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public Treatment getTreatment() {
        return Optional.ofNullable(treatment).orElse(new Treatment("Ne s'applique pas"));
    }

    public Doctor getPrescriber() {
        return prescriber;
    }

    //setters

    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    protected Antecedent clone() {
        try {
            Antecedent clone = (Antecedent) super.clone();
            //modif - HAMZA
            clone.treatment = (this.treatment!=null) ? this.treatment.clone() : null;
            clone.diagnostic = this.diagnostic.clone();
            clone.prescriber = this.prescriber.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antecedent that = (Antecedent) o;
        return id == that.id && Objects.equals(beginning, that.beginning) && Objects.equals(end, that.end) && Objects.equals(treatment, that.treatment) && Objects.equals(diagnostic, that.diagnostic) && Objects.equals(prescriber, that.prescriber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginning, end, treatment, diagnostic, prescriber);
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
