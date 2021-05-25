package client.gui;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.dbControl.QuestionDBController;

public class CreateQuestionController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FXML
    private ComboBox<String> comboBank;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtTeacherName;

    @FXML
    private Button btnBack;

    @FXML
    private TextArea txtAnswerA;

    @FXML
    private TextArea txtAnswerB;

    @FXML
    private TextArea txtAnswerC;

    @FXML
    private TextArea txtAnswerD;

    @FXML
    private ComboBox<String> comboCorrectAnswer;

    @FXML
    private Button btnCreateQuestion;

    
    @FXML
    void Click_CreateQuestion(ActionEvent event) {

    	ArrayList<String> answers = new ArrayList<String>();
    	answers.add(txtAnswerA.getText());
    	answers.add(txtAnswerB.getText());
    	answers.add(txtAnswerC.getText());
    	answers.add(txtAnswerD.getText());
    	
    	if(comboBank.getItems().isEmpty())
    		ClientUI.display("First Create question bank");
    	else if(txtAnswerA.getText().trim().isEmpty()||txtAnswerB.getText().trim().isEmpty()||txtAnswerC.getText().trim().isEmpty()||txtAnswerD.getText().trim().isEmpty())
		    ClientUI.display("Please first insert the answers");
    	else { if(txtDescription.getText().trim().isEmpty()) 
    			ClientUI.display("Description field is empty!");
    		else 
    		{
    			Question q  = new Question(2, txtDescription.getText(), answers,Integer.parseInt(comboCorrectAnswer.getValue()), txtTeacherName.getText());
    			TeacherTestController.addQuestion(q);
    			}	
    	}
    }
    @FXML
    void Click_Back(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml"));
		Parent root;
		try {
			ScreenControllers.teacherMenuController = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage teacherMenu = new Stage();
			teacherMenu.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = teacherMenu;
			teacherMenu.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
   
    
	public void start() {

		txtAnswerA.setText("");
		txtAnswerB.setText("");
		txtAnswerC.setText("");
		txtAnswerD.setText("");
		txtDescription.setText("");
		txtTeacherName.setText("fix Later");//take the teacher object from login and get teacher name by getter
		txtTeacherName.setEditable(false);
		comboBank.setEditable(false);
		comboBank.setItems(TeacherTestController.getAllQBanks());
		ArrayList<String> list  = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		comboCorrectAnswer.setItems(FXCollections.observableArrayList(list));
		comboCorrectAnswer.setEditable(false);
		
		

	}



}
