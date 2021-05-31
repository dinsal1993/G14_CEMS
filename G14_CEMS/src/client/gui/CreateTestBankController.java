package client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Course;
import entity.QuestionBank;
import entity.TestBank;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import server.dbControl.TeacherTestDBController;

public class CreateTestBankController {

    @FXML
    private Text txtCreateTestsBank;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblBankName;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblExistingBanks;

    @FXML
    private TextField txtBankName;

    @FXML
    private TextField txtCourseName;

    @FXML
    private ListView<String> lstViewExistingBanks;

    @FXML
    private Button btnCreateBank;

    @FXML
    private Button btnCreateTest;
    
    private ObservableList<String> testBankList;
    
    @FXML
    void click_back(ActionEvent event) {
    	
    	  FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml"));
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
    void click_createBank(ActionEvent event) {

    	ArrayList<Course> course = new ArrayList<Course>();
    	Course c  = new Course();
    	c.setName(txtCourseName.getText());
    	course.add(c);
    
    	 if(txtBankName.getText().trim().isEmpty())
 		    ClientUI.display("Please first enter bank name");
    	 else if(txtCourseName.getText().trim().isEmpty())
    		 ClientUI.display("Please enter course name");
    	 else {
    		 	TestBank TB  = new TestBank(3, txtBankName.getText(),course);
		    	TeacherTestController.insertTestBank(TB);
    		    this.lstViewExistingBanks.setEditable(false); 
    		    this.txtCourseName.setEditable(false);
    		    this.txtBankName.setEditable(false);
    	 }
    		 
    		 
    	 

    }

    @FXML
    void click_createTest(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTestForm.fxml"));
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

	public void start() {
		// TODO Auto-generated method stub
		testBankList = TeacherTestController.getAllTestBanks();
		lstViewExistingBanks.setItems(testBankList);
		}
}
