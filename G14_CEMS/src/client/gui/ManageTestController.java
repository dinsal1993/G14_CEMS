package client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ManageTestController {

    @FXML
    private Text txtManageTest;

    @FXML
    private TextField txtTestID;

    @FXML
    private Text txtMustFillThisFieldToContinue;

    @FXML
    private Label lblTestID;

    @FXML
    private Button btnLockTest;

    @FXML
    private TextField txtExecutionCode;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblExecutionCode;

    @FXML
    private Button btnSetExecutionCode;

    @FXML
    private Label txtStudentsUseThisCodeToTakeASpecificTest;

    @FXML
    private Button btnRequestUpdateTestTime;

    @FXML
    private Label lblRequestExtraTime;

    @FXML
    private Label lblReasons;

    @FXML
    private CheckBox chkBoxRequestExtraTime;

    @FXML
    private TextField txtReasons;

}
