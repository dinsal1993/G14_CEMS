package entity;

import java.io.Serializable;

public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	public int bankId;
	public int courseId;
	public String name;
	
	public Course(int bankId, int courseId, String name) {
		this.bankId = bankId;
		this.courseId = courseId;
		this.name = name;
	}
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int id) {
		this.bankId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Course [bankId=" + bankId + ", courseId=" + courseId + ", name=" + name + "]";
	}
	
}
