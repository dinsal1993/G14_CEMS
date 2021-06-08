package client.controllers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;

//import javax.swing.JOptionPane;

import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.Subject;
import entity.Test;
import entity.TestBank;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.client.*;
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
	
		awaitResponse = false; 
		Message message = (Message)msg;

		System.out.println(message.getMessageType());
		switch(message.getMessageType()) {
		case GetAllTests:
		case GetAllTestsBySubject:
			TeacherTestController.testArr = (ArrayList<Test>)message.getMessageData();
		break;
		case GetAllSubjects:
			TeacherTestController.subjects = (ArrayList<Subject>)message.getMessageData();
			break;
		case GetQuestionsBySubject:
			TeacherTestController.questionsBySubject = (ArrayList<Question>)message.getMessageData();
			break;
		case UpdateQuestion:
			if((boolean)message.getMessageData())
				ClientUI.display("edit question succesfully");
			else
				ClientUI.display("error editing question");
			break;
		case addQuestion:
			if((boolean)message.getMessageData())
				ClientUI.display("Created question successfully");
			else
				ClientUI.display("error creating question");
			break;
		case GetQuestionByID:
			TeacherTestController.specificQ = (Question)message.getMessageData();
			break;
		case AddTest:
			if((boolean)message.getMessageData())
				ClientUI.display("Created test successfully");
			else
				ClientUI.display("error creating test");
			break;
		case updateTest:
			if((boolean)message.getMessageData())
				ClientUI.display("update successfully");
			else
				ClientUI.display("error update test");
			break;
		case deleteTest:
			if((boolean)message.getMessageData())
				ClientUI.display("delete test successfully");
			else
				ClientUI.display("error deleting question");
			break;
		case GetNextQID:
			TeacherTestController.nextQID = (int)message.getMessageData();
			break;
		case GetNextTID:
			TeacherTestController.nextTID = (int)message.getMessageData();
			break;
		case GetTCount:
			TeacherTestController.tCountByBankAndCourse = (int)message.getMessageData();
			break;
		case GetSubjectID:
			TeacherTestController.subjectID = (String)message.getMessageData();
			break;
		case GetCourseID:
			TeacherTestController.courseID = (String)message.getMessageData();
			break;
		case getCoursesBySubject:
			TeacherTestController.courseArr = (ArrayList<Course>)message.getMessageData();
			break;
		case insertQuestionBank:
		ClientUI.display("Created question bank successfully");
		break;
		case insertTestBank:
			ClientUI.display("Created test bank successfully");
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
		case LockTest:
			TeacherTestController.lockTest((Test)message.getMessageData());
			break;
		case SuccessLockTest:
			ClientUI.display("Test Has Been Locked Successfully!");
			break;
		case RequestExtraTime:
			TeacherTestController.requestExtraTime((testCopy)message.getMessageData());
			break;
		case SentExtraTimeRequest:
			ClientUI.display("Request Has Been Sent!");
			break;
		case RefreshCourseTable:
			TeacherTestController.refreshCourseTable();
			break;
		case CourseList:
			TeacherTestController.courseArr = (ArrayList<Course>)message.getMessageData();
			break;
		case AddCourse:
			TeacherTestController.addCourse((Course)message.getMessageData());
			break;
		case CourseAdded:
			ClientUI.display("Course Has Been Added!");
			break;
		case DeleteCourse:
			TeacherTestController.deleteCourse((Course)message.getMessageData());
			break;
		case CourseDeleted:		
			ClientUI.display("Course Has Been Deleted!");
			break;

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
			if(!(boolean)message.getMessageData())
				StudentController.validCode = false;
			else
				StudentController.validCode = true;
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
			//ClientUI.display((String)message.getMessageData());			
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
