package com.uqam.model;

import javax.persistence.*;

@Entity
@Table(name = "tNotes")
public class Note implements Cloneable{

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    private String content;

    public Note() {
    }

    public Note(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    protected Note clone() {
        try {
            return (Note) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Note{" +
                "content='" + content + '\'' +
                '}';
    }
}
