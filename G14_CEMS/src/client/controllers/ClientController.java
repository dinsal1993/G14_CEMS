package client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

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
		awaitResponse = false; // zarih liot be sof a kod????
		Message message = (Message)msg;
		
		switch(message.getMessageType()) {
		case TestsList :
			TeacherTestController.testArr = (ArrayList<Test>)message.getMessageData();
			break;
		case GetAllSubjects:
			TeacherTestController.subjects = (ArrayList<Subject>)message.getMessageData();
			break;
		case GetQuestionsBySubject:
			TeacherTestController.questionsBySubject = (ArrayList<Question>)message.getMessageData();
			break;
		case SuccessUpdateTest:
			ClientUI.display("Update succesful");
			break;
		case addQuestion:
			ClientUI.display("Created question successfully");
			break;
		case GetQuestionByID:
			TeacherTestController.specificQ = (Question)message.getMessageData();
			break;
		case AddTest:
			System.out.println("inside clientController.AddTest");
			ClientUI.display((String)message.getMessageData());
			break;
		case GetQCount:
			TeacherTestController.qCountByBank = (int)message.getMessageData();
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
