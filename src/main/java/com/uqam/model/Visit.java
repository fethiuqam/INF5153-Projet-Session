package com.uqam.model;

import java.util.Date;
import java.util.List;

public class Visit {

    private Date date;
    private String summary;
    private List<String> notes;
    private Treatment treatment;
    private Diagnostic diagnostic;
    private Doctor doctor;

    public Date getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }

    public List<String> getNotes() {
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
}
