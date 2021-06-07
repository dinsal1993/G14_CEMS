package server.dbControl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import entity.TestBank;
import ocsf.server.ConnectionToClient;

public class TestDBController implements Serializable {
	private static final long serialVersionUID = 1L;

	static ResultSet rs;
	static ArrayList<Object> testIDAndTheTest = new ArrayList<Object>();
	static HashMap<String, ConnectionToClient> connectionClientMap = 
			new HashMap<String, ConnectionToClient>();

	/*
	 * public static void main(String[] args) { /* DBConnector db = new
	 * DBConnector();
	 * 
	 * ArrayList<Integer> points = new ArrayList<Integer>(); String sqlQuery =
	 * "select questionPoint from test"; Statement ps; ResultSet rs; Blob
	 * qPointsBlob = null; try { ps = db.myConn.createStatement(); rs =
	 * ps.executeQuery(sqlQuery); while(rs.next()) qPointsBlob = rs.getBlob(1);
	 * BufferedInputStream bis1 = new
	 * BufferedInputStream(qPointsBlob.getBinaryStream()); ObjectInputStream ois1 =
	 * new ObjectInputStream(bis1); Object obj = ois1.readObject(); points =
	 * (ArrayList<Integer>) obj;
	 * 
	 * System.out.println(points.toString()); } catch (SQLException | IOException |
	 * ClassNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * DBConnector db = new DBConnector(); String updateSQL = "UPDATE test " +
	 * "SET questions =  " + "WHERE id=?"; try { PreparedStatement ps =
	 * DBConnector.myConn.prepareStatement(updateSQL);
	 * 
	 * File newFile = new File("exam.docx"); byte[] mybytearray = new byte[(int)
	 * newFile.length()]; FileInputStream fis = new FileInputStream(newFile);
	 * BufferedInputStream bis = new BufferedInputStream(fis);
	 * 
	 * bis.read(mybytearray, 0, mybytearray.length); Blob b =
	 * DBConnector.myConn.createBlob();
	 * 
	 * // fill blob objct with byte array b.setBytes(1, mybytearray);
	 * 
	 * // attach blob object to sql query ps.setBlob(1, b); ps.setString(2,
	 * "000000");
	 * 
	 * // activate sql query ps.executeUpdate(); } catch (Exception e) {
	 * System.out.println("Error send (Files)msg) to Server"); }
	 * System.out.println("done"); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	/**
	 * get the id of the test according to the execution code
	 * 
	 * @param execCode
	 * @return the id of the test if execution code valid otherwise null
	 */
	public static String FindTestIdAccordingToExecCode(String execCode) {
		String id = null;
		String selectQuery = "select id from test where executioncode = ?";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement pstmt = DBConnector.myConn.prepareStatement(selectQuery);
				pstmt.setString(1, execCode);
				rs = pstmt.executeQuery(); // rs with THE only test
				if (rs.next()) {// if true - there is execCode-to IDTEST
					id = rs.getString(1);
				}
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * get the manual Test!!!
	 * 
	 * @return the byte array of the manual test blob
	 */

	public static byte[] getTest(String executionCode) {
		ResultSet rs;
		Blob blobManualTest = null;
		byte[] byteManualTest = null;
		String selectQuery = "select manualQuestions from test where executionCode = ?";
		try {
				PreparedStatement pstmt = DBConnector.myConn.prepareStatement(selectQuery);
				pstmt.setString(1, executionCode);
				rs = pstmt.executeQuery(); // rs with THE only test
		
			if (rs.next()) {
				blobManualTest = rs.getBlob(1);
				byteManualTest = new byte[(int) blobManualTest.length()];
				byteManualTest = blobManualTest.getBytes(1, (int) blobManualTest.length());
				rs.close();
			}
			else {System.out.println("there is no MANUAL TEST");}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return byteManualTest;
	}

	// zarih lishloh le po et ha -> TEST ID,YEAR,MONTH,DAY,StudentID -(olay bimkom
	// username) -> efshar lasot rak ahri she yesh lano kvar mng test, she sham
	// samim arahim?
	/**
	 * save the manual test word document file in data base
	 * 
	 * @param arrByteManualTestUpload array byte of the manual test word document
	 *                                file
	 * @return true in case that the manual test is saved in data base
	 */
	public static boolean SaveManualTest(byte[] arrByteManualTestUpload) {

		String updateSQL = "UPDATE testcopy " + "SET studentAnswersManual = ? " + "WHERE id=?";
		try {
			PreparedStatement ps = DBConnector.myConn.prepareStatement(updateSQL);

			Blob blobManualTest = DBConnector.myConn.createBlob();

			// fill blob object with byte array
			blobManualTest.setBytes(1, arrByteManualTestUpload);

			// attach blob object to sql query
			ps.setBlob(1, blobManualTest);
			ps.setString(2, "010101");

			// activate sql query
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Server");
		}
		return false;
	}

//ragah
	public static void addStudentToOnGoing(ArrayList<String> list) {
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

	// ragah
	public static void removeStudentFromOnGoing(ArrayList<String> list) {
		String sqlQuery = "delete from ongoing where executionCode = '" + list.get(0) + "' AND username = '"
				+ list.get(1) + "';";

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

	/**
	 * adding Connection client to hash map with key Username
	 * 
	 * @param client
	 * @param string
	 */
	public static void addConnectionClientToHashMap
	(ConnectionToClient client, String username) {
		connectionClientMap.put(username, client);
	}

	public static void removeConnectionClientToHashMap(String username) {
		connectionClientMap.remove(username);

		
	}

	/**zarih lasot she rak a mora she banta et a mivhan tohal linOL
	 * lock test 
	 * @param execCode execution code of the test wants to lock
	 */
	public static void lockTest(String execCode) {
		noLognerAvailableTest(execCode);
		
	}

	private static void noLognerAvailableTest(String execCode) {
		
		String sqlQuery = "delete from plannedtest where execCode = ?";

		try {
			if (DBConnector.myConn != null) {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, execCode);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
		
	}
	
	/**
	 * add the execution code to the cems.test DB
	 * @param idUsernameExec array for id of the test and username of who wrote it and the execution code for it
	 */

	public static void addExecCodetoTestDB(ArrayList<String> idUsernameExec ) {
		String sqlQuery = "UPDATE test SET executionCode = ? WHERE"
				+ " id = ? and teacherUsername = ?";
		try {
				PreparedStatement ps = DBConnector.myConn.prepareStatement(sqlQuery);
				ps.setString(1, idUsernameExec.get(2)); 
				ps.setString(2, idUsernameExec.get(0)); 
				ps.setString(3, idUsernameExec.get(1));
				ps.executeUpdate();
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
