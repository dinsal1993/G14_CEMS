package client.gui;

import java.io.IOException;
import java.util.Observable;

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

	}

	@FXML
	void click_Back(ActionEvent event)
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml"));
		Parent root;
		try {
			ScreenControllers.teacherMenuController = loader.getController();
			root = loader.load();
			Scene scene = new Scene(root);
			Stage teacherMenu = new Stage();
			teacherMenu.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = teacherMenu;
			teacherMenu.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void click_CreateTest(ActionEvent event) {

	}

	public void start() {
		initUI();

		// when bank is chosen, update course list accordingly
		cmbBankName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (cmbBankName.getSelectionModel().getSelectedItem() != null) {
					cmbCourseName.getItems().clear();
					courseList = TeacherTestController.getCourseList(newValue.toString());
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
		cmbBankName.getItems().addAll(testBankList);

	}

	public void startFromNewBank(String newBankName, String firstCourse) {
		// TODO:
		// initialize BankName and Course from the NewBank Form
	}
	
	public String generateCode()
	{
		// chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(4);
  
        for (int i = 0; i < 4; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
  
        return sb.toString();
	}
	
	@FXML
	public void clickGenerate(ActionEvent event)
	{
		txtExeCode.setText(generateCode());
	}
}
