package client.gui;

import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import entity.Question;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class EditDeleteQuestionController {

	@FXML
	private ComboBox<String> cmbSubject;

	@FXML
	private ListView<String> lstQuestions;

	@FXML
	private TextArea txtDescription;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnEdit;

	private ObservableList<String> questions = FXCollections.observableArrayList();

	@FXML
	void click_back(ActionEvent event) {

	}

	@FXML
	void click_edit(ActionEvent event) {

	}

	public void start() {
		initUI();
		bankListener();
		setDescription();
		txtDescription.setEditable(false);
	}

	private void initUI() {
		String username = ScreenControllers.loginFormController.getUsername();
		cmbSubject.setItems(TeacherTestController.getAllSubjects(username));
		bankListener();
		setDescription();

	}

	private void setDescription() {
		lstQuestions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				for (Question q : TeacherTestController.questionsBySubject) {
					if (q.getId().equals(newValue)) {
						txtDescription.setText(q.getDescription());
						return;
					}
				}

			}
		});

	}

	private void bankListener() {

		cmbSubject.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("before change: " + lstQuestions.getItems());
				lstQuestions.getItems().clear();
				System.out.println("lst after clear: " + lstQuestions.getItems());
				System.out.println("questions: " + questions);
				try {
					if (newValue != null || !newValue.equals("")) {
						String username = ScreenControllers.loginFormController.getUsername();
						String subject = cmbSubject.getSelectionModel().getSelectedItem();
						String subjectID = TeacherTestController.getSubjectID(subject);
						
						TeacherTestController.getQuestionsBySubject(subjectID, username);
						for (Question q : TeacherTestController.questionsBySubject)
							lstQuestions.getItems().add(q.getId());
						System.out.println("questions after add: " + questions);
					}
				} catch (NullPointerException e) {
					return;
				}

			}
		});
	}

}
