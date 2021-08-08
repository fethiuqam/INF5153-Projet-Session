package com.uqam.controller;

import com.uqam.model.Note;
import com.uqam.model.Visit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Set;

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
    Set<Note> note;

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

        //get note
        Iterator<Note> iter = note.iterator();
        Note first = iter.next();

        inputField.setText(first.getContent());

        //show apply button
        apply.setVisible(true);

    }

    @FXML
    void applyEdit(MouseEvent event) {

        //get note
        Iterator<Note> iter = note.iterator();
        Note first = iter.next();

        first.setContent(inputField.getText());

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

        //get note
        Iterator<Note> iter = note.iterator();
        if(iter.hasNext()){
            Note first = iter.next();
            noteContent.setText(first.getContent());
        }

        //resize window (stage) to fit content
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.sizeToScene();
    }

}
