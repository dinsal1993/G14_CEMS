package entity;

import java.io.Serializable;

public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**Subject ID.*/
	public String subjectID;
	
	/**Course ID.*/
	public String courseID;
	
	/**Course name.*/
	public String name;
	
	/**
	 * Main Course constructor.
	 * @param subjectID the subject ID.
	 * @param courseID the course ID.
	 * @param name the course name.
	 */
	public Course(String subjectID, String courseID, String name) {
		this.subjectID = subjectID;
		this.courseID = courseID;
		this.name = name;
	}
	
	public Course()
	{
		
	}
	

	
	public String getSubjectID() {
		return subjectID;
	}

	
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
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
