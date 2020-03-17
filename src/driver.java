import java.io.File;
import java.util.Scanner;

public class driver {
	public static void main(String[] args){
		Scanner scn = new Scanner(System.in);
		Division div = new Division();
		Division division = new Division("001", 5, 10);
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		division.addMembersFromCSV("Members.csv");
//		division.printListOfNames();
//		division.groupBySimularity();
//		float x = Member.euclideanDistance(new Member("Joe", "Cott", "001241", 30f, 95f,70f, 10f, 10f),new Member("Tim", "Dan", "031211", 36f, 15f,50f, 70f, 40f));
//		System.out.println(x);
		division.groupBySimularity();
	}

	private static void fillFromFile(){

	}
}