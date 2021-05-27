package server.dbControl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import client.controllers.ClientUI;
import entity.Course;
import entity.Test;
import entity.TestBank;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


public class StudentDBController {
	
public static boolean checkTest(String testID) {
		
	String sqlQuery = "select id from test";
	boolean flag = false;
	try {
		if(DBConnector.myConn != null)
		{
			Statement st = DBConnector.myConn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			while(rs.next())
			{
				if((rs.getString("id")).equals(testID))
					flag = true;
			}
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return flag;
	
	}

	public static boolean checkStudentID(String studentID) {
	
	String sqlQuery = "select id from user";
	boolean flag = false;
	try {
		if(DBConnector.myConn != null)
		{
			Statement st = DBConnector.myConn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			while(rs.next())
			{
				if((rs.getString("id")).equals(studentID))
					flag = true;
			}
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return flag;
	
	}

	public static boolean checkValidCode(String code) {
		String sqlQuery = "select executioncode from pretest";
		boolean flag = false;
		try {
			if(DBConnector.myConn != null)
			{
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while(rs.next())
				{
					if((rs.getString("executionCode")).equals(code))
						flag = true;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}


}
