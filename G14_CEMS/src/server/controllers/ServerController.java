package server.controllers;


import java.io.BufferedInputStream;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import java.sql.Blob;

import client.controllers.ClientUI;
import client.controllers.StudentController;
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
			client.setInfo("username",(String)message.getMessageData());
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
		case getCoursesBySubject:
			getCoursesBySubject((String)message.getMessageData());
			break;
		case GetNextQID:
			getNextQID((ArrayList<String>)message.getMessageData());
			break;
		case GetNextTID:
			getNextTID((ArrayList<String>)message.getMessageData());
			break;
		case GetTCount:
			getTCount((ArrayList<String>)message.getMessageData());
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
			break;
		case UpdateQuestion:
			check = QuestionDBController.updateQuestion((Question)message.getMessageData());
			msgFromServer = new Message(MessageType.UpdateQuestion, check);
			break;

		case GetTestCount:
			int countTest = TeacherTestDBController.getTestCount();
			msgFromServer = new Message(MessageType.TestCount, countTest);
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
		case RequestExtraTime:
			TeacherTestDBController.requestExtraTime((testCopy)message.getMessageData());
			msgFromServer = new Message(MessageType.SentExtraTimeRequest, null);
			break;
		//case RefreshCourseTable:
		//	ArrayList<Course> list = TeacherTestDBController.refreshCourseTable();
		//	msgFromServer = new Message(MessageType.CourseList, list);
		//	break;
		case AddCourse:
			TeacherTestDBController.addCourse((Course)message.getMessageData());
			msgFromServer = new Message(MessageType.CourseAdded,null);
			break;
		case DeleteCourse:
			TeacherTestDBController.deleteCourse((Course)message.getMessageData());
			msgFromServer = new Message(MessageType.CourseDeleted,null);
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
			boolean isCodeExist = StudentDBController.checkValidCode((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CheckedCode,isCodeExist);

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

		if(specificMsg == false)
			sendToAllClients(msgFromServer);
		else {
			specificMsg = false;
			//send to specific clients
		}

	}

	private void getAllTestsBySubject(String subject) {
		ArrayList<Test> arr = TeacherTestDBController.getAllTestsBySubject(subject);
		msgFromServer = new Message(MessageType.GetAllTestsBySubject, arr);
	}

	private void getNextTID(ArrayList<String> arr) {
		int count = TeacherTestDBController.getNextTID(arr);
		msgFromServer = new Message(MessageType.GetNextTID, count);
		
	}

	private void getAllTests(String username) {
		ArrayList<Test> arr = TeacherTestDBController.getAllTests(username);
		msgFromServer = new Message(MessageType.GetAllTests, arr);
		
	}

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

	private void getTCount(ArrayList<String> arr) {
		int count = TeacherTestDBController.getTestCount(arr);
		msgFromServer = new Message(MessageType.GetTCount, count);
		
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
