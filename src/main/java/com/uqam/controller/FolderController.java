package com.uqam.controller;

import com.uqam.model.*;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


public class FolderController extends Observable implements Initializable {

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
    private Text expiryDate;


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

    @FXML
    private Text errorMessage;

    @FXML
    private HBox errorInterface;

    @FXML
    private  Text errorMessageAntecedent;

    @FXML
    private  HBox errorInterfaceAntecedent;

    @FXML
    private ImageView expiryWarningIcon;

    public Set<Antecedent> getAntecedents() {
        return new HashSet<>(antecedentsObservableList);
    }

    public Set<Visit> getVisits() {
        return new HashSet<>(visitObservableList);
    }

    public void setSession (Session session){
        this.session = session;
    }

    public static void initialStage(Stage stage , Class cls , Session session) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(cls.getResource("/views/folder.fxml"));
        Parent folderRoot = fxmlLoader.load();
        FolderController controller = fxmlLoader.getController();
        controller.setSession(session);
        Scene scene = new Scene(folderRoot);
        stage.getIcons().add(new Image("/images/windowIcon.png"));
        stage.setTitle("CentRAMQ Accès Médecin - Dossier");
        stage.setScene(scene);
        stage.setResizable(false);
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
            expiryDate.setText(currentPatient.getInsuranceExpirationDate().toString());
            dateOfBirth.setText(currentPatient.getDateOfBirth().toString());
            cityOfBirth.setText(currentPatient.getBirthCity());
            address.setText(currentPatient.getContact().getAddress());
            phone.setText(currentPatient.getContact().getPhone());
            email.setText(currentPatient.getContact().getEmail());
            gender.setText(currentPatient.getGender().toString());
            father.setText(currentPatient.getFather());
            mother.setText(currentPatient.getMother());

            //handle expiry
            if (currentPatient.getInsuranceExpirationDate().before(new Date(System.currentTimeMillis()))){
                expiryDate.setFill(Color.RED);
                expiryWarningIcon.setVisible(true);

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Carte expirée");
                alert.setContentText("La carte d'assurance maladie de ce patient est expiréé.");

                alert.show();

            }

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
            //add current folder as observer
            addObserver(session.getCurrentFolder());
        });
    }

    @FXML
    void addAntecedent(MouseEvent event) {

        //TODO bug - no treament saved if no date start/end
        if (!antecedentDiagnostic.getText().equals("") && !antecedentTreatment.getText().equals("")) {

            //hide error message
            errorInterfaceAntecedent.setVisible(false);

            String diagnosticInput = antecedentDiagnostic.getText();
            String treatmentInput = antecedentTreatment.getText();
            LocalDate dateStartInput = antecedentStartDate.getValue();
            LocalDate dateEndInput = antecedentEndDate.getValue();

            antecedentsObservableList.add(session.createNewAntecedent(diagnosticInput,treatmentInput,dateStartInput,dateEndInput));
            antecedentDiagnostic.clear();
            antecedentTreatment.clear();
            antecedentStartDate.getEditor().clear();
            antecedentEndDate.getEditor().clear();
            setChanged();
            notifyObservers();
        } else {

            //error message
            if (errorInterfaceAntecedent.isVisible()){
                TranslateTransition shake = new TranslateTransition(Duration.millis(40), errorInterfaceAntecedent);
                shake.setFromX(0.0);
                shake.setByX(5);
                shake.setCycleCount(3);
                shake.setAutoReverse(true);
                shake.playFromStart();
            }else{
                errorInterfaceAntecedent.setVisible(true);
            }
            errorMessageAntecedent.setText("Le diagnostic et le traitement de l'antécédant sont nécessaires.");
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


    @FXML
    void restoreVisit(MouseEvent event) throws IOException {

        session.resetFolder();
        Stage mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FolderController.initialStage(mainStage, getClass(), session);

    }

    public void saveFolder(MouseEvent mouseEvent) throws IOException, AppException {
        String buttonText = addVisitLabel.getText();
        if (buttonText.equals("Annuler")){
            if (!visitSummary.getText().equals("")){
                visitObservableList.add(addVisit());
                logout(mouseEvent);
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
                errorMessage.setText("Le résumé de la visite est nécessaire.");
            }

        }else{
            logout(mouseEvent);
        }


    }
    public void logout(MouseEvent mouseEvent) throws IOException, AppException {
        setChanged();
        notifyObservers();
        session.saveFolder();

        Stage mainStage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        HomeController.initialStage(mainStage, getClass(), session);
    }

}
