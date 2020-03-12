import java.io.File;
import java.util.Scanner;

public class driver {
	public static void main(){
		Scanner scn = new Scanner(System.in);
//		Division division = new Division("001");
//		division.addMember(new Member("Joey", ""));
		float x =Member.euclideanDistance(new Member("Joe", "Cott", "001241", 30f, 95f,70f, 10f, 10f),new Member("Tim", "Dan", "031211", 30f, 95f,70f, 10f, 10f));
		System.out.println(x);
	}

	private static void fillFromFile(){

	}
}
