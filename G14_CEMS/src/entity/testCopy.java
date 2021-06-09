package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class testCopy  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String testID;
	private String year;
	private String month;
	private String day;
	private String semester;
	private ArrayList<Integer> studentAnswers;
	private int finalScore;
	private long actualTime;
	private String studentID;
	private boolean scoreApproved;
	private String reasons;
	
	/**
	 * array byte of the manual test to upload.
	 */
	private byte[] arrByteManualTestUpload;

	/**
	 * teacher who created the test
	 */
	private String teacherUsername;
	
	/**
	 * teacher that execute the test
	 */
	private String teacherUsernameExecute;

	public String getTeacherUsernameExecute() {
		return teacherUsernameExecute;
	}
	
	public void setTeacherUsernameExecute(String teacherUsernameExecute) {
		 this.teacherUsernameExecute = teacherUsernameExecute;
	}
	
	
	
	public byte[] getArrByteManualTestUpload() {
		return arrByteManualTestUpload;
	}
	public void setArrByteManualTestUpload(byte[] arrByteManualTestUpload) {
		this.arrByteManualTestUpload = arrByteManualTestUpload;
	}
	
	public void setTeacherUsername(String teacherUsername)
	{
		this.teacherUsername = teacherUsername;
	}
	
	public String getTeacherUsername()
	{
		return this.teacherUsername;
	}
	
	/**
	 * 
	 * @param testID
	 * @param year
	 * @param semester
	 * @param month
	 * @param day
	 * @param studentAnswers
	 * @param finalScore
	 * @param actualTime
	 * @param studentID
	 * @param scoreApproved
	 */
	
	public testCopy(String testID, String year, String semester,
			String month,String day,
			ArrayList<Integer> studentAnswers, int finalScore,
			long actualTime, String studentID, boolean scoreApproved,String teacherUsername) {
		this.testID = testID;
		this.year = year;
		this.semester = semester;
		this.studentAnswers = studentAnswers;
		this.finalScore = finalScore;
		this.actualTime = actualTime;
		this.studentID = studentID;
		this.scoreApproved = scoreApproved;
		this.month = month;
		this.day = day;
		this.teacherUsername = teacherUsername;
		reasons = null;
	}
	public testCopy() {
		// TODO Auto-generated constructor stub
	}
	
	public String getTestID() {
		return testID;
	}
	public void setTestID(String string) {
		this.testID = string;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonth()
	{
		return this.month;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getDay()
	{
		return this.day;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public ArrayList<Integer> getStudentAnswers() {
		return studentAnswers;
	}
	public void setStudentAnswers(ArrayList<Integer> studentAnswers) {
		this.studentAnswers = studentAnswers;
	}
	public int getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	public long getActualTime() {
		return actualTime;
	}
	public void setActualTime(long actualTime) {
		this.actualTime = actualTime;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public boolean isScoreApproved() {
		return scoreApproved;
	}
	public void setScoreApproved(boolean scoreApproved) {
		this.scoreApproved = scoreApproved;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reason) {
		this.reasons = reason;
	}
	@Override
	public String toString() {
		return "testCopy [testID=" + testID + ", year=" + year +
				", semester=" + semester + ", studentAnswers="
				+ studentAnswers + ", finalScore=" + finalScore +
				", actualTime=" + actualTime + ", studentID="
				+ studentID + ", scoreApproved=" + scoreApproved + "]";
	}
	
	
}