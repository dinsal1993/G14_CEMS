package server.dbControl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import client.controllers.ClientUI;
import entity.Course;
import entity.Question;
import entity.QuestionBank;
import entity.Test;
import entity.TestBank;




public class QuestionDBController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws SQLException, IOException {
		
		ArrayList<Question> questions = new ArrayList<>();
		ArrayList<String> answers = new ArrayList<>();
		ArrayList<Integer> points = new ArrayList<>();
		answers.add("81");
		answers.add("100");
		answers.add("72");
		answers.add("66");
		points.add(5);
		questions.add(new Question(1, "9 * 9 = ", answers,1,"Rajih"));
		/*answers = new ArrayList<>();
		answers.add("21");
		answers.add("31");
		answers.add("25");
		answers.add("36");
		points.add(10);
		questions.add(new Question(2, "5 * 5 = ", answers,3,"Rajih"));*/
		Test test = new Test(1,180,questions,points,"1234","Rajih");
		String sql = "insert into test values (?, ?, ?, ?, ?)";
		DBConnector db = new DBConnector();
		PreparedStatement ps = DBConnector.myConn.prepareStatement(sql);
		ps.setString(1, "2");
		ps.setString(2, "300");
		

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(questions);
		byte[] courseAsByte;
		courseAsByte = baos.toByteArray();
		Blob b = DBConnector.myConn.createBlob();
		b.setBytes(1, courseAsByte);
		ps.setBlob(3, b);
		
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
		oos1.writeObject(points);
		byte[] courseAsByte1;
		courseAsByte1 = baos1.toByteArray();
		Blob b1 = DBConnector.myConn.createBlob();
		b1.setBytes(1, courseAsByte1);
		ps.setBlob(4, b1);
		ps.setString(5,"Rajih");
		ps.executeUpdate();

	}
	
	
	/*public static ArrayList<Question> getAllQuestions() {

		ArrayList<Question> questions = new ArrayList<Question>();
		
		String sqlQuery = "select * from question";

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					// Gather Data
					ArrayList<String> answers = new ArrayList<String>();
					Integer id = Integer.parseInt(rs.getString(1));
					String description = rs.getString(2);
					int correctAnswer = Integer.parseInt(rs.getString(3));
					String teacherName = rs.getString(4);
					String option1 = rs.getString(5);
					String option2 = rs.getString(6);
					String option3 = rs.getString(7);
					String option4 = rs.getString(8);
					answers.add(option1);
					answers.add(option2);
					answers.add(option3);
					answers.add(option4);
					//questions.add(new Question(null,description,answers,correctAnswer,teacherName));
				}
				rs.close();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}*/
	
	public static ArrayList<String> getAllQuestionBanks() {

		ArrayList<String> banks = new ArrayList<String>();
		String sqlQuery = "select * from questionbank";

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					// Gather Data
					String name = rs.getString(2);
					banks.add(name);
				}
				rs.close();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return banks;
	}


	public static void addQuestion(Question q) {

		String sqlQuery = "insert into question (id,description,correctAnswer,teacherName,A1,A2,A3,A4) values (?,?,?,?,?,?,?,?)";
		
		PreparedStatement pst = null;
		try {
			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				pst.setString(1, String.valueOf(q.getId()));
				pst.setString(2, q.getDescription());
				pst.setString(3,String.valueOf( q.getCorrectAnswer()));
				pst.setString(4, q.getTeacherName());
				pst.setString(5, q.getAnswers().get(0));
				pst.setString(6, q.getAnswers().get(1));
				pst.setString(7, q.getAnswers().get(2));
				pst.setString(8, q.getAnswers().get(3));
				pst.executeUpdate();

				//DBConnector.myConn.close();
				pst.close();


			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void insertQuestionBank(QuestionBank QB) throws SQLException
	{
		try (PreparedStatement pst = DBConnector.myConn.prepareStatement(
		        "SELECT 1 FROM cems.questionbank WHERE name = ?")) {
			pst.setString(1,QB.getName());

		    try (ResultSet rs = pst.executeQuery()) {
		        if (rs.next()) {
		        	//ClientUI.display("This bank name already exist!");
		            // handle account already exists
		        } else {
		            try (PreparedStatement insert = DBConnector.myConn.prepareStatement(
		                    "insert into cems.questionbank (id,name) values (?,?)")) {
		            	insert.setString(1, String.valueOf(QB.getId()));
						
						insert.setString(2,QB.getName() );
		             
		                insert.executeUpdate();
		                insert.close();
		            }
		        }
		        
		    }
		    pst.close();
		}
	}		
	
	public static HashMap<String, Test> getTestQuestions() {
	
		String sqlQuery = "select * from test";
		
		Test temp;
		ArrayList<Question> questions;
		ArrayList<Integer> points;
		
		HashMap<String, Test> testMap = new HashMap<String, Test>();
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					questions = new ArrayList<>();
					points = new ArrayList<>();
					temp = new Test();
					
					Blob questionsBlob = rs.getBlob(3);
					BufferedInputStream bis = new BufferedInputStream(questionsBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					questions = (ArrayList<Question>) ois.readObject();
					

					Blob qPointsBlob = rs.getBlob(4);
					BufferedInputStream bis1 = new BufferedInputStream(qPointsBlob.getBinaryStream());
					ObjectInputStream ois1 = new ObjectInputStream(bis1);
					points = (ArrayList<Integer>) ois1.readObject();
					
					// construct current read test
					
					temp.setId(Integer.parseInt(rs.getString(1)));
					temp.setDuration(Integer.parseInt(rs.getString(2)));
					temp.setQuestions(questions);
					temp.setPointsPerQuestion(points);
					temp.setTeacherName(rs.getString(5));
			

					// Add test to hashMap
					testMap.put(rs.getString(1), temp);

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
		return testMap;
	}
	
	public static String getTestID(String code)
	{
		String id = null;
		String sqlQuery = "select id from pretest where executioncode = "+code+"";
		
				try {
					if(DBConnector.myConn != null)
					{
						Statement st = DBConnector.myConn.createStatement();
						ResultSet rs = st.executeQuery(sqlQuery);
						while(rs.next())
						{
							id = rs.getString("id");
						}
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
			}
				
			return id;
		

	}
	
	

}//End class


		
	


