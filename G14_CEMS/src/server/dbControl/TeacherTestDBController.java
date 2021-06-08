package server.dbControl;

import java.io.*;
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
import entity.Subject;
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

	/**
	 * get the test
	 * 
	 * @param idTest   id of the test
	 * @param TestType Manual or computerized
	 * @throws ClassNotFoundException
	 */
	private static void readBlob(String idTest, String TestType) throws ClassNotFoundException {
		DBConnector db = new DBConnector();
		// update sql
		String selectSQL = "SELECT questions FROM test WHERE id=?";
		ResultSet rs = null;
		System.out.println("lifni try");
		try {
			PreparedStatement pstmt = DBConnector.myConn.prepareStatement(selectSQL);
			// set parameter;
			pstmt.setString(1, idTest);
			rs = pstmt.executeQuery();

			rs.first();
			BufferedInputStream bis = new BufferedInputStream(rs.getBlob(1).getBinaryStream());
			byte[] blobByte = new byte[1024];
			File manualTest = new File("manualTest2.docx");

			FileOutputStream fos = new FileOutputStream(manualTest);

			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int a;
			while ((a = bis.read(blobByte)) != -1) {
				bos.write(blobByte, 0, a);
				bos.flush();
				fos.flush();
			}
		} catch (SQLException |

				IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * insert the TEST_YADANI to db with ID 0000
	 */
	public static void writeBlob() {

		DBConnector db = new DBConnector();
		String updateSQL = "UPDATE test " + "SET questions = ? " + "WHERE id=?";
		try {
			PreparedStatement ps = DBConnector.myConn.prepareStatement(updateSQL);

			File newFile = new File("exam.docx");
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);

			bis.read(mybytearray, 0, mybytearray.length);
			Blob b = DBConnector.myConn.createBlob();

			// fill blob objct with byte array
			b.setBytes(1, mybytearray);

			// attach blob object to sql query
			ps.setBlob(1, b);
			ps.setString(2, "000000");

			// activate sql query
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Server");
		}
		System.out.println("done");
	}

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

		String sqlQuery = "update test set isLocked = ? where id = ?;";
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
	/*
	 * public static ArrayList<Course> refreshCourseTable() {
	 * 
	 * ArrayList<Course> list = new ArrayList<>(); String sqlQuery =
	 * "select * from courses";
	 * 
	 * try { if (DBConnector.myConn != null) { Statement st =
	 * DBConnector.myConn.createStatement(); ResultSet rs =
	 * st.executeQuery(sqlQuery); while (rs.next()) {
	 * 
	 * list.add(new Course(rs.getString("bankID"), rs.getString("courseID"),
	 * rs.getString("name")));
	 * 
	 * list.add(new Course(Integer.parseInt(rs.getString("bankID")),
	 * Integer.parseInt(rs.getString("courseID")), rs.getString("name")));
	 * 
	 * } }
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * return list; }
	 */

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
}
