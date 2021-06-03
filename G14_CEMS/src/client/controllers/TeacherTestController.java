package client.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.QuestionBank;
import entity.Subject;
import entity.Test;
import entity.TestBank;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TeacherTestController {
	public static final int MAX_TEST_TIME = 300;
	public static ObservableList<Test> list = FXCollections.observableArrayList();
	public static ObservableList<Course> courseList = FXCollections.observableArrayList();
	public static ObservableList<TestBank> testBanklist = FXCollections.observableArrayList();
	public static ArrayList<Test> testArr = new ArrayList<>();
	public static ArrayList<Course> courseArr = new ArrayList<>();
	public static Test t = new Test();
	public static testCopy tc = new testCopy();
	public static int testCount;
	public static Course course = new Course();

	public static HashMap<String, TestBank> banksMap;
	public static ArrayList<Subject> subjects;
	public static int qCountByBank;
	public static String subjectID;
	public static ArrayList<Question> questionsBySubject;
	public static String courseID;
	public static int tCountByBankAndCourse;

	public static boolean updateTestValidFields(String testID, String newDuration) {
		int id, duration;
		try {
			id = Integer.parseInt(testID);
			duration = Integer.parseInt(newDuration);
		} catch (Exception e) {
			return false;
		}

		if (id < 1 || id > getTestCount())
			return false;
		if (duration <= 0 || duration > MAX_TEST_TIME)
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

		ObservableList<String> arr = FXCollections.observableArrayList(banksMap.keySet());
		return arr;
	}

	public static ObservableList<String> getCourseList(String subjectName) {
		String subjectID = getSubjectID(subjectName);

		ObservableList<String> arr = FXCollections.observableArrayList();
		Message msg = new Message(MessageType.getCoursesBySubject, subjectID);
		ClientUI.accept(msg);

		for (Course c : courseArr)
			arr.add(c.getName());
		return arr;
	}

	public static void addQuestion(Question q) {
		Message msg = new Message(MessageType.addQuestion, q);
		ClientUI.accept(msg);
	}

	public static void insertQuestionBank(QuestionBank QB) {
		Message msg = new Message(MessageType.insertQuestionBank, QB);
		ClientUI.accept(msg);

	}

	public static void insertTestBank(TestBank TB) {
		Message msg = new Message(MessageType.insertTestBank, TB);
		ClientUI.accept(msg);
	}

	public static ObservableList<String> getAllSubjects(String username) {
		Message msg = new Message(MessageType.GetAllSubjects, username);
		ClientUI.accept(msg);
		ObservableList<String> answer = FXCollections.observableArrayList();
		for (Subject s : subjects)
			answer.add(s.getName());
		return answer;
	}

	public static void lockTest(Test test) {
		Message msg = new Message(MessageType.LockTest, test);
		ClientUI.accept(msg);

	}

	public static void requestExtraTime(testCopy test) {
		Message msg = new Message(MessageType.RequestExtraTime, test);
		ClientUI.accept(msg);

	}

	public static ObservableList<Course> refreshCourseTable() {
		Message msg = new Message(MessageType.RefreshCourseTable, courseArr);
		ClientUI.accept(msg);

		/*
		 * for (Course c : courseList) courseList.remove(c);
		 */
		for (Course c : courseArr)
			courseList.add(c);

		return courseList;

	}

	public static void addCourse(Course c) {
		Message msg = new Message(MessageType.AddCourse, c);
		ClientUI.accept(msg);
	}

	public static void deleteCourse(Course c) {
		Message msg = new Message(MessageType.DeleteCourse, c);
		ClientUI.accept(msg);
	}

	// get count of questions in bank
	public static int getQCount(String subjectID, String username) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(username);
		Message msg = new Message(MessageType.GetQCount, arr);
		ClientUI.accept(msg);
		return qCountByBank;
	}

	public static String getSubjectID(String bankName) {
		Message msg = new Message(MessageType.GetSubjectID, bankName);
		ClientUI.accept(msg);
		return subjectID;

	}

	public static ObservableList<Question> getQuestionsBySubject(String subjectID, String username) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(username);
		Message msg = new Message(MessageType.GetQuestionsBySubject, arr);
		ClientUI.accept(msg);

		ObservableList<Question> qList = FXCollections.observableArrayList(questionsBySubject);
		return qList;

	}

	// before add question to question list check if question exist and
	// teacher input valid score
	public static String checkValidQuestionID(String questionID, String score,int currentSum, ObservableList<String> questionsInTest) {
		boolean questionExist = false, scoreValid = false;
		int scoreNum = 0;
		//check that teacher input a valid questionID from the list of
		//possible questions
		for(Question q : questionsBySubject) {
			if(q.getId().equals(questionID))
				questionExist = true;
		}
		//check if question already added to test
		for(String s : questionsInTest) {
			if(s.equals(questionID))
				return "question already in test";
		}
		//check score input is a number
		try {
			scoreNum = Integer.parseInt(score);
		}catch(NumberFormatException e) {
			e.getStackTrace();
			return "Score must be a number";
		}
		//check total sum not over 100
		if(currentSum + scoreNum > 100)
			return "total points sum > 100";
		//check score is between 1 and 100
		else if (scoreNum < 1 || scoreNum > 100)
			return "Score must be between 1 and 100";
		else
			scoreValid = true;
		if(scoreValid && questionExist)
			return "valid";
		return "not valid";
	}

	public static String getCourseID(String subject, String courseName) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subject);
		arr.add(courseName);
		Message msg = new Message(MessageType.GetCourseID, arr);
		ClientUI.accept(msg);
		return courseID;
	}

	public static int getTCount(String subjectID, String courseID) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(courseID);
		Message msg = new Message(MessageType.GetTCount, arr);
		ClientUI.accept(msg);
		return tCountByBankAndCourse;
	}

}
