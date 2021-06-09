package client.gui;


import javafx.event.ActionEvent;

import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
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
    private TextField txtExecCode;

    @FXML
    private TextField txtTestIDForExtraTime;

	public Button getBtnLockTest() {
		return btnLockTest;
	}

	public Button getBtnBack() {
		return btnBack;
	}

	@FXML
    private Button btnLockTest;

    @FXML
    private Button btnBack;

    public String getTxtExecCode() {
		return txtExecCode.getText();
	}

    @FXML 
    void clickLockTest(ActionEvent event) {
    	Message msg = new Message(MessageType.lockTest, getTxtExecCode() );
    	ClientUI.accept(msg);   	
    }
    
    @FXML
    void clickBack(ActionEvent event) {
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml")); 
		Parent root2;
		try {
			root2 = loader2.load();
			Scene scene = new Scene(root2);
			UserController.currentStage.setScene(scene);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScreenControllers.teacherMenuController.start();
    }

    @FXML
	void ClickLockTest(ActionEvent event) {
    	int flag = 0;
		//if (txtTestID.getText().matches(".*[0-9].*") == true && txtTestID.getText().matches(".*[a-z].*") == false 
		//	&& txtTestID.getText().matches(".*[A-Z].*") == false) {
			Test test = new Test();
			//list = (tblTests.getItems());
			//TeacherTestController.getAllTest();
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
		//else
		//	ClientUI.display("Error in test ID");
	}

    
/*    @FXML
    public void clickRequestExtraTime()
    {	
    	if(chkBoxRequestExtraTime.isSelected())
    	{
    		String id = txtTestIDForExtraTime.getText();
    		String reasons = txtReasons.getText();
    		testCopy tc = new testCopy();
			//list = (tblTests.getItems());
			//TeacherTestController.getAllTest();
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
    }	*/
    /*   @FXML ragah or something
   	void ClickLockTest(ActionEvent event) {
       	int flag = 0;
   		if (txtTestID.getText().matches(".*[0-9].*") == true && txtTestID.getText().matches(".*[a-z].*") == false 
   			&& txtTestID.getText().matches(".*[A-Z].*") == false) {
   			Test test = new Test();
   			//list = (tblTests.getItems());
   			TeacherTestController.getAllTest();
   			// get chosen test details
   			for (Test t : TeacherTestController.list) {
   				if (t.getId() == Integer.parseInt(txtTestID.getText())) {
   					test.setId(t.getId());
   					flag = 1;
   				}
   			}
   			if(flag == 0)
   				ClientUI.display("Test doesnt exist!");
   			else
   				TeacherTestController.lockTest(test);
   		}
   		else
   			ClientUI.display("Error in test ID");
   	}*/
//}




