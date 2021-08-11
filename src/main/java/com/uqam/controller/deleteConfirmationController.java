package com.uqam.controller;


import com.uqam.model.Antecedent;
import com.uqam.model.Session;
import com.uqam.model.Visit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class deleteConfirmationController {

    Session session;
    Visit visit;
    Antecedent antecedent;
    ObservableList observableList;
    AnchorPane anchorPane;

    @FXML
    private Text itemToDelete;

    @FXML
    private HBox delete;

    @FXML
    private HBox cancel;

    @FXML
    void cancelDelete(MouseEvent event) {

        Stage stage = (Stage) cancel.getScene().getWindow();

//        stage.setOnHiding(new EventHandler<WindowEvent>() {
//
//            @Override
//            public void handle(WindowEvent event) {
//                Platform.runLater(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        session.deleteVisit(visit);
//                        observableListVisit.remove(visit);
//                        Stage previousStage = (Stage) anchorPane.getScene().getWindow();
//                        previousStage.close();
//
//                    }
//                });
//            }
//        });

        stage.close();
    }

    @FXML
    void deleteObject(MouseEvent event) {

        switch (itemToDelete.getText()){
            case "cette visite?":
                session.deleteVisit(visit);
                observableList.remove(visit);
            break;
            case "cet antecedent":
                observableList.remove(antecedent);
            break;
        }


        Stage stage = (Stage) delete.getScene().getWindow();
        stage.close();
        Stage previousStage = (Stage) anchorPane.getScene().getWindow();
        previousStage.close();
    }

    public void setDataVisit(Session session, Visit visit, ObservableList observableList, AnchorPane anchorPane){
        this.itemToDelete.setText("cette visite?");
        this.session = session;
        this.visit = visit;
        this.observableList = observableList;
        this.anchorPane = anchorPane;
    }

    public void setDataAntecedent(Session session, Antecedent antecedent, ObservableList observableList, AnchorPane anchorPane){
        this.itemToDelete.setText("cet antecedent?");
        this.session = session;
        this.antecedent = antecedent;
        this.observableList = observableList;
        this.anchorPane = anchorPane;
    }



}