package client.controllers;

import entity.Message;
import entity.MessageType;
import entity.User;
import javafx.stage.Stage;

public class UserController {

	/**
	 * true if user exist otherwise false
	 */
	public static boolean userExist;
	/**
	 * true if user connected otherwise false
	 */
	public static boolean flagUserAlreadyConnected;
	
	/**
	 * true if username and password match otherwise false
	 */
	public static boolean validInfo;
	
	/**
	 * Permission - the kind of the user {student,principle,teacher}
	 */
	public static String permission;
	
	/**
	 * the condition of the user after he tried to login 
	 */
	public static String logInStatus;
	
	/**
	 * current stage of the user 
	 */
	public static Stage currentStage;
	
	/**
	 * stage before current stage of the user 
	 */
	public static Stage extraStage;
	
	public static Stage extraStage2;
	
	
	/**
	 *  send to the server the details about the login
	 * @param username of the user
	 * @param password of the user
	 * @return the condition of the user - success-teacher,principle,student,or why login failed
	 */

	public static String logIn(String username, String password) {
		User currentLogIn = new User(username,password);
		Message msg = new Message(MessageType.logIn,currentLogIn);
		ClientUI.accept(msg);
		return logInStatus;
	}
	
	
	
}
