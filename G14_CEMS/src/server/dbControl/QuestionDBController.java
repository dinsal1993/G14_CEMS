package server.dbControl;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;


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


import entity.Question;
import entity.QuestionBank;
import entity.Subject;

import client.controllers.ClientUI;
import entity.Course;
import entity.Question;
import entity.QuestionBank;
import entity.Test;
import entity.TestBank;


public class QuestionDBController {
	
	public static ArrayList<Subject> getAllSubjects(String username) {

		ArrayList<Subject> subjects = new ArrayList<Subject>();
		String sqlQuery = "select * from teacher where username = \"" + username + "\";";
		
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					// Gather Data
					Blob subjectsBlob = rs.getBlob(2);
					BufferedInputStream bis = new BufferedInputStream(subjectsBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					subjects = (ArrayList<Subject>) ois.readObject();
				}
				rs.close();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subjects;
	}
		
	public static boolean addQuestion(Question q) {

		String sqlQuery = "insert into question (id,description,correctAnswer,teacherName,A1,A2,A3,A4) values (?,?,?,?,?,?,?,?)";
		
		PreparedStatement pst = null;
		try {
			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				pst.setString(1, q.getId());
				pst.setString(2, q.getDescription());
				pst.setString(3, String.valueOf(q.getCorrectAnswer()));
				pst.setString(4, q.getTeacherName());
				pst.setString(5, q.getAnswers().get(0));
				pst.setString(6, q.getAnswers().get(1));
				pst.setString(7, q.getAnswers().get(2));
				pst.setString(8, q.getAnswers().get(3));
				pst.setString(9, q.getTeacherUsername());
				pst.executeUpdate();
				

				// DBConnector.myConn.close();
				pst.close();
				return true;
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}
	

	public static boolean updateQuestion(Question q) {
		String sqlQuery = "update question set description = ?,"
				+ " correctAnswer = ?, A1 = ?,A2 = ?," + " A3 = ?, A4 = ? "
						+ "where id = ? and teacherUsername = ?;";

		PreparedStatement pst = null;
		try {
			if (DBConnector.myConn != null) {
				pst = DBConnector.myConn.prepareStatement(sqlQuery);
				pst.setString(1, q.getDescription());
				pst.setString(2, String.valueOf(q.getCorrectAnswer()));
				pst.setString(3, q.getAnswers().get(0));
				pst.setString(4, q.getAnswers().get(1));
				pst.setString(5, q.getAnswers().get(2));
				pst.setString(6, q.getAnswers().get(3));
				pst.setString(7, q.getId());
				pst.setString(8, q.getTeacherUsername());
				pst.executeUpdate();
				

				// DBConnector.myConn.close();
				pst.close();
				return true;
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}

	public static void insertQuestionBank(QuestionBank QB) throws SQLException {
		try (PreparedStatement pst = DBConnector.myConn
				.prepareStatement("SELECT 1 FROM cems.questionbank WHERE name = ?")) {
			pst.setString(1, QB.getName());

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					// ClientUI.display("This bank name already exist!");
					// handle account already exists
				} else {
					try (PreparedStatement insert = DBConnector.myConn
							.prepareStatement("insert into cems.questionbank (id,name) values (?,?)")) {
						insert.setString(1, String.valueOf(QB.getId()));

						insert.setString(2, QB.getName());

						insert.executeUpdate();
						insert.close();
					}
				}

			}
			pst.close();
		}

	}
	//get num of questions in question bank
	public static int getNextQID(ArrayList<String> arr) {
		String sqlQuery = "select * from question where id like \"" + arr.get(0) + "%\" and"
				+ " teacherUsername = \"" + arr.get(1) + "\";";
		int place = 1;
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while(rs.next()) {
				int rowIDNum = Integer.parseInt(rs.getString(1).substring(2));
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
	//get subjectID from bank name
	public static String getSubjectID(String bankName) {
		String sqlQuery = "select * from subject;";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					if (rs.getString(2).equals(bankName))
						return rs.getString(1);
				}
				rs.close();
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	
	public static Test getTestQuestions(String code) {
	
		String sqlQuery = "select * from test where executionCode ="+code+"";
		
		Test temp = null;
		ArrayList<Question> questions;
		ArrayList<Integer> points;
		
		
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				while (rs.next()) {
					questions = new ArrayList<>();
					points = new ArrayList<>();
					
					Blob questionsBlob = rs.getBlob(3);
					BufferedInputStream bis = new BufferedInputStream(questionsBlob.getBinaryStream());
					ObjectInputStream ois = new ObjectInputStream(bis);
					questions = (ArrayList<Question>) ois.readObject();
					

					Blob qPointsBlob = rs.getBlob(4);
					BufferedInputStream bis1 = new BufferedInputStream(qPointsBlob.getBinaryStream());
					ObjectInputStream ois1 = new ObjectInputStream(bis1);
					points = (ArrayList<Integer>) ois1.readObject();
					
					// construct current read test
					temp = new Test();
					temp.setId(rs.getString(1));
					temp.setDuration(Integer.parseInt(rs.getString(2)));
					temp.setQuestions(questions);
					temp.setPointsPerQuestion(points);
					temp.setTeacherName(rs.getString(5));
					temp.setTeacherUsername(rs.getString(6));
					temp.setExecutionCode(code);
					temp.setTeacherNotes(rs.getString(8));
					temp.setStudentNotes(rs.getString(9));
					

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
		return null;
	}

	public static Question getQuestionByID(ArrayList<String> arr) {
		String sqlQuery = "select * from question where id = \"" + 
				arr.get(0) + "\" and teacherUsername = \"" + arr.get(1) + "\";";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				rs.next();
				
				String id = rs.getString(1);
				String description = rs.getString(2);
				int correctAnswer = Integer.parseInt(rs.getString(3));
				String teacherName = rs.getString(4);
				ArrayList<String> answers = new ArrayList<>();
				answers.add(rs.getString(5));
				answers.add(rs.getString(6));
				answers.add(rs.getString(7));
				answers.add(rs.getString(8));
				String teacherUsername = rs.getString(9);
				
				return new Question(id, description, answers, correctAnswer, teacherName, teacherUsername);		
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	
	/*public static String getTestID(String code)
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
		

	}*/
	
	

//}//End class


	public static boolean deleteQuestion(Question q) {
		String sqlQuery = "delete from question where id = ? and teacherUsername = ?;";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, q.getId());
				ps.setString(2, q.getTeacherUsername());
				ps.executeUpdate();
				return true;
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
