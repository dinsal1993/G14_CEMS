package entity;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L; 
	
	/**User ID.*/
	private int id;
	
	/**User's username.*/
	private String username;
	
	/**User's password.*/
	private String password;
	
	/**User's firstname.*/
	private String firstName;
	
	/**User's lastname.*/
	private String lastName;
	
	/**User's email.*/
	private String email;
	
	/**User permission , could be - Student , Teacher , principal*/
	private String permissions;
	
	
	public User(int id, String username, String password, String firstName, String lastName, String email,
			String permissions) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.permissions = permissions;
	}
	
	/**
	 * 
	 * @param username username account
	 * @param password password for the username  account
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermssions(String permssions) {
		this.permissions = permssions;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", permssions=" + permissions + "]";
	}
	
	

}
