package com.uqam.model;

import com.uqam.controller.FolderController;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tFolders")
public class Folder implements Cloneable, Observer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner")
    private Patient owner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "folder", referencedColumnName = "id")
    private Set<Visit> visits = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "folder", referencedColumnName = "id")
    private Set<Antecedent> antecedents = new HashSet<>();


    public Folder() {
    }

    public Folder(Patient owner, Set<Visit> visits, Set<Antecedent> antecedents) {
        this.owner = owner;
        this.visits = visits;
        this.antecedents = antecedents;
    }

    //methode pour rajouter une visite
    public void addVisit(Visit visite) {
        visits.add(visite);
    }

    //methode pour rajouter un antecedent
    public void addAntecedent(Antecedent antecedent) {
        antecedents.add(antecedent);
    }

    //getters
    public Patient getOwner() {
        return owner;
    }

    public Folder duplicate() {
        Folder clone = new Folder();
        clone.id = this.id;
        clone.owner = this.owner.clone();
        Set<Visit> visitsClone = new HashSet<>();
        for (Visit v : this.visits) {
            visitsClone.add(v.clone());
        }
        clone.visits = visitsClone;
        Set<Antecedent> antecedentsClone = new HashSet<>();
        for (Antecedent a : this.antecedents) {
            antecedentsClone.add(a.clone());
        }
        clone.antecedents = antecedentsClone;
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return id == folder.id && Objects.equals(owner, folder.owner) && Objects.equals(visits, folder.visits) && Objects.equals(antecedents, folder.antecedents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, visits, antecedents);
    }

    @Override
    public String toString() {
        return "Folder{" +
                "owner=" + owner +
                ", visits=" + visits +
                ", antecedents=" + antecedents.toString() +
                '}';
    }

    public Set<Visit> getVisits() {
        return new HashSet<>(visits);
    }

    public Set<Antecedent> getAntecedents() {
        return new HashSet<>(antecedents);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof FolderController) {
            FolderController controller = (FolderController) observable;
            visits = new HashSet<>(controller.getVisits());
            antecedents = new HashSet<>(controller.getAntecedents());
        }
    }
}
