package client.gui;
import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.UserController;
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

public class TestTypeController {

    @FXML
    private TextField txtStudentID;

    @FXML
    private TextField txtTestID;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnOnlineTest;

    @FXML
    private Button btnManualTest;
    
    @FXML
    private Label lblisTestIDValid;

    @FXML
    private Label lblisStudentIDValid;
    
    public static String testID;
    public static String studentID;
    
    @FXML
    void clickOnlineTest(ActionEvent event) 
    {
    	if(isTestExist())
		{
    		lblisTestIDValid.setText("");
    		testID = txtTestID.getText();
 
    		if(isStudentIDExist())
    		{
    		studentID = txtStudentID.getText();
    		lblisStudentIDValid.setText("");
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("OnlineTestForm.fxml"));
    		Parent root;
    		try {
    			ScreenControllers.onlineTestControl = loader.getController();
    			root = loader.load();
    			Scene scene = new Scene(root);
    			Stage onlineTest = new Stage();
    			onlineTest.setScene(scene);
    			UserController.currentStage.hide();
    			UserController.currentStage =onlineTest;
    			onlineTest.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		}
    		else
    		{
    			lblisStudentIDValid.setText("Please Enter A Valid Student ID.");
    			lblisStudentIDValid.setTextFill(Color.RED);
        		ClientUI.display("Student Doesnt Exist!");
    		}
		}
    	else
    	{
    		lblisTestIDValid.setText("Please Enter A Valid Test.");
    		lblisTestIDValid.setTextFill(Color.RED);
    		ClientUI.display("Test Doesnt Exist!");
    	}
    }
    
    
    @FXML
    void clickManualTest(ActionEvent event) 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ManualTestForm.fxml"));
		Parent root;
		try {
			ScreenControllers.manualTestControl = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage manualTest = new Stage();
			manualTest.setScene(scene);
			UserController.currentStage.hide();
			UserController.currentStage = manualTest;
			manualTest.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void clickBack(ActionEvent event) 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMenuForm.fxml"));
		Parent root;
		try {
			ScreenControllers.studentMenuControl = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage student = new Stage();
			student.setScene(scene);
			UserController.currentStage.hide();
			UserController.currentStage = student;
			student.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    public String getStudentID()
    {
    	return txtStudentID.getText();
    }
    
    public String getTestID()
    {
       	return  txtTestID.getText();
    }
    
    
  
    
    public boolean isTestExist()
    {
    	if(StudentController.isTestExist(getTestID()))
    		return true;
    	return false;
    
    }
    
    public boolean isStudentIDExist()
    {
    	if(StudentController.isStudentIDExist(getStudentID()))
    		return true;
    	return false; 
    }
    
   


	public void start() {
		// TODO Auto-generated method stub
		
	}
}
