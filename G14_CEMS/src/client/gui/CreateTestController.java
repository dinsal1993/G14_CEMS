package client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CreateTestController {

    @FXML
    private AnchorPane UpperAP_CreateTest;

    @FXML
    private Text CreateTestTitle;

    @FXML
    private AnchorPane LowerAP_CreateTest;

    @FXML
    private Label BNtxt_CreateTest;

    @FXML
    private Label CourseNametxt_CreateTest;

    @FXML
    private Label Durationtxt_CreateTest;

    @FXML
    private Label TNotestxt_CreateTest;

    @FXML
    private Label Snotestxt_CreateTest;

    @FXML
    private ComboBox<?> BNcmb_CrateTest;

    @FXML
    private ComboBox<?> CNcmb_CreateTest;

    @FXML
    private TextField DurationtxtFailed_CreateTest;

    @FXML
    private TextArea SnotestxtArea_CreateTest;

    @FXML
    private TextArea TnotesTextArea_CreateTest;

    @FXML
    private Button Backbtn_CreateTest;

    @FXML
    private Button AddQbtn_CreateTest;

    @FXML
    private Button CreateTbn_CreateTest;

}
