package client.gui;

import java.io.IOException;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.UserController;
import entity.Question;
import entity.Test;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class TestQuestionsAndAnswersController {
	
	 @FXML
	    private Button btnBack;

	    @FXML
	    private Button btnNext;

	    @FXML
	    private TextArea qstDescription;

	    @FXML
	    private RadioButton answer3;

	    @FXML
	    private RadioButton answer4;

	    @FXML
	    private RadioButton answer1;

	    @FXML
	    private RadioButton answer2;

	    @FXML
	    private Label option1;

	    @FXML
	    private Label option2;

	    @FXML
	    private Label option3;

	    @FXML
	    private Label option4;
	    
	    
	    public static ObservableList<Question> questionsList = OnlineTestController.getList();
	    public int i;
	    
	    
	    public void start(int j) {
	    
	    	ToggleGroup firstAnswer = new ToggleGroup();
    		answer1.setToggleGroup(firstAnswer);
    		answer2.setToggleGroup(firstAnswer);
    		answer3.setToggleGroup(firstAnswer);
    		answer4.setToggleGroup(firstAnswer);
    		answer1.setSelected(true);
 
	    	setQuestionDescription(j);
	    	setQuestionOptions(j);
	    	i = j;
	    	i++;
	    	
	    	if(i == questionsList.size())
	    		btnNext.setText("Submit");
	    }
	    
	    public void setQuestionDescription(int i)
	    {
	    	qstDescription.setText(questionsList.get(i).getDescription());
	    
	    }
	    
	    public void setQuestionOptions(int i)
	    {		    		
	    	option1.setText(questionsList.get(i).getAnswers().get(0));
	    	option2.setText(questionsList.get(i).getAnswers().get(1));
	    	option3.setText(questionsList.get(i).getAnswers().get(2));
	    	option4.setText(questionsList.get(i).getAnswers().get(3));
	    	
	    }
	    
	    @FXML
	    void click_Next(ActionEvent event) {
	    	
	    	if(i == questionsList.size())
	    	{
	    		ClientUI.display("Congratulaions! You have finished the test.");
	    		return;
	    	}
        	FXMLLoader loader = new FXMLLoader(getClass().getResource
    				("TestQuestionsAndAnswersForm.fxml"));
        	Parent root = null;
        	
        	try {
    			root = loader.load();
    			ScreenControllers.duringTestControl = loader.getController();
    	    	Scene scene = new Scene(root);

    	    	UserController.currentStage.setScene(scene);
    	    	ScreenControllers.duringTestControl.start(i);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}

	    }
	    
}
