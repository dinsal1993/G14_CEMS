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
import entity.Message;
import entity.MessageType;
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

	private boolean fromEdit;

	private Question questionForEdit;

	public void start() {
		fromAddQuestion = false;
		fromEdit = false;
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
		String subject = comboBank.getSelectionModel().getSelectedItem();
		String qID = TeacherTestController.getNextQuestionID(teacherUsername, subject);
		
		if(qID.equals("error")) {
			ClientUI.display("maximum number of questions reached");
			return;
		}
		String correctAnswer = comboCorrectAnswer.getSelectionModel().getSelectedItem();
		Question q = new Question(qID, txtDescription.getText(), answers,
				-1, txtTeacherName.getText(), teacherUsername);
		String valid = TeacherTestController.isValidFieldsCreateQuestion(q, subject, correctAnswer);
		if(valid.equals("valid")) {
			q.setCorrectAnswer(Integer.parseInt(correctAnswer));
			TeacherTestController.addQuestion(q);
		}else
			ClientUI.display(valid);
	}

	@FXML
	void Click_Back(ActionEvent event) {
		if (fromAddQuestion) {
			UserController.extraStage2.close();
			UserController.extraStage.show();
		} else if (fromEdit) {

			fromEdit = false;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDeleteQuestionForm.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ScreenControllers.editDeleteQuestionControl = loader.getController();
			Scene scene = new Scene(root);
			UserController.currentStage.setScene(scene);
			ScreenControllers.editDeleteQuestionControl.start();
		} else {
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
	}

	@FXML
	void click_edit(ActionEvent event) {
		ArrayList<String> answers = new ArrayList<String>();
		answers.add(txtAnswerA.getText());
		answers.add(txtAnswerB.getText());
		answers.add(txtAnswerC.getText());
		answers.add(txtAnswerD.getText());
		String correctAnswer = comboCorrectAnswer.getSelectionModel().getSelectedItem();
		String subject = comboBank.getSelectionModel().getSelectedItem();
		
		questionForEdit.setDescription(txtDescription.getText());
		questionForEdit.setAnswers(answers);

		String valid = TeacherTestController.isValidFieldsCreateQuestion(questionForEdit, subject, correctAnswer);
		if(valid.equals("valid")) {
			questionForEdit.setCorrectAnswer(Integer.parseInt(correctAnswer));
			Message msg = new Message(MessageType.UpdateQuestion, questionForEdit);
			//Send message !)
		}
		
		//Question q = new Question(id, description, answers, correctAnswer, teacherName, teacherUsername);
	}

	@FXML
	void click_delete(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		start();

	}

	private String getsubjectFromID(Question q) {
		String subject = null;
		String idChosen = q.getId().substring(0, 2);

		String username = ScreenControllers.loginFormController.getUsername();
		TeacherTestController.getAllSubjects(username);
		for (Subject s : TeacherTestController.subjects) {
			if (s.getId().equals(idChosen))
				subject = s.getName();
		}
		return subject;
	}

	public void startFromAddQuestion(Question chosenQuestion) {
		String subject = getsubjectFromID(chosenQuestion);

		comboBank.getItems().clear();
		comboBank.getItems().add(subject);
		comboBank.getSelectionModel().select(0);
		disableUI(chosenQuestion);
		fromAddQuestion = true;
	}

	public void startEdit() {
		fromEdit = true;

		String subject = getsubjectFromID(questionForEdit);

		comboBank.getItems().clear();
		comboBank.getItems().add(subject);
		comboBank.getSelectionModel().select(0);
		disableUI(questionForEdit);

		btnDelete.setVisible(true);
		btnDelete.setDisable(false);
		btnEdit.setVisible(true);
		btnEdit.setDisable(false);
	}

	private void disableUI(Question q) {
		comboBank.setDisable(true);
		txtDescription.setText(q.getDescription());
		txtDescription.setDisable(true);
		txtAnswerA.setText(q.getAnswers().get(0));
		txtAnswerA.setDisable(true);
		txtAnswerB.setText(q.getAnswers().get(1));
		txtAnswerB.setDisable(true);
		txtAnswerC.setText(q.getAnswers().get(2));
		txtAnswerC.setDisable(true);
		txtAnswerD.setText(q.getAnswers().get(3));
		txtAnswerD.setDisable(true);
		txtTeacherName.setText(q.getTeacherName());
		txtTeacherName.setDisable(true);
		comboCorrectAnswer.getItems().clear();
		comboCorrectAnswer.getItems().add(String.valueOf(q.getCorrectAnswer()));
		comboCorrectAnswer.setDisable(true);
		comboCorrectAnswer.getSelectionModel().select(0);
		btnCreateQuestion.setDisable(true);

	}

	public void setQuestionForEdit(Question q) {
		questionForEdit = q;
	}

	public void setFromEdit(boolean value) {
		fromEdit = value;

	}
}
