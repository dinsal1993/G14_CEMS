package client.gui;

import java.io.IOException;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Question;
import entity.Test;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class AddQuestionController {

	@FXML
	private Text txtHeader;

	@FXML
	private TextField txtQuestionID;

	@FXML
	private TextField txtScore;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnView;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnRemove;

	@FXML
	private TableView<Question> tblQuestionList;

	@FXML
	private TableColumn<Question, Integer> col_id;

	@FXML
	private TableColumn<Question, String> col_description;

	// shows the question ID's of the questions that were added to the test
	@FXML
	private ListView<String> lstQuestionsInTest;

	// list of all the questions from same subject
	private ObservableList<String> questionsBySubject;

	private ArrayList<Integer> pointsPerQuestion;

	@FXML
	void click_addQuestion(ActionEvent event) {
		int sum = 0;
		for (Integer p : pointsPerQuestion)
			sum += p;
		ObservableList<String> questionsInTest = lstQuestionsInTest.getItems();
		String isValid = TeacherTestController.checkValidQuestionID(txtQuestionID.getText(), txtScore.getText(), sum,
				questionsInTest);
		if (isValid.equals("valid")) {
			lstQuestionsInTest.getItems().add(txtQuestionID.getText());
			pointsPerQuestion.add(Integer.parseInt(txtScore.getText()));
		} else
			ClientUI.display(isValid);
	}

	@FXML
	void click_removeQuestion(ActionEvent event) {
		int index = -1;

		String qID = lstQuestionsInTest.getSelectionModel().getSelectedItem();
		ObservableList<String> questionsInTest = lstQuestionsInTest.getItems();
		for (int i = 0; i < questionsInTest.size(); i++) {
			if (questionsInTest.get(i).equals(qID))
				index = i;
		}

		lstQuestionsInTest.getItems().remove(index);
		pointsPerQuestion.remove(index);
	}

	@FXML
	void click_viewQuestion(ActionEvent event) {
		Question chosenQuestion = null;
		for (Question q : TeacherTestController.questionsBySubject) {
			if (q.getId().equals(txtQuestionID.getText()))
				chosenQuestion = q;
		}
		if (chosenQuestion == null)
			ClientUI.display("Error in question id");
		else {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionForm.fxml"));
				Parent root = loader.load();
				ScreenControllers.createQuestionControl = loader.getController();
				Scene scene = new Scene(root);
				UserController.extraStage2.setScene(scene);
				UserController.extraStage2.show();
				ScreenControllers.createQuestionControl.startFromAddQuestion(chosenQuestion);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void click_Back(ActionEvent event) {
		questionsBySubject = null;
		pointsPerQuestion = null;
		ScreenControllers.createTestControl.setQuestionsAdded(false);
		UserController.extraStage.hide();
		UserController.currentStage.show();
	}

	@FXML
	void click_confirm(ActionEvent event) {
		ScreenControllers.createTestControl.setTestQuestions(lstQuestionsInTest);
		ScreenControllers.createTestControl.setPointsPerQuestion(pointsPerQuestion);
		ScreenControllers.createTestControl.setQuestionsAdded(true);
		UserController.extraStage.hide();
		UserController.currentStage.show();
	}

	public void start(String subjectID, String username) {
		pointsPerQuestion = new ArrayList<>();
		col_id.setCellValueFactory(new PropertyValueFactory<Question, Integer>("id"));
		col_description.setCellValueFactory(new PropertyValueFactory<Question, String>("description"));
		fillTable(subjectID, username);

	}

	private void fillTable(String subjectID, String username) {
		tblQuestionList.getItems().clear();
		tblQuestionList.getItems().setAll(
				TeacherTestController.getQuestionsBySubject(
						subjectID,username));

	}

}
