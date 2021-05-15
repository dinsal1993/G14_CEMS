package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Test implements Serializable {
	private int id;
	private int duration;
	private ArrayList<Question> questions;
	private ArrayList<Integer> pointsPerQuestion;
	private String executionCode;
	private String teacherName;
	public Test(int id, int duration, ArrayList<Question> questions, 
				ArrayList<Integer> pointsPerQuestion,String executionCode,
				String teacherName) {
		this.id = id;
		this.duration = duration;
		this.questions = questions;
		this.pointsPerQuestion = pointsPerQuestion;
		this.executionCode = executionCode;
		this.teacherName = teacherName;
	}
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
	
	@Override
	public String toString() {
		return "Test [id=" + id + ", duration=" + duration + ", questions=" + questions + ", pointsPerQuestion="
				+ pointsPerQuestion + ", executionCode=" + executionCode + ", teacherName=" + teacherName + "]";
	}
	
	
	
	

}
