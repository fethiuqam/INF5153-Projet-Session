package com.uqam.model;

import java.sql.Date;

public class VisitBuilder {

    private Doctor doctor;
    private Date date;
    private String summary;
    private String notes;
    private Treatment treatment;
    private Diagnostic diagnostic;


    public VisitBuilder(Doctor doctor, Date date) {
        this.doctor = doctor;
        this.date = date;
    }

    public VisitBuilder treatment(String treatment) {
        if (treatment != null)
            this.treatment = new Treatment(treatment);

        return this;
    }

    public VisitBuilder diagnostic(String diagnostic) {
        if (diagnostic != null)
            this.diagnostic = new Diagnostic(diagnostic);

        return this;
    }

    public VisitBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }

    public VisitBuilder notes(String notes) {
        this.notes = notes;
        return this;
    }

    public Visit build() {
        return new Visit(doctor, diagnostic, treatment, date, summary, notes);
    }
}
