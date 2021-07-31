package com.uqam.model;

public class Diagnostic {

    private String designation;

    //methode to validate the length of the diagnostic name
    public boolean validDesignation(String designation){
        return designation.length()>2;
    }

    public Diagnostic(String designation) {
        this.designation = designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }
}
