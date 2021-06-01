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
	public static Test testQuestions = new Test();
	public static testCopy tc = new testCopy();
	public static int testCount;
	public static Course course = new Course();
    public static int currentQuestions = 0;
    public static int currentBanks = 0;
    public static String testID;
	
	public static HashMap<String, TestBank> banksMap;
	public static HashMap<String, Question> questionMap;
	public static HashMap<String, Test> testMap;
	public static ArrayList<String> QuestionArr;
	public static ArrayList<Question> QuestionList;
	
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
	
	public static void lockTest(Test test) {
		Message msg = new Message(MessageType.LockTest, test);
		ClientUI.accept(msg);
		
	}
	
	public static void requestExtraTime(testCopy test) {
		Message msg = new Message(MessageType.RequestExtraTime, test);
		ClientUI.accept(msg);
		
	}
	
	public static ObservableList<Course> refreshCourseTable()
	{
		Message msg = new Message(MessageType.RefreshCourseTable, courseArr);
		ClientUI.accept(msg);
		
		/*for (Course c : courseList)
			courseList.remove(c);*/
		for (Course c : courseArr)
			courseList.add(c);
		
		return courseList;
		
	}
	
	public static void addCourse(Course c)
	{
		Message msg = new Message(MessageType.AddCourse,c);
		ClientUI.accept(msg);
	}
	
	public static void deleteCourse(Course c)
	{
		Message msg = new Message(MessageType.DeleteCourse,c);
		ClientUI.accept(msg);
	}
	
	public static int getCurrentQuestionNum()
	{
		Message msg = new Message(MessageType.GetQuestionsNumber,null);
		ClientUI.accept(msg);
		return currentQuestions;
		
	}
	
	public static int getCurrentBankNum()
	{
		Message msg = new Message(MessageType.GetQuestionBankNumber,null);
		ClientUI.accept(msg);
		return currentBanks;
	}
	
	public static ObservableList<Question> getAllQuestions() {		
		Message msg = new Message(MessageType.GetAllQuestions, null);
		ClientUI.accept(msg);
		
		return FXCollections.<Question>observableArrayList(QuestionList);
	}
	
	public static HashMap<String, Test> getTestQuestions() {		
	
		Message msg = new Message(MessageType.GetTestQuestions, null);
		ClientUI.accept(msg);
		
		return testMap;
	}
	
	public static String getTestID(String code)
	{
		Message msg = new Message(MessageType.GetTestCode,code);
		ClientUI.accept(msg);
		
		return testID;
	}

}
