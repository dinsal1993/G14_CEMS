package client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.Test;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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
	private ArrayList<Question> testQuestions;
	private ArrayList<Integer> pointsPerQuestion;
	private boolean questionsAdded;
	


	@FXML
	void click_AddQuestion(ActionEvent event) {
		if (questionsAdded == false) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestionForm.fxml"));
			Parent root = null;
			try {
				root = loader.load();
				ScreenControllers.addQuestionControl = loader.getController();
				Scene scene = new Scene(root);
				UserController.currentStage.hide();
				UserController.extraStage.setScene(scene);
				UserController.extraStage.show();
				String username = ScreenControllers.loginFormController.getUsername();
				String subjectID = TeacherTestController.getSubjectID(cmbBankName.getValue());
				ScreenControllers.addQuestionControl.start(subjectID, username);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			UserController.extraStage.show();
			UserController.currentStage.hide();
		}

	}

	@FXML
	void click_Back(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml"));
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
		String subject = cmbBankName.getSelectionModel().getSelectedItem();
		String subjectID = TeacherTestController.getSubjectID(subject);

		String course = cmbCourseName.getSelectionModel().getSelectedItem();
		String courseID = TeacherTestController.getCourseID(subjectID, course);

		String teacherUsername = ScreenControllers.loginFormController.getUsername();
		// set test ID
		// get num of tests in specific subject and course
		int testCount = TeacherTestController.getTCount(subjectID, courseID, teacherUsername);
		testCount++;
		String testID = null;
		if (testCount < 10)
			testID = subjectID + courseID + "0" + testCount;
		else if (testCount <= 99)
			testID = subjectID + courseID + testCount;
		else {
			ClientUI.display("maximum number of tests in course reached");
			return;
		}

		int duration = Integer.parseInt(txtDuration.getText());
		String teacherName = "setName";
		
		String teacherNotes = txtTeacherNotes.getText();
		String studentNotes = txtStudentNotes.getText();
		Test t = new Test(testID, duration, testQuestions, pointsPerQuestion, teacherName, teacherUsername,
				teacherNotes, studentNotes);

		Message msg = new Message(MessageType.AddTest, t);
		ClientUI.accept(msg);

		initUI();

	}

	public void start() {
		initUI();
		bankListener();
	}

	private void bankListener() {

		cmbBankName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if (newValue != null || !newValue.equals("")) {
						cmbCourseName.getItems().clear();
						courseList = TeacherTestController.getCourseList(newValue);
						cmbCourseName.getItems().addAll(courseList);
					}
				} catch (NullPointerException e) {
					return;
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
		String username = ScreenControllers.loginFormController.getUsername();
		testBankList = TeacherTestController.getAllSubjects(username);
		cmbBankName.getItems().addAll(testBankList);

		testQuestions = new ArrayList<>();
		pointsPerQuestion = new ArrayList<>();
		questionsAdded = false;

	}

	public void setTestQuestions(ListView<String> questions) {
		ObservableList<String> arr = questions.getItems();
		for (String s : arr) {
			for (Question q : TeacherTestController.questionsBySubject) {
				if (q.getId().equals(s))
					testQuestions.add(q);
			}
		}
	}

	public void setPointsPerQuestion(ArrayList<Integer> points) {
		pointsPerQuestion = points;
	}

	public void setQuestionsAdded(boolean value) {
		questionsAdded = value;
	}

	
}
