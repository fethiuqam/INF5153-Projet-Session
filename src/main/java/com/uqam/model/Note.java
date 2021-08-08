package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tNotes")
public class Note {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    private String content;

    public Note() {}

    public Note(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    @Override
    public String toString() {
        return "Note{" +
                "content='" + content + '\'' +
                '}';
    }
}
