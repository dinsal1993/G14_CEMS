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
    
    public void start() {
    	
    }
    
    @FXML
    void click_CreateTest(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource
				("CreateTestForm.fxml"));
    	Parent root = null;
    	
    	try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ScreenControllers.createTestControl = loader.getController();
    	Scene scene = new Scene(root);

    	UserController.currentStage.setScene(scene);
    	ScreenControllers.createTestControl.start();
    	
    }

}