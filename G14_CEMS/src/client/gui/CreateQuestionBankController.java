package client.gui;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Question;
import entity.QuestionBank;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateQuestionBankController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FXML
    private AnchorPane UpperAP_CreateQB;

    @FXML
    private Text CreateQBTitle;

    @FXML
    private AnchorPane LowerAP_CreateQB;

    @FXML
    private Text CreateBnote;

    @FXML
    private Label BNtxt;

    @FXML
    private TextField BNtxtFiled;

    @FXML
    private Button CreateQ;

    @FXML
    private Button CreateB;

    @FXML
    private Button CreateQBback;

	public void start() {
		// TODO Auto-generated method stub
		
	}
	 @FXML
	void ClickCreateBank(ActionEvent event) {
		 	
		 if(BNtxtFiled.getText().trim().isEmpty())
		    ClientUI.display("Please first enter bank name");
		 else {
	    	QuestionBank QB  = new QuestionBank(3, BNtxtFiled.getText());
	    	TeacherTestController.insertQuestionBank(QB);}

	 }
	 
	 @FXML
	    void Click_CreateQuestion(ActionEvent event) {
		 //first go to create question window
		 //then back to create question bank window

	    }
    
		@FXML
		void click_Back(ActionEvent event) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml"));
			Parent root;
			try {
				ScreenControllers.teacherMenuController = loader.getController();
				root = loader.load();
				Scene scene = new Scene(root);
				Stage teacherMenu = new Stage();
				teacherMenu.setScene(scene);
				UserController.currentStage.hide(); // close?
				UserController.currentStage = teacherMenu;
				teacherMenu.show();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

  

}
