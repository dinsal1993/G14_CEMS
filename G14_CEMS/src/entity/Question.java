package entity;

import java.util.ArrayList;

public class Question {
	private int id;
	private String description;
	private ArrayList<String> answers;
	private int correctAnswer;
	private String teacherName;
	
	public Question(int id, String description, ArrayList<String> answers,
			int correctAnswer, String teacherName) {
		this.id = id;
		this.description = description;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
		this.teacherName = teacherName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", answers=" + answers + ", correctAnswer="
				+ correctAnswer + ", teacherName=" + teacherName + "]";
	}
	
	

}
