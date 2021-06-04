package client.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

	/**
	 * array byte of the uploaded word document file.
	 */
	static byte[] arrByteManualTestUpload;

	/**
	 * flag to know if the user uploaded at least one test
	 */
	static boolean flagForUploadedTest = false;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnDownloadTest;

	@FXML
	private Button btnUploadTest;

	/**
	 * Student chooses where to download the manual test and save it on his
	 * computer.
	 * 
	 * @param event
	 */
	@FXML
	void clickDownloadTest(ActionEvent event) {
		UserController.byteManualTest = null;
		Message msg = new Message(MessageType.downloadManualTest, "0000"); // in manual test id = 0000 always
		ClientUI.accept(msg);
		if (UserController.byteManualTest != null) {
			System.out.println(UserController.byteManualTest);
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialFileName("Manual Test");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document", "*.docx"));
				File selectedDirectory = fileChooser.showSaveDialog(UserController.currentStage);
				if (selectedDirectory == null)
					return; // in case that the user click cancel in choose directory to save the test
				File manualTest = new File(selectedDirectory.getAbsolutePath());

				FileOutputStream fos = new FileOutputStream(manualTest);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				bos.write(UserController.byteManualTest, 0, UserController.byteManualTest.length);

				bos.flush();
				fos.flush();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Student chooses the word document test file he wants to upload
	 * 
	 * @param event
	 */
	@FXML
	void clickUploadTest(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document", "*.docx"));
			File fileToUpload = fileChooser.showOpenDialog(UserController.currentStage);
			if (fileToUpload == null)
				return; // in case that the user click cancel in choose upload file (Manual test)
			arrByteManualTestUpload = new byte[(int) fileToUpload.length()];
			FileInputStream fis = new FileInputStream(fileToUpload);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(arrByteManualTestUpload, 0, arrByteManualTestUpload.length);
			System.out.println(arrByteManualTestUpload);
			bis.close();
			flagForUploadedTest = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * If the student wants to change his word document file that he uploaded he
	 * change it in case he already uploaded once. otherwise get a message
	 * perspectively
	 * 
	 * @param event
	 */
	@FXML
	void clickEditTheSubmission(ActionEvent event) {
		if (flagForUploadedTest) {
			clickUploadTest(event);
		} else {
			ClientUI.display("You must to upload a test for edit your submission");
		}
	}

	/**
	 * sends the array byte of the uploaded word document file to the server and
	 * returns to student menu Stage
	 * 
	 * @param event
	 */
	@FXML
	void clickSubmit(ActionEvent event) {
		Message msg = new Message(MessageType.submitManualTest, arrByteManualTestUpload);
		ClientUI.accept(msg);
		
		if (UserController.flagForSubmittedTestSuccessfully) {
			ClientUI.display("Succsessfully uploaded");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMenuForm.fxml"));
			Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
				//UserController.extraStage = UserController.currentStage; // save the current stage // doesnt have back
																			// button from student menu,-> lo yahol
																		// lahzor ahora la submit test. ma kore be
																			// mikre ve o roze leshanot et aagasha
																			// shelo?
				UserController.currentStage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
