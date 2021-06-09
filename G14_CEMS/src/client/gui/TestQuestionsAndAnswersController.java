package client.gui;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.UserController;
import entity.Question;
import entity.Test;
import entity.TestDocs;
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
	    
	    @FXML
	    private Label lblTeacherNotes;
	    
	    public static Test questionsList = OnlineTestController.test;
	    public int i,finalGrade = 0;
	    public static ArrayList<Integer> selectedAnswers = new ArrayList<>();	    
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
	    	lblTeacherNotes.setText(questionsList.getTeacherNotes());
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
	    				
	    			finalGrade = checkAnswers(currentAnswers);
	    			System.out.println("You final grade is : "+finalGrade+"");
	    			
	    			//save student answers to DB before returning to MainMenu
	    			ArrayList<Integer> answers = new ArrayList<>(currAnswer);
	    			for(int i = 0;i < currAnswer;i++)
	    				answers.add(i,currentAnswers[i]);
	    		
	    			testCopy tc = new testCopy();
	    			tc.setTestID(questionsList.getId());
	    			tc.setYear(examDateAndTime.get(2).split("-")[0]);
	    			tc.setMonth(examDateAndTime.get(2).split("-")[1]);
	    			tc.setDay(examDateAndTime.get(2).split("-")[2]);
	    			tc.setStudentAnswers(answers);
	    			tc.setFinalScore(finalGrade);
	    			tc.setActualTime(timeTookToFinish/60);
	    			tc.setTeacherUsername(questionsList.getTeacherName());
	    			tc.setStudentID(LoginFormController.username);
	    			StudentController.submitTest(tc);
	    			
	    			//need to check if this is the last student to submit , then show statistics.
	    			if(StudentController.checkIfLastStudent(questionsList.getExecutionCode()))
	    			{
	    				TestDocs td = submitTestDocs();
                        //insert to testDocs 
                        StudentController.insertToTestDocs(td);
                        //remove test from ongoing because it has ended
                        StudentController.removeTestFromPlannedTest(questionsList.getExecutionCode());
                        //set test code in table test to null
                        StudentController.updateTestCodeToNull(questionsList.getId());
                        
	    			}
	    		
	    			returnToStudentMainMenu();
	    			ClientUI.display("You have finished the test. Good Luck!");	
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
				ScreenControllers.studentMenuControl = 
						loader.getController();
				Scene scene = new Scene(root);
				UserController.currentStage.setScene(scene);
				ScreenControllers.studentMenuControl.start();
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
	        
	    	//totalSec = questionsList.getDuration() * 60 - OnlineTestController.delayTimeInSecs;
	    	totalSec = 20;
	    	
	    	
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
	                        if (totalSec <= 0) 
	                        {
	                        	//the student couldnt finish the test in the given time
	                            timer.cancel();            
	                            lblTimer.setText("00:00:00");
	                            finalGrade = 0;
	                            testCopy failed = new testCopy();
	                            failed.setActualTime(0);
	                            failed.setTestID(questionsList.getId());
	        	    			failed.setYear(examDateAndTime.get(2).split("-")[0]);
	        	    			failed.setMonth(examDateAndTime.get(2).split("-")[1]);
	        	    			failed.setDay(examDateAndTime.get(2).split("-")[2]);
	        	    			failed.setTeacherUsername(questionsList.getTeacherName());
	        	    			failed.setStudentAnswers(null);
	        	    			failed.setFinalScore(0);
	        	    			failed.setStudentID(UserController.username);
	        	    			StudentController.submitFailedTest(failed);
	                            ClientUI.display("Sorry you couldn't finish the test in the given time...");
	                            
	                           
	                            
	                            TestDocs td = submitTestDocs();
	                            //insert to testDocs 
	                            StudentController.insertToTestDocs(td);
	                            
	                            //remove test from ongoing because it has ended
	                            StudentController.removeTestFromPlannedTest(questionsList.getExecutionCode());
	                            //set test code in table test to null
	                            StudentController.updateTestCodeToNull(questionsList.getId());
	                            returnToStudentMainMenu();
	                        }
	                    }
	                });
	            }
	        };

	        timer.schedule(timerTask, 0, 1000);
	    }
	   
	    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
	    {
	  
	        // Create a new ArrayList
	        ArrayList<T> newList = new ArrayList<T>();
	  
	        // Traverse through the first list
	        for (T element : list) {
	  
	            // If this element is not present in newList
	            // then add it
	            if (!newList.contains(element)) {
	  
	                newList.add(element);
	            }
	        }
	  
	        // return the new list
	        return newList;
	    }
	   
	    public TestDocs submitTestDocs()
	    {

            TestDocs td = new TestDocs();
            td.setId(questionsList.getId());
            td.setAssignedTime(OnlineTestController.totalDurationOfExam);
            td.setYear(examDateAndTime.get(2).split("-")[0]);
            td.setDate(examDateAndTime.get(2));
            String semester = "";
            switch (examDateAndTime.get(2).split("-")[1])
            {
            case "01":
            	semester = "01";
            	break;
            case "02":
            	semester = "01";
            	break;
            case "03":
            	semester = "01";
            	break;
            case "04":
            	semester = "02";
            	break;
            case "05":
            	semester = "02";
            	break;
            case "06":
            	semester = "02";
            	break;
            case "07":
            	semester = "02";
            	break;
            case "08":
            	semester = "02";
            	break;
            case "09":
            	semester = "02";
            	break;
            case "10":
            	semester = "01";
            	break;
            case "11":
            	semester = "01";
            	break;
            case "12":
            	semester = "01";
            	break;
            	
            }
            td.setSemester(semester);
            //get number of students who started the exam from db.
            //StudentController.getNumberOfStudentsStartedExam();
            int numOfStudentsStartedExam = StudentController.getNumberOfStudentsStartedExam(questionsList.getId());
            td.setNumStudentsStart(numOfStudentsStartedExam);
            //get number of students who finished the exam from db.
           // StudentController.getNumberOfStudentsFinishedExam();
            int numOfStudentsFinishedExam = StudentController.getNumberOfStudentsFinishedExam(questionsList.getId());
            td.setNumStudentsFinishInTime(numOfStudentsFinishedExam);
            int numOfStudentsNotFinished = numOfStudentsStartedExam - numOfStudentsFinishedExam;
            td.setNumStudentsNotFinishInTime(numOfStudentsNotFinished);
            System.out.println("Students started :"+numOfStudentsStartedExam);
            System.out.println("Students finished :"+numOfStudentsFinishedExam);
            System.out.println("Students didnt finish :"+numOfStudentsNotFinished);
            //get all grades.
            ArrayList<Integer> grades = StudentController.getStudentGrades(questionsList.getId());
            int gradesSum = 0;
            for(int i =0;i<grades.size();i++)
            	gradesSum+= grades.get(i);
            double average = gradesSum / grades.size();
            td.setAverage(average);
            //we need to get the median , therefore we need to sort the grades list
            //in order to return the middle index.
            
            System.out.println(grades);
            //set distribuion
            ArrayList<Integer> distribution = new ArrayList<>(10);
            distribution.add(0, 0);
            distribution.add(1, 0);
            distribution.add(2, 0);
            distribution.add(3, 0);
            distribution.add(4, 0);
            distribution.add(5, 0);
            distribution.add(6, 0);
            distribution.add(7, 0);
            distribution.add(8, 0);
            distribution.add(9, 0);
            
            for(int i = 0;i < grades.size(); i++)
            {
            	if(grades.get(i)/10 == 10)
            		distribution.set(9, distribution.get(i)+1);
            	else
            		distribution.set(grades.get(i)/10, distribution.get(grades.get(i)/10)+1);
            }
            System.out.println("distribution = "+distribution);
            td.setDistribution(distribution);
            
            List<Integer> sortedGrades = grades;
            sortedGrades = removeDuplicates(grades);
            sortedGrades = sortedGrades.stream().sorted().collect(Collectors.toList());
            double median;
            if(sortedGrades.size() % 2 == 0)
            	median = (sortedGrades.get(sortedGrades.size()/2) + sortedGrades.get(sortedGrades.size()/2 - 1)) / 2;
            else
            	median = sortedGrades.get(sortedGrades.size()/2);
            
           
            System.out.println("final grades : "+sortedGrades);
            System.out.println("the median is : "+median);
            td.setMedian(median);
            
            return td;
	    }
	    
}
