package com.uqam.model;

import java.util.Date;

public class Antecedent {

    private Date debut;

    private Date fin;

    private Diagnostic diagnostic;

    private Treatment treatment;

    private Doctor prescriber;

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
