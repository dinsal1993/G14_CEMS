package client.gui;

import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginFormController {

	@FXML
	private Button btnLogin;

	@FXML
	private Label lblUsername;

	@FXML
	private Label lblPassword;

	@FXML
	private Button btnRegister;

	@FXML
	private Button btnForgetPassword;

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	public String getUsername() {
		return txtUsername.getText();
	}

	private String getPassword() {
		return txtPassword.getText();
	}

	public void start(Stage primaryStage) throws IOException {

	}

	@FXML
	void Login(ActionEvent event) {

		String logInStatus = UserController.logIn(getUsername(),
				getPassword());
		switch (logInStatus) {
		case "You must fill all the fields":
		case "The user is not exist":
		case "The password is incorrect":
		case "The user is already connected":
			ClientUI.display(logInStatus);
			break;
		case "teacher": // openTeacherMenuForm();

			FXMLLoader loader = new FXMLLoader(getClass().getResource
					("TeacherMenuForm.fxml"));
			Parent root;
			try {
				ScreenControllers.teacherMenuController = 
						loader.getController();
				root = loader.load();
				Scene scene = new Scene(root);
				Stage teacher = new Stage();
				teacher.setScene(scene);
				UserController.currentStage.hide(); // close?
				UserController.currentStage = teacher;
				teacher.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			ClientUI.display("cant read message from server");
		}

	}

}
