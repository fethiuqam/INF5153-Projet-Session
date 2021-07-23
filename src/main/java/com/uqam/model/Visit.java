package com.uqam.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

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
    @JoinColumn(name="visit", referencedColumnName="id")
    private List<Note> notes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="treatment")
    private Treatment treatment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="diagnostic")
    private Diagnostic diagnostic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="doctor")
    private Doctor doctor;

    public Date getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }

    public List<Note> getNotes() {
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
