package entity;

/** A bank (Subject) containing multiple questions */
public class QuestionBank {
	
	/** Bank id, used for unique identification */
	public int id;
	
	/** Name of the bank (Subject) */
	private String name;
	
	/**
	 * @param id Unique id of the bank
	 * @param name Name of the bank
	 */
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
