package client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class AddQuestionController {

    @FXML
    private Text txtHeader;

    @FXML
    private ComboBox<?> comboBank;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnAddToTest;

    @FXML
    private TableView<?> tblQuestionList;

    @FXML
    private TableColumn<?, ?> col_id;

    @FXML
    private TableColumn<?, ?> col_description;

    @FXML
    private TableColumn<?, ?> col_score;

    @FXML
    private TableColumn<?, ?> col_view;

    @FXML
    private TableColumn<?, ?> col_choose;

    @FXML
    void Click_AddToTest(ActionEvent event) {

    }

    @FXML
    void Click_Back(ActionEvent event) {

    }

}
