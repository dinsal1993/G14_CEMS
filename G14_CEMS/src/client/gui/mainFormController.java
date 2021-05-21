package client.gui;

import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class mainFormController {

	@FXML
	private Button btnSeeTests;

	@FXML
	private Button btnExit;

	@FXML
	void ClickExit(ActionEvent event) {
		ClientUI.exit();
	}

	@FXML
	void ClickSeeTests(ActionEvent event) throws IOException {

		if (null == ScreenControllers.createTestControl) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTestForm.fxml"));
			Parent root = loader.load();
			
			ScreenControllers.createTestControl = loader.getController();
			Scene scene = new Scene(root);
			if (ClientUI.secondStage == null)
				ClientUI.secondStage = new Stage();
			ClientUI.secondStage.setTitle("See All Tests");
			ClientUI.secondStage.setScene(scene);
			ClientUI.secondStage.show();
			ClientUI.firstStage.hide();
		} else {
			ClientUI.secondStage.show();
			ClientUI.firstStage.hide();
		}
		
		ScreenControllers.createTestControl.start();

	}

	public void start(Stage primaryStage) throws IOException {
	}

}