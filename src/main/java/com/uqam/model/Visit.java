package com.uqam.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.sql.Date;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "tVisits")
public class Visit implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_")
    private Date date;

    private String summary;
    private String notes ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "treatment")
    private Treatment treatment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "diagnostic")
    private Diagnostic diagnostic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    public Visit() {
    }

    public Visit(Date date, String summary, String notes, Diagnostic diagnostic, Treatment treatment, Doctor doctor) {
        this.date = date;
        this.summary = summary;
        this.notes = notes;
        this.treatment = treatment;
        this.diagnostic = diagnostic;
        this.doctor = doctor;
    }


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


    public void setDate(Date date) {
        this.date = date;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    protected Visit clone() {
        try {
            Visit clone = (Visit) super.clone();
            clone.treatment = this.treatment != null ? this.treatment.clone(): null;
            clone.diagnostic = this.diagnostic != null ? this.diagnostic.clone() : null;
            clone.doctor = this.doctor.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
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
