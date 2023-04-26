public class Unit_Coursework extends Unit{

    private String unitCode;
    private double a1;
    private double a2;
    private double exam;

    public Unit_Coursework(String studentID, String unitCode, double a1, double a2, double exam) {
        super(studentID,"CW");
        this.unitCode = unitCode;
        this.a1 = a1;
        this.a2 = a2;
        this.exam = exam;
    }
    
    public double getOverallMarks(){
        //TODO: calculate coursework overall marks;
        return a1+a2+exam;
    }
    
    public String getFinalGrade(){
        return Helper.computeCWGrade(getOverallMarks());
    }
    
    public String getCSVString() {
    	//TODO: return a string in the following format
    	//studentID,unitCode,a1,a2,exam
    	return getStudentID() + "," + unitCode + "," + a1 + "," + a2 + "," + exam;
    }
}
