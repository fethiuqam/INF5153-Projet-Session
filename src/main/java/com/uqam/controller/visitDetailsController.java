package com.uqam.controller;

import com.uqam.model.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class visitDetailsController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label dateHeader;

    @FXML
    private Label establishmentHeader;

    @FXML
    private StackPane ownerIndicator;

    @FXML
    private Text doctorContent;

    @FXML
    private TextArea treatmentInputField;

    @FXML
    private Text treatmentContent;

    @FXML
    private TextArea diagnosticInputField;

    @FXML
    private Text diagnoticContent;

    @FXML
    private TextArea summaryInputField;

    @FXML
    private Text summaryContent;

    @FXML
    private TextArea noteInputField;

    @FXML
    private Text noteContent;

    @FXML
    private HBox apply;

    @FXML
    private HBox ownerBouttons;

    Session session;
    Visit visit;
    ObservableList<Visit> observableList;

    Doctor doctor;
    Date date;
    Treatment treatment;
    Diagnostic diagnostic;
    String summary;
    String note;
    Establishment establishment;

    public void setVisit(Visit passedVisit) {
        this.visit = passedVisit;
    }

    public void setData(Session session, ObservableList<Visit> observableList){
        this.session = session;
        this.observableList = observableList;
    }

    public void initialize() {

        Platform.runLater(() -> {
            showWindow();
        });



    }

    @FXML
    void editVisit(MouseEvent event) {

       hideText();

       showInputFields();

       treatmentInputField.setText(treatment.getDesignation());
       diagnosticInputField.setText(diagnostic.getDesignation());
       summaryInputField.setText(summary);
       noteInputField.setText(note);

        apply.setVisible(true);

    }

    @FXML
    void deleteVisit(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/deleteConfirmation.fxml"));
            Parent root = fxmlLoader.load();
            deleteConfirmationController controller = fxmlLoader.getController();
            controller.setDataVisit(session, visit, observableList, anchorPane);
            Stage newWindow = new Stage();
            newWindow.setTitle("Confirmation suppression");
            newWindow.getIcons().add(new Image("/images/windowIcon.png"));
            newWindow.setScene(new Scene(root));
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(((Node)event.getSource()).getScene().getWindow() );
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void applyEdit(MouseEvent event) {

        Treatment modifiedTreatment = new Treatment(treatmentInputField.getText());
       visit.setTreatment(modifiedTreatment);

       Diagnostic modifiedDiagnostic = new Diagnostic(diagnosticInputField.getText());
       visit.setDiagnostic(modifiedDiagnostic);

       String modifiedSummary = summaryInputField.getText();
       visit.setSummary(modifiedSummary);

       String modifiedNote =  noteInputField.getText();
       visit.setNotes(modifiedNote);

        showText();

        hideInputFields();

        //hide apply button
        apply.setVisible(false);

        showWindow();
    }

    private void showWindow() {

        this.doctor = visit.getDoctor();
        this.date = visit.getDate();
        this.treatment = visit.getTreatment();
        this.diagnostic = visit.getDiagnostic();
        this.summary = visit.getSummary();
        this.note = visit.getNotes();
        this.establishment = doctor.getEstablishment();

        dateHeader.setText(date.toString());
        establishmentHeader.setText(establishment.getDesignation());

        doctorContent.setText(doctor.getFirstname()+ " " + doctor.getLastname());
        treatmentContent.setText(treatment.getDesignation());
        diagnoticContent.setText(diagnostic.getDesignation());
        summaryContent.setText(summary);
        noteContent.setText(note);

        //allow edit and delete
        String visitDoctor = visit.getDoctor().getFirstname() + visit.getDoctor().getLastname();
        String sessionDoctor = session.getDoctor().getFirstname() + session.getDoctor().getLastname();

        if (visitDoctor.equals(sessionDoctor)){
            ownerBouttons.setVisible(true);
        }else{
            ownerBouttons.setVisible(false);
        }

        //resize window (stage) to fit content
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.sizeToScene();
    }

    private void hideText(){

        treatmentContent.setVisible(false);
        diagnoticContent.setVisible(false);
        summaryContent.setVisible(false);
        noteContent.setVisible(false);

    }

    private void showText(){

        treatmentContent.setVisible(true);
        diagnoticContent.setVisible(true);
        summaryContent.setVisible(true);
        noteContent.setVisible(true);

    }

    private void hideInputFields() {

        treatmentInputField.setVisible(false);
        diagnosticInputField.setVisible(false);
        summaryInputField.setVisible(false);
        noteInputField.setVisible(false);

    }

    private void showInputFields() {

        treatmentInputField.setVisible(true);
        diagnosticInputField.setVisible(true);
        summaryInputField.setVisible(true);
        noteInputField.setVisible(true);

    }

}
