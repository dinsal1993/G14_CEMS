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


import client.gui.LoginFormController;

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


	public static String checkValidCode(String code) {
		String sqlQuery = "select id from test where executionCode = "+code+"";
		String id= null;

		try {
			if(DBConnector.myConn != null)
			{
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				
				while(rs.next())
				{
					id = rs.getString(1);
						
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(id);
		return id;
	}
	
	public static void submitTest(testCopy tc)
	{
	
		String sqlQuery = "insert into testcopy (id ,year, month,day,studentAnswers,finalScore,actualTime,studentUsername,scoreApproved,status,studentAnswersManual) values (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(tc.getTestID()));
				ps.setString(2, String.valueOf(tc.getYear()));
				ps.setString(3, String.valueOf(tc.getMonth()));
				ps.setString(4, String.valueOf(tc.getDay()));
				
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = null;
				try {
					oos = new ObjectOutputStream(baos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					oos.writeObject(tc.getStudentAnswers());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] answersAsByte;
				answersAsByte = baos.toByteArray();
				Blob b = DBConnector.myConn.createBlob();
				b.setBytes(1, answersAsByte);		
				ps.setBlob(5, b);
				ps.setString(6, String.valueOf(tc.getFinalScore()));
				ps.setString(7, String.valueOf(tc.getActualTime()));
				ps.setString(8, String.valueOf(tc.getStudentID()));
				ps.setString(9, String.valueOf("Yes"));
				ps.setString(10, String.valueOf("Approved / Online"));
				ps.setString(11, null);
				
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void addStudentToOnGoing(ArrayList<String> list) 
	{
		String sqlQuery = "insert into ongoing (executionCode,username) values (?,?)";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(list.get(0)));
				ps.setString(2, String.valueOf(list.get(1)));
		
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void removeStudentFromOnGoing(ArrayList<String> list) 
	{
		String sqlQuery = "delete from ongoing where executionCode = '" + list.get(0) + "' AND username = '"+ list.get(1) +"';";
		
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;	
	}

	public static ArrayList<String> getExamDate(String code)
	{
		ArrayList<String> list = new ArrayList<>();
		String sqlQuery = "select * from plannedtest where execCode = "+code+"";
		
		try {
			if(DBConnector.myConn != null)
			{
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while(rs.next())
				{
					list.add(0,String.valueOf(rs.getString(1)));
					list.add(1,String.valueOf(rs.getString(2)));
					list.add(2,String.valueOf(rs.getString(3)));
					list.add(3,String.valueOf(rs.getString(4)));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(list);
		return list;
	}

}
