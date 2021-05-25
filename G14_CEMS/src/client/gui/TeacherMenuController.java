package client.gui;

import java.io.IOException;

import client.controllers.ScreenControllers;
import client.controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public void clickCreateQuestion(ActionEvent event) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionForm.fxml"));
		Parent root;
		try {
			ScreenControllers.createQuestionControl = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage question = new Stage();
			question.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = question;
			question.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    public void clickCreateQuestionBank(ActionEvent event) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionBankForm.fxml"));
		Parent root;
		try {
			ScreenControllers.createQuestionBankControl = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage questionBank = new Stage();
			questionBank.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = questionBank;
			questionBank.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void clickCreateTest(ActionEvent event) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTestForm.fxml"));
		Parent root;
		try {
			ScreenControllers.createTestControl = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage test = new Stage();
			test.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = test;
			test.show();
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