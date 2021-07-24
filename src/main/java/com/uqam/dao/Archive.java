package com.uqam.dao;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tArchive")
public class Archive {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    private String insuranceNumber;
    private Date modificationDate;
    private String folder;

    public Archive() {
    }

    public Archive(String insuranceNumber, Date modificationDate, String folder) {
        this.insuranceNumber = insuranceNumber;
        this.modificationDate = modificationDate;
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
