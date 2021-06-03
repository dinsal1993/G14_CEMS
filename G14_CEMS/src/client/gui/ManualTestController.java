package client.gui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ManualTestController {

	@FXML
	private Button btnBack;

	@FXML
	private Button btnDownloadTest;

	@FXML
	private Button btnUploadTest;

	@FXML
	void clickDownloadTest(ActionEvent event)  {
		UserController.byteManualTest = null;
		Message msg = new Message(MessageType.downloadManualTest, "0000"); // in manual test id = 0000 always
		ClientUI.accept(msg);
		if (UserController.byteManualTest != null) {
			System.out.println(UserController.byteManualTest);
////////////////////////////////////////////////////////// from internet there is a exception while we do cancel. what to do about it/
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialFileName("Manual Test");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document", ".docx"));
				File selectedDirectory = fileChooser.showSaveDialog(UserController.currentStage);
				if(selectedDirectory==null)return; // in case that the user click cancel in choose directory to save the test
				File manualTest = new File(selectedDirectory.getAbsolutePath()); 
						
				FileOutputStream fos = new FileOutputStream(manualTest);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				bos.write(UserController.byteManualTest, 0, UserController.byteManualTest.length);
			
				bos.flush();
				fos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
	}

	@FXML
	void uploadTest(ActionEvent event) {

	}

	@FXML
	void back(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TestTypeForm.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			UserController.extraStage = UserController.currentStage; // save the current stage

			UserController.currentStage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
