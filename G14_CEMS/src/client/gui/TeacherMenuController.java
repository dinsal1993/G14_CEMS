package client.gui;

import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.UserController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TeacherMenuController {

    @FXML
    private Label lblTests;
    
    @FXML
    private Label lblQuestion;

    @FXML
    private Button btnEditTest;

    @FXML
    private Button btnCreateTestBank;

    @FXML
    private Button btnCreateTest;

    @FXML
    private Button btnEditCourses;

    @FXML
    private Button btnCheckTestScore;

    @FXML
    private Button btnSeeTestsStatistics;

    @FXML
    private Button btnManageATest;

    @FXML
    private Button btnCreateQuestionBank;

    @FXML
    private Button btnCreateQuestion;

    @FXML
    private Button btnEditQuestion;

    @FXML
    private Button btnBack;
    
   
    
    @FXML
    void click_createQuestion(ActionEvent event) {
    	
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
    void click_editQuestion(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionForm.fxml"));
		Parent root;
		try {
		ScreenControllers.createQuestionControl = loader.getController();
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
		

    }
    
   
    
    @FXML
    void click_createQbank(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionBankForm.fxml"));
		Parent root;
		try {
		ScreenControllers.createQBankControl = loader.getController();
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

    }

}