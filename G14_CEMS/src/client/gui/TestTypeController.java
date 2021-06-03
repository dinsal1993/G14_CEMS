package client.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TestTypeController {

	@FXML
	private TextField txtExecCode;

	/**
	 * 
	 * @return
	 */
	public String getTxtExecCode() {
		return txtExecCode.getText();
	}

	@FXML
	private Button btnBack;

	@FXML
	private Button btnOnlineTest;

	@FXML
	private Button btnManualTest;

	/**
	 * change the stage to the manual test form in case the execution code is right
	 * 
	 * @param event
	 */
	@FXML
	void clickManualTest(ActionEvent event) {
		UserController.CurrentTestID = null;
		Message msg = new Message(MessageType.execCode, getTxtExecCode());
		ClientUI.accept(msg);
		if (UserController.CurrentTestID != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ManualTestForm.fxml"));
			Parent root;
			try {
				ScreenControllers.manualTestController = loader.getController();
				root = loader.load();
				Scene scene = new Scene(root);
				UserController.extraStage = UserController.currentStage; // save the current stage

				UserController.currentStage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void back(ActionEvent event) {

		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("StudentMenuForm.fxml")); // change the name to
																								// StudentMenu"Form"
		Parent root2;
		try {
			UserController.extraStage = null;
			root2 = loader2.load();
			ScreenControllers.studentMenuController = loader2.getController();
			Scene scene = new Scene(root2);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
