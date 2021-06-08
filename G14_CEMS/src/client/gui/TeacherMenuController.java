package client.gui;

import java.io.IOException;
import java.util.ArrayList;

import client.controllers.ClientUI;
import javafx.application.Application;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Test;
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
    private Button btnViewTest;

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
    
    private ArrayList<Test> tests;
    
    public void start() {
    	
    }
   
   //shahar for planing plan
   /**
    * moves to Plan a test stage
    * @param event
    */
   @FXML
   void clickPlaningATest(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PlanningATestForm.fxml"));
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
    
    @FXML
    void click_createQuestion(ActionEvent event) {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionForm.fxml"));
		Parent root;
		try {
		
		root = loader.load();
		ScreenControllers.createQuestionControl = loader.getController();
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.createQuestionControl.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void click_editQuestion(ActionEvent event) {		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDeleteQuestionForm.fxml"));
		Parent root;
		try {
		
		root = loader.load();
		ScreenControllers.editDeleteQuestionControl = loader.getController();
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.editDeleteQuestionControl.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void click_viewTest(ActionEvent event) {		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTestForm.fxml"));
		Parent root;

		try {
		
		root = loader.load();
		ScreenControllers.createTestControl = loader.getController();
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.createTestControl.startView();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void click_editTest(ActionEvent event) {		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTestForm.fxml"));
		Parent root;

		String username = ScreenControllers.loginFormController.getUsername();
		tests = TeacherTestController.getAllTests(username);

		try {
		
		root = loader.load();
		ScreenControllers.createTestControl = loader.getController();
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.createTestControl.startEdit(tests);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
   
    

    
    @FXML
    void click_CreateTest(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource
				("CreateTestForm.fxml"));
    	Parent root = null;
    	
    	try {
			root = loader.load();
			ScreenControllers.createTestControl = loader.getController();
	    	Scene scene = new Scene(root);

	    	UserController.currentStage.setScene(scene);
	    	ScreenControllers.createTestControl.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
  
    @FXML
    public void clickManageTest(ActionEvent event) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageTestForm.fxml"));
		Parent root;
		try {
			ScreenControllers.manageTestControl = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage manage = new Stage();
			manage.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = manage;
			manage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}