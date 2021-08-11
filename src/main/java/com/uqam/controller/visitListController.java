package com.uqam.controller;

import com.uqam.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class visitListController extends ListCell<Visit> {

    @FXML
    private AnchorPane visitCell;

    private FXMLLoader loader;

    @FXML
    private Label establishment;

    @FXML
    private Text doctor;

    @FXML
    private Text treatment;

    @FXML
    private Text diagnostic;

    @FXML
    private Text date;

    @FXML
    private Text summary;

    @FXML
    private Circle notesCircleIndicator;

    @FXML
    private Text notesNumber;

    @FXML
    private StackPane ownerIndicator;

    private Visit currentVisit;

    private Session session;

    @Override
    protected void updateItem(Visit visit, boolean empty) {
        super.updateItem(visit, empty);

        if (empty || visit == null) {

            setText(null);
            setGraphic(null);

        } else {

            currentVisit = visit;

            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/views/VisitListCell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            setVisitInformation(visit);

            // Adjust note indicator
            if (!visit.getNotes().isEmpty()) {
                Color notePresentColor = Color.web("#9BC9F6", 1.0);
                notesNumber.setText("1");
                notesCircleIndicator.setFill(notePresentColor);
            } else {
                Color notePresentColor = Color.web("#DDEEFF", 1.0);
                notesNumber.setText("0");
                notesCircleIndicator.setFill(notePresentColor);
            }

            // Adjust owner/doctor indicator
            String visitDoctor = visit.getDoctor().getFirstname() + visit.getDoctor().getLastname();
            String sessionDoctor = session.getDoctor().getFirstname() + session.getDoctor().getLastname();

            if(visitDoctor.equals(sessionDoctor)){
                ownerIndicator.setVisible(true);
            }else{
                ownerIndicator.setVisible(false);
            }

            setText(null);
            setGraphic(visitCell);
        }

    }

    public void setVisitInformation (Visit visit){
        if (visit.getDiagnostic() != null)
            diagnostic.setText(visit.getDiagnostic().getDesignation());

        if (visit.getTreatment() != null)
            treatment.setText(visit.getTreatment().getDesignation());

        if (visit.getDoctor() != null){
            doctor.setText(visit.getDoctor().getFirstname() + " " + visit.getDoctor().getLastname());
            establishment.setText(visit.getDoctor().getEstablishment().getDesignation());
        }

        if (visit.getDate() != null)
            date.setText(visit.getDate().toString());

        if (visit.getSummary() != null)
            summary.setText(visit.getSummary());

    }


    public void setSession(Session session){
        this.session = session;
    }

}
