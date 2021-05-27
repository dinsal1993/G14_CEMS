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

public class OnlineTestController {

    @FXML
    private Button btnStartTest;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtExecutionCode;
    
    @FXML
    private Label lblValidCode;
    
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
    		
    		//TO-DO:
    		//Need to start a timer before starting the exam 
    		//open the right window
    		
    		/*
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
    		}*/
		}
    	else
    	{
    		lblValidCode.setText("Please Enter A Valid Code.");
    		lblValidCode.setTextFill(Color.RED);
    		ClientUI.display("Code Doesnt Exist!");
    	}
}
    
    
    
    public String getExecutionCode()
    {
    	return txtExecutionCode.getText();
    }
    
    public boolean isExecutionCodeValid()
    {
    	if(StudentController.isExecutionCodeValid(getExecutionCode()))
    		return true;
    	return false;
    
    
    }

}
