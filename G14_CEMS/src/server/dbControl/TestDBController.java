package server.dbControl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import entity.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestDBController {

	public static ArrayList<Test> getAllTests() {

		ArrayList<Test> tests = new ArrayList<Test>();
		String sqlQuery = "select * from test";

		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);

				while (rs.next()) {
					// Gather Data
					int id = Integer.parseInt(rs.getString(1));
					String subject = rs.getString(2);
					String course = rs.getString(3);
					int duration = Integer.parseInt(rs.getString(4));
					int pointsPerQuestion = Integer.parseInt(rs.getString(5));

					// Create Test object and add to ObservableList<Test>
					tests.add(new Test(id, subject, course, duration, pointsPerQuestion));
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
			if(DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				ResultSet rs = st.executeQuery(sqlQuery);
				rs.next();
				return Integer.parseInt(rs.getString(1));
			}
		}catch(SQLException e) {
				e.printStackTrace();
			}
		return -1;
	}
}
		
	

