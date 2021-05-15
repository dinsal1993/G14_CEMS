package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.controllers.ClientUI;
import client.controllers.TestController;
import entity.Message;
import entity.MessageType;
import entity.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SeeTestsFormController {

	@FXML
	private TableView<Test> tblTests;

	@FXML
	private TableColumn<Test, Integer> col_id;

	@FXML
	private TableColumn<Test, String> col_subject;

	@FXML
	private TableColumn<Test, String> col_course;

	@FXML
	private TableColumn<Test, Integer> col_duration;

	@FXML
	private TableColumn<Test, Integer> col_pointsPerQuestion;

	@FXML
	private TextField txtTestID;

	@FXML
	private TextField txtNewDuration;

	@FXML
	private Button btnUpdateDuration;

	@FXML
	private Button btnBack;

	//public static ObservableList<Test> list;

	@FXML
	void ClickBack(ActionEvent event) {
		ClientUI.secondStage.close();
		ClientUI.firstStage.show();
	}

	@FXML
	void ClickUpdateDuration(ActionEvent event) {
		if (TestController.updateTestValidFields(txtTestID.getText(), txtNewDuration.getText())) {
			Test test = new Test();
			//list = (tblTests.getItems());

			// get chosen test details
			for (Test t : TestController.list) {
				if (t.getId() == Integer.parseInt(txtTestID.getText())) {
					test.setId(t.getId());
					//test.setSubject(t.getSubject());
					//test.setCourse(t.getCourse());
					test.setDuration(Integer.parseInt(txtNewDuration.getText()));
					test.setPointsPerQuestion(t.getPointsPerQuestion());
				}

			}
			TestController.updateTestDuration(test);

			fillTable();
		}
		else
			ClientUI.display("Error in test ID or duration");
	}

	public void start() throws IOException {
		// init UI

		//list = FXCollections.observableArrayList();

		// System.out.println("is table null ? " + tblTests.getId());
		txtNewDuration.setText("");
		txtTestID.setText("");
		initColumns();
		fillTable();

	}

	private void initColumns() {
		// System.out.println("is null col?" + col_id.getId() + " " + col_id);
		col_id.setCellValueFactory(new PropertyValueFactory<Test, Integer>("id"));
		col_subject.setCellValueFactory(new PropertyValueFactory<Test, String>("subject"));
		col_course.setCellValueFactory(new PropertyValueFactory<Test, String>("course"));
		col_duration.setCellValueFactory(new PropertyValueFactory<Test, Integer>("duration"));
		col_pointsPerQuestion.setCellValueFactory(new PropertyValueFactory<Test, Integer>("pointsPerQuestion"));

	}

	private void fillTable() {

		tblTests.getItems().clear();
		
		//updates the observable list of tests in TestController
		TestController.getAllTest();

		tblTests.setItems(TestController.list);

	}
}