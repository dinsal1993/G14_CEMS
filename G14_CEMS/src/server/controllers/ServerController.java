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
		case GetAllTests:
			ArrayList<Test> arr = TeacherTestDBController.getAllTests();
			msgFromServer = new Message(MessageType.TestsList, arr);
			break;
		case GetAllQuestionBank:
			ArrayList<String> arrQ = QuestionDBController.getAllQuestionBanks();
			msgFromServer = new Message(MessageType.QuestionBankList, arrQ);
			break;
		case addQuestion:
			QuestionDBController.addQuestion((Question) message.getMessageData());
			msgFromServer = new Message(MessageType.addQuestion, null);
			break;
		case insertQuestionBank:
			try {
				QuestionDBController.insertQuestionBank((QuestionBank) message.getMessageData());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msgFromServer = new Message(MessageType.insertQuestionBank, null);
			break;
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
			byte[] byteManualTest = TestDBController.getTest((String)message.getMessageData()); // lo hayav, efshar she tamid nase ba func shel get test she yavi et a blob shel execcODE 0000 , LE SHEM HAVANA , OLAY NIMHAK
			 msgFromServer = new Message(MessageType.downloadManualTest, byteManualTest); // mabey blob not object dont forgert
			 break;
		case submitManualTest:
			boolean saveTestInDB = TestDBController.SaveManualTest((byte[])message.getMessageData());
			if(saveTestInDB) { msgFromServer = new Message(MessageType.submitManualTest, "Successfully submitted");}
			else { msgFromServer = new Message(MessageType.submitManualTest, "Error in submit the test in to the database");}
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
