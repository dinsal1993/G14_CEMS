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
import entity.Question;
import entity.Test;
import entity.TestBank;
import entity.TestDocs;
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
		String id= null;
		if(code == null)
			return id;
		
		String sqlQuery = "select id from test where executionCode = "+code+"";
		
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
	
		String sqlQuery = "insert into testcopy (id ,teacherUsername,year, month,day,studentAnswers,finalScore,actualTime,studentUsername,scoreApproved,status,studentAnswersManual,teacherUsernameExecute,reasons) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(tc.getTestID()));
				ps.setString(2, String.valueOf(tc.getTeacherUsername()));
				ps.setString(3, String.valueOf(tc.getYear()));
				ps.setString(4, String.valueOf(tc.getMonth()));
				ps.setString(5, String.valueOf(tc.getDay()));
				
				
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
				ps.setBlob(6, b);
				ps.setString(7, String.valueOf(tc.getFinalScore()));
				ps.setString(8, String.valueOf(tc.getActualTime()));
				ps.setString(9, String.valueOf(tc.getStudentID()));
				ps.setString(10, String.valueOf("No"));
				ps.setString(11, String.valueOf("Approved / Online"));
				ps.setString(12, null);
				ps.setString(13, String.valueOf(tc.getTestWriterUsername()));
				ps.setString(14, null); // reasons
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

	public static void submitFailedTest(testCopy failed) {
		String sqlQuery = "insert into testcopy (id ,teacherUsername,year, month,day,studentAnswers,finalScore,actualTime,studentUsername,scoreApproved,status,studentAnswersManual,teacherUsernameExecute,reasons) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(failed.getTestID()));
				ps.setString(2, String.valueOf(failed.getTeacherUsername()));
				ps.setString(3, String.valueOf(failed.getYear()));
				ps.setString(4, String.valueOf(failed.getMonth()));
				ps.setString(5, String.valueOf(failed.getDay()));
				
				
				ps.setString(6, null);
				ps.setString(7, String.valueOf(failed.getFinalScore()));
				ps.setString(8, String.valueOf(failed.getActualTime()));
				ps.setString(9, String.valueOf(failed.getStudentID()));
				ps.setString(10, String.valueOf("No"));
				ps.setString(11, String.valueOf("Failed / Online"));
				ps.setString(12, null);
				ps.setString(13, String.valueOf(failed.getTestWriterUsername()));
				ps.setString(14, null);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

	public static int getNumberOfStudentsStartedExam(String id) {
		
		String sqlQuery = "select count(*) from testcopy where id ="+id+"";
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

	public static int getNumOfStudentsFinishedExam(String id) {
		String sqlQuery = "select count(*) from testcopy where status ='Approved / Online' AND id ='"+id+"'";
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

	public static ArrayList<Integer> getGrades(String id) {
		
		ArrayList<Integer> list = new ArrayList<>();
		String sqlQuery = "select finalScore from testcopy where id = "+id+"";
		
		try {
			if(DBConnector.myConn != null)
			{
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while(rs.next())
				{
					list.add(Integer.parseInt(rs.getString("finalScore")));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

	public static void insertToTestDocs(TestDocs td) {
		
		String sqlQuery = "insert into testdocs (id ,year, semester,date,assignedTime,numStudentsStart,numStudentsFinish,numStudentsNotFinishInTime,average,median,distribution) values (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, String.valueOf(td.getId()));
				ps.setString(2, String.valueOf(td.getYear()));
				ps.setString(3, String.valueOf(td.getSemester()));
				ps.setString(4, String.valueOf(td.getDate()));
				ps.setString(5, String.valueOf(td.getAssignedTime()));
				ps.setString(6, String.valueOf(td.getNumStudentsStart()));
				ps.setString(7, String.valueOf(td.getNumStudentsFinishInTime()));
				ps.setString(8, String.valueOf(td.getNumStudentsNotFinishInTime()));
				ps.setString(9, String.valueOf(td.getAverage()));
				ps.setString(10, String.valueOf(td.getMedian()));
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = null;
				try {
					oos = new ObjectOutputStream(baos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					oos.writeObject(td.getDistribution());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] distributionAsByte;
				distributionAsByte = baos.toByteArray();
				Blob b = DBConnector.myConn.createBlob();
				b.setBytes(1, distributionAsByte);		
				ps.setBlob(11, b);
						
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void removeTestFromPlanned(String code) {
		String sqlQuery = "delete from plannedtest where execCode ="+code+"";
		
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

	public static void updateTestCodeToNull(String id) {
		String sqlQuery = "update test set executionCode = ? where id = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, null);
				ps.setString(2, String.valueOf(id));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static int checkLastStudent(String code) {
		String sqlQuery = "select count(*) from ongoing where executionCode ="+code+"";
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

	
	public static ArrayList<testCopy> getStudentTestDetails(String username) 
	{
		ArrayList<testCopy> testcopy = new ArrayList<>();
		ArrayList<Integer> answers;
		String sqlQuery = "select * from testcopy where studentUsername ='"+username+"'";
		
		try {
			if(DBConnector.myConn != null)
			{
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while(rs.next())
				{
					answers = new ArrayList<>();
					
					Blob answersBlob = rs.getBlob(6);
					if(answersBlob == null)
						answers = null;
					else
					{
						BufferedInputStream bis = new BufferedInputStream(answersBlob.getBinaryStream());
						ObjectInputStream ois = new ObjectInputStream(bis);
						answers = (ArrayList<Integer>) ois.readObject();
					}
					
					
					testcopy.add(new testCopy(rs.getString("id"),rs.getString("year"),null,rs.getString("month")
							,rs.getString("day"),answers,Integer.parseInt(rs.getString("finalScore"))
							,0,null,true,rs.getString("teacherUsernameExecute"),null,rs.getString("status")));					
					
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
		
		
		return testcopy;
		
	}

	public static String getSubjectNamebyID(String id) {
		
		String sqlQuery = "select name from subject where id ='"+id+"'";
		String subjectName = "";
		try {
			if(DBConnector.myConn != null)
			{
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				
				while(rs.next())
				{
					subjectName = rs.getString(1);
						
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return subjectName;
		
	}
	

}
