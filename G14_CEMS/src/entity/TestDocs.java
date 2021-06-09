package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class TestDocs implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String year;
	private String semester;
	private String date;
	private String assignedTime;
	private int numStudentsStart;
	private int numStudentsNotFinishInTime;
	private int numStudentsFinishInTime;
	private double average;
	private double median;
	private ArrayList<Integer> distribution;
	
	
	public TestDocs(String id, String year, String semester,String date, String assignedTime,
			int numStudentsStart, int numStudentsNotFinishInTime, 
			int numStudentsFinishInTime, double average , double median,ArrayList<Integer> distribution) {
		this.id = id;
		this.year = year;
		this.semester = semester;
		this.date = date;
		this.assignedTime = assignedTime;
		this.numStudentsStart = numStudentsStart;
		this.numStudentsNotFinishInTime = numStudentsNotFinishInTime;
		this.numStudentsFinishInTime = numStudentsFinishInTime;
		this.average = average;
		this.median = median;
		this.distribution = distribution;
		
	}
	
	public TestDocs()
	{
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getAssignedTime() {
		return assignedTime;
	}
	public void setAssignedTime(String assignedTime) {
		this.assignedTime = assignedTime;
	}
	public int getNumStudentsStart() {
		return numStudentsStart;
	}
	public void setNumStudentsStart(int numStudentsStart) {
		this.numStudentsStart = numStudentsStart;
	}
	public int getNumStudentsNotFinishInTime() {
		return numStudentsNotFinishInTime;
	}
	public void setNumStudentsNotFinishInTime(int numStudentsNotFinishInTime) {
		this.numStudentsNotFinishInTime = numStudentsNotFinishInTime;
	}
	public int getNumStudentsFinishInTime() {
		return numStudentsFinishInTime;
	}
	public void setNumStudentsFinishInTime(int numStudentsFinishInTime) {
		this.numStudentsFinishInTime = numStudentsFinishInTime;
	}
	
	public void setAverage(double average)
	{
		this.average = average;
	}
	
	public double getAverage()
	{
		return this.average;
	}
	
	public void setMedian(double median)
	{
		this.median = median;
	}
	
	public double getMedian()
	{
		return this.median;
	}
	
	public void setDistribution(ArrayList<Integer> distribution)
	{
		this.distribution = distribution;
	}
	
	public ArrayList<Integer> getDistribution()
	{
		return this.distribution;
	}
	
	
	
	@Override
	public String toString() {
		return "TestDocs [id=" + id + ", year=" + year + ", semester=" + 
				semester + ", assignedTime=" + assignedTime
				+ ", numStudentsStart=" + numStudentsStart + 
				", numStudentsNotFinishInTime=" 
				+ numStudentsNotFinishInTime + 
				", numStudentsFinishInTime=" +
				numStudentsFinishInTime + "]";
	}
}
