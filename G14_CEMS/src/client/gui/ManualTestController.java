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

public class ManualTestController {
	
	@FXML
    private Button btnBack;
	
	
    @FXML
    void clickBack(ActionEvent event) 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource
				("TestTypeForm.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.testTypecontrol.start();

    }

}
