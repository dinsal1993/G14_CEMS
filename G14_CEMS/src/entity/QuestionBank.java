package entity;

public class QuestionBank {
	public int id;
	private String name;
	
	public QuestionBank(int id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "QuestionBank [id=" + id + ", name=" + name + "]";
	}
	
	
	

}
