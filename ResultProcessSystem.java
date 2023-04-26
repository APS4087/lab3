import java.util.ArrayList;

import java.util.*;
import java.io.*;

public class ResultProcessSystem {

	//Constant for file name - do not change.
	private final static String STUDENTS_DATA_FILE_NAME = "students.csv";
	private final static String UNITS_RESULT_DATA_FILE_NAME = "units_result.csv";
	private final static String RESULTS_RELEASE__OUTPUT_FILENAME = "results_release.csv";
	private final static String UNMATCH_UNITS_RESULT_FILENAME = "unmatch_units_result.csv";

	//One ArrayList to store both Coursework and Research Student and
	//one Arraylist to store both Unit
	//make use of Polymorphism!
	private static ArrayList<Student> students;
	private static ArrayList<Unit> units;

	public static void run() {
		students = new ArrayList<Student>();
		units = new ArrayList<Unit>();
		readInStudentsDataFromCSVFile();
		readInStudentsUnitResultFromCSVFile();
		matchUnitToStudent();
		sortStudentsArrayListByStudentID();
		printStudentArrayListToResultReleaseCSV();
		printUnitsWithNoStudentsMatchToCSV();
	}

	private static void readInStudentsDataFromCSVFile() {

		try {
			Scanner file = new Scanner(new File(STUDENTS_DATA_FILE_NAME ));
			while(file.hasNextLine()) {
				//TODO: convert each record to a student object
				//add student object to ArrayList students

				String line = file.nextLine().trim();
				if(line.equals("")){continue;}

				String[] data = line.split(",");
				String studentType = data[0].trim();
				String firstName = data[1].trim();
				String lastName = data[2].trim();
				String studentID = data[3].trim();

				if(studentType.equalsIgnoreCase("C")) {
					Student_Coursework cStu = new Student_Coursework(firstName,lastName,studentID);
					students.add(cStu);
				}else if(studentType.equalsIgnoreCase("R")){
					Student_Research rStu = new Student_Research(firstName,lastName,studentID);
					students.add(rStu);
				}else{
					System.out.println("error");
				}
				file.nextLine();
			}

		}catch(FileNotFoundException ex) {
			System.out.println("Student data file not found");
		}
	}

	private static void readInStudentsUnitResultFromCSVFile() {
		try {
			Scanner file = new Scanner(new File(UNITS_RESULT_DATA_FILE_NAME));
			while(file.hasNextLine()) {
				//TODO: convert each record to either a Unit_Coursework
				//or Unit_Research object and add it to ArrayList units.
				String line = file.nextLine().trim();
				if(line.equals("")){continue;}

				String[] data = line.split(",");
				String studentID = data[0].trim();
				if(studentID.charAt(0)=='C') {
					// Coursework student
					String unitCode = data[1].trim();
					double ass1Score = Double.parseDouble(data[2].trim());
					double ass2Score = Double.parseDouble(data[3].trim());
					double examScore = Double.parseDouble(data[4].trim());

					Unit_Coursework unit_CW = new Unit_Coursework(studentID,unitCode,ass1Score,ass2Score,examScore);
					// Adding coursework unit to unit arraylist
					units.add(unit_CW);
				}else if(studentID.charAt(0)=='R'){
					// Research student
					double proposalScore = Double.parseDouble(data[1].trim());
					double finalDissertationScore = Double.parseDouble(data[2].trim());
					Unit_Research unitResearch = new Unit_Research(studentID,proposalScore,finalDissertationScore);
					// Adding research work to unit arraylist
					units.add(unitResearch);
				}else{
					System.out.println("error");
				}

				file.nextLine();
			}

		}catch(FileNotFoundException ex) {
			System.out.println(UNITS_RESULT_DATA_FILE_NAME+" file not found");
		}
	}

	private static void matchUnitToStudent() {
		//TODO: look for each student object unit results in units ArrayList
		//assign the unit into the student object
		//remove the assigned unit from units ArrayList
		for (Student stu : students){
			for (Unit unit : units) {

				if (unit.getStudentID().equals(stu.getId())) {
					// Using instanceof to condition check
					if (stu instanceof Student_Coursework && unit instanceof Unit_Coursework) {
						stu.setUnit(unit);
						units.remove(unit);
						break;
					} else if (stu instanceof Student_Research && unit instanceof Unit_Research) {
						stu.setUnit(unit);
						units.remove(unit);
						break;
					}
				}
			}
		}
	}

	private static void sortStudentsArrayListByStudentID() {
		//TODO: sort the ArrayList students by Student ID
		// Doing selection sort
		for(int i = 0;i< students.size()-1;i++){
			for(int j = i+1; j<students.size();j++){
				if(students.get(i).getId().compareTo(students.get(j).getId())>0){
					Student temp = students.get(i);
					students.set(i, students.get(j));
					students.set(j,temp);
				}
			}
		}
	}

	private static void printStudentArrayListToResultReleaseCSV() {

		try {
			PrintWriter pw = new PrintWriter(RESULTS_RELEASE__OUTPUT_FILENAME);
			//TODO: print result_release.csv
			pw.println("StudentID,Result Release Status");

			for (Student stu : students){
				String resultReleaseStatus = stu.getFinalGrade();

				if(resultReleaseStatus.equals("NIL")){
					if(stu instanceof Student_Coursework){
						resultReleaseStatus = "Coursework incomplete";
					} else if (stu instanceof Student_Research) {
						resultReleaseStatus = "Research incomplete";
					}else {
						resultReleaseStatus = "No unit attempt";
					}
				}
				pw.println(stu.getId()+","+resultReleaseStatus);

			}
			pw.close();

		}catch(FileNotFoundException ex) {
			System.out.println("Unable to open "+RESULTS_RELEASE__OUTPUT_FILENAME);
		}
	}

	private static void printUnitsWithNoStudentsMatchToCSV() {
		try {
			PrintWriter pw = new PrintWriter(UNMATCH_UNITS_RESULT_FILENAME);
			//TODO: print unmatch_units_result.csv
			pw.close();

		}catch(FileNotFoundException ex) {
			System.out.println("Unable to open "+UNMATCH_UNITS_RESULT_FILENAME);
		}
	}

	public static String getStudentsDataFileName() {
		return STUDENTS_DATA_FILE_NAME;
	}

	public static String getUnitsResultDataFileName() {
		return UNITS_RESULT_DATA_FILE_NAME;
	}

	public static String getResultsReleaseOutputFilename() {
		return RESULTS_RELEASE__OUTPUT_FILENAME;
	}

	public static String getUnmatchUnitsResultFilename() {
		return UNMATCH_UNITS_RESULT_FILENAME;
	}

	public static void main(String[] args) {
		run();
	}
}


