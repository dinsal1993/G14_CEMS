package client.gui;

import java.io.IOException;

import client.controllers.ClientUI;
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
		if (null == ClientUI.seeTestsControl) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SeeTestsForm.fxml"));
			Parent root = loader.load();
			
			ClientUI.seeTestsControl = loader.getController();
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
		
		ClientUI.seeTestsControl.start();

	}

	public void start(Stage primaryStage) throws IOException {
		//Parent root = (Parent) FXMLLoader.load(getClass().getResource("mainForm.fxml"));
		//Scene scene = new Scene(root);
		// scene.getStylesheets().add(getClass().getResource("/gui/ServerForm.css").toExternalForm());
		//primaryStage.setTitle("Student Main Menu");
		//primaryStage.setScene(scene);

		//primaryStage.show();
	}

}