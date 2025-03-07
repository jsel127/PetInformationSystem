package Pet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PetController implements Initializable {
    @FXML
    private Button myLogEventBtn;
    @FXML
    private Button myGenerateTrainingReportBtn;
    @FXML
    private Button myGenerateExerciseReportBtn;
    @FXML
    private Button myGeneratePottyReportBtn;
    private int myPetID;
    public void initialize(URL theURL, ResourceBundle theRB) {

    }

    public void setPetID(int thePetID) {
        if (thePetID > 0) {
            myPetID = thePetID;
        }
    }

    @FXML
    public void openLogEventFXML(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myLogEventBtn.getScene().getWindow();
            currentStage.close();

            Stage eventStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/event.fxml").openStream());

            EventController eventController = (EventController) loader.getController();
            eventController.setPetID(myPetID);

            Scene scene = new Scene(root);
            eventStage.setScene(scene);
            eventStage.setTitle("Event Dashboard");
            eventStage.setResizable(false);
            eventStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openGenerateTrainingReportFXML(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myGenerateTrainingReportBtn.getScene().getWindow();
            currentStage.close();

            Stage reportStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Report/trainingReport.fxml").openStream());

            ReportController reportController = (ReportController) loader.getController();
            reportController.setPetID(myPetID);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Report Dashboard");
            reportStage.setResizable(false);
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openGenerateExerciseReportFXML(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myGenerateExerciseReportBtn.getScene().getWindow();
            currentStage.close();

            Stage reportStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Report/exerciseReport.fxml").openStream());

            ReportController reportController = (ReportController) loader.getController();
            reportController.setPetID(myPetID);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Report Dashboard");
            reportStage.setResizable(false);
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}