package server.dbControl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import client.controllers.ClientUI;
import entity.Question;
import entity.QuestionBank;


public class QuestionDBController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public static ArrayList<Question> getAllQuestions() {

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
					questions.add(new Question(null,description,answers,correctAnswer,teacherName));
				}
				rs.close();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}
	
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
		
	}
		/*String sqlQuery = "insert into cems.questionbank (id,name) values (?,?)";

		PreparedStatement pst = null;
		try {
			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				pst.setString(1, String.valueOf(QB.getId()));
				
				pst.setString(2,QB.getName() );
				pst.executeUpdate();

				//DBConnector.myConn.close();
				pst.close();


			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	


