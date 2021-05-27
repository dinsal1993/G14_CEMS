package entity;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import client.gui.CreateQuestionBankController;

/** A bank (Subject) containing multiple questions */
public class QuestionBank implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Bank id, used for unique identification */
	public int id;
	
	/** Name of the bank (Subject) */
	private String name;
	//change(2 digits)
	//private static final AtomicInteger count = new AtomicInteger(9);
	
	/**
	 * @param id Unique id of the bank
	 * @param name Name of the bank
	 */
	public QuestionBank(AtomicInteger id, String name) {
		//change
		this.id = CreateQuestionBankController.count.incrementAndGet();
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
