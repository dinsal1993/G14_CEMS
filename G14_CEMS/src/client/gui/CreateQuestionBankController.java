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

public class CreateQuestionBankController {

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
    
    
	public boolean flag=false;

	 @FXML
	void ClickCreateBank(ActionEvent event) {
		
		 if(BNtxtFiled.getText().trim().isEmpty())
		    ClientUI.display("Please first enter bank name");
		 
		 else { 
			 QuestionBank QB  = new QuestionBank(3, BNtxtFiled.getText());
		    	TeacherTestController.insertQuestionBank(QB);
	    	this.BNtxtFiled.setEditable(false);
	    	this.CreateB.setDisable(true);
	    	
		 }

	 }
	 
	 @FXML
	    void Click_CreateQuestion(ActionEvent event) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionForm.fxml"));
			Parent root;
			try {
			ScreenControllers.createQuestionControl = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage teacher = new Stage();
			teacher.setScene(scene);
			UserController.currentStage = teacher;

			UserController.currentStage.hide(); // close?
			teacher.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

	    }

	 
	@FXML
	    void click_back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource
				("TeacherMenuForm.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.teacherMenuController.start();
	}
 }

