package entity;

import java.util.ArrayList;

public class Teacher extends User {
	
	/**Teacher id*/
	public int id;
	
	/**List that conatins all the subject the teacher teaches.*/
	public ArrayList<String> subjects;
	
	
	
	public Teacher(int id, String username, String password, String firstName, String lastName, String email,
			String permissions, ArrayList<String> subjects) {
		super(id, username, password, firstName, lastName, email, permissions);
		this.subjects = subjects;
	}
	
	public ArrayList<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(ArrayList<String> subjects) {
		this.subjects = subjects;
	}
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", subjects=" + subjects + "]";
	}
	
	

}
