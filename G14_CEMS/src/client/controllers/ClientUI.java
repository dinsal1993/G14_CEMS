package client.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.gui.SeeTestsFormController;
import client.gui.mainFormController;
import entity.Message;
import entity.Test;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application {
	
	public static ClientController clientControl;
	
	public static ArrayList<Test> tests;
	
	public static Stage firstStage;
	public static Stage secondStage;
	
	public static mainFormController mainControl;
	public static SeeTestsFormController seeTestsControl;
	
	public static void main(String[] args) {
	    launch(args);
	  }

	@Override
	public void start(Stage primaryStage) throws Exception {
		clientControl = new ClientController("localhost", 5555, this);
		
		ClientUI.firstStage = primaryStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/gui/mainForm.fxml"));
		Parent root = loader.load();
		mainControl = loader.getController();
		Scene scene = new Scene(root);
		firstStage.setTitle("Student Main Menu");
		firstStage.setScene(scene);
		firstStage.show();
		
		mainControl = new mainFormController();
		mainControl.start(primaryStage);
	}
	
	public static void exit() {
		try {
			clientControl.closeConnection();
		}catch (IOException e) {
			System.out.println("Error in closing connection");
		}
		System.exit(0);
	}
	
	public static void display(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public static void accept(Object msg) {
		clientControl.handleMessageFromClientUI(msg);
	}

}
