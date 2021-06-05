package client.controllers;

import java.util.ArrayList;

import entity.Message;
import entity.MessageType;
import entity.testCopy;
import server.dbControl.StudentDBController;

public class StudentController {

	public static boolean testExist;
	public static boolean studentIDExist;
	public static boolean validCode;
	
	public static boolean isTestExist(String testID)
	{
		Message msg = new Message(MessageType.CheckTest, testID);
		ClientUI.accept(msg);
		return testExist;
	}
	
	public static boolean isStudentIDExist(String studentID)
	{
		Message msg = new Message(MessageType.CheckStudentID, studentID);
		ClientUI.accept(msg);
		return studentIDExist;
	}

	public static boolean isExecutionCodeValid(String code) {
		
		Message msg = new Message(MessageType.CheckValidCode, code);
		ClientUI.accept(msg);
		return validCode;
	}

	public static void submitTest(testCopy tc)
	{
		Message msg = new Message(MessageType.SubmitTest, tc);
		ClientUI.accept(msg);
		return;
	}

	public static void AddStudentToOnGoing(ArrayList<String> studentDetails) {
		
		Message msg = new Message(MessageType.AddStudentToOnGoing, studentDetails);
		ClientUI.accept(msg);
		return;
		
	}

	public static void removeStudentFromOnGoing(ArrayList<String> studentDetails)
	{
		Message msg = new Message(MessageType.RemoveStudentFromOnGoing, studentDetails);
		ClientUI.accept(msg);
		return;
		
	}
	
	
}//End StudentController
