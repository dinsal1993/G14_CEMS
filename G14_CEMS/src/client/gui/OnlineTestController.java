package client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Question;
import entity.Test;
import entity.TestBank;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OnlineTestController {

    @FXML
    private Button btnStartTest;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtExecutionCode;
    
    @FXML
    private Label lblValidCode;
    
    public static ObservableList<Question> testQuestionsList;
    public static Test test;
    public static ObservableList<Test> tests;
    public static ArrayList<String> studentDetails;
    
    
    @FXML
    void clickBack(ActionEvent event) 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource
				("TestTypeForm.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.testTypecontrol.start();

    }
    
    @FXML
    void clickStart(ActionEvent event) 
    {
    	if(isExecutionCodeValid())
		{
    		lblValidCode.setText("Its A Valid Code!");
    		lblValidCode.setTextFill(Color.GREEN);
    		test = TeacherTestController.getTestQuestions(getExecutionCode());
    		//System.out.println(test);
    		
    		//get student details to enter to table 'ongoing' in DB.
    		studentDetails = new ArrayList<>();
    		studentDetails.add(0,getExecutionCode());
    		studentDetails.add(1,LoginFormController.username);
    		
    		StudentController.AddStudentToOnGoing(studentDetails);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource
    				("TestQuestionsAndAnswersForm.fxml"));
        	Parent root = null;
        	
        	try {
    			root = loader.load();
    			ScreenControllers.duringTestControl = loader.getController();
    	    	Scene scene = new Scene(root);

    	    	UserController.currentStage.setScene(scene);
    	    	ScreenControllers.duringTestControl.start(0);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
		}
    	else
    	{
    		lblValidCode.setText("Please Enter A Valid Code.");
    		lblValidCode.setTextFill(Color.RED);
    		ClientUI.display("Code Doesnt Exist!");
    	}
}
    
    public boolean isExecutionCodeValid()
    {
    	if(StudentController.isExecutionCodeValid(getExecutionCode()))
    		return true;
    	return false;

    }
    
    public String getExecutionCode()
    {
    	return txtExecutionCode.getText();
    }
   
    public static Test getTest()
    {
    	return test;
    }
    
    
 
}
