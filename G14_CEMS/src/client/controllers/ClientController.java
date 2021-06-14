package client.controllers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import client.gui.ManualTestController;
import client.gui.TestQuestionsAndAnswersController;
import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.RequestExtraTime;
import entity.Test;
import entity.TestBank;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ocsf.client.*;
import ocsf.server.ConnectionToClient;
import server.dbControl.UserDBController;
//updated
public class ClientController extends AbstractClient {

	public static boolean awaitResponse = false;
	public ClientUI clientUI;

	public ClientController(String host, int port, ClientUI clientUI) {
		super(host, port);
		this.clientUI = clientUI;
		// openConnection();
		// closeConnection();
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
	System.out.println("in handle message from server");
		 // zarih liot be sof a kod????
		awaitResponse = false;
		Message message = (Message)msg;
		System.out.println(message.getMessageType() + ", " + message.getMessageData());
		switch(message.getMessageType()) {
		case TestsList :
			TeacherTestController.testArr = (ArrayList<Test>)message.getMessageData();
			break;
		case QuestionBankList :
			TeacherTestController.QuestionArr = (ArrayList<String>)message.getMessageData();
			break;
		case SuccessUpdateTest:
			ClientUI.display("Update succesful");
			break;
		case addQuestion:
			ClientUI.display("Created question successfully");
			break;
		case insertQuestionBank:
		ClientUI.display("Created question bank successfully");
		break;
		case TestCount:
			TeacherTestController.testCount = (int)message.getMessageData();
			break;
		case TestBanksList:
			TeacherTestController.banksMap = (HashMap<String, TestBank>)message.getMessageData();
			break;
		case logIn:
			UserController.logInStatus = (String)message.getMessageData();
	     	break;
	
		case execCode:
			if(message.getMessageData() == null) ClientUI.display("execution code invalid");
			else { UserController.CurrentTestID = (String)message.getMessageData();}
			break;		
		case downloadManualTest:
			UserController.byteManualTest = (byte[])message.getMessageData();
			break;
		case submitManualTest:	
			if( ((String)message.getMessageData()).equals("Successfully submitted")) {
				UserController.flagForSubmittedTestSuccessfully = true; }		
			break;
		case ContinuePlanTest:
			if( ((String)message.getMessageData()).equals("TestID and username matched")) {
				UserController.flagForContinuePlanTest= true; }		
		case InsertPlanTest:
			UserController.InsertPlanTest = (String)message.getMessageData();
			break;
		case lockTest:
			ClientUI.display("TEACHER LOCKED THE TEST! Good luck"
					+ " next time click submit to get back to studentmenu");

			StudentController.flagForLockTest = true;
			break;
		case lockTestTeacher:
			ClientUI.display("Locked succssesfully");
			break;
		case getManualTestDetails:
			 System.out.println( "IN CLIENTCONTROLLER");
			 System.out.println( ManualTestController.manualTestDetails+"IN CLIENTCONTROLLER");
			 ManualTestController.manualTestDetails = (ArrayList<String>)message.getMessageData();
			 System.out.println( ManualTestController.manualTestDetails+"IN CLIENTCONTROLLER");
			 break;
		case RequestExtraTime:
			if(message.getMessageData().equals("Can Request")) {
				TeacherTestController.flagForRequestValidExecCode = true;
			}
			break;
			//ragah
		case CheckTest:
			StudentController.isTestExist((String)message.getMessageData());
			break;
		case CheckedTest:
			if(!(boolean)message.getMessageData())
				StudentController.testExist = false;
			else
				StudentController.testExist = true;
			break;
		case CheckStudentID:
			StudentController.isStudentIDExist((String)message.getMessageData());
			break;
		case CheckedStudentID:
			if(!(boolean)message.getMessageData())
				StudentController.studentIDExist = false;
			else
				StudentController.studentIDExist = true;
			break;
		case CheckValidCode:
			StudentController.isExecutionCodeValid((String)message.getMessageData());
			break;
		case CheckedCode:
			if((String)message.getMessageData() == null)
				StudentController.validCode = false;
			else
				StudentController.validCode = true;
			break;
		case GetTestQuestions:
			TeacherTestController.getTestQuestions((String)message.getMessageData());
			break;
		case TestQuestions:
			TeacherTestController.currentTest = (Test)message.getMessageData();
			break;
		case GetTestCode:
			TeacherTestController.getTestID((String)message.getMessageData());
			break;
		case GotTestCode:
			TeacherTestController.testID = (String)message.getMessageData();
			break;
		case SubmitTest:
			StudentController.submitTest((testCopy)message.getMessageData());
			break;
		case SubmittedTest:  // RAGAH ASITI PO flag be mida ve ha student lahaz al submit ve lo heala klom ba manual. 
			if((boolean)message.getMessageData()) {
				UserController.flagForSubmittedTestSuccessfully =true;
					ClientUI.display("Test has been submitted successfully!");}
			else
				ClientUI.display("Error in submit test");
			break;
		case GetExamDate:
			StudentController.getExamDate((String)message.getMessageData());
			break;
		case GotExamDate:
			StudentController.examDate = (ArrayList<String>)message.getMessageData();
			break;
		case getExtraTime:
			ExtraTimeController.list = 
				(ArrayList<RequestExtraTime>)message.getMessageData();
			break;
		case addExtraTime:
			TestQuestionsAndAnswersController.addExtraTime = 
							Integer.parseInt((String)message.getMessageData());
			clientUI.display("You get more time for finish the test:"
							+ (String)message.getMessageData()+"min");
			break;
		case addExtraTimePrinciple:
			ClientUI.display("Adding extra time succssesfully");
			break;
			
			default:
				ClientUI.display("cant read message from server");
		}
		
	}
	

	public void handleMessageFromClientUI(Object msg) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(msg);
			// wait for response
			while (awaitResponse) {
				try {
					System.out.println("sleep");
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			ClientUI.display("Could not send message to server");

		}
	}

}
