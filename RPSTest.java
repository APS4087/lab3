import java.util.*;
import java.io.*;

public class RPSTest {

	private static ArrayList<ResultRelease> results;
	private static ArrayList<Unit> unmatchUnits;
	private static ResultProcessSystem RPS;
	
	public static void main(String[] args) {
		generateDataFiles();
		deleteRPSGenerateFiles();
		
		//execute ResultProcessSystem
		RPS.run();
		
		//read in result release data
		readResultRelease();
		
		//check if sort by student ID
		try {
			assert checkIfSortByStudentID() == true;
			System.out.println("Sort by Student ID test passed");
		}catch(AssertionError e) {
			System.out.println("Sort by Student ID test failed");
		}
		//check if student result data is correct
		try {
			assert checkResultReleaseIsCorrect() == true;
			System.out.println("Result Release test passed");
		}catch(AssertionError e) {
			System.out.println("Result Release test failed");
		}
		
		//check if unmatch unit is correct
		readUnmatchUnits();
		
		try {
			assert checkUnmatchUnitIsCorrect() == true;
			System.out.println("Unmatch units test passed");
		}catch(AssertionError e) {
			System.out.println("Unmatch units test failed");
		}
		
	}
	
	private static boolean checkUnmatchUnitIsCorrect() {
		
		//expected results
		String u1 = "C444,CSIT511,55.0,45.0,48.0";
		String u2 = "R666,55.0,45.0";
		
		String[] uDatas = {u1,u2};
		
		boolean result = true;
		for(String s : uDatas) {
			String[] data = s.split(",");
			String id = data[0];
			Unit unit = getUnmatchUnitByStudentID(id);
			if(unit==null) {
				result=false;
				break;
			}
			if(!unit.getCSVString().equals(s)) {
				result=false;
				break;
			}
		}
		return result;
	}
	
	private static Unit getUnmatchUnitByStudentID(String id) {
		Unit toReturn = null;
		for(Unit unit:unmatchUnits) {
			if(unit.equals(id)) {
				toReturn = unit;
				break;
			}
		}
		return toReturn;
	}
	
	private static void readUnmatchUnits() {
		unmatchUnits = new ArrayList<Unit>();
		try {
			Scanner file = new Scanner(new File(RPS.getUnmatchUnitsResultFilename()));
			
			while(file.hasNextLine()) {
				String line = file.nextLine();
				String[] data = line.split(",");
				String id = data[0];
				
				Unit unit = null;
				if(id.startsWith("C")) {
					String code = data[1];
					double a1 = Double.parseDouble(data[2]);
					double a2 = Double.parseDouble(data[3]);
					double exam = Double.parseDouble(data[4]);
					unit = new Unit_Coursework(id,code,a1,a2,exam);
				}else if(id.startsWith("R")) {
					double proposal = Double.parseDouble(data[1]);
					double finalDissertation = Double.parseDouble(data[2]);
					unit = new Unit_Research(id,proposal,finalDissertation);
				}
				unmatchUnits.add(unit);
			}
			
		}catch(FileNotFoundException ex) {
			System.out.println(RPS.getUnmatchUnitsResultFilename()+" unable to open");
		}
	}
	
	private static boolean checkResultReleaseIsCorrect() {
		//expected results
		String rr1 = "C111,HD";
		String rr2 = "C222,P";
		String rr3 = "C333,C";
		String rr4 = "C555,No unit attempt";
		String rr5 = "R111,F";
		String rr6 = "R222,P";
		String rr7 = "R333,P";
		String rr8 = "R444,Research incomplete";
		
		String[] rrDatas = {rr1,rr2,rr3,rr4,rr5,rr6,rr7,rr8};
		boolean result = true;
		
		for(String s : rrDatas) {
			String[] data = s.split(",");
			String id = data[0];
			String r = data[1];
			ResultRelease rr = getResultReleaseByStudentID(id);
			if(rr==null || !rr.equals(id,r)) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	
	private static ResultRelease getResultReleaseByStudentID(String id) {
		ResultRelease toReturn = null;
		for(ResultRelease result: results) {
			if(result.equals(id)) {
				toReturn = result;
				break;
			}
		}
		return toReturn;
	}
	
	private static void readResultRelease() {
		results = new ArrayList<ResultRelease>();
		try {
			Scanner file = new Scanner(new File(RPS.getResultsReleaseOutputFilename()));
			
			while(file.hasNextLine()) {
				String line = file.nextLine();
				String[] data = line.split(",");
				String studentID = data[0];
				String result = data[1];
				ResultRelease r = new ResultRelease(studentID,result);
				results.add(r);
			}
			
		}catch(FileNotFoundException ex) {
			System.out.println(RPS.getResultsReleaseOutputFilename()+" unable to open");
		}
	}
	
	private static boolean checkIfSortByStudentID() {
		boolean sorted = true;
		String[] data = {"C111","C222","C333","C555","R111","R222","R333","R444"};
		if(results.size()==0) {
			sorted = false;
		}
		for(int i=0;i<results.size();i++) {
			if(!results.get(i).equals(data[i])) {
				sorted = false;
				break;
			}
		}
		return sorted;
	}
	
	
	private static void deleteRPSGenerateFiles() {
		 File resultReleaseFile = new File(RPS.getResultsReleaseOutputFilename()); 
		 resultReleaseFile.delete();
		 
		 File unmatchUnitsResult = new File(RPS.getUnmatchUnitsResultFilename()); 
		 unmatchUnitsResult.delete();
	}
	
	private static void generateDataFiles() {
		//test data
		String d1 = "C,Peter,Jordon,C222";
		String d2 = "C,John,Smith,C111";
		String d3 = "C,Bob,Church,C555";
		String d4 = "C,David,Jackson,C333";
		String d5 = "R,Gary,Kenny,R333";
		String d6 = "R,Eric,Trump,R222";
		String d7 = "R,Bob,Johnson,R111";
		String d8 = "R,Donald,Ray,R444";
		
		String[] sDatas = {d1,d2,d3,d4,d5,d6,d7,d8};
		
		try {
			PrintWriter pw = new PrintWriter(RPS.getStudentsDataFileName());
			for(String s: sDatas) {
				pw.println(s);
			}
			pw.close();
		}catch(FileNotFoundException ex) {
			System.out.println(RPS.getStudentsDataFileName()+" unable to open");
		}
		
		//test data
		String r1 = "R222,85.0,72.0";
		String r2 = "C111,CSIT454,89.0,88.0,86.5";
		String r3 = "C333,CSIT511,75.5,72.0,58.5";
		String r4 = "R111,40.0,47.5";
		String r5 = "C222,CSIT454,76.0,56.5,65.0";
		String r6 = "R333,80.5,70.0";
		String r7 = "C444,CSIT511,55.0,45.0,48.0";
		String r8 = "R666,55.0,45.0";
		String[] rDatas = {r1,r2,r3,r4,r5,r6,r7,r8};
		
		try {
			PrintWriter pw = new PrintWriter(RPS.getUnitsResultDataFileName());
			for(String s: rDatas) {
				pw.println(s);
			}
			pw.close();
		}catch(FileNotFoundException ex) {
			System.out.println(RPS.getUnitsResultDataFileName()+" unable to open");
		}
	}
}
