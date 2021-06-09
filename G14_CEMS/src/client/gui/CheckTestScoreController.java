package client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CheckTestScoreController {

    @FXML
    private Text txtCheckTestScore;

    @FXML
    private TextField txtTestID;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtSemester;

    @FXML
    private Label lblTestID;

    @FXML
    private Label lblYear;

    @FXML
    private Label lblSemester;

    @FXML
    private Label lblStudentID;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnShowTestList;

    @FXML
    private TextField txtStudentID;

    @FXML
    private Button btnApproveTestScore;

    @FXML
    private Button btnUpdateScore;

    @FXML
    private Button btnSeeTest;

    @FXML
    private Label lblTestList;

    @FXML
    private TableView<?> tblTestList;

    @FXML
    private TableColumn<?, ?> clmStudentID;

    @FXML
    private TableColumn<?, ?> clmScore;

}
