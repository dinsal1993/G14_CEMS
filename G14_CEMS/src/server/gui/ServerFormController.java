package server.gui;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import server.controllers.ServerController;
import server.controllers.ServerUI;

public class ServerFormController  {


    @FXML
    private Label lblIpAddress;

    @FXML
    private Label lblHostName;

    @FXML
    private Label lblStatus;
    
    @FXML
    private Button btnUpdate;
    
    @FXML
    private Button btnExit;
    
    
    @FXML
    void ClickUpdate(ActionEvent event) {
    	lblIpAddress.setText(ServerUI.getClientIP());
    	lblHostName.setText(ServerUI.getHostName());
    	lblStatus.setText(ServerUI.getStatus());
    	
    }
    
    @FXML
    void ClickExit(ActionEvent event) {
    	ServerUI.exit();
    }
    
    public void start(Stage primaryStage) throws Exception {	
		Parent root = (Parent)FXMLLoader.load(getClass().getResource("ServerForm.fxml"));
		
    	//FXMLLoader loader = new FXMLLoader();
    	//if(loader.getClass().getResource("/serverForm.fxml") != null)
    	//	System.out.println("get class success");
    	//else
    	//	System.out.println("get class failed");
    	//loader.setController(ServerUI.serverControl);
    	//System.out.println("setController success");
    	
    	
    	//Parent root = loader.load();
    	//System.out.println("load success");
    	//FXMLLoader loader = new FXMLLoader();
		//loader.setLocation(getClass().getResource("serverForm.fxml"));
		//Parent root = loader.load();
		
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerForm.css").toExternalForm());
		primaryStage.setTitle("Server Status");
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		
	}


	}


