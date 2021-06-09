package client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.gui.ManualTestController;
import client.gui.StudentMenuController;
import entity.Message;
import entity.MessageType;
import entity.testCopy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ocsf.server.ConnectionToClient;

public class StudentController {

	public static boolean testExist;
	public static boolean studentIDExist;
	public static boolean validCode;
	public static ArrayList<String> examDate;
	/**
	 * flag is true when the student do manual test
	 */
	public static boolean flagForManualTest;
	/**
	 * the client's that doing the test
	 */
	public static List<ConnectionToClient> onGoingTestStudents;
	/**
	 * if theacher locked the test so flag is true
	 */
	public static boolean flagForLockTest=false;

	public static boolean isTestExist(String testID) {
		Message msg = new Message(MessageType.CheckTest, testID);
		ClientUI.accept(msg);
		return testExist;
	}

	public static boolean isStudentIDExist(String studentID) {
		Message msg = new Message(MessageType.CheckStudentID, studentID);
		ClientUI.accept(msg);
		return studentIDExist;
	}

	public static boolean isExecutionCodeValid(String code) {

		Message msg = new Message(MessageType.CheckValidCode, code);
		ClientUI.accept(msg);
		return validCode;
	}

	public static void submitTest(testCopy tc) {
		Message msg = new Message(MessageType.SubmitTest, tc);
		ClientUI.accept(msg);
		return;
	}

	public static void AddStudentToOnGoing(ArrayList<String> studentDetails) {

		Message msg = new Message(MessageType.AddStudentToOnGoing, studentDetails);
		ClientUI.accept(msg);
		return;

	}

	public static void removeStudentFromOnGoing(ArrayList<String> studentDetails) {
		Message msg = new Message(MessageType.RemoveStudentFromOnGoing, studentDetails);
		ClientUI.accept(msg);
		return;

	}

	public static ArrayList<String> getExamDate(String executionCode) {
		Message msg = new Message(MessageType.GetExamDate, executionCode);
		ClientUI.accept(msg);
		return examDate;

	}

	/*public static void lockTest() {
		
		ManualTestController a = new ManualTestController();
		a.lockedTest();

		/*if(ScreenControllers.manualTestController != null)
		ScreenControllers.manualTestController.lockedTest();
		else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMenuForm.fxml"));
			Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
				UserController.currentStage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ClientUI.display("TEACHER LOCKED THE TEST! Good luck next time");

		}


	}*/
	
	
	

	/**
	 * 
	 */
	/*public static void testBeenLocked() {
		ClientUI.display("TEACHER LOCKED THE TEST! Good luck next time");
		if (flagForManualTest) {// student choose to do manual test
			FXMLLoader loader = ScreenControllers.studentMenuController.
			Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
				UserController.currentStage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Message msg2 = new Message
					(MessageType.RemoveStudentFromOnGoing, ManualTestController.studentDetails);
			ClientUI.accept(msg2);
		}*/

	}// End StudentController

