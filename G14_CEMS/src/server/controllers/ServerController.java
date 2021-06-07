package server.controllers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import java.sql.Blob;

import client.controllers.ClientUI;
import client.gui.CreateQuestionController;
import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.QuestionBank;
import entity.Test;

import entity.User;
import entity.testCopy;
import entity.TestBank;

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

	public ServerController(int port) {
		super(port);
		dbConnector = new DBConnector();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message) msg;

		switch (message.getMessageType()) {



		case GetAllTestBanks:
			System.out.println("in server controller. recieved msg");
			getAllTestBanks();
			break;
		case UpdateTestDuration:
			TeacherTestDBController.updateTestDuration((Test) message.getMessageData());
			msgFromServer = new Message(MessageType.SuccessUpdateTest, null);
			break;
		case GetTestCount:
			int countTest = TeacherTestDBController.getTestCount();
			msgFromServer = new Message(MessageType.TestCount, countTest);
			break;
		case logIn:
			String logInStatus = UserDBController.tryToConnect((User) message.getMessageData());
			msgFromServer = new Message(MessageType.logIn, logInStatus);
			break;
		case LockTest:
			TeacherTestDBController.lockTest((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.SuccessLockTest, null);
			break;
		case RequestExtraTime:
			TeacherTestDBController.requestExtraTime((testCopy)message.getMessageData());
			msgFromServer = new Message(MessageType.SentExtraTimeRequest, null);
			break;
		case RefreshCourseTable:
			ArrayList<Course> list = TeacherTestDBController.refreshCourseTable();
			msgFromServer = new Message(MessageType.CourseList, list);
			break;
		case AddCourse:
			TeacherTestDBController.addCourse((Course)message.getMessageData());
			msgFromServer = new Message(MessageType.CourseAdded,null);
			break;
		case DeleteCourse:
			TeacherTestDBController.deleteCourse((Course)message.getMessageData());
			msgFromServer = new Message(MessageType.CourseDeleted,null);
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
		sendToAllClients(msgFromServer);
		
	}



	private void getAllTestBanks() {

		HashMap<String, TestBank> testBankMap = TeacherTestDBController.getAllTestBanks();
		msgFromServer = new Message(MessageType.TestBanksList, testBankMap);

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
