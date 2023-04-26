public class Helper {

	public static String computeCWGrade(double score) {
		String grade;
		//TODO: Compute grade based on the score
		if(score>=85){
			grade = "HD";

		} else if (score>=75) {
			grade = "D";
		} else if (score>=65) {
			grade = "C";
		}else if (score>=50){
			grade = "P";
		}else if(score>=0) {
			grade = "F";
		}else {
			grade = "NIL";
		}
		return grade;
	}

	public static String computeRGrade(double score) {
		String grade;
		//TODO: Compute grade based on the score
		if(score >= 50){
			grade = "P";
		}else if (score>=0){
			grade = "F";
		}else{
			grade = "NIL";
		}
		return grade;
	}
}
