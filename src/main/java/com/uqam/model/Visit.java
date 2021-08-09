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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "treatment")
    private Treatment treatment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "diagnostic")
    private Diagnostic diagnostic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    public Visit() {
    }

    private Visit(VisitBuilder builder) {
        this.date = builder.date;
        this.summary = builder.summary;
        this.notes = builder.notes;
        this.treatment = builder.treatment;
        this.diagnostic = builder.diagnostic;
        this.doctor = builder.doctor;
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

    public static class VisitBuilder{

        private Doctor doctor;
        private Date date;
        private String summary;
        private String notes ;
        private Treatment treatment;
        private Diagnostic diagnostic;


        public VisitBuilder (Doctor doctor, Date date) {
            this.doctor = doctor;
            this.date = date;
        }

        public  VisitBuilder treatment ( Treatment treatment) {
            this.treatment = treatment;
            return this;
        }

        public  VisitBuilder diagnostic ( Diagnostic diagnostic) {
            this.diagnostic = diagnostic;
            return this;
        }

        public VisitBuilder summary ( String summary){
            this.summary = summary;
            return this;
        }

        public VisitBuilder notes (String notes) {
            this.notes = notes;
            return this;
        }

        public Visit build() {
            Visit visit = new Visit(this);
            return  visit;
        }
    }
}
