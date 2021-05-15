package entity;

import java.util.ArrayList;

public class TestBank {
	public int id;
	public String name;
	public ArrayList<Course> courses;
	
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
