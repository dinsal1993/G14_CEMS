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
import entity.Test;
import entity.TestBank;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeacherTestDBController implements Serializable {

	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
		/*
		 * ArrayList<Course> courses = new ArrayList<Course>(); courses.add(new
		 * Course(2, 1, "Electromagnetism")); courses.add(new Course(2, 2,
		 * "Mechanics")); TestBank bank = new TestBank(2, "Physics", courses); String
		 * sql = "insert into testbank values (?, ?, ?)"; DBConnector db = new
		 * DBConnector(); PreparedStatement ps =
		 * DBConnector.myConn.prepareStatement(sql); ps.setString(1, "2");
		 * ps.setString(2, "Physics");
		 * 
		 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream
		 * oos = new ObjectOutputStream(baos); oos.writeObject(courses); byte[]
		 * courseAsByte; courseAsByte = baos.toByteArray(); Blob b =
		 * DBConnector.myConn.createBlob(); b.setBytes(1, courseAsByte); ps.setBlob(3,
		 * b); ps.executeUpdate();
		 */
		// Shahar- input- MIVHAN YADANI LEDOGMA -id-0000.
		 writeBlob();
		//readBlob("0000","a");
	}

	/**
	 * get the test
	 * @param idTest id of the test 
	 * @param TestType Manual or computerized
	 * @throws ClassNotFoundException
	 */
	private static void readBlob(String idTest,String TestType) throws ClassNotFoundException {
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
			while(( a = bis.read(blobByte))!= -1) {
				 bos.write(blobByte,0,a);
			      bos.flush();
			      fos.flush();}
			   //   blobByte = ""; }
				
		/*	System.out.println("ahri try2");
			ObjectInputStream ois = new ObjectInputStream(bis);	
			System.out.println("ahri try3");
		
			blobByte = (byte[])ois.readObject();
			if(TestType.equals("Manual")) { // ELSE -> Lo baniti zarih livnot*/
		//	File manualTest = new File("manualTest.docx");

		   //   FileOutputStream fos = new FileOutputStream(manualTest);

		    //  BufferedOutputStream bos = new BufferedOutputStream(fos);
				
		     // bos.write( ((MyFile)msg).getMybytearray() ,0,((MyFile)msg).getSize());
		     // bos.write(blobByte,0,blobByte.length);
		     // bos.flush();
		      //fos.flush();

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

/////////////////////////////////////////////////////////////////////////

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
	/*
	 * DBConnector db = new DBConnector();
	 * 
	 * // update sql String updateSQL = "UPDATE test " + "SET questions = ? " +
	 * "WHERE id=?";
	 * 
	 * try { if (DBConnector.myConn == null) System.out.println("NULL");
	 * PreparedStatement pstmt = DBConnector.myConn.prepareStatement(updateSQL);
	 * 
	 * // read the file File file = new File("exam.docx"); FileInputStream input =
	 * new FileInputStream(file);
	 * 
	 * // set parameters pstmt.setBinaryStream(1, input); pstmt.setString(2,
	 * "0000"); DBConnector.myConn.createBlob()
	 * 
	 * // store the resume file in database System.out.println("Reading file " +
	 * file.getAbsolutePath()); System.out.println("Store file in the database.");
	 * pstmt.executeUpdate();
	 * 
	 * } catch (SQLException | FileNotFoundException e) {
	 * System.out.println(e.getMessage()); }
	 */

/////////////////////////////////////////////////////////////////////////
	public static ArrayList<Test> getAllTests() {

		ArrayList<Test> tests = new ArrayList<Test>();
		String sqlQuery = "select id,duration,isLocked from test";

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					// Gather Data
					int id = Integer.parseInt(rs.getString(1));
					int duration = Integer.parseInt(rs.getString(2));
					// String subject = rs.getString(2);
					String isLocked = rs.getString(3);

					// int pointsPerQuestion = Integer.parseInt(rs.getString(5));

					// Create Test object and add to ObservableList<Test>
					tests.add(new Test(id, duration, null, null, null, null));

					// tests.add(new Test(id, subject, course, duration, pointsPerQuestion));
				}
				rs.close();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tests;
	}

	public static void updateTestDuration(Test t) {

		String sqlQuery = "update test set duration = ? where id = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(t.getDuration()));
				ps.setString(2, String.valueOf(t.getId()));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

		String sqlQuery = "update test set isLocked = ? where id = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf("true"));
				ps.setString(2, String.valueOf(t.getId()));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
					list.add(new Course(Integer.parseInt(rs.getString("bankID")),
							Integer.parseInt(rs.getString("courseID")), rs.getString("name")));
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
				ps.setString(1, String.valueOf(c.getBankId()));
				ps.setString(2, String.valueOf(c.getCourseId()));
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
				ps.setString(1, String.valueOf(c.getBankId()));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
