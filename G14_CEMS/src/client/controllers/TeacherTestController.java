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

	public static ArrayList<Course> courseArr = new ArrayList<>();
	public static Test t = new Test();
	public static testCopy tc = new testCopy();
	public static int testCount;
	public static Course course = new Course();

	public static HashMap<String, TestBank> banksMap;
	public static ArrayList<Subject> subjects;
	public static int nextQID;
	public static int nextTID;
	public static String subjectID;
	public static ArrayList<Question> questionsBySubject;
	public static String courseID;
	public static int tCountByBankAndCourse;
	public static Question specificQ;
	public static ArrayList<Test> testArr;

	public static ArrayList<Test> getAllTests(String username) {
		Message msg = new Message(MessageType.GetAllTests, username);
		ClientUI.accept(msg);

		return testArr;
	}
	
	public static ArrayList<Test> getAllTestsBySubject(String subjectID) {
		Message msg = new Message(MessageType.GetAllTestsBySubject, subjectID);
		ClientUI.accept(msg);

		return testArr;
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
	
	public static String getCourseIDNotDB(String subjectID, String courseName) {
		for(Course c: courseArr) {
			if(c.getSubjectID().equals(subjectID) && c.getName().equals(courseName))
				return c.getCourseID();
		}
		return null;
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


	public static int getNextQID(String subjectID, String username) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(username);
		Message msg = new Message(MessageType.GetNextQID, arr);
		ClientUI.accept(msg);
		return nextQID;
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

	// before add question to question list in test check if question exist and
	// teacher input valid score
	public static String checkValidQuestionID(String questionID, String score, int currentSum,
			ObservableList<String> questionsInTest) {
		boolean questionExist = false, scoreValid = false;
		int scoreNum = 0;
		System.out.println("in checkValidQuestionID");
		// check that teacher input a valid questionID from the list of
		// possible questions
		for (Question q : questionsBySubject) {
			if (q.getId().equals(questionID))
				questionExist = true;
		}
		// check if question already added to test
		for (String s : questionsInTest) {
			if (s.equals(questionID))
				return "question already in test";
		}
		// check score input is a number
		try {
			scoreNum = Integer.parseInt(score);
		} catch (NumberFormatException e) {
			e.getStackTrace();
			return "Score must be a number";
		}
		// check total sum not over 100
		if (currentSum + scoreNum > 100)
			return "total points sum > 100";
		// check score is between 1 and 100
		else if (scoreNum < 1 || scoreNum > 100)
			return "Score must be between 1 and 100";
		else
			scoreValid = true;
		if (scoreValid && questionExist)
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

	public static int getTCount(String subjectID, String courseID, String username) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(courseID);
		arr.add(username);
		Message msg = new Message(MessageType.GetTCount, arr);
		ClientUI.accept(msg);
		return tCountByBankAndCourse;
	}

	public static Question getQuestionByID(String id, String username) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(id);
		arr.add(username);
		Message msg = new Message(MessageType.GetQuestionByID, arr);
		ClientUI.accept(msg);
		return specificQ;
	}

	public static String isValidFieldsCreateQuestion(Question q, String subject, String correctAnswer) {
		if (subject == null)
			return "please choose subject";
		else if (q.getAnswers().get(0).equals("") || q.getAnswers().get(1).equals("")
				|| q.getAnswers().get(2).equals("") || q.getAnswers().get(3).equals(""))
			return "please write answers";
		else if (correctAnswer == null)
			return "please choose correct answer";
		else if (q.getDescription().equals(""))
			return "please enter description";
		return "valid";
	}
	
	public static String isValidFieldsCreateTest(Test t,String subject, String course, String duration) {
		if (subject == null)
			return "please choose subject";
		else if (course == null)
			return "please choose course";
		else if (duration.equals("") || duration == null)
			return "please enter duration";
		else if (t.getPointsPerQuestion().size() == 0)
			return "did not set points for questions";
		else if (t.getQuestions().size() == 0)
			return "did not set questions for test";
		return "valid";
	}

	public static String getNextQuestionID(String teacherUsername, String subject) {
		String subjectID = getSubjectID(subject);
		int nextID = getNextQID(subjectID, teacherUsername);
		if (nextID < 10)
			return (subjectID + "00" + nextID);
		else if (nextID <= 99)
			return (subjectID + "0" + nextID);
		else if (nextID <= 999)
			return (subjectID + nextID);
		else {
			return "error";
		}
	}

	public static String getNextTestID(String subjectID, String courseID, String teacherUsername) {
		int nextID = getNextTID(subjectID, courseID, teacherUsername);
		if (nextID < 10)
			return (subjectID + courseID + "0" + nextID);
		else if (nextID <= 99)
			return (subjectID + courseID + nextID);
		else
			return "error";
	}

	private static int getNextTID(String subjectID, String courseID, String teacherUsername) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(courseID);
		arr.add(teacherUsername);
		Message msg = new Message(MessageType.GetNextTID, arr);
		ClientUI.accept(msg);
		return nextTID;
	}
}
