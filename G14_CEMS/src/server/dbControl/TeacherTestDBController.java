package server.dbControl;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import client.controllers.ClientUI;
import entity.Course;
import entity.Question;
import entity.Subject;
import entity.Test;
import entity.TestBank;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TeacherTestDBController {


	public static ArrayList<String> lockTestDin(String executionCode) {
		ArrayList<String> arr = new ArrayList<>();
		String sqlQuery = "select * from ongoing where execution code =" + " \"" + executionCode
				+ "\" and username like %";

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next())
					arr.add(rs.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arr;
	}


	public static ArrayList<Course> getCoursesBySubject(String subjectID) {
		ArrayList<Course> arr = new ArrayList<>();
		String sqlQuery = "select * from course where subjectID like \"" + subjectID + "%\";";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next())
					arr.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	public static ArrayList<Question> getQuestionsBySubject(ArrayList<String> arr) {
		ArrayList<Question> answer = new ArrayList<>();
		String sqlQuery = "select * from question where id like \"" + arr.get(0) + "%\" and teacherUsername = \""
				+ arr.get(1) + "\";";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					String id = rs.getString(1);
					String description = rs.getString(2);
					ArrayList<String> answers = new ArrayList<>();
					answers.add(rs.getString(5));
					answers.add(rs.getString(6));
					answers.add(rs.getString(7));
					answers.add(rs.getString(8));
					int correctAnswer = Integer.parseInt(rs.getString(3));
					String teacherName = rs.getString(4);
					String teacherUsername = "add";
					answer.add(new Question(id, description, answers, correctAnswer, teacherName, teacherUsername));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answer;
	}

	public static String getCourseID(ArrayList<String> arr) {
		String sqlQuery = "select * from course where subjectID = \"" + arr.get(0) + "\" and name = \"" + arr.get(1)
				+ "\";";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				rs.next();
				return rs.getString(2);
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getTestCount(ArrayList<String> arr) {
		String sqlQuery = "select count(*) from test where id like" + " \"" + arr.get(0) + arr.get(1)
				+ "%\" and teacherUsername = \"" + arr.get(2) + "\";";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				rs.next();
				System.out.println("count = " + rs.getInt(1));
				int answer = Integer.parseInt(rs.getString(1));
				st.close();
				return answer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String addTest(Test t) {
		String sqlQuery = "insert into test values (?,?,?,?,?,?,?,?,?)";

		PreparedStatement pst = null;
		try {

			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);

				// serialize object
				Blob questionsBlob = DBConnector.myConn.createBlob();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(t.getQuestions());
				oos.close();

				Blob pointsBlob = DBConnector.myConn.createBlob();
				ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
				ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
				oos2.writeObject(t.getPointsPerQuestion());
				oos2.close();
				

				// store in byte array
				byte[] questionsAsByte = baos.toByteArray();
				byte[] pointsAsByte = baos2.toByteArray();

				// fill blob object with byte array
				questionsBlob.setBytes(1, questionsAsByte);
				pointsBlob.setBytes(1, pointsAsByte);

				pst.setBlob(3, questionsBlob);
				pst.setBlob(4, pointsBlob);
				
				pst.setString(1, t.getId());
				pst.setString(2, String.valueOf(t.getDuration()));
				pst.setString(5, t.getTeacherName());
				pst.setString(6, t.getTeacherUsername());
				pst.setString(7, null);
				pst.setString(8, t.getTeacherNotes());
				pst.setString(9, t.getStudentNotes());
				pst.executeUpdate();
				return "Success";
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error";

	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Test> getAllTests(String username) {
		
		String sqlQuery = "select * from test where teacherUsername = \"" + username + "\";";
		ArrayList<Test> arr = new ArrayList<Test>();
		ArrayList<Question> questions;
		ArrayList<Integer> points;
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					questions = new ArrayList<>();
					points = new ArrayList<>();
					String id = rs.getString(1);
					int duration = Integer.parseInt(rs.getString(2));
					String teacherName = rs.getString(5);
					String teacherUsername = rs.getString(6);
					String teacherNotes = rs.getString(8);
					String studentNotes = rs.getString(9);
					
					Blob questionsBlob = rs.getBlob(3);
					BufferedInputStream bis = new BufferedInputStream(questionsBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					questions = (ArrayList<Question>) ois.readObject();
					System.out.println(questions);
					ois.close();
					
					Blob qPointsBlob = rs.getBlob(4);
					BufferedInputStream bis1 = new BufferedInputStream(qPointsBlob.getBinaryStream());
					ObjectInputStream ois1 = new ObjectInputStream(bis1);
					points = (ArrayList<Integer>) ois1.readObject();
										
					Test t = new Test(id, duration, questions, points, teacherName, teacherUsername,
							teacherNotes, studentNotes);
					arr.add(t);

				}
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	
	public static int getNextTID(ArrayList<String> arr) {
		String sqlQuery = "select * from test where id like \"" + arr.get(0) + arr.get(1) + "%\" and"
				+ " teacherUsername = \"" + arr.get(2) + "\";";
		int place = 1;
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while(rs.next()) {
				int rowIDNum = Integer.parseInt(rs.getString(1).substring(4));
				if(place < rowIDNum)
					return place;
				else
					while(place <= rowIDNum)
						place++;
				}
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return place;
	}

	public static ArrayList<Test> getAllTestsBySubject(String subjectID) {
		String sqlQuery = "select * from test where id like \"" + subjectID + "%\";";
		ArrayList<Test> arr = new ArrayList<Test>();
		ArrayList<Question> questions;
		ArrayList<Integer> points;
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					questions = new ArrayList<>();
					points = new ArrayList<>();
					String id = rs.getString(1);
					int duration = Integer.parseInt(rs.getString(2));
					String teacherName = rs.getString(5);
					String teacherUsername = rs.getString(6);
					String teacherNotes = rs.getString(8);
					String studentNotes = rs.getString(9);
					
					Blob questionsBlob = rs.getBlob(3);
					BufferedInputStream bis = new BufferedInputStream(questionsBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					questions = (ArrayList<Question>) ois.readObject();
					ois.close();
					
					Blob qPointsBlob = rs.getBlob(4);
					BufferedInputStream bis1 = new BufferedInputStream(qPointsBlob.getBinaryStream());
					ObjectInputStream ois1 = new ObjectInputStream(bis1);
					points = (ArrayList<Integer>) ois1.readObject();
										
					Test t = new Test(id, duration, questions, points, teacherName, teacherUsername,
							teacherNotes, studentNotes);
					arr.add(t);

				}
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Tests: " + arr);
		return arr;
	}

	public static boolean deleteTest(Test t) {
		System.out.println("in DB: Test = " + t);
		String sqlQuery = "delete from test where id = ? and teacherUsername = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, t.getId());
				ps.setString(2, t.getTeacherUsername());
				ps.executeUpdate();
				return true;
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateTest(Test t) {
		String sqlQuery = "update test set duration = ?,"
				+ " questions = ?, questionPoint = ?,teacherNotes = ?," + " studentNotes = ? "
						+ "where id = ? and teacherUsername = ?;";

		PreparedStatement pst = null;
		try {
			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				pst.setString(1, String.valueOf(t.getDuration()));
				pst.setString(4, t.getTeacherNotes());
				pst.setString(5, t.getStudentNotes());
				pst.setString(6, t.getId());
				pst.setString(7, t.getTeacherUsername());
				
				// serialize object
				Blob questionsBlob = DBConnector.myConn.createBlob();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(t.getQuestions());
				oos.close();

				Blob pointsBlob = DBConnector.myConn.createBlob();
				ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
				ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
				oos2.writeObject(t.getPointsPerQuestion());
				oos2.close();
				

				// store in byte array
				byte[] questionsAsByte = baos.toByteArray();
				byte[] pointsAsByte = baos2.toByteArray();

				// fill blob object with byte array
				questionsBlob.setBytes(1, questionsAsByte);
				pointsBlob.setBytes(1, pointsAsByte);
				
				//set blob
				pst.setBlob(2, questionsBlob);
				pst.setBlob(3, pointsBlob);
				

				pst.executeUpdate();
				pst.close();
				return true;
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			
		}
		return false;
	}

	/**
	 * check if the Id of the test are matching to the username of the teacher
	 * 
	 * @param IDandUsername
	 * @return true when there is a match
	 */

	public static boolean checkValidIdAndUsernameTest(ArrayList<String> IDandUsername) {

		boolean ValidIdAndUsername = false;
		ResultSet rs2;
		String selectSQL = "SELECT id,teacherUsername FROM test";
		Statement pstmt;
		try {
			pstmt = DBConnector.myConn.createStatement();
			rs2 = pstmt.executeQuery(selectSQL);
			while (rs2.next()) {
				if (rs2.getString(1).equals(IDandUsername.get(0))) {
					if (rs2.getString(2).equals(IDandUsername.get(1))) {
						ValidIdAndUsername = true;
					}
				}
			}
			rs2.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ValidIdAndUsername;
	}

	/**
	 * insert all the info that she get in to the data base
	 * 
	 * @param messageD
	 * @return true if succsseed
	 * 
	 */
//////////////// lo asiti adain check valid DATE!! me ayom ve alaaa.. she lo yochlo lasim yom lfni.. o be oto yom shaa lfni ahshav..
	public static String insertPlanTestToDB(ArrayList<String> planTest) {
		System.out.println(planTest.toString());
		String validExecCode = checkValidExecCode(planTest.get(0));

		if (validExecCode.equals("Execution code must be 4 fields, digits and letters")) {
		
			return "Execution code must be 4 fields, digits and letters";
		}

		if (validExecCode.equals("Execuion Code already exist." + " please choose another one")) {
			return "Execuion Code already exist. please choose another one";
		}

		if (!checkValidStartTime(planTest.get(1))) {
			return "Start time must be in format hh:mm:ss or hh:mm";
		}

		if (!checkValidDuration(planTest.get(2))) {
			return "Duration must be a number between 0-480";
		}

		// checkValidDate(planTest.get(0)); // eich osim me ayom va ala?

		String insertPlannedTest = "INSERT INTO plannedtest " + "(execCode, startHour, duration, date)"
				+ " VALUES (?, ?, ?, ?)";
		PreparedStatement ps;

		try {
			ps = DBConnector.myConn.prepareStatement(insertPlannedTest);

			ps.setString(1, planTest.get(0));
			ps.setString(2, planTest.get(1));
			ps.setString(3, planTest.get(2));
			System.out.println(planTest.get(3));
			System.out.println(planTest.toString());

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			java.util.Date u = sdf.parse(planTest.get(3));
			long ms = u.getTime();
			java.sql.Date testDate = new java.sql.Date(ms);
			ps.setDate(4, testDate);
			System.out.println(planTest.toString());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "ok";
	}

	/**
	 * check the validation of the duration must be a string between 0-480 [8 hours]
	 * 
	 * @param duration
	 * @return true in case the duration is valid
	 */
	private static boolean checkValidDuration(String duration) {
		int durationInteger = Integer.parseInt(duration);

		System.out.println(durationInteger);
		if (!duration.matches("[0-9]+")) {
			return false;
		}
		System.out.println(durationInteger);

		if (durationInteger >= 480 || durationInteger <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * check the validation of the execution code
	 * 
	 * @param startTime start time of the test
	 * @return true in case the string start time in time format
	 */
	
	@SuppressWarnings("finally")
	private static boolean checkValidStartTime(String startTime) {
		boolean flag = false;
		try {		
			SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
			sdf.setLenient(false);
			java.util.Date u = sdf.parse(startTime);
			flag=true;
			}
			catch (ParseException e) {
				flag=false;
			e.printStackTrace();
		} 
		finally {return flag;}
	}

	/**
	 * check the validation of the execution code
	 * 
	 * @param execCode that the teacher entered
	 * @return true in case its valid
	 */
	private static String checkValidExecCode(String execCode) { // leosif bdika she en od exec
		ResultSet rs;
		String validExecCode = "ok";

		if (execCode.length() != 4 || (!isLetterOrDigit(execCode))) {
			validExecCode = "Execution code must be 4 fields, digits and letters";
		}
		String sqlQuery = "select execCode from plannedtest where execCode = ?;";
		try {
			PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
			ps.setString(1, execCode);
			rs = ps.executeQuery();
			rs.beforeFirst();
			if (rs.next()) // means that there is execCode like that already
				validExecCode = "Execuion Code already exist. please choose another one";

			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return validExecCode;
	}

	private static boolean isLetterOrDigit(String execCode) {
		for (int i = 0; i < execCode.length(); i++) {
			if (!Character.isLetterOrDigit(execCode.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
