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
import entity.Question;
import entity.Test;
import entity.TestBank;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeacherTestDBController {

	public static int getTestCount() {
		String sqlQuery = "select count(*) from test";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				rs.next();
				return Integer.parseInt(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static HashMap<String, TestBank> getAllTestBanks() {
		String sqlQuery = "select * from testbank";

		// save test Banks in HashMap for later use without accessing DB
		// every time - better performance.
		TestBank temp;
		ArrayList<Course> courses;

		// hashMap of testBanks for faster access
		HashMap<String, TestBank> testBankMap = new HashMap<String, TestBank>();
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {

					Blob coursesBlob = rs.getBlob(3);
					BufferedInputStream bis = new BufferedInputStream(coursesBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					courses = (ArrayList<Course>) ois.readObject();

					// construct current read testBank
					temp = new TestBank();
					temp.setId(Integer.parseInt(rs.getString(1)));
					temp.setName(rs.getString(2));
					temp.setCourses(courses);

					// Add testBank to hashMap
					testBankMap.put(temp.getName(), temp);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testBankMap;
	}

	public static void lockTest(Test t) {

		/*
		 * DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		 * LocalDateTime currentDate = LocalDateTime.now();
		 * //System.out.println(dtf.format(now)); DateTimeFormatter dtf1 =
		 * DateTimeFormatter.ofPattern("HH"); LocalDateTime currentHour =
		 * LocalDateTime.now(); DateTimeFormatter dtf2 =
		 * DateTimeFormatter.ofPattern("mm"); LocalDateTime currentMinutes =
		 * LocalDateTime.now(); String date = dtf.format(currentDate); String hour =
		 * dtf1.format(currentHour); String minutes = dtf2.format(currentMinutes);
		 * System.out.println(date); System.out.println(hour);
		 * System.out.println(minutes);
		 */
		String sqlQuery = "update pretest set isgoing = ? where id = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(0));
				ps.setString(2, String.valueOf(t.getId()));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> lockTestDin(String executionCode) {
		ArrayList<String> arr = new ArrayList<>();
		String sqlQuery = "select * from ongoing where execution code ="
				+ " \"" + executionCode + "\" and username like %";

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

	// has to be edited
	public static void requestExtraTime(testCopy tc) {

		String sqlQuery = "update testcopy set requestExtraTime = ? ,reasons = ? where id = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf("yes"));
				ps.setString(2, String.valueOf(tc.getReasons()));
				ps.setString(3, String.valueOf(tc.getTestID()));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Course> refreshCourseTable() {

		ArrayList<Course> list = new ArrayList<>();
		String sqlQuery = "select * from courses";

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					list.add(new Course(rs.getString("bankID"),
							rs.getString("courseID"), rs.getString("name")));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static void addCourse(Course c) {
		String sqlQuery = "insert into courses (bankID ,courseID, name) values (?,?,?)";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(c.getSubjectID()));
				ps.setString(2, String.valueOf(c.getCourseID()));
				ps.setString(3, String.valueOf(c.getName()));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void deleteCourse(Course c) {
		String sqlQuery = "delete from courses where bankID = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(c.getSubjectID()));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void insertTestBank(TestBank TB) throws IOException {

		ArrayList<Course> courses = new ArrayList<Course>();
		String sqlQuery = "insert into cems.testbank (id,name,courses) values (?,?,?)";

		PreparedStatement pst = null;
		try {

			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				// serialize object
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(courses);
				// store in byte array
				byte[] empAsByte;
				empAsByte = baos.toByteArray();
				// create blob object
				Blob b = DBConnector.myConn.createBlob();

				// fill blob object with byte array

				pst.setString(1, String.valueOf(TB.getId()));
				pst.setString(2, TB.getName());
				b.setBytes(1, empAsByte);
				pst.setBlob(3, b);

				pst.executeUpdate();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		String sqlQuery = "select * from question where id like \"" + 
		arr.get(0) + "%\" and teacherUsername = \"" + arr.get(1) + "\";";
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
		String sqlQuery = "select * from course where subjectID = \"" + 
				arr.get(0) + "\" and name = \"" + arr.get(1) + "\";";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				rs.next();
				return rs.getString(2);
				}
			 else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getTestCount(ArrayList<String> arr) {
		String sqlQuery = "select count(*) from test where id like"
				+ " \"" + arr.get(0) + arr.get(1) + "%\";";
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
				
				Blob pointsBlob = DBConnector.myConn.createBlob();
				ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
				ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
				oos.writeObject(t.getPointsPerQuestion());
				
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
}
