package client.controllers;

import entity.Message;
import entity.MessageType;
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

	public static boolean isExecutionCodeValid(String executionCode) {
		
		Message msg = new Message(MessageType.CheckValidCode, executionCode);
		ClientUI.accept(msg);
		return validCode;
	}
	
	
}//End StudentController
