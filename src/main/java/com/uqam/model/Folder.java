package com.uqam.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

@Entity
@Table(name = "tFolders")
public class Folder implements Cloneable{

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name="owner")
    private Patient owner;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="folder", referencedColumnName="id")
    private Set<Visit> visits = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="folder", referencedColumnName="id")
    private Set<Antecedent> antecedents = new HashSet<>();

    private void addVisit(Visit visite){
        visits.add(visite);
    }
    private void addAntecedent(Antecedent antecedent){
        antecedents.add(antecedent);
    }


    public Patient getPatient() {
        return owner;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public Set<Antecedent> getAntecedents() {
        return antecedents;
    }

    @Override
    public Folder clone() {
        try {
            Folder clone = (Folder) super.clone();
            clone.owner = this.owner.clone();
            Set<Visit> visitsClone = new HashSet<>();
            for (Visit v :this.visits) {
                visitsClone.add(v.clone());
            }
            clone.visits = visitsClone;
            Set<Antecedent> antecedentsClone = new HashSet<>();
            for (Antecedent a :this.antecedents) {
                antecedentsClone.add(a.clone());
            }
            clone.antecedents = antecedentsClone;
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "owner=" + owner +
                ", visits=" + visits +
                ", antecedents=" + antecedents.toString() +
                '}';
    }
}
