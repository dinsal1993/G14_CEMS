package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class testCopy  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	/** test ID.*/
	private String testID;
	
	/**test planned year*/
	private String year;
	
	/**test planned month*/
	private String month;
	
	/**test planned day*/
	private String day;
	
	/**the semester in which the exam took place.*/
	private String semester;
	
	/**List of the student selected answers.*/
	private ArrayList<Integer> studentAnswers;
	
	/**student final score.*/
	private int finalScore;
	
	/**actual time took the student to finish the exam*/
	private long actualTime;
	
	/**student ID*/
	private String studentID;
	
	/**True if the teacher approved the score , otherwise False.*/
	private boolean scoreApproved;
	
	/**the username of the teacher who wrote the exam.*/
	private String teacherUsername;
	private String testWriterUsername;
	private String reasons;
	private String status;
	private String subject;
	
	public testCopy(String testID, String year, String semester,
			String month,String day,
			ArrayList<Integer> studentAnswers, int finalScore,
			long actualTime, String studentID, boolean scoreApproved,String teacherUsername
			,String testWriterUsername,String status) {
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
		this.testWriterUsername = testWriterUsername;
		this.status = status;
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
	
	public void setTeacherUsername(String teacherUsername)
	{
		this.teacherUsername = teacherUsername;
	}
	
	public String getTeacherUsername()
	{
		return this.teacherUsername;
	}
	
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reason) {
		this.reasons = reason;
	}
	
	public void setTestWriterUsername(String name)
	{
		this.testWriterUsername = name;
	}
	
	public String getTestWriterUsername()
	{
		return this.testWriterUsername;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	public String getSubject()
	{
		return this.subject;
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