package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * represents a copy of a test done by a student
 */
public class testCopy  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** the testID  */
	private String testID;
	
	/** the year of the test */
	private String year;
	
	/** the month of the test */
	private String month;
	
	/** the day of the test */
	private String day;
	
	/** the test semester (01 or 02) */
	private String semester;
	
	/** a list of the students answer
	 * array[0] -> answer in question 0(first question) */
	private ArrayList<Integer> studentAnswers;
	
	/** student's final score */
	private int finalScore;
	
	/** actual time of test - including extensions */
	private long actualTime;
	
	/** the student ID */
	private String studentID;
	
	/** test approved by teacher or not
	 * cannot see copy until score is approved */
	private boolean scoreApproved;
	
	/** if finalScore was changed - teacher must provide reasons */
	private String reasons;
	
	/**
	 * @param testID the ID of the test
	 * @param year the year of the test
	 * @param semester the test semester
	 * @param month the month of the test
	 * @param day the day of the test
	 * @param studentAnswers a list of student's answers
	 * @param finalScore student's final score
	 * @param actualTime actual test time - including extensions
	 * @param studentID student ID
	 * @param scoreApproved teacher approved test or not
	 */
	public testCopy(String testID, String year, String semester,
			String month,String day,
			ArrayList<Integer> studentAnswers, int finalScore,
			long actualTime, String studentID, boolean scoreApproved) {
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