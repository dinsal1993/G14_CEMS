package server.dbControl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
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

public class TestDBController implements Serializable{
	private static final long serialVersionUID = 1L;


	static ResultSet rs;
	static ArrayList<Object> testIDAndTheTest = new ArrayList<Object>();


	public static void main(String[] args) {

		Integer a = 6;
		if(a instanceof Integer)System.out.println("ubt");
		System.out.println("as");
	}
	
	/**
	 * get the id of the test according to the execution code
	 * 
	 * @param execCode
	 * @return the id of the test if execution code valid otherwise null
	 */
	public static String FindTestIdAccordingToExecCode(String execCode)  {
		String id=null;
		String selectQuery = "select id from test where executioncode = ?";
		try {
			if (DBConnector.myConn != null) {
				PreparedStatement pstmt = DBConnector.myConn.prepareStatement(selectQuery);
				pstmt.setString(1, execCode);
				rs = pstmt.executeQuery(); // rs with THE only test			
				if(rs.next()) {// if true - there is execCode-to IDTEST
					 id = rs.getString(1);
				 }
			} else
				System.out.println("myConn is NULL !");
		} catch (SQLException e) {
			e.printStackTrace();}
		return id;
	}

	/**
	 * get the manual Test!!!
	 * 
	 * return the buffered input stream of the blob of the manual test
	 * @throws IOException 
	 * @return the byte array of the manual test blob
	 */

	public static byte[] getTest(String executionCode) { 
		ResultSet rs2;
		 Blob blobManualTest= null;
		 byte[] byteManualTest = null;
		String selectSQL = "SELECT questions FROM test WHERE id=0000"; // efshar lasot siman shela where id = ? ve az execCode ./ MEyotar
		Statement pstmt;
		try {
			pstmt = DBConnector.myConn.createStatement();
		rs2 = pstmt.executeQuery(selectSQL);
		if(rs2.next()) {
		 blobManualTest = rs2.getBlob(1);
		 byteManualTest = new byte[(int) blobManualTest.length()];
		 byteManualTest = blobManualTest.getBytes(1, (int) blobManualTest.length());		 
		 rs2.close();}

		else
			System.out.println("there is no MANUAL TEST in 0000 id");rs2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return byteManualTest;
	}

	
	// zarih lishloh le po et ha -> TEST ID,YEAR,MONTH,DAY,StudentID -(olay bimkom username) -> efshar lasot rak ahri she yesh lano kvar mng test, she sham samim arahim?
	/**
	 * save the manual test word document file in data base
	 * @param arrByteManualTestUpload array byte of the manual test word document file
	 * @return true in case that the manual test is saved in data base
	 */
	public static boolean SaveManualTest(byte[] arrByteManualTestUpload) {
		
		String updateSQL = "UPDATE testcopy " + "SET studentAnswersManual = ? " + "WHERE id=?";
		try {
			PreparedStatement ps = DBConnector.myConn.prepareStatement(updateSQL);

			Blob blobManualTest = DBConnector.myConn.createBlob();
		
			// fill blob object with byte array
			blobManualTest.setBytes(1,arrByteManualTestUpload);

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
}
