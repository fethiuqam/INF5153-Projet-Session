package com.uqam.controller;

import com.uqam.model.Antecedent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.io.IOException;

public class AntecedentListController extends ListCell<Antecedent> {

    @FXML
    private AnchorPane antecedentCell;

    private FXMLLoader loader;

    @FXML
    private Text traitement;

    @FXML
    private Text diagnostique;

    @FXML
    private Text medecinTraitant;

    @FXML
    private Text dateDebut;

    @FXML
    private Text dateFin;

    //datepicker java
    //LocalDate locald = LocalDate.of(1967, 06, 22);
    //Date date = Date.valueOf(locald); // Magic happens here!

    @Override
    protected void updateItem(Antecedent antecedent, boolean empty) {
        super.updateItem(antecedent, empty);

        if (empty || antecedent == null) {

            setText(null);
            setGraphic(null);

        } else {

            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/views/AntecedentListCell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (diagnostique != null)
                diagnostique.setText(antecedent.getDiagnostic().getDesignation());

            if (traitement != null){
                if (antecedent.getTreatment() != null){
                    traitement.setText(antecedent.getTreatment().getDesignation());
                }
            }

            if (medecinTraitant != null){
                if (antecedent.getPrescriber() != null){
                    medecinTraitant.setText(antecedent.getPrescriber().getFirstname() + " " + antecedent.getPrescriber().getLastname());
                }
            }


            if (dateDebut != null){
                if (antecedent.getBeginning() != null)
                    dateDebut.setText(antecedent.getBeginning().toString());
            }

            if (dateFin != null){
                if (antecedent.getEnd()!= null)
                    dateFin.setText(antecedent.getEnd().toString());
            }

            setText(null);
            setGraphic(antecedentCell);
        }

    }
}
