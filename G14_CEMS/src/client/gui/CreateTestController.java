package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.TestBank;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateTestController {

	@FXML
	private AnchorPane UpperAP_CreateTest;

	@FXML
	private Text CreateTestTitle;

	@FXML
	private AnchorPane LowerAP_CreateTest;

	@FXML
	private Label BNtxt_CreateTest;

	@FXML
	private Label CourseNametxt_CreateTest;

	@FXML
	private Label Durationtxt_CreateTest;

	@FXML
	private Label TNotestxt_CreateTest;

	@FXML
	private Label Snotestxt_CreateTest;

	@FXML
	private ComboBox<String> cmbBankName;

	@FXML
	private ComboBox<String> cmbCourseName;

	@FXML
	private TextField txtDuration;

	@FXML
	private TextArea txtStudentNotes;

	@FXML
	private TextArea txtTeacherNotes;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnAddQuestion;

	@FXML
	private Button btnCreateTest;
	 
	@FXML
	private Label ExecutionCodetxt_CreateTest;

	@FXML
	private TextField txtExeCode;

	@FXML
	private Button btnGenerateCode;

	private boolean fromNewBank;

	private ObservableList<String> testBankList;
	private ObservableList<String> courseList;

	@FXML
	void click_AddQuestion(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource
				("AddQuestionForm.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		UserController.currentStage.hide();
		UserController.extraStage.setScene(scene);
		UserController.extraStage.show();
		ScreenControllers.addQuestionControl.start(cmbBankName.getValue());
	}

	@FXML
	void click_Back(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource
				("TeacherMenuForm.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.teacherMenuController.start();
	}

	@FXML
	void click_CreateTest(ActionEvent event) {

	}

	public void start() {
		initUI();
		bankListener();
	}

	private void bankListener() {
		cmbBankName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue != null && oldValue != null) {
					cmbCourseName.getItems().clear();
					courseList = 
							TeacherTestController.getCourseList(newValue);
					cmbCourseName.getItems().addAll(courseList);
				}
				
				
			}
		});
	}

	private void initUI() {
		cmbBankName.getItems().clear();
		cmbCourseName.getItems().clear();
		txtDuration.setText("");
		txtTeacherNotes.setText("");
		txtStudentNotes.setText("");

		// add banks to Bank comboBox
		testBankList = TeacherTestController.getAllTestBanks();
		//System.out.println(testBankList);
		cmbBankName.getItems().addAll(testBankList);

	}

	public void startFromNewBank(String newBankName, String firstCourse) {
		// TODO:
		// initialize BankName and Course from the NewBank Form
	}

	
}
