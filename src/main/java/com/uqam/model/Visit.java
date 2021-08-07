package com.uqam.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.sql.Date;

import javax.persistence.*;
import java.util.HashSet;
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

    public Visit(Date date, String summary, String notes, Treatment treatment, Diagnostic diagnostic, Doctor doctor) {
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
        return treatment;
    }
    public Diagnostic getDiagnostic() {
        return diagnostic;
    }
    public Doctor getDoctor() {
        return doctor;
    }

    @Override
    protected Visit clone() {
        try {
            Visit clone = (Visit) super.clone();
            clone.treatment = this.treatment.clone();
            clone.diagnostic = this.diagnostic.clone();
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
