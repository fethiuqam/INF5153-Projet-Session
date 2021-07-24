package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tNotes")
public class Note {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    private String content;

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "content='" + content + '\'' +
                '}';
    }
}
