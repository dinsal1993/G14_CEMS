package client.gui;


import javafx.event.ActionEvent;

import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Test;
import entity.testCopy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManageTestController {

    @FXML
    private Text txtManageTest;

    @FXML
    private TextField txtTestID;

    @FXML
    private Text txtMustFillThisFieldToContinue;

    @FXML
    private Label lblTestID;

    @FXML
    private Button btnLockTest;


    @FXML
    private Button btnBack;

    @FXML
    private Button btnRequestExtraTime;
    
    @FXML
    private Label lblRequestExtraTime;

    @FXML
    private Label lblReasons;

    @FXML
    private CheckBox chkBoxRequestExtraTime;

    @FXML
    private TextField txtReasons;
    
    @FXML
    private Label lblEnterTestID;

    @FXML
    private TextField txtTestIDForExtraTime;
    
    @FXML
    void Click_Back(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource
				("TeacherMenuForm.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.teacherMenuController.start();
    }
    @FXML
	void ClickLockTest(ActionEvent event) {
    	int flag = 0;
		if (txtTestID.getText().matches(".*[0-9].*") == true && txtTestID.getText().matches(".*[a-z].*") == false 
			&& txtTestID.getText().matches(".*[A-Z].*") == false) {
			Test test = new Test();
			//list = (tblTests.getItems());
			TeacherTestController.getAllTest();
			// get chosen test details
			//for (Test t : TeacherTestController.list) {
			//	if (t.getId() == Integer.parseInt(txtTestID.getText())) {
			//		test.setId(t.getId());
			//		flag = 1;
			//	}

			//}
			if(flag == 0)
				ClientUI.display("Test doesnt exist!");
			else
				TeacherTestController.lockTest(test);
		}
		else
			ClientUI.display("Error in test ID");
	}
    
    @FXML
    public void clickRequestExtraTime()
    {	
    	if(chkBoxRequestExtraTime.isSelected())
    	{
    		String id = txtTestIDForExtraTime.getText();
    		String reasons = txtReasons.getText();
    		testCopy tc = new testCopy();
			//list = (tblTests.getItems());
			TeacherTestController.getAllTest();
			// get chosen test details
			//for (Test t : TeacherTestController.list) 
			//{
			//	if (t.getId() == Integer.parseInt(id)) 
			//	{
			//		tc.setTestID(t.getId());	
			//		tc.setReasons(reasons);
			//		TeacherTestController.requestExtraTime(tc);
			//	}
		//	}
			
    	}
    	else //checkbox isnt selected
    		ClientUI.display("Must Check 'Request Extra Time' to continue.");
    }	
}
