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
        if (treatment != null)
            this.treatment = new Treatment(treatment);

        return this;
    }

    public AntecedentBuilder diagnostic(String diagnostic) {
        if (diagnostic != null)
            this.diagnostic = new Diagnostic(diagnostic);

        return this;
    }

    public AntecedentBuilder dateStart(Date datePassed) {
        if (datePassed != null)
            this.start = datePassed;

        return this;
    }

    public AntecedentBuilder dateEnd (Date datePassed) {
        if (datePassed != null)
            this.end = datePassed;

        return this;
    }

    public Antecedent build () {
        return new Antecedent(start, end, diagnostic, treatment,doctor);
    }
}
