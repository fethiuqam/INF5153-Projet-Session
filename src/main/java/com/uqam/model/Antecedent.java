package com.uqam.model;
import java.sql.Date;
public class Antecedent {

    private Date debut;
    private Date fin;
    private Diagnostic diagnostic;
    private Treatment treatment;
    private Doctor prescriber;

    public Antecedent(Date debut, Date fin, Diagnostic diagnostic, Treatment treatment, Doctor prescriber) {
        this.debut = debut;
        this.fin = fin;
        this.diagnostic = diagnostic;
        this.treatment = treatment;
        this.prescriber = prescriber;
    }

    public Antecedent(){

    }



    //setters
    public void setDebut(Date debut) {
        this.debut = debut;
    }
    public void setFin(Date fin) {
        this.fin = fin;
    }
    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    public void setPrescriber(Doctor prescriber) {
        this.prescriber = prescriber;
    }

    //getters
    public Date getDebut() {
        return debut;
    }
    public Date getFin() {
        return fin;
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
}
