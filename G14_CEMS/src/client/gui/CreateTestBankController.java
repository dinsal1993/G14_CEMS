package client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CreateTestBankController {

    @FXML
    private Text txtCreateTestsBank;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblBankName;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblExistingBanks;

    @FXML
    private TextField txtBankName;

    @FXML
    private TextField txtCourseName;

    @FXML
    private ListView<?> lstViewExistingBanks;

    @FXML
    private Button btnCreateBank;

    @FXML
    private Button btnCreateTest;

}
