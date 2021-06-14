package client.gui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private TextField txtStudentID;

    @FXML
    private Label lblValidStudentID;
    
    public static ObservableList<Question> testQuestionsList;
    public static Test test;
    public static ObservableList<Test> tests;
    public static ArrayList<String> studentDetails;
    public static ArrayList<String> examTime;
    public static long delayTimeInSecs;
    
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
		ScreenControllers.testTypeController.start(); // lo ose klom bentaim
    }
    
    @FXML
    void clickStart(ActionEvent event) throws ParseException 
    {
    	if(isStudentIDExist())
		{
    		lblValidStudentID.setText("Its A Valid Student ID!");
    		lblValidStudentID.setTextFill(Color.GREEN);
    		test = TeacherTestController.getTestQuestions(getExecutionCode());
    		examTime = new ArrayList<>();
    		examTime = StudentController.getExamDate(getExecutionCode());
    		
       		SimpleDateFormat defaultFormat = new SimpleDateFormat("HH:mm:ss");
    		String examStartHour = examTime.get(1);
    		String currentTime = defaultFormat.format(Calendar.getInstance().getTime());
    		
    		Date startExamHour = defaultFormat.parse(examStartHour);
    		Date currHour = defaultFormat.parse(currentTime);
    		long difference = startExamHour.getTime() - currHour.getTime();
    		long diffSeconds = difference / 1000 % 60;
    		long diffMinutes = difference / (60 * 1000) % 60;
    		long diffHours = difference / (60 * 60 * 1000) % 24;
    				
    		
    		delayTimeInSecs = diffHours * 3600 + diffMinutes * 60 + diffSeconds;
    		
    		//System.out.println(difference);
    		//System.out.println(test);
    		
    		//get student details to enter to table 'ongoing' in DB.
    		studentDetails = new ArrayList<>();
    		studentDetails.add(0,getExecutionCode());
    		studentDetails.add(1,UserController.username);
    		
    		
    		if(delayTimeInSecs * -1 < test.getDuration() * 60)
    		{
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
    			lblValidStudentID.setText("");
    			ClientUI.display("Exam Has Already Ended!");
    		}
    		
		}
    	else
    	{
    		lblValidStudentID.setText("Please Enter A Valid Student ID.");
    		lblValidStudentID.setTextFill(Color.RED);
    		ClientUI.display("Student Doesnt Exist!");
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
    	return TestTypeController.code;
    }
    
   
    public static Test getTest()
    {
    	return test;
    }
    
    public String getStudentID()
    {
    	return txtStudentID.getText();
    }
  
    public boolean isStudentIDExist()
    {
    	if(StudentController.isStudentIDExist(getStudentID()))
    		return true;
    	return false; 
    }
    
 
}
