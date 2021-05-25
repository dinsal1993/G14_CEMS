package client.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.QuestionBank;
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

	
	public static HashMap<String, TestBank> banksMap;
	public static ArrayList<String> QuestionArr;

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
		
		ObservableList<String> arr =
				FXCollections.observableArrayList(banksMap.keySet());
		return arr;
	}

	public static ObservableList<String> getCourseList(String bankName) {
		ObservableList<String> arr = FXCollections.observableArrayList();
		ArrayList<Course> courses = banksMap.get(bankName).getCourses();

		for(Course c : courses)
			arr.add(c.getName());
		return arr;
	}
	
	public static void addQuestion(Question q) {
		Message msg = new Message(MessageType.addQuestion,q);
		ClientUI.accept(msg);
	}
	
	public static void insertQuestionBank(QuestionBank QB)
	{
		Message msg = new Message(MessageType.insertQuestionBank,QB);
		ClientUI.accept(msg);
		
	}

	public static ObservableList<String> getAllQBanks() {		
		Message msg = new Message(MessageType.GetAllQuestionBank, null);
		ClientUI.accept(msg);
		return FXCollections.<String>observableArrayList(QuestionArr);
	}

}
