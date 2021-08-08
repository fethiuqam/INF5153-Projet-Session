package com.uqam.controller;

import com.uqam.model.Visit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NoteController {

    @FXML
    private Label date;

    @FXML
    private Text noteContent;

    @FXML
    private HBox edit;

    @FXML
    private HBox apply;

    @FXML
    private TextArea inputField;

    @FXML
    private AnchorPane anchorPane;
    Visit visit;
    String note;

    public void setVisit(Visit passedVisit) {
        this.visit = passedVisit;
        this.note = passedVisit.getNotes();
    }

    public void initialize() {

        Platform.runLater(() -> {

            if (note !=null) {
                showContent();
            }

        });

    }

    @FXML
    void editNote(MouseEvent event) {

        //hide non editable note text
        noteContent.setVisible(false);

        //show editable note text
        inputField.setVisible(true);

        inputField.setText(note);

        //show apply button
        apply.setVisible(true);

    }

    @FXML
    void applyEdit(MouseEvent event) {

        //get note


        note =  inputField.getText();

        //show non editable note text
        noteContent.setVisible(true);

        //hide editable note text
        inputField.setVisible(false);


        //hide apply button
        apply.setVisible(false);

        showContent();
    }

    private void showContent() {
        date.setText(visit.getDate().toString());


        noteContent.setText(note);


        //resize window (stage) to fit content
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.sizeToScene();
    }

}
