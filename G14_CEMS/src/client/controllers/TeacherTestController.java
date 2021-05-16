package client.controllers;

import java.util.ArrayList;

import entity.Message;
import entity.MessageType;
import entity.Test;
import entity.TestBank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TeacherTestController {
	public static final int MAX_TEST_TIME = 300;
	public static ObservableList<Test> list = FXCollections.observableArrayList();
	public static ArrayList<Test> testArr = new ArrayList<>();
	public static Test t = new Test();
	public static int testCount;
	
	public static ArrayList<String> testBankArray = new ArrayList<String>();

	public static boolean updateTestValidFields(String testID, String newDuration) {
		int id, duration;
		try {
			id = Integer.parseInt(testID);
			duration = Integer.parseInt(newDuration);
		}catch(Exception e) { return false; }
		
		if(id < 1 || id > getTestCount())
			return false;
		if(duration <= 0 || duration > MAX_TEST_TIME)
			return false;
		return true;
		
	}

	private static int getTestCount() {
		Message msg = new Message(MessageType.GetTestCount, null);
		ClientUI.accept(msg);
		return testCount;
	}

	public static void updateTestDuration(Test test) {
		Message msg = new Message(MessageType.UpdateTestDuration, test);
		ClientUI.accept(msg);
		
	}

	public static void getAllTest() {
		Message msg = new Message(MessageType.GetAllTests, null);
		ClientUI.accept(msg);
		
		for (Test t : list)
			list.remove(t);
		for (Test t : testArr)
			list.add(t);
	}


	public static ObservableList<String> getAllTestBanks() {		
		Message msg = new Message(MessageType.GetAllTestBanks, null);
		ClientUI.accept(msg);
		return FXCollections.<String>observableArrayList(testBankArray);
	}

}
