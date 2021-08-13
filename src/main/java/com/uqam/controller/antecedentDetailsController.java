package com.uqam.controller;

import com.uqam.model.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.Optional;

public class antecedentDetailsController {

    @FXML
    private AnchorPane anchorPane;

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
    private DatePicker dateBeginningPicker;

    @FXML
    private Text dateBeginningContent;

    @FXML
    private DatePicker dateEndPicker;

    @FXML
    private Text dateEndContent;

    @FXML
    private HBox ownerBouttons;

    @FXML
    private HBox delete;

    @FXML
    private HBox edit;

    @FXML
    private HBox apply;

    Session session;
    ObservableList<Visit> observableList;
    Antecedent antecedent;

    Doctor doctor;
    Optional<Date> dateBeginning;
    Optional<Date> dateEnd;
    Treatment treatment;
    Diagnostic diagnostic;

    public void setData(Antecedent antecedent, Session session, ObservableList observableList){
        this.session = session;
        this.observableList = observableList;
        this.antecedent = antecedent;
    }

    public void initialize() {
        Platform.runLater(() -> {
            showWindow();
        });
    }

    @FXML
    void applyEdit(MouseEvent event) {

        if (treatmentInputField.getText()!=null) {
            Treatment modifiedTreatment = new Treatment(treatmentInputField.getText());
            antecedent.setTreatment(modifiedTreatment);
        }

        if (diagnosticInputField.getText() != null) {
            Diagnostic modifiedDiagnostic = new Diagnostic(diagnosticInputField.getText());
            antecedent.setDiagnostic(modifiedDiagnostic);
        } else {
            System.out.println("diagnostic is null\n");
        }

        if (dateBeginningPicker.getValue() !=null)
        antecedent.setBeginning(Date.valueOf(dateBeginningPicker.getValue()));

        if (dateEndPicker.getValue()!=null)
        antecedent.setEnd(Date.valueOf(dateEndPicker.getValue()));

        showText();

        hideInputFields();

        //hide apply button
        apply.setVisible(false);

        showWindow();

    }

    @FXML
    void delete(MouseEvent event) {

    }

    @FXML
    void edit(MouseEvent event) {

        hideText();

        showInputFields();

        treatmentInputField.setText(treatment.getDesignation());
        diagnosticInputField.setText(diagnostic.getDesignation());


        dateBeginning.ifPresent(date -> dateBeginningPicker.setValue(date.toLocalDate()));

        dateEnd.ifPresent(date -> dateEndPicker.setValue(date.toLocalDate()));

        apply.setVisible(true);

    }

    private void showWindow() {

        this.doctor = antecedent.getPrescriber();
        this.dateBeginning = antecedent.getBeginning();
        this.dateEnd = antecedent.getEnd();
        this.treatment = antecedent.getTreatment();
        this.diagnostic = antecedent.getDiagnostic();


        doctorContent.setText(doctor.getFirstname()+ " " + doctor.getLastname());
        treatmentContent.setText(treatment.getDesignation());
        diagnoticContent.setText(diagnostic.getDesignation());

        if(dateBeginning.isPresent())
            dateBeginningContent.setText(dateBeginning.get().toString());


        if (dateEnd.isPresent())
            dateEndContent.setText(dateEnd.get().toString());

        //allow edit and delete
        String antecedentDoctor = doctor.getFirstname() + doctor.getLastname();
        String sessionDoctor = session.getDoctor().getFirstname() + session.getDoctor().getLastname();

        if (antecedentDoctor.equals(sessionDoctor)){
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
        dateBeginningContent.setVisible(false);
        dateEndContent.setVisible(false);

    }

    private void showText(){

        treatmentContent.setVisible(true);
        diagnoticContent.setVisible(true);
        dateBeginningContent.setVisible(true);
        dateEndContent.setVisible(true);

    }

    private void hideInputFields() {

        treatmentInputField.setVisible(false);
        diagnosticInputField.setVisible(false);
        dateBeginningPicker.setVisible(false);
        dateEndPicker.setVisible(false);

    }

    private void showInputFields() {

        treatmentInputField.setVisible(true);
        diagnosticInputField.setVisible(true);
        dateBeginningPicker.setVisible(true);
        dateEndPicker.setVisible(true);

    }


}
