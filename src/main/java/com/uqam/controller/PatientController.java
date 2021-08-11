package com.uqam.controller;

import com.uqam.model.*;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.*;

//TODO remove "edit" on notes due to not being able to access original Visits.

public class PatientController extends Observable implements Initializable {

    Session session;

    // Listviews
    private ObservableList<Antecedent> antecedentsObservableList;

    private  ObservableList<Visit> visitObservableList;

    @FXML
    private ListView<Antecedent> antecedentsListView;

    @FXML
    private ListView<Visit> visitListView;

    // Patient information
    Patient currentPatient;

    @FXML
    private Text name;

    @FXML
    private Text firstName;

    @FXML
    private Text insuranceNumber;

    @FXML
    private Text dateOfBirth;

    @FXML
    private Text cityOfBirth;

    @FXML
    private Text address;

    @FXML
    private Text phone;

    @FXML
    private Text email;

    @FXML
    private Text gender;

    @FXML
    private Text father;

    @FXML
    private Text mother;

    // Current visit input

    @FXML
    private TextField visitDiagnostic;

    @FXML
    private TextField visitTreatment;

    @FXML
    private TextArea visitSummary;

    @FXML
    private TextArea visitNotes;

    // Antecedent input

    @FXML
    private TextField antecedentDiagnostic;

    @FXML
    private TextField antecedentTreatment;

    @FXML
    private DatePicker antecedentStartDate;

    @FXML
    private DatePicker antecedentEndDate;

    // new visit

    @FXML
    private VBox newVisitFields;

    @FXML
    private HBox addVisitButton;

    @FXML
    private Label addVisitLabel;

    @FXML
    private ImageView plusIcon;

    RotateTransition plusToCross;

    @FXML
    private Label currentVisitDate;

    @FXML
    private Label currentVisitEstablishment;


    public PatientController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {


            currentPatient = session.getCurrentFolder().getOwner();
            plusToCross = new RotateTransition(Duration.millis(400), plusIcon);

            // Setting patient information text
            name.setText(currentPatient.getLastname());
            firstName.setText(currentPatient.getFirstname());
            insuranceNumber.setText(currentPatient.getInsuranceNumber());
            dateOfBirth.setText(currentPatient.getDateOfBirth().toString());
            cityOfBirth.setText(currentPatient.getBirthCity());
            address.setText(currentPatient.getContact().getAddress());
            phone.setText(currentPatient.getContact().getPhone());
            email.setText(currentPatient.getContact().getEmail());
            gender.setText(currentPatient.getGender().toString());
            father.setText(currentPatient.getFather());
            mother.setText(currentPatient.getMother());

            // Setting new Visit non modifiable information
            currentVisitDate.setText(new Date(System.currentTimeMillis()).toString());
            currentVisitEstablishment.setText(session.getDoctor().getEstablishment().getDesignation());

            //Antecedent list
            antecedentsObservableList = FXCollections.observableArrayList(session.getAntecedents());
            antecedentsListView.setItems(antecedentsObservableList);
            antecedentsListView.setCellFactory(antecedentList -> {
                AntecedentListController antecedentListController = new AntecedentListController();
                antecedentListController.setSession(session);
                return antecedentListController;
            });

            //Visit list
            visitObservableList = FXCollections.observableArrayList(session.getVisits());
            visitListView.setItems(visitObservableList);
            visitListView.setCellFactory(visitList -> {
                visitListController visitListController = new visitListController();
                visitListController.setSession(session);
                return  visitListController;
            });

            //Antecedent list on click listener
           antecedentsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Antecedent currentAntecedent = antecedentsListView.getSelectionModel().getSelectedItem();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/antecedentDetails.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        antecedentDetailsController controller = fxmlLoader.getController();
                        controller.setData(currentAntecedent,session,antecedentsObservableList);
                        Stage newWindow = new Stage();
                        newWindow.setTitle("Antecedent");
                        newWindow.getIcons().add(new Image("/images/windowIcon.png"));
                        newWindow.setScene(new Scene(root));
                        newWindow.initModality(Modality.WINDOW_MODAL);
                        newWindow.initOwner(((Node)event.getSource()).getScene().getWindow() );

                        // update data on detail window close
                        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent e) {
                                antecedentsListView.refresh();
                            }
                        });

                        newWindow.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            //Visit list on click listener
            visitListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Visit currentVisit = visitListView.getSelectionModel().getSelectedItem();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/visiteDetails.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        visitDetailsController controller = fxmlLoader.getController();
                        controller.setVisit(currentVisit);
                        controller.setData(session,visitObservableList);
                        Stage newWindow = new Stage();
                        newWindow.setTitle("Visite");
                        newWindow.getIcons().add(new Image("/images/windowIcon.png"));
                        newWindow.setScene(new Scene(root));
                        newWindow.initModality(Modality.WINDOW_MODAL);
                        newWindow.initOwner(((Node)event.getSource()).getScene().getWindow() );

                        // update data on detail window close
                        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent e) {
                                visitListView.refresh();
                            }
                        });

                        newWindow.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            addObserver(session.getCurrentFolder());
            addObserver(session);
        });
    }

    @FXML
    void addAntecedent(MouseEvent event) {

    }

    @FXML
    void showVisitCreator(MouseEvent event) {

        String buttonText = addVisitLabel.getText();

        if (plusToCross.getStatus() != Animation.Status.RUNNING){

            if (buttonText.equals("Ajouter")){

                newVisitFields.setVisible(true);
                addVisitLabel.setText("Annuler");

                plusToCross.setByAngle(45);

            } else {

                newVisitFields.setVisible(false);
                addVisitLabel.setText("Ajouter");

                plusToCross.setByAngle(-45);

            }

            plusToCross.setCycleCount(1);
            plusToCross.setInterpolator(Interpolator.EASE_OUT);
            plusToCross.play();

        }

    }

    Visit addVisit(){
        String diagnosticInput = visitDiagnostic.getText();
        String treatmentInput = visitTreatment.getText();
        String summaryInput = visitSummary.getText();
        String notesInput = visitNotes.getText();
        return session.createNewVisit(diagnosticInput,treatmentInput,summaryInput,notesInput);
    }

    @FXML
    void cancelVisit(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
        Parent homeRoot = (Parent) fxmlLoader.load();

        connectSession(fxmlLoader, session);
        var scene = new Scene(homeRoot);
        Stage mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainStage.setScene(scene);
    }

    public void setSession (Session session){
        this.session = session;
    }

    public void connectSession (FXMLLoader fxmlLoader, Session session){
        //add session to controller
        HomeController controller = fxmlLoader.getController();
        controller.setSession(session);
    }

    public void saveFolder(MouseEvent mouseEvent) throws IOException {
        String buttonText = addVisitLabel.getText();
        if (buttonText.equals("Annuler")){
            visitObservableList.add(addVisit());
        }
        setChanged();
        notifyObservers();
        try {
            session.saveFolder();
            System.out.println("dossier sauvegardé");
        } catch (AppException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
        Parent homeRoot = (Parent) fxmlLoader.load();

        session.reinitializeFolder();

        connectSession(fxmlLoader, session);
        var scene = new Scene(homeRoot);
        Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        mainStage.setScene(scene);

    }

    public Set<Antecedent> getAntecedents() {
        return new HashSet<>(antecedentsObservableList);
    }

    public Set<Visit> getVisits() {
        return new HashSet<>(visitObservableList);
    }
}
