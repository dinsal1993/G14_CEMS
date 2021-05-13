package entity;

import java.io.Serializable;

public class Test implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String subject;
	private String course;
	private int duration;
	private int pointsPerQuestion;
	
	public Test() {
	}
	
	public Test(int id, String subject, String course, int duration,
			int pointsPerQuestion) {
		this.id = id;
		this.subject = subject;
		this.course = course;
		this.duration = duration;
		this.pointsPerQuestion = pointsPerQuestion;
	}
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getSubject() { return subject; }
	public void setSubject(String subject) { this.subject = subject; }
	public String getCourse() { return course; }
	public void setCourse(String course) { this.course = course; }
	public int getDuration() { return duration; }
	public void setDuration(int duration) { this.duration = duration; }
	public int getPointsPerQuestion() { return pointsPerQuestion; }
	public void setPointsPerQuestion(int pointsPerQuestion) {
		this.pointsPerQuestion = pointsPerQuestion;
	}
	@Override
	public String toString() {
		return "[" + id + ", " + subject + ", " + course + ", " +
				duration + ", " + pointsPerQuestion + "]";
	}
	
	

}
