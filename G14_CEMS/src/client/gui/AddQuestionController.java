package client.gui;

import entity.Question;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class AddQuestionController {

    @FXML
    private Text txtHeader;

    @FXML
    private ComboBox<String> comboBank;

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
    
    @FXML
    private ListView<String> lstQuestionsInTest;
    
    private ObservableList<String> QuestionsInTest;

    @FXML
    void click_addQuestion(ActionEvent event) {
    	//TODO add question to ListView and List
    }

    @FXML
    void click_removeQuestion(ActionEvent event) {
    	//TODO remove from ListView and List
    }

    @FXML
    void click_viewQuestion(ActionEvent event) {
    	//TODO open new stage with CreateQuestionForm
    	//all buttons disabled except "back"
    	//this is just to view the question
    }

    @FXML
    void Click_Back(ActionEvent event) {

    }

	public void start(String bank) {
		
		
	}

}

