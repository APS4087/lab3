public class Student_Research extends Student{

    private Unit_Research unit;
    
    public Student_Research(String firstName, String lastName, String id) {
        super(firstName, lastName, id, "R");
    }
    
    public String getFinalGrade(){
    	//TODO: return the student final grade
    	String finalGrade = unit.getFinalGrade();
    	return finalGrade;
    }

    public void setUnit(Unit unit) {
        this.unit = (Unit_Research)unit;
    }
    
    public String getCSVString() {
    	return this.getId()+","+this.getFinalGrade();
    }
}
