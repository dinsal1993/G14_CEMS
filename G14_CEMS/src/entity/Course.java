package entity;

import java.io.Serializable;

public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	public int subjectID;
	public int courseID;
	public String name;
	
	public Course(int subjectID, int courseID, String name) {
		this.subjectID = subjectID;
		this.courseID = courseID;
		this.name = name;
	}
	
	public Course()
	{
		
	}
	


	public int getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Course [subjectID=" + subjectID + ", courseID=" + courseID + ", name=" + name + "]";
	}
	
	
	
}
