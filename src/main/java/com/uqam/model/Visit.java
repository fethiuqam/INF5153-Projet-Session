package com.uqam.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tVisits")
public class Visit {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    @Column(name = "date_")
    private Date date;
    private String summary;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="visit", referencedColumnName="id")
    private Set<Note> notes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="treatment")
    private Treatment treatment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="diagnostic")
    private Diagnostic diagnostic;

    public Visit() {}

    public Visit(Date date, Doctor doctor){
        this.date = date;
        this.doctor = doctor;
    }

    // For testing purposes
    public Visit(Date date, String summary, Set<Note> notes, Treatment treatment, Diagnostic diagnostic, Doctor doctor) {
        this.date = date;
        this.summary = summary;
        this.notes = notes;
        this.treatment = treatment;
        this.diagnostic = diagnostic;
        this.doctor = doctor;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="doctor")
    private Doctor doctor;

    public Date getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }

    public Set<Note> getNotes() {
        return notes;
    }


    public Treatment getTreatment() {
        return treatment;
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setNotes( Set<Note> notes) {
        this.notes = notes;
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
