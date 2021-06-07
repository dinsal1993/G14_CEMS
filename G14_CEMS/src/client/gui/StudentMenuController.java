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
    private Button btnTakeATest;

    @FXML
    private Button btnShowScoresAndCopies;

    @FXML
    private Button btnBack;
    
    @FXML
    public void clickTakeATest(ActionEvent event) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("TestTypeForm.fxml"));
		Parent root;
		try {
			ScreenControllers.testTypeController = loader.getController();
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

	public void start() { //ragah
		// TODO Auto-generated method stub
		
	}
    
    

}
