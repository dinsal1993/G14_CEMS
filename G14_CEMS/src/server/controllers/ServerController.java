package server.controllers;


import java.io.BufferedInputStream;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import java.sql.Blob;

import client.controllers.ClientUI;
import client.controllers.StudentController;
import client.controllers.UserController;
import client.gui.CreateQuestionController;
import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.QuestionBank;
import entity.Subject;
import entity.Test;

import entity.User;
import entity.testCopy;
import entity.TestBank;
import entity.TestDocs;
import javafx.collections.ObservableList;
import ocsf.server.*;
import server.dbControl.*;

public class ServerController extends AbstractServer {

	private static Question q;
	public String clientIp;
	public String hostName;
	public String clientConnected = "Not Connected";
	public static DBConnector dbConnector;
	Message msgFromServer = null;
	boolean specificMsg = false;
	String temp;

	public ServerController(int port) {
		super(port);
		dbConnector = new DBConnector();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message) msg;
		boolean check;
		switch (message.getMessageType()) {
		case Hello:
			//client.setInfo("username",(String)message.getMessageData());
			//HashMap<String,Object> ass = new HashMap<String, Object>();
			msgFromServer = new Message(MessageType.Hello, "hello " + (String)message.getMessageData());
			break;
		case GetAllSubjects:
			ArrayList<Subject> subjects = QuestionDBController.getAllSubjects((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GetAllSubjects, subjects);
			break;
		case GetAllTests:
			getAllTests((String)message.getMessageData());
			break;
		case GetAllTestsBySubject:
			getAllTestsBySubject((String)message.getMessageData());
			break;
		case GetAllTestsDocsBySubject:
			getAllTestsDocsBySubject((ArrayList<String>)message.getMessageData());
			break;
		case getCoursesBySubject:
			getCoursesBySubject((String)message.getMessageData());
			break;
		case GetNextQID:
			getNextQID((ArrayList<String>)message.getMessageData());
			break;
		case GetNextTID:
			getNextTID((ArrayList<String>)message.getMessageData());
			break;
		case addQuestion:
			check = QuestionDBController.addQuestion((Question) message.getMessageData());
			msgFromServer = new Message(MessageType.addQuestion, check);
			break;
		case DeleteQuestion:
			check = QuestionDBController.deleteQuestion((Question)message.getMessageData());
			msgFromServer = new Message(MessageType.DeleteQuestion, check);
			break;
		case deleteTest:
			System.out.println("recieved msg: serverController: test is: " + (Test)message.getMessageData());
			check = TeacherTestDBController.deleteTest((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.deleteTest, check);
			break;
		case updateTest:
			check = TeacherTestDBController.updateTest((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.deleteTest, check);
			break;
		case GetQuestionByID:
			Question q = QuestionDBController.getQuestionByID((ArrayList<String>)message.getMessageData());
			msgFromServer = new Message(MessageType.GetQuestionByID, q);
			break;
		case AddTest:
			String temp = TeacherTestDBController.addTest((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.AddTest, temp);
		case UpdateQuestion:
			check = QuestionDBController.updateQuestion((Question)message.getMessageData());
			msgFromServer = new Message(MessageType.UpdateQuestion, check);
			break;

		case GetSubjectID:
			getSubjectID((String)message.getMessageData());
			break;
		case GetCourseID:
			getCourseID((ArrayList<String>)message.getMessageData());
			break;
		case GetQuestionsBySubject:
			getQuestionsBySubject((ArrayList<String>)message.getMessageData());
			break;
		case logIn:
			String logInStatus = UserDBController.tryToConnect((User) message.getMessageData());
			msgFromServer = new Message(MessageType.logIn, logInStatus);
			break;
		case LockTest:
			//TeacherTestDBController.lockTest((Test)message.getMessageData());
			lockTest(TeacherTestDBController.lockTestDin((String)message.getMessageData()));
			specificMsg = true;
			break;

		case CheckTest:
			boolean flag = StudentDBController.checkTest((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CheckedTest,flag);
			break;
		case CheckStudentID:
			boolean isStudentIDExist = StudentDBController.checkStudentID((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CheckedStudentID,isStudentIDExist);
			break;
		case CheckValidCode:
			String testId = StudentDBController.checkValidCode((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CheckedCode,testId);
			break;
		case execCode:
			String id = TestDBController.FindTestIdAccordingToExecCode((String)message.getMessageData());
			 msgFromServer = new Message(MessageType.execCode, id);
			break;
		case downloadManualTest:
			byte[] byteManualTest = TestDBController.getTest((String)message.getMessageData()); 
			 msgFromServer = new Message(MessageType.downloadManualTest, byteManualTest); 
			 break;
		case submitManualTest:
			boolean saveTestInDB = TestDBController.SaveManualTest((byte[])message.getMessageData());
			if(saveTestInDB) { msgFromServer = new Message(MessageType.submitManualTest, "Successfully submitted");}
			else { msgFromServer = new Message(MessageType.submitManualTest, "Error in submit the test in to the database");}

			break;
			
		case ContinuePlanTest:
			boolean ValidIdAndUsername = TeacherTestDBController.checkValidIdAndUsernameTest((ArrayList<String>)message.getMessageData());
			if(ValidIdAndUsername) { msgFromServer = new Message(MessageType.ContinuePlanTest, "TestID and username matched");}
			else { msgFromServer = new Message(MessageType.ContinuePlanTest, "TestID and username not matched");}
			break;
			
		case InsertPlanTest:
			String insertPlanToDb = TeacherTestDBController.insertPlanTestToDB((ArrayList<String>)message.getMessageData());
			msgFromServer = new Message(MessageType.InsertPlanTest,insertPlanToDb);
			break;
			
			//RAGAH - en bdika she ze takin.ok? 
			//add to HAShMAP ConnectionClient. Shr
		case AddStudentToOnGoing:
			TestDBController.addConnectionClientToHashMap
			(client,((ArrayList<String>)message.getMessageData()).get(2));// client, username of the student
			TestDBController.addStudentToOnGoing
			((ArrayList<String>)message.getMessageData());
			break;
		case RemoveStudentFromOnGoing:
			TestDBController.removeConnectionClientToHashMap
			(((ArrayList<String>)message.getMessageData()).get(2));// client, username of the student
			TestDBController.removeStudentFromOnGoing((ArrayList<String>)message.getMessageData());
			break;
			
		case lockTest:
			TestDBController.lockTest((String)message.getMessageData());
			break;
		case AddExecCodeToTestDB:
			TestDBController.addExecCodetoTestDB((ArrayList<String>)message.getMessageData());
			break;
			
			
			/// ragah
			
		case GetTestQuestions:
			Test test = QuestionDBController.getTestQuestions((String)message.getMessageData());
			msgFromServer = new Message(MessageType.TestQuestions, test);
			break;
		case SubmitTest:
			StudentDBController.submitTest((testCopy)message.getMessageData());
			msgFromServer = new Message(MessageType.SubmittedTest, null);
			break;
		case AddStudentToOnGoingOnline:
			StudentDBController.addStudentToOnGoing((ArrayList<String>)message.getMessageData());
			break;
		case RemoveStudentFromOnGoingOnline:
			StudentDBController.removeStudentFromOnGoing((ArrayList<String>)message.getMessageData());
			break;

		case GetExamDate:
			ArrayList<String> examDate = new ArrayList<>();
			examDate = StudentDBController.getExamDate((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GotExamDate, examDate);
			break;
			
			
		default:	
			msgFromServer = new Message(MessageType.Error, null);
		}

		if(specificMsg == false)
			sendToAllClients(msgFromServer);
		else {
			specificMsg = false;
			//send to specific clients
		}

	}

	private void getAllTestsDocsBySubject(ArrayList<String> arr) {
		ArrayList<TestDocs> answer = ReportDBController.getAllTestsDocsBySubject(arr);
		msgFromServer = new Message(MessageType.GetAllTestsDocsBySubject, answer);
		
	}

	private void getAllTestsBySubject(String subject) {
		ArrayList<Test> arr = TeacherTestDBController.getAllTestsBySubject(subject);
		msgFromServer = new Message(MessageType.GetAllTestsBySubject, arr);
	}

	private void getNextTID(ArrayList<String> arr) {
		int count = TeacherTestDBController.getNextTID(arr);
		msgFromServer = new Message(MessageType.GetNextTID, count);
		
	}
	
	
	/**get all the tests the teacher wrote
	 * @param username teacher username
	 */
	private void getAllTests(String username) {
		ArrayList<Test> arr = TeacherTestDBController.getAllTests(username);
		msgFromServer = new Message(MessageType.GetAllTests, arr);
		
	}

	/**
	 * @param usersList
	 */
	private void lockTest(ArrayList<String> usersList) {
		Thread[] connections = super.getClientConnections();
		Message msg = new Message(MessageType.LockTest, null);
		for(String s : usersList) {
			for(Thread t : connections) {
				ConnectionToClient c = (ConnectionToClient)t;
				try {
					if(c.getInfo("username").equals(s))
						c.sendToClient(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

	private void getCourseID(ArrayList<String> arr) {
		String id = TeacherTestDBController.getCourseID(arr);
		if(id == null)
			msgFromServer = new Message(MessageType.Error, "no such course");
		else
			msgFromServer = new Message(MessageType.GetCourseID, id);
		
	}

	private void getQuestionsBySubject(ArrayList<String> arr) {
		ArrayList<Question> answer = TeacherTestDBController.getQuestionsBySubject(arr);
		msgFromServer = new Message(MessageType.GetQuestionsBySubject, answer);
	}

	private void getCoursesBySubject(String subjectID) {
		ArrayList<Course> arr = TeacherTestDBController.getCoursesBySubject(subjectID);
		msgFromServer = new Message(MessageType.getCoursesBySubject, arr);
		
	}

	private void getSubjectID(String bankName) {
		String id = QuestionDBController.getSubjectID(bankName);
		if(id == null)
			msgFromServer = new Message(MessageType.Error, "no such question bank");
		msgFromServer = new Message(MessageType.GetSubjectID, id);
	}

	private void getNextQID(ArrayList<String> arr) {
		int count = QuestionDBController.getNextQID(arr);
		msgFromServer = new Message(MessageType.GetNextQID, count);
		
	}



	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		clientConnected = "connected";
		clientIp = client.getInetAddress().getHostAddress();
		hostName = client.getInetAddress().getHostName();
		System.out.println(client + " connected !");
	}

	@Override
	protected void serverStarted() {
		super.serverStarted();
		System.out.println("server started and listening on port " + getPort());
	}
}
