package client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateQuestionController {

    @FXML
    private ComboBox<?> comboBank;

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
    private ComboBox<?> comboCorrectAnswer;

    @FXML
    private Button btnCreateQuestion;

    @FXML
    void Click_Back(ActionEvent event) {

    }

    @FXML
    void Click_CreateQuestion(ActionEvent event) {

    }

}
