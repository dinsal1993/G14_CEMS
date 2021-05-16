package entity;

import java.util.ArrayList;

/** A bank (Subject) containing multiple Tests */
public class TestBank {
	
	/** Bank id, used for unique identification*/
	public int id;
	
	/** Name of the bank (Subject) */
	public String name;
	
	/** List of courses in the Bank (Subject) */
	public ArrayList<Course> courses;
	
	/**
	 * @param id Unique id of the bank
	 * @param name Name of the bank
	 * @param courses List of courses in the bank
	 */
	public TestBank(int id, String name, ArrayList<Course> courses) {
		this.id = id;
		this.name = name;
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "TestBank [id=" + id + ", name=" + name + ", courses=" + courses + "]";
	}
}
