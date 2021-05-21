package server.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import client.controllers.ClientUI;
import entity.Message;
import entity.MessageType;
import entity.Test;

import entity.User;

import entity.TestBank;

import javafx.collections.ObservableList;
import ocsf.server.*; 
import server.dbControl.*;


public class ServerController extends AbstractServer {
	
	public String clientIp;
	public String hostName;
	public String clientConnected = "not connected";
	public static DBConnector dbConnector;
	Message msgFromServer = null;

	public ServerController(int port) {
		super(port);
		dbConnector = new DBConnector();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message)msg;
		
		switch(message.getMessageType()) {
		case GetAllTests:
			ArrayList<Test> arr = TeacherTestDBController.getAllTests();
			msgFromServer = new Message(MessageType.TestsList,arr);
			break;
		case GetAllTestBanks:
			System.out.println("in server controller. recieved msg");
			getAllTestBanks();
			break;
		case UpdateTestDuration:
			TeacherTestDBController.updateTestDuration((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.SuccessUpdateTest, null);
			break;
		case GetTestCount:
			int countTest = TeacherTestDBController.getTestCount();
			msgFromServer = new Message(MessageType.TestCount, countTest);
			break;
		case logIn:
			String logInStatus = UserDBController.tryToConnect( (User)message.getMessageData() );
			msgFromServer = new Message(MessageType.logIn,logInStatus);
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
