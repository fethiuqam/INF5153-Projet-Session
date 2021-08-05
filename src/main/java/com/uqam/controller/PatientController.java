package com.uqam.controller;

import com.uqam.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    // Medical history
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

    // Antecedent input

    @FXML
    private TextField antecedentDiagnostic;

    @FXML
    private TextField antecedentTreatment;

    // Header bouttons
    @FXML
    private HBox cancel;



    public PatientController() {

        // ********* SAMPLE DATA *********

        long millis=System.currentTimeMillis();
        Date debut = new Date(millis);
        Date fin = new Date(millis);
        Diagnostic diagnostic1 = new Diagnostic("diagnostic 1");
        Diagnostic diagnostic2 = new Diagnostic("diagnostic 2");
        Treatment treatment1 = new Treatment("traitement 1 ");
        Treatment treatment2 = new Treatment("traitement 2 ");
        Establishment etablissement1 = new Establishment("0231030213", "Hopital 1");
        Establishment etablissement2 = new Establishment("2323330213", "Hopital 2");
        Doctor doctor1 = new Doctor("Jean", "Dubois", "JDB89019229030", "specialite", etablissement1);
        Doctor doctor2 = new Doctor("Michael", "Desjardins", "MDJ89019229030", "specialite", etablissement2);
        String noteContent1 = "Maecenas et ligula sit amet elit molestie facilisis. Sed massa erat, pharetra non vestibulum ut, ullamcorper at ex. Nam ullamcorper justo semper, tincidunt libero at, eleifend felis. Cras vitae quam vel massa porta volutpat. Duis malesuada massa in mauris consequat, non malesuada magna ornare. Ut et vulputate nisi, elementum molestie ipsum. Pellentesque a gravida arcu. Vestibulum condimentum orci eu orci congue, ac bibendum sapien elementum. Nulla sed nunc laoreet, commodo eros sit amet, pretium massa. Nullam interdum efficitur consequat. Morbi sit amet luctus nulla, ut iaculis mi. Praesent at leo eget risus maximus vestibulum ac vitae purus. Phasellus dignissim sem eget nisi tempor cursus. Quisque sagittis sem id mauris efficitur varius. Phasellus dignissim volutpat rutrum. ";
        String noteContent2 = "Amet duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie faucibus aliquet gravida turpis. Volutpat consequat elit faucibus sed. Pulvinar ut bibendum amet fames vulputate.";
        String noteContent3 = "Troisieme type de Note duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie faucibus aliquet gravida turpis. Volutpat consequat elit faucibus duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie  duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie  duis tincidunt odio sed tempus aliquam duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie  duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie  duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie  duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie  duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie  duis tincidunt odio sed tempus aliquam purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie purus venenatis dignissim. Morbi fusce augue id massa sit donec. Fames eget tempor vivamus feugiat dignissim tempor elit blandit nullam. Augue cras malesuada aliquam vitae consectetur adipiscing lectus ultricies lectus. Venenatis risus molestie";
        Note note1 = new Note(noteContent1);
        Note note2 = new Note(noteContent2);
        Note note3 = new Note(noteContent3);
        Contact patientContactInformation = new Contact("8774 Merry Meadow", "(623) 488-8798", "Azadeh.Rackley@gmail.com");
        currentPatient = new Patient("Azadeh", "Rackley", Gender.FEMALE, debut, "Blackwater", "867173283", patientContactInformation, null );
        // ********* SAMPLE DATA *********

        HashSet<Antecedent> databaseList = new HashSet<Antecedent>();
        databaseList.add(new Antecedent(debut, fin, diagnostic1, treatment1,doctor1));
        databaseList.add(new Antecedent(debut, fin, diagnostic2, treatment1,doctor2));
        databaseList.add(new Antecedent(debut, fin, diagnostic1, treatment2,doctor2));
        databaseList.add(new Antecedent(debut, fin, diagnostic2, treatment2,doctor1));

        HashSet<Visit> visitList = new HashSet<>();
        visitList.add(new Visit(debut, ("Resume pour la visite #1") , note1, treatment1, diagnostic1, doctor1 ));
        visitList.add(new Visit(debut, ("Resume pour la visite #2") , null, treatment1, diagnostic1, doctor1 ));
        visitList.add(new Visit(debut, ("Resume pour la visite #3") , null, treatment2, diagnostic1, doctor2 ));
        visitList.add(new Visit(debut, ("Resume pour la visite #4") , note2, treatment1, diagnostic2, doctor2 ));
        visitList.add(new Visit(debut, ("Resume pour la visite #5") , note3, treatment1, diagnostic1, doctor1 ));


        antecedentsObservableList = FXCollections.observableArrayList(databaseList);

        visitObservableList = FXCollections.observableArrayList(visitList);




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        antecedentsListView.setItems(antecedentsObservableList);
        antecedentsListView.setCellFactory(antecedentList -> new AntecedentListController());

        visitListView.setItems(visitObservableList);
        visitListView.setCellFactory(visitList -> new visitListController());

//        visitListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//                                            @Override
//                                            public void handle(MouseEvent click) {
//
//                                                Visit visitSelected = visitListView.getSelectionModel()
//                                                        .getSelectedItem();
//
//                                                try {
//                                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/note.fxml"));
//                                                    Parent root = (Parent) fxmlLoader.load();
//                                                    NoteController controller = fxmlLoader.getController();
//                                                    controller.setVisit(visitSelected);
//                                                    Stage newWindow = new Stage();
//
//                                                    newWindow.setTitle("Notes");
//                                                    newWindow.setScene(new Scene(root));
//                                                    newWindow.show();
//                                                } catch (IOException e) {
//                                                    e.printStackTrace();
//                                                }
//
//
//                                            }
//    });


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


    }

    @FXML
    void cancelVisit(MouseEvent event) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
        var scene = new Scene(newRoot);
        Stage mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainStage.setScene(scene);
    }

}
