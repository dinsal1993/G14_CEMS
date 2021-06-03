package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.controllers.UserController;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Question;
import entity.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import javafx.scene.layout.AnchorPane;

import server.dbControl.QuestionDBController;
////////////////change

public class CreateQuestionController implements Initializable {

	@FXML
	private ComboBox<String> comboBank;

	@FXML
	private TextArea txtDescription;

	@FXML
	private TextField txtTeacherName;

	@FXML
	private Button btnBack;

	@FXML
	private TextArea txtAnswerA;

	@FXML
	private TextArea txtAnswerB;

	@FXML
	private TextArea txtAnswerC;

	@FXML
	private TextArea txtAnswerD;

	@FXML
	private ComboBox<String> comboCorrectAnswer;

	@FXML
	private Button btnCreateQuestion;
	
    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

	private boolean fromAddQuestion;

	public void start() {
		fromAddQuestion = false;
		txtAnswerA.setText("");
		txtAnswerB.setText("");
		txtAnswerC.setText("");
		txtAnswerD.setText("");
		txtDescription.setText("");
		txtTeacherName.setText(LoginFormController.username);// take the teacher object from login and get teacher name
																// by getter
		txtTeacherName.setEditable(false);
		comboBank.setEditable(false);
		comboBank.setDisable(false);
		comboBank.getItems().clear();
		String username = ScreenControllers.loginFormController.getUsername();
		comboBank.setItems(TeacherTestController.getAllSubjects(username));
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");

		comboCorrectAnswer.getItems().clear();
		comboCorrectAnswer.setItems(FXCollections.observableArrayList(list));
		comboCorrectAnswer.setEditable(false);

		txtDescription.setDisable(false);
		txtAnswerA.setDisable(false);
		txtAnswerB.setDisable(false);
		txtAnswerC.setDisable(false);
		txtAnswerD.setDisable(false);
		txtTeacherName.setDisable(false);
		comboCorrectAnswer.setDisable(false);
		btnCreateQuestion.setDisable(false);
		fromAddQuestion = false;
		btnDelete.setVisible(false);
		btnDelete.setDisable(true);
		btnEdit.setVisible(false);
		btnEdit.setDisable(true);

	}

	@FXML
	void Click_CreateQuestion(ActionEvent event) {

		ArrayList<String> answers = new ArrayList<String>();
		answers.add(txtAnswerA.getText());
		answers.add(txtAnswerB.getText());
		answers.add(txtAnswerC.getText());
		answers.add(txtAnswerD.getText());
		String teacherUsername = ScreenControllers.loginFormController.getUsername();

		if (comboBank.getItems().isEmpty())
			ClientUI.display("First Create question bank");
		else if (txtAnswerA.getText().trim().isEmpty() || txtAnswerB.getText().trim().isEmpty()
				|| txtAnswerC.getText().trim().isEmpty() || txtAnswerD.getText().trim().isEmpty())
			ClientUI.display("Please first insert the answers");
		else {
			if (txtDescription.getText().trim().isEmpty())
				ClientUI.display("Description field is empty!");
			else {
				String subject = comboBank.getSelectionModel().getSelectedItem();
				String subjectID = TeacherTestController.getSubjectID(subject);
				int questionCount = TeacherTestController.getQCount(subjectID, teacherUsername);
				String qID = null;
				questionCount++;
				if (questionCount < 10)
					qID = subjectID + "00" + questionCount;
				else if (questionCount <= 99)
					qID = subjectID + "0" + questionCount;
				else if (questionCount <=999)
					qID = subjectID + questionCount;
				else {
					ClientUI.display("maximum number of questions in bank reached");
					return;
				}
				Question q = new Question(qID, txtDescription.getText(), answers,
						Integer.parseInt(comboCorrectAnswer.getValue()), txtTeacherName.getText(),teacherUsername);
				TeacherTestController.addQuestion(q);

			}
		}

	}

	@FXML
	void Click_Back(ActionEvent event) {
		if (fromAddQuestion == false) {
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
		}else {
			UserController.extraStage2.close();
			UserController.extraStage.hide();
			UserController.extraStage.show();
			
		}
	}
	
	@FXML
	void click_edit(ActionEvent event) {
		
	}
	
	@FXML
	void click_delete(ActionEvent event) {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		start();

	}

	public void startFromAddQuestion(Question chosenQuestion) {
		String subject = null;
		String idChosen = chosenQuestion.getId().substring(0, 2);
		System.out.println("idChosen: " + idChosen);
		String username = ScreenControllers.loginFormController.getUsername();
		TeacherTestController.getAllSubjects(username);
		for (Subject s : TeacherTestController.subjects) {
			if (s.getId().equals(idChosen))
				subject = s.getName();
		}
		System.out.println("subject: " + subject);
		comboBank.getItems().clear();
		comboBank.getItems().add(subject);
		comboBank.getSelectionModel().select(0);
		comboBank.setDisable(true);
		txtDescription.setText(chosenQuestion.getDescription());
		txtDescription.setDisable(true);
		txtAnswerA.setText(chosenQuestion.getAnswers().get(0));
		txtAnswerA.setDisable(true);
		txtAnswerB.setText(chosenQuestion.getAnswers().get(1));
		txtAnswerB.setDisable(true);
		txtAnswerC.setText(chosenQuestion.getAnswers().get(2));
		txtAnswerC.setDisable(true);
		txtAnswerD.setText(chosenQuestion.getAnswers().get(3));
		txtAnswerD.setDisable(true);
		txtTeacherName.setText(chosenQuestion.getTeacherName());
		txtTeacherName.setDisable(true);
		comboCorrectAnswer.getItems().clear();
		comboCorrectAnswer.getItems().add(String.valueOf(chosenQuestion.getCorrectAnswer()));
		comboCorrectAnswer.setDisable(true);
		comboCorrectAnswer.getSelectionModel().select(0);
		btnCreateQuestion.setDisable(true);
		fromAddQuestion = true;
	}
}
