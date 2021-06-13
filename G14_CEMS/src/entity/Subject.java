package entity;

import java.io.Serializable;

public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**Subject ID.*/
	private String id;
	
	/**Subject name.*/
	private String name;
	
	/**
	 * 
	 * @param id refers to the subject id.
	 * @param name refers to the subject name
	 */
	public Subject(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + "]";
	}
}
