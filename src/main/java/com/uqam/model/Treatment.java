package com.uqam.model;

public class Treatment {

    private String designation;

    //methode to validate the length of the Treatment to make it suppose to be used

    public Treatment(String designation) {
        this.designation = designation;
    }

    private boolean validTreatment(String designation){
        return designation.length()>9;
    }
    public String getDesignation() {
        return designation;
    }
}
