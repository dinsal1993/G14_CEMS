package client.gui;

import java.io.IOException;

import client.controllers.ClientUI;
import javafx.application.Application;
import client.controllers.ScreenControllers;
import client.controllers.UserController;
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
  
   public void start() {
    	
    }
    
    @FXML
    void click_CreateQuestion(ActionEvent event) {
    	
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
    void click_CreateQBank(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionBankForm.fxml"));
		Parent root;
		try {
		
		root = loader.load();
		ScreenControllers.createQBankControl = loader.getController();
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.createQBankControl.start();
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
			// TODO Auto-generated catch block
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
       @FXML
    public void clickEditCourses(ActionEvent event) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCoursesForm.fxml"));
		Parent root;
		try {
			ScreenControllers.editCoursesControl = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage editCourse = new Stage();
			editCourse.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = editCourse;
			editCourse.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}