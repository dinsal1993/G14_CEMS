package entity;

import java.util.ArrayList;

public class testCopy {
	private int testID;
	private int year;
	private int semester;
	private ArrayList<Integer> studentAnswers;
	private int finalScore;
	private int actualTime;
	private int studentID;
	private boolean scoreApproved;
	
	public testCopy(int testID, int year, int semester, 
			ArrayList<Integer> studentAnswers, int finalScore,
			int actualTime, int studentID, boolean scoreApproved) {
		this.testID = testID;
		this.year = year;
		this.semester = semester;
		this.studentAnswers = studentAnswers;
		this.finalScore = finalScore;
		this.actualTime = actualTime;
		this.studentID = studentID;
		this.scoreApproved = scoreApproved;
	}
	public int getTestID() {
		return testID;
	}
	public void setTestID(int testID) {
		this.testID = testID;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
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
	public int getActualTime() {
		return actualTime;
	}
	public void setActualTime(int actualTime) {
		this.actualTime = actualTime;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public boolean isScoreApproved() {
		return scoreApproved;
	}
	public void setScoreApproved(boolean scoreApproved) {
		this.scoreApproved = scoreApproved;
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
