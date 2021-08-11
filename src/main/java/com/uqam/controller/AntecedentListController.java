package com.uqam.controller;

import com.uqam.model.Antecedent;
import com.uqam.model.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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

    @FXML
    private StackPane ownerIndicator;

    private Session session;

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

            if (antecedent.getDiagnostic().getDesignation() != null)
                diagnostique.setText(antecedent.getDiagnostic().getDesignation());

            traitement.setText(antecedent.getTreatment().getDesignation());

             if (antecedent.getPrescriber() != null)
                 medecinTraitant.setText(antecedent.getPrescriber().getFirstname() + " " + antecedent.getPrescriber().getLastname());


            if (antecedent.getBeginning().isPresent())
                dateDebut.setText(antecedent.getBeginning().get().toString());


            if (antecedent.getEnd().isPresent())
                dateFin.setText(antecedent.getEnd().get().toString());

            // Adjust owner/doctor indicator
            String antecedentDoctor = antecedent.getPrescriber().getFirstname() + antecedent.getPrescriber().getLastname();
            String sessionDoctor = session.getDoctor().getFirstname() + session.getDoctor().getLastname();

            if(antecedentDoctor.equals(sessionDoctor)){
                ownerIndicator.setVisible(true);
            }else{
                ownerIndicator.setVisible(false);
            }

            setText(null);
            setGraphic(antecedentCell);
        }

    }

    public void setSession(Session session){
        this.session = session;
    }
}
