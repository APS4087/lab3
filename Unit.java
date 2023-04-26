abstract public class Unit {
    
    private String unitType;
    private String studentID;

    public Unit(String studentID, String unitType) {
    	this.studentID = studentID;
        this.unitType = unitType;
    }

    public String getStudentID() {
    	return this.studentID;
    }
    
    public boolean equals(String id) {
    	return this.studentID.equals(id);
    }
    
    abstract double getOverallMarks();
    
    abstract String getFinalGrade();
   
    abstract String getCSVString();
}
