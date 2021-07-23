package com.uqam.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tFolders")
public class Folder {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name="owner")
    private Patient owner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="folder", referencedColumnName="id")
    private List<Visit> visits;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="folder", referencedColumnName="id")
    private List<Antecedent> antecedents;

    public Patient getOwner() {
        return owner;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public List<Antecedent> getAntecedents() {
        return antecedents;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "owner=" + owner +
                ", visits=" + visits +
                ", antecedents=" + antecedents +
                '}';
    }
}
