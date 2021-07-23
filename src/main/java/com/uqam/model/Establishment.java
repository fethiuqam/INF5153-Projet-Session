package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tEstablishments")
public class Establishment {

    @Id @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    private String identification;
    private String designation ;

    public String getId() {
        return identification;
    }

    public String getDesignation() {
        return designation;
    }

    @Override
    public String toString() {
        return "Establishment{" +
                "identification='" + identification + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
