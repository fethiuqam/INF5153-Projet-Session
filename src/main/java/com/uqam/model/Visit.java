package com.uqam.model;

import java.sql.Date;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "tVisits")
public class Visit implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_")
    private Date date;

    private String summary;
    private String notes;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "treatment")
    private Treatment treatment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "diagnostic")
    private Diagnostic diagnostic;

    @ManyToOne
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    public Visit() {
    }

    public Visit(Doctor doctor, Diagnostic diagnostic, Treatment treatment, Date date, String summary, String notes) {
        this.date = date;
        this.summary = summary;
        this.notes = notes;
        this.treatment = treatment;
        this.diagnostic = diagnostic;
        this.doctor = doctor;
    }

    //getters
    public Date getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }

    public String getNotes() {
        return notes;
    }

    public Treatment getTreatment() {
        return Optional.ofNullable(treatment).orElse(new Treatment("Ne s'applique pas"));
    }

    public Diagnostic getDiagnostic() {
        return Optional.ofNullable(diagnostic).orElse(new Diagnostic("Ne s'applique pas"));
    }

    public Doctor getDoctor() {
        return doctor;
    }

    //setters
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    protected Visit clone() {
        try {
            Visit clone = (Visit) super.clone();
            clone.treatment = this.treatment != null ? this.treatment.clone() : null;
            clone.diagnostic = this.diagnostic != null ? this.diagnostic.clone() : null;
            clone.doctor = this.doctor.clone();
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
        Visit visit = (Visit) o;
        return id == visit.id && Objects.equals(date, visit.date) && Objects.equals(summary, visit.summary) && Objects.equals(notes, visit.notes) && Objects.equals(treatment, visit.treatment) && Objects.equals(diagnostic, visit.diagnostic) && Objects.equals(doctor, visit.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, summary, notes, treatment, diagnostic, doctor);
    }

    @Override
    public String toString() {
        return "Visit{" +
                "date=" + date +
                ", summary='" + summary + '\'' +
                ", notes=" + notes +
                ", treatment=" + treatment +
                ", diagnostic=" + diagnostic +
                ", doctor=" + doctor +
                '}';
    }

}
