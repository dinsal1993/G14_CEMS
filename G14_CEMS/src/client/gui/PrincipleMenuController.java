package client.gui;

import java.io.IOException;

import client.controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PrincipleMenuController {

    @FXML
    void clickBack(ActionEvent event) {

    }

    /**
     * 
     * 
     * @param event
     */
    @FXML
    void clickExtraTimeRequests(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PrincipleRequestForm.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

  
    @FXML
    void clickStatisticalReports(ActionEvent event) {
    	System.out.println("yeSSs");
    
    }
    

   @FXML
    void clickSystemData(ActionEvent event) {
    	System.out.println("yesS");

    }

}
