package client.gui;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.UserController;
import entity.Question;
import entity.Test;
import entity.testCopy;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class TestQuestionsAndAnswersController {
	
	 	@FXML
	    private Button btnBack;

	    @FXML
	    private Button btnNext;

	    @FXML
	    private TextArea qstDescription;

	    @FXML
	    private RadioButton answer1;

	    @FXML
	    private RadioButton answer2;
	    
	    @FXML
	    private RadioButton answer3;

	    @FXML
	    private RadioButton answer4;

	    @FXML
	    private Label option1;

	    @FXML
	    private Label option2;

	    @FXML
	    private Label option3;

	    @FXML
	    private Label option4;
	    
	    @FXML
	    private Label points;
	    
	    @FXML
	    private Label lblTimer;
	    
	    public static Test questionsList = OnlineTestController.test;
	    public int i,finalGrade = 0;
	    public static ArrayList<Integer> selectedAnswers = new ArrayList<>();

		public static int addExtraTime;	    
	    private ToggleGroup firstAnswer;
	    public int[] currentAnswers =  new int[questionsList.getQuestions().size()];
	    public int currAnswer;
	    public long timeTookToFinish;
	    public ArrayList<String> examDateAndTime = OnlineTestController.examTime;
	    
	    //timer fields
	    private long min, sec, hr, totalSec; 
	    private Timer timer;
	    
	    public void showAnswer(int answerIndex)
	    {
	    	currAnswer = answerIndex;
	    	
	    	if(currAnswer == 0)
    		{
    			btnBack.setVisible(false);
    		}
    		else
    	    	btnBack.setVisible(true);
	    	
	    	if(currAnswer == currentAnswers.length-1)
	    		btnNext.setText("Submit");
	    	else
	    		btnNext.setText("Next");
	    	
	    	//Show answer details
	    	setQuestionDescription(currAnswer);
	    	setQuestionOptions(currAnswer);
	    	
	    	
	    	if(currentAnswers[currAnswer] != 0 )
	    	{
	    		firstAnswer.getToggles().get(currentAnswers[currAnswer]-1).setSelected(true);
	    	}
	    	else
	    	{
	    		answer1.setSelected(false);
	    		answer2.setSelected(false);
	    		answer3.setSelected(false);
	    		answer4.setSelected(false);
	    	}
	    	
	    }
	    
	    public void start(int j) {  	
	    	
	    	setTimer();
	    	firstAnswer = new ToggleGroup();
	    	answer1.setToggleGroup(firstAnswer);
	    	answer2.setToggleGroup(firstAnswer);
	    	answer3.setToggleGroup(firstAnswer);
	    	answer4.setToggleGroup(firstAnswer);
	    	
	    	
	    	currAnswer = j;
	    	
	    	for(int i=0; i< currentAnswers.length; i++)
	    		currentAnswers[i] = 0;
	    	
	    	showAnswer(currAnswer);
	    
	    }
	    
	    public void setQuestionDescription(int i)
	    {
	    	
	    	qstDescription.setText(questionsList.getQuestions().get(i).getDescription());
	    	points.setText(questionsList.getPointsPerQuestion().get(i).toString());
	    }
	    
	    public void setQuestionOptions(int i)
	    {		    		
	    	option1.setText(questionsList.getQuestions().get(i).getAnswers().get(0));
	    	option2.setText(questionsList.getQuestions().get(i).getAnswers().get(1));
	    	option3.setText(questionsList.getQuestions().get(i).getAnswers().get(2));
	    	option4.setText(questionsList.getQuestions().get(i).getAnswers().get(3));
	    	
	    }
	    
	    @FXML
	    void click_Next(ActionEvent event) {
	    	
	    	
	    	//Save previous question's details
	    	if(answer1.isSelected())
	    		currentAnswers[currAnswer] = 1;
	    	else if(answer2.isSelected())		    	
	    		currentAnswers[currAnswer] = 2;
	    	else if(answer3.isSelected())		    	
	    		currentAnswers[currAnswer] = 3;
	    	else if(answer4.isSelected())		    	
	    		currentAnswers[currAnswer] = 4;
	    	else
	    		currentAnswers[currAnswer] = 0;
	    	
	    	currAnswer++;
	    	
	    	
	    	//Moving to the next question
	    	if(currAnswer == questionsList.getQuestions().size() )
	    	{
	    		if(this.IsTestCompeted())
	    		{
	    			timeTookToFinish -= totalSec;
	    			printTime(timeTookToFinish);
	    			ClientUI.display("You have finished the test. Good Luck!");		
	    			finalGrade = checkAnswers(currentAnswers);
	    			System.out.println("You final grade is : "+finalGrade+"");
	    			
	    			//save student answers to DB before returning to MainMenu
	    			ArrayList<Integer> answers = new ArrayList<>(currAnswer);
	    			for(int i = 0;i < currAnswer;i++)
	    				answers.add(i,currentAnswers[i]);
	    		
	    			testCopy tc = new testCopy();
	    			tc.setTestID(questionsList.getId());
	    			tc.setYear(examDateAndTime.get(3).split("-")[0]);
	    			tc.setMonth(examDateAndTime.get(3).split("-")[1]);
	    			tc.setDay(examDateAndTime.get(3).split("-")[2]);
	    			tc.setStudentAnswers(answers);
	    			tc.setFinalScore(finalGrade);
	    			tc.setActualTime(timeTookToFinish); 
	    			tc.setStudentID(UserController.username); //shahar shina
	    			StudentController.submitTest(tc);
	    			
	    		
	    			returnToStudentMainMenu();
	    			return;
	    		}
	    		else
	    		{
	    			currAnswer--;
	    			ClientUI.display("Your test isn't completed yet, please make sure to fill all the questions.");
	    			showAnswer(currAnswer);
	    		}
	    	}
	    	else
	    		showAnswer(currAnswer);

	    }
	    
	    @FXML
	    void click_Back(ActionEvent event) {
	    	
	    	//Save previous question's details
	    	if(answer1.isSelected())
	    		currentAnswers[currAnswer] = 1;
	    	else if(answer2.isSelected())		    	
	    		currentAnswers[currAnswer] = 2;
	    	else if(answer3.isSelected())		    	
	    		currentAnswers[currAnswer] = 3;
	    	else if(answer4.isSelected())		    	
	    		currentAnswers[currAnswer] = 4;
	    	else
	    		currentAnswers[currAnswer] = 0;
	    	
	    	
	    	//Moving to the next question
	    	currAnswer--;
	    	showAnswer(currAnswer);
	
	    }
	    
	    public void returnToStudentMainMenu()
	    {
	    	StudentController.removeStudentFromOnGoing(OnlineTestController.studentDetails);
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource
					("StudentMenuForm.fxml"));
			Parent root;
			try {
				
				root = loader.load();
				ScreenControllers.studentMenuController = 
						loader.getController();
				Scene scene = new Scene(root);
				UserController.currentStage.setScene(scene);
				ScreenControllers.studentMenuController.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    public int checkAnswers(int[] array)
	    {
	    	int points = 0;
	    	for(int r = 0; r < array.length;r++)
	    	{
	    		if(questionsList.getQuestions().get(r).getCorrectAnswer() == array[r])
	    			points += questionsList.getPointsPerQuestion().get(r);
	    	}
	    	
	    	return points;
	    }
	    
	        
	    private boolean IsTestCompeted()
	    {	
	    	for(int i = 0; i < this.currentAnswers.length; i++ )
	    		if(this.currentAnswers[i] == 0)
	    			return false;
	    	return true;
	    }
	    
	    public void printTime(long time)
	    {
	    	long min = TimeUnit.SECONDS.toMinutes(time);
	        long sec = time - (min * 60);
	        long hr = TimeUnit.MINUTES.toHours(min);
	        min = min - (hr * 60);
	        System.out.println("Took you : "+(format(hr) + ":" + format(min) + ":" + format(sec)+" to finish the test."));
 
	    }
	    public void convertTime() {
	    	totalSec = totalSec+addExtraTime;////////
	        min = TimeUnit.SECONDS.toMinutes(totalSec);
	        sec = totalSec - (min * 60);
	        hr = TimeUnit.MINUTES.toHours(min);
	        min = min - (hr * 60);
	        lblTimer.setText(format(hr) + ":" + format(min) + ":" + format(sec));

	        totalSec--;
	        
	    }
	    
	    private String format(long value) {
	        if (value < 10) {
	            return 0 + "" + value;
	        }

	        return value + "";
	    }
	    
	    private void setTimer() {
	        
	    	//SHOULD BE totalSec = exam duration - (current local time - exam start hour)
	    	totalSec = questionsList.getDuration() * 60 + OnlineTestController.delayTimeInSecs;
	    
	   
	    	timeTookToFinish = totalSec;
	        this.timer = new Timer();

	        TimerTask timerTask = new TimerTask() {
	            @Override
	            public void run() {
	                Platform.runLater(new Runnable() {
	                    @Override
	                    public void run() {
	                        convertTime();
	                        if(totalSec < 60)
	                        {
	                        	lblTimer.setTextFill(Color.RED);
	                        }
	                        if (totalSec <= 0) {
	                            timer.cancel();            
	                            lblTimer.setText("00:00:00");
	                            finalGrade = 0;
	                            ClientUI.display("Sorry you couldn't finish the test in the given time...");
	        	    			
	                            returnToStudentMainMenu();
	                        }
	                    }
	                });
	            }
	        };

	        timer.schedule(timerTask, 0, 1000);
	    }
	    
	   
	    
	    
}
