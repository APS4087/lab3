//This class is used for testing, do not change
public class ResultRelease {
	private String studentID;
	private String result;

	public ResultRelease(String studentID, String result) {
		this.studentID = studentID;
		this.result = result;
	}

	public boolean equals(String studentID, String result) {
		return this.studentID.equals(studentID) && this.result.equals(result);
	}
	
	public boolean equals(String studentID) {
		return this.studentID.equals(studentID);
	}

	public String getStudentID() {
		return studentID;
	}

	public boolean studentIDMoreThan(ResultRelease result) {
		int n = this.studentID.compareTo(result.getStudentID());
		if (n > 0) {
			return true;
		} else {
			return false;
		}
	}
}
