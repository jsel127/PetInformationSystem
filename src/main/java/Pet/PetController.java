package Pet;

import Owner.ExpenseController;
import Owner.OwnerController;
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
    private Button myGenerateReportBtn;
    @FXML
    private Button myReturnOwnerPageBtn;

    private int myPetID;
    private int myUserID;

    public void initialize(URL theURL, ResourceBundle theRB) {

    }

    public void setPetID(int thePetID) {
        if (thePetID < 1) {
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
    public void openGenerateReportFXML(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myGenerateReportBtn.getScene().getWindow();
            currentStage.close();

            Stage reportStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Pet/report.fxml").openStream());

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

    /**
     * Returns to the Owner Dashboard.
     * @param theEvent the event that triggered the return.
     */
    @FXML
    public void returnToOwnerDashboard(ActionEvent theEvent) {
        try {
            Stage currentStage = (Stage) myReturnOwnerPageBtn.getScene().getWindow();
            currentStage.close();

            Stage expenseStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Owner/owner.fxml").openStream());

            OwnerController ownerController = (OwnerController) loader.getController();
            ownerController.setUserID(myUserID);
            Scene scene = new Scene(root);
            expenseStage.setScene(scene);
            expenseStage.setTitle("Owner Dashboard");
            expenseStage.setResizable(false);
            expenseStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
