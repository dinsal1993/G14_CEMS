package server.dbControl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.Question;
import entity.QuestionBank;
import entity.Subject;

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

	public static void addQuestion(Question q) {
		System.out.println("in db: q:" + q);
		String sqlQuery = "insert into question (id,description,correctAnswer,teacherName,A1,A2,A3,A4,teacherUsername) values (?,?,?,?,?,?,?,?,?)";

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

			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
	public static int getQuestionCount(ArrayList<String> arr) {
		String sqlQuery = "select count(*) from question where id like \"" + arr.get(0) + "%\" and"
				+ " teacherUsername = \"" + arr.get(1) + "\";";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				rs.next();
				int answer = Integer.parseInt(rs.getString(1));
				System.out.println("num of questions: " + answer);
				st.close();
				return answer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
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

}
