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
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					if ((rs.getString("id")).equals(testID))
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
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					if ((rs.getString("id")).equals(studentID))
						flag = true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;

	}

	public static String checkValidCode(String code) {
		String sqlQuery = "select id from test where executionCode = " + code + "";
		String id = null;
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					id = rs.getString(1);

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(id);
		return id;
	}

	public static boolean submitTest(testCopy tc) {
		System.out.println(tc.toString() + "submit test in STUDENT DB CONTROLLER");
		String sqlQuery = "insert into testcopy "
				+ "(id,teacherUsername,year, month,day,studentAnswers,finalScore,"
				+ "actualTime,studentUsername,scoreApproved,status,"
				+ "studentAnswersManual,teacherUsernameExecute) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(tc.getTestID()));
				ps.setString(2, String.valueOf(tc.getTeacherUsername()));
				ps.setString(3, String.valueOf(tc.getYear()));
				ps.setString(4, String.valueOf(tc.getMonth()));
				ps.setString(5, String.valueOf(tc.getDay()));
				// ps.setString(8, String.valueOf(tc.getActualTime()));
				ps.setString(8, String.valueOf("0"));

				ps.setString(9, String.valueOf(tc.getStudentID()));
				Blob b = DBConnector.myConn.createBlob();
				System.out.println("Before if in THE SUBMIT TEST DBCONTROLLL");

				if (tc.getStudentAnswers() != null) {// online test //ragah///////////////
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = null;
					try {
						oos = new ObjectOutputStream(baos);
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						oos.writeObject(tc.getStudentAnswers());
					} catch (IOException e) {
						e.printStackTrace();
					}
					byte[] answersAsByte;
					answersAsByte = baos.toByteArray();
					b.setBytes(1, answersAsByte);
					ps.setBlob(6, b);
					ps.setString(7, String.valueOf(tc.getFinalScore()));
					// ps.setString(8, String.valueOf(tc.getActualTime()));
					// ps.setString(9, String.valueOf(tc.getStudentID()));
					ps.setString(10, String.valueOf("Yes"));
					ps.setString(11, String.valueOf("Approved / Online"));
					ps.setString(12, null);
				} // sof ragah
				else {
					ps.setString(6, null);
					ps.setString(7, null);
					ps.setString(10, null);
					ps.setString(11, null);
					// put ManualTest
					System.out.println("0");

					b.setBytes(1, tc.getArrByteManualTestUpload());
					System.out.println("1");

					ps.setBlob(12, b);
					System.out.println("2");

					ps.setString(13, tc.getTeacherUsernameExecute());
					System.out.println("3");

				}

				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// shahr shicpol kod

	public static ArrayList<String> getManualTestInfo(String code) {
		ArrayList<String> testDetails = null;
		String sqlQuery = "select id,duration,teacherUsername from test where executionCode =" + code + "";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {

					// id,duration,teacherUsername
					testDetails = new ArrayList<String>();
					// construct current read test
					testDetails.add(rs.getString(1));
					testDetails.add(rs.getString(2));
					testDetails.add(rs.getString(3));
					System.out.println("NExt1 ::: " + testDetails.toString());

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<String> toAdd = getExamDate(code); // olay shirshor lo tov
		// id,duration,teacherUsername,execCode,startHour,teacherUsernameExecute,Date;
		testDetails.addAll(toAdd);
		System.out.println("NExt2 ::: " + testDetails.toString());
		return testDetails;
	}

	// shichpol kod YOM AHARON LETAKEN
	/*
	 * public static void submitTestManual(testCopy tc) { String sqlQuery =
	 * "insert into testcopy (id ,teacherUsername,year, month,day,studentAnswers,finalScore,actualTime,studentUsername,scoreApproved,status,studentAnswersManual) values (?,?,?,?,?,?,?,?,?,?,?,?)"
	 * ; try { if (DBConnector.myConn != null) { PreparedStatement ps =
	 * DBConnector.myConn.prepareStatement(sqlQuery); ps.setString(1,
	 * String.valueOf(tc.getTestID())); ps.setString(2,
	 * String.valueOf(tc.getTeacherUsername())); ps.setString(3,
	 * String.valueOf(tc.getYear())); ps.setString(4,
	 * String.valueOf(tc.getMonth())); ps.setString(5, String.valueOf(tc.getDay()));
	 * 
	 * /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * ObjectOutputStream oos = null; try { oos = new ObjectOutputStream(baos); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } try { oos.writeObject(failed.getStudentAnswers()); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } byte[] answersAsByte; answersAsByte =
	 * baos.toByteArray(); Blob b = DBConnector.myConn.createBlob(); //b.setBytes(1,
	 * answersAsByte); ps.setString(6, null); ps.setString(7,
	 * String.valueOf(failed.getFinalScore())); ps.setString(8,
	 * String.valueOf(failed.getActualTime())); ps.setString(9,
	 * String.valueOf(failed.getStudentID())); ps.setString(10,
	 * String.valueOf("Yes")); ps.setString(11, String.valueOf("Failed / Online"));
	 * ps.setString(12, tc.getArrByteManualTestUpload()); ps.executeUpdate(); } }
	 * catch (SQLException e) { e.printStackTrace(); }
	 */

	public static void addStudentToOnGoing(ArrayList<String> list) {
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

	public static void removeStudentFromOnGoing(ArrayList<String> list) {
		String sqlQuery = "delete from ongoing where executionCode = '" + list.get(0) + "' AND username = '"
				+ list.get(1) + "';";

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

	public static ArrayList<String> getExamDate(String code) {
		ArrayList<String> list = new ArrayList<>();
		String sqlQuery = "select * from plannedtest where execCode = " + code + "";

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					list.add(0, String.valueOf(rs.getString(1)));
					list.add(1, String.valueOf(rs.getString(2)));
					list.add(2,rs.getString(3)); //teacherUserName execute
					list.add(3, String.valueOf(rs.getString(4)));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(list);
		return list;
	}

}
