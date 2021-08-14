package com.uqam.model;

import java.sql.Date;
import java.time.LocalDate;

public class AntecedentBuilder {

    private Diagnostic diagnostic;
    private Treatment treatment;
    private Date start;
    private Date end;
    private Doctor doctor;

    public AntecedentBuilder (Doctor doctor){
        this.doctor = doctor;
    }

    public AntecedentBuilder treatment(String treatment) {
        if (treatment != null && !treatment.isEmpty())
            this.treatment = new Treatment(treatment);

        return this;
    }

    public AntecedentBuilder diagnostic(String diagnostic) {
        if (diagnostic != null && !diagnostic.isEmpty())
            this.diagnostic = new Diagnostic(diagnostic);

        return this;
    }

    public AntecedentBuilder dateStart(LocalDate datePassed) {
        if (datePassed != null)
            this.start = Date.valueOf(datePassed);

        return this;
    }

    public AntecedentBuilder dateEnd (LocalDate datePassed) {
        if (datePassed != null)
            this.end = Date.valueOf(datePassed);

        return this;
    }

    public Antecedent build () {
        return new Antecedent(start, end, diagnostic, treatment,doctor);
    }
}
