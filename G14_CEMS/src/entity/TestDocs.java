package entity;

public class TestDocs {
	private int id;
	private int year;
	private int semester;
	private int assignedTime;
	private int numStudentsStart;
	private int numStudentsNotFinishInTime;
	private int numStudentsFinishInTime;
	private StatisticalReport stats;
	
	public TestDocs(int id, int year, int semester, int assignedTime,
			int numStudentsStart, int numStudentsNotFinishInTime, 
			int numStudentsFinishInTime, StatisticalReport stats) {
		this.id = id;
		this.year = year;
		this.semester = semester;
		this.assignedTime = assignedTime;
		this.numStudentsStart = numStudentsStart;
		this.numStudentsNotFinishInTime = numStudentsNotFinishInTime;
		this.numStudentsFinishInTime = numStudentsFinishInTime;
		this.stats = stats;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getAssignedTime() {
		return assignedTime;
	}
	public void setAssignedTime(int assignedTime) {
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
	public StatisticalReport getStats() {
		return stats;
	}
	public void setStats(StatisticalReport stats) {
		this.stats = stats;
	}
	@Override
	public String toString() {
		return "TestDocs [id=" + id + ", year=" + year + ", semester=" + 
				semester + ", assignedTime=" + assignedTime
				+ ", numStudentsStart=" + numStudentsStart + 
				", numStudentsNotFinishInTime=" 
				+ numStudentsNotFinishInTime + 
				", numStudentsFinishInTime=" +
				numStudentsFinishInTime + ", stats="
				+ stats + "]";
	}
}
