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
    private TextField txtExecCode;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnOnlineTest;

    @FXML
    private Button btnManualTest;
    
    @FXML
    private Label lblIsExecCodeValid;
   
    public static String code;
    
    @FXML
    void clickOnlineTest(ActionEvent event) 
    {
    	
    	if(StudentController.isExecutionCodeValid(getExecutionCode()))
		{
    		code = getExecutionCode();
    		lblIsExecCodeValid.setText("");
    		
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
    			lblIsExecCodeValid.setText("Please Enter A Valid Execution Code.");
    			lblIsExecCodeValid.setTextFill(Color.RED);
        		ClientUI.display("Code Doesnt Exist!");
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
    

    public String getExecutionCode()
    {
    	return txtExecCode.getText();
    }
   


	public void start() {
		// TODO Auto-generated method stub
		
	}
}
