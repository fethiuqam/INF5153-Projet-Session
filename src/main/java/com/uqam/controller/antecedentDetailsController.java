package com.uqam.controller;

import com.uqam.model.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
    private HBox apply;

    @FXML
    private Text errorMessage;

    @FXML
    private HBox errorInterface;

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

        if (!treatmentInputField.getText().equals("") && !diagnosticInputField.getText().equals("")){

            errorInterface.setVisible(false);

                Treatment modifiedTreatment = new Treatment(treatmentInputField.getText());
                antecedent.setTreatment(modifiedTreatment);

                Diagnostic modifiedDiagnostic = new Diagnostic(diagnosticInputField.getText());
                antecedent.setDiagnostic(modifiedDiagnostic);

            if (dateBeginningPicker.getValue() !=null)
                antecedent.setBeginning(Date.valueOf(dateBeginningPicker.getValue()));

            if (dateEndPicker.getValue()!=null)
                antecedent.setEnd(Date.valueOf(dateEndPicker.getValue()));

            showText();

            hideInputFields();

            //hide apply button
            apply.setVisible(false);

            showWindow();

        }else{
            //error message
            if (errorInterface.isVisible()){
                TranslateTransition shake = new TranslateTransition(Duration.millis(40), errorInterface);
                shake.setFromX(0.0);
                shake.setByX(5);
                shake.setCycleCount(3);
                shake.setAutoReverse(true);
                shake.playFromStart();

            }else{
                errorInterface.setVisible(true);
            }
            errorMessage.setText("Le diagnostic et le traitement de l'antécédant sont nécessaires.");
        }

    }

    @FXML
    void delete(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/deleteConfirmation.fxml"));
            Parent root = fxmlLoader.load();
            deleteConfirmationController controller = fxmlLoader.getController();
            controller.setDataAntecedent(session, antecedent, observableList, anchorPane);
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

        if(dateBeginning.isPresent()){
            dateBeginningContent.setText(dateBeginning.get().toString());
        }else{
            dateBeginningContent.setText("Ne s'applique pas");
        }

        if (dateEnd.isPresent()){
            dateEndContent.setText(dateEnd.get().toString());
        }else{
            dateEndContent.setText("Ne s'applique pas");
        }

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
