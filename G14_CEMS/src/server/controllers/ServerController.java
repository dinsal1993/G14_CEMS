package server.controllers;

import java.util.ArrayList;

import client.controllers.ClientUI;
import entity.Message;
import entity.MessageType;
import entity.Test;
import javafx.collections.ObservableList;
import ocsf.server.*; 
import server.dbControl.*;

public class ServerController extends AbstractServer {
	
	public String clientIp;
	public String hostName;
	public String clientConnected = "not connected";
	public static DBConnector dbConnector;

	public ServerController(int port) {
		super(port);
		dbConnector = new DBConnector();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message)msg;
		Message msgFromServer = null;
		ArrayList<Test> arr;
		switch(message.getMessageType()) {
		case GetAllTests:
			arr = TestDBController.getAllTests();
			msgFromServer = new Message(MessageType.TestsList,arr);
			break;
		case UpdateTestDuration:
			TestDBController.updateTestDuration((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.SuccessUpdateTest, null);
			break;
		case GetTestCount:
			int countTest = TestDBController.getTestCount();
			msgFromServer = new Message(MessageType.TestCount, countTest);
			break;
			default:
				msgFromServer = new Message(MessageType.Error, null);
		}
		sendToAllClients(msgFromServer);
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
