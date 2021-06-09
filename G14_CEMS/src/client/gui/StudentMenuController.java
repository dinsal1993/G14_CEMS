package client.gui;

import java.io.IOException;

import client.controllers.ScreenControllers;
import client.controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StudentMenuController {

    @FXML
    private Button btnTakeTest;

    @FXML
    private Button btnTestScores;

    @FXML
    private Button btnBack;      

    @FXML
    void clickTakeATest(ActionEvent event) 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("TestTypeForm.fxml"));
		Parent root;
		try {
			ScreenControllers.testTypecontrol = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage testType = new Stage();
			testType.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = testType;
			testType.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void start() {
		// TODO Auto-generated method stub
		
	}

}
