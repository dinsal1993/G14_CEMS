package entity;

import java.io.Serializable;
import java.util.ArrayList;

/** Test class representing a Test for student built by teachers */
public class Test implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** Test id */
	private int id;
	
	/** Test duration in minutes */
	private int duration;
	
	/** list of questions */
	private ArrayList<Question> questions;
	
	/** pointsPerQuestion[i] represents the question points for
	 *  questions[i] */
	private ArrayList<Integer> pointsPerQuestion;
	
	/** Used by student to take the test*/
	private String executionCode;
	
	/** the author of the test */
	private String teacherName;
	
	private String isLocked;
	
	/**
	 * 
	 * @param id Test id
	 * @param duration Test duration in minutes 
	 * @param questions list of questions in the test
	 * @param pointsPerQuestion pointsPerQuestion[i] represents the 
	 *  question points for questions[i]
	 * @param executionCode Used by students to take the test
	 * @param teacherName the teacher that wrote the test
	 */
	public Test(int id, int duration, ArrayList<Question> questions, 
				ArrayList<Integer> pointsPerQuestion,String executionCode,
				String teacherName) {
		this.id = id;
		this.duration = duration;
		this.questions = questions;
		this.pointsPerQuestion = pointsPerQuestion;
		this.executionCode = executionCode;
		this.teacherName = teacherName;
		this.isLocked = "false";
	}
	
	/**
	 * Empty Constructor */
	public Test() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	public ArrayList<Integer> getPointsPerQuestion() {
		return pointsPerQuestion;
	}
	public void setPointsPerQuestion(ArrayList<Integer> pointsPerQuestion) {
		this.pointsPerQuestion = pointsPerQuestion;
	}
	public String getExecutionCode() {
		return executionCode;
	}
	public void setExecutionCode(String executionCode) {
		this.executionCode = executionCode;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	public String getLocked() {
		return isLocked;
	}
	public void setLocked(String lock) {
		this.isLocked = lock;
	}
	
	@Override
	public String toString() {
		return "Test [id=" + id + ", duration=" + duration + ", questions=" + questions + ", pointsPerQuestion="
				+ pointsPerQuestion + ", executionCode=" + executionCode + ", teacherName=" + teacherName + "]";
	}
	
	
	
	

}
