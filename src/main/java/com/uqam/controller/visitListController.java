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
import java.util.Iterator;

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
    private StackPane openNoteButton;

    Visit currentVisit;

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

            //get note

            if (!visit.getNotes().isEmpty()) {
                Color notePresentColor = Color.web("#9BC9F6", 1.0);
                notesNumber.setText("1");
                notesCircleIndicator.setFill(notePresentColor);
                openNoteButton.setDisable(false);
            } else {
                Color notePresentColor = Color.web("#DDEEFF", 1.0);
                notesNumber.setText("0");
                notesCircleIndicator.setFill(notePresentColor);
                this.openNoteButton.setDisable(true);
            }

            setText(null);
            setGraphic(visitCell);
        }

    }

    @FXML
    void openNote(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/note.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            NoteController controller = fxmlLoader.getController();
            controller.setVisit(currentVisit);
            Stage newWindow = new Stage();
            newWindow.setTitle("Notes");
            newWindow.getIcons().add(new Image("/images/windowIcon.png"));
            newWindow.setScene(new Scene(root));
            // ajout FETHI
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(((Node)event.getSource()).getScene().getWindow() );
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
