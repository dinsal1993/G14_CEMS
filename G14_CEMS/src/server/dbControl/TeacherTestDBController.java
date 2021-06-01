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
import java.util.Date;  
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;   


public class TeacherTestDBController {

	public static void main(String[] args) throws SQLException, IOException {
		ArrayList<Course> courses = new ArrayList<Course>();
		courses.add(new Course(2, 1, "Electromagnetism"));
		courses.add(new Course(2, 2, "Mechanics"));
		TestBank bank = new TestBank(2, "Physics", courses);
		String sql = "insert into testbank values (?, ?, ?)";
		DBConnector db = new DBConnector();
		PreparedStatement ps = DBConnector.myConn.prepareStatement(sql);
		ps.setString(1, "2");
		ps.setString(2, "Physics");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(courses);
		byte[] courseAsByte;
		courseAsByte = baos.toByteArray();
		Blob b = DBConnector.myConn.createBlob();
		b.setBytes(1, courseAsByte);
		ps.setBlob(3, b);
		ps.executeUpdate();

	}

	public static ArrayList<Test> getAllTests() {

		//TO-DO:
		//need to select question and questionPoint from DB and add them to test
		
		ArrayList<Test> tests = new ArrayList<Test>();
		String sqlQuery = "select * from test";

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					// Gather Data
					int id = Integer.parseInt(rs.getString(1));
					int duration = Integer.parseInt(rs.getString(2));
					
					// Create Test object and add to ObservableList<Test>
					tests.add(new Test(id, duration,null, null, null,null));

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
		
		/*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime currentDate = LocalDateTime.now();  
		//System.out.println(dtf.format(now));
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH");  
		LocalDateTime currentHour = LocalDateTime.now();
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("mm");  
		LocalDateTime currentMinutes = LocalDateTime.now();
		String date = dtf.format(currentDate);
		String hour = dtf1.format(currentHour);
		String minutes = dtf2.format(currentMinutes);
		System.out.println(date);
		System.out.println(hour);
		System.out.println(minutes);
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
	
	//has to be edited
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
	
	public static ArrayList<Course> refreshCourseTable()
	{
		
		ArrayList<Course> list = new ArrayList<>();
		String sqlQuery = "select * from courses";
		
		try {
			if(DBConnector.myConn != null)
			{
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while(rs.next())
				{
					list.add(new Course(Integer.parseInt(rs.getString("bankID")),Integer.parseInt(rs.getString("courseID")),rs.getString("name")));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public static void addCourse(Course c)
	{
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

	public static void deleteCourse(Course c)
	{
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
	public static void insertTestBank(TestBank TB) throws IOException
	{
		
		
		ArrayList<Course> courses = new ArrayList<Course>();
		String sqlQuery = "insert into cems.testbank (id,name,courses) values (?,?,?)";

		PreparedStatement pst = null;
		try {
	
			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				//serialize object
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(courses);
				//store in byte array
				byte[] empAsByte;
				empAsByte = baos.toByteArray();
				//create blob object
				Blob b = DBConnector.myConn.createBlob();
				
				//fill blob object with byte array
				
				pst.setString(1, String.valueOf(TB.getId()));
				pst.setString(2,TB.getName() );
				b.setBytes(1, empAsByte);
				pst.setBlob(3, b);
				
				pst.executeUpdate();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}
