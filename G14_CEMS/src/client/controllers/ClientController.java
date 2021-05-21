package client.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entity.Message;
import entity.MessageType;
import entity.Test;
import javafx.collections.ObservableList;
import ocsf.client.*;

public class ClientController extends AbstractClient {
	
	public static boolean awaitResponse = false;
	public ClientUI clientUI;

	public ClientController(String host, int port, ClientUI clientUI) {
		super(host, port);
		this.clientUI = clientUI;
		//openConnection();
		//closeConnection();
	}


	@Override
	protected void handleMessageFromServer(Object msg) {
		awaitResponse = false;
		Message message = (Message)msg;
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
			TeacherTestController.testBankArray = (ArrayList<String>)message.getMessageData();
			break;
			default:
				ClientUI.display("cant read message from server");
		}
		
	}
	
	public void handleMessageFromClientUI(Object msg) {
		try
	    {
	    	openConnection();//in order to send more than one message
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
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	      ClientUI.display("Could not send message to server");
	      
	    }
	}

}
