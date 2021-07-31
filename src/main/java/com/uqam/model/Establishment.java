package com.uqam.model;

public class Establishment {

    private String id;
    private String designation ;


    public Establishment(String id, String designation) {
        this.id = id;
        this.designation = designation;
    }

    //method to validate the length of the establishment
    public boolean validDesignation(String designation){
        return designation.length()>1;
    }

    //getters
    public String getId() {
        return id;
    }
    public String getDesignation() {
        return designation;
    }
}
