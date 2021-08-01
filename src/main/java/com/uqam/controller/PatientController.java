package com.uqam.controller;

import com.uqam.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    private ListView<Antecedent> antecedentsListView;

    private ObservableList<Antecedent> antecedentsObservableList;

    public PatientController() {

        // ********* SAMPLE DATA *********
        long millis=System.currentTimeMillis();
        Date debut = new Date(millis);
        Date fin = new Date(millis);
        Diagnostic diagnostic1 = new Diagnostic("diagnostic 1");
        Diagnostic diagnostic2 = new Diagnostic("diagnostic 2");
        Treatment treatment1 = new Treatment("traitement 1 ");
        Treatment treatment2 = new Treatment("traitement 2 ");
        Establishment etablissement1 = new Establishment("0231030213", "Hopital 1");
        Establishment etablissement2 = new Establishment("2323330213", "Hopital 2");
        Doctor doctor1 = new Doctor("Jean", "Dubois", "JDB89019229030", "specialite", etablissement1);
        Doctor doctor2 = new Doctor("Michael", "Desjardins", "MDJ89019229030", "specialite", etablissement2);
        // ********* SAMPLE DATA *********

        HashSet<Antecedent> databaseList = new HashSet<Antecedent>();
        databaseList.add(new Antecedent(debut, fin, diagnostic1, treatment1,doctor1));
        databaseList.add(new Antecedent(debut, fin, diagnostic2, treatment1,doctor2));
        databaseList.add(new Antecedent(debut, fin, diagnostic1, treatment2,doctor2));
        databaseList.add(new Antecedent(debut, fin, diagnostic2, treatment2,doctor1));


        antecedentsObservableList = FXCollections.observableArrayList(databaseList);





    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        antecedentsListView.setItems(antecedentsObservableList);
        antecedentsListView.setCellFactory(antecedentList -> new AntecedentListController());
    }
}
