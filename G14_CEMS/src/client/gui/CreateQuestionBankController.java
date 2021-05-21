package client.gui;

import java.io.Serializable;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.TeacherTestController;
import entity.Question;
import entity.QuestionBank;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CreateQuestionBankController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FXML
    private AnchorPane UpperAP_CreateQB;

    @FXML
    private Text CreateQBTitle;

    @FXML
    private AnchorPane LowerAP_CreateQB;

    @FXML
    private Text CreateBnote;

    @FXML
    private Label BNtxt;

    @FXML
    private TextField BNtxtFiled;

    @FXML
    private Button CreateQ;

    @FXML
    private Button CreateB;

    @FXML
    private Button CreateQBback;

	public void start() {
		// TODO Auto-generated method stub
		
	}
	 @FXML
	void ClickCreateBank(ActionEvent event) {
		 	
		 if(BNtxtFiled.getText().trim().isEmpty())
		    ClientUI.display("Please first enter bank name");
		 else {
	    	QuestionBank QB  = new QuestionBank(3, BNtxtFiled.getText());
	    	TeacherTestController.insertQuestionBank(QB);}

	 }
	 
	 @FXML
	    void Click_CreateQuestion(ActionEvent event) {
		 //first go to create question window
		 //then back to create question bank window

	    }
    
    

  

}
