import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Division {
	private ArrayList<Member> members = new ArrayList<Member>();
	private ArrayList<Team> teamOfMembers = new ArrayList<Team>();
	private String divisionID;
	private int teamSize, teamNum;

	public Division(){

	}

	public Division(String divisionID){
		this.divisionID = divisionID;
	}

	public Division(String divisionID, int teamSize, int teamNum) {
		this.divisionID = divisionID;
		this.teamSize = teamSize;
		this.teamNum = teamNum;
	}

	public Division(int teamSize, int teamNum) {
		this.teamSize = teamSize;
		this.teamNum = teamNum;
	}

	public Division(ArrayList<Team> teamOfMembers, int teamSize, int teamNum) {
		this.teamOfMembers = teamOfMembers;
		this.teamSize = teamSize;
		this.teamNum = teamNum;
	}

	public Division(ArrayList<Team> teamOfMembers, String divisionID, int teamSize, int teamNum) {
		this.teamOfMembers = teamOfMembers;
		this.divisionID = divisionID;
		this.teamSize = teamSize;
		this.teamNum = teamNum;
	}

	public String getDivisionID() {
		return divisionID;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public int getTeamNum() {
		return teamNum;
	}

	public void setDivisionID(String divisionID) {
		this.divisionID = divisionID;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}

	public HashMap<String, Object> member(String memberId){
		for(int x = 0; x < teamNum; x++){
			Member tempMember = teamOfMembers.get(x).findMemberById(memberId);
			if(tempMember.isValidMember()) return tempMember.getMemberAsHashMap();
		}
		return new HashMap<String, Object>();
	}

	public void addMember(Member member){
		this.members.add(member);
	}

	public void addMember(ArrayList<Member> member){
		this.members.addAll(member);
	}

	public void groupBySimularity(int teamSize){

	}

	public void printTeams(){
		for (int x = 0; x < teamOfMembers.size(); x++){
			System.out.println("\n____________________________________\n");
			for (int y = 0; y < teamOfMembers.get(x).getSize();y++){
				System.out.println(teamOfMembers.get(x).getMember(y).getFullname() + "\t" + teamOfMembers.get(x).getMember(y).getEuclideanDistanceCenter() + "\t" + Member.euclideanDistance(teamOfMembers.get(x).getMember(y), teamOfMembers.get(x).getMember(0)));
			}
			System.out.println("Average Euclidean Distance: " + teamOfMembers.get(x).getAverageEuclideanDistance());
		}
	}

	public void groupBySimularity() {
		SortByCenterDistance();
		ArrayList<Member> tempMembers = (ArrayList<Member>) members.clone();
		for (int x = 0; x < teamNum; x++){
			Team tempTeam = new Team(String.valueOf(x));
			tempTeam.addMember(tempMembers.get(0));
			tempMembers.remove(0);
			System.out.println("Size" + tempMembers.size());
			for (int y = 0; y < teamSize-1; y++){
				int index = 0;
				Member m = tempMembers.get(0);
				for (int j =1; j < tempMembers.size(); j++){
					if (Math.abs(Member.euclideanDistance(tempTeam.getTeamOfMembers().get(0), m)) > Math.abs(Member.euclideanDistance(tempTeam.getTeamOfMembers().get(0), tempMembers.get(j)))) {
						m = tempMembers.get(j);
						index = j;
					}
				}
				tempTeam.addMember(m);
				tempMembers.remove(index);
			}
			teamOfMembers.add(tempTeam);
		}
		printTeams();
		//TODO How the fuck do i do this
	}

	private void SortByCenterDistance(){
		for (int x = 0; x < members.size(); x++) {
			System.out.println(String.valueOf(x) + " " + members.get(x).getFullname() + "\t" + members.get(x).getEuclideanDistanceCenter());
		}
		System.out.println("----------------------------------");
		if (members.size()>0) {
			ArrayList<Member> tempMembers = (ArrayList<Member>) members.clone();
			members = new ArrayList<Member>();
			int n = tempMembers.size();
			for (int x = 0; x < n; x++) {
				Member m = tempMembers.get(0);
				int im = 0;
				for (int y = 0; y < tempMembers.size(); y++) {
					if (Math.abs(tempMembers.get(y).getEuclideanDistanceCenter()) > Math.abs(m.getEuclideanDistanceCenter())) {
						m = tempMembers.get(y);
						im = y;
					}
				}
				members.add(m);
				tempMembers.remove(im);
			}
			for (int x = 0; x < members.size(); x++) {
				System.out.println(members.get(x).getFullname() + "\t" + members.get(x).getEuclideanDistanceCenter());
			}
		}
	}

	private float[][] getCompareBox(){
		float[][] compareBox = new float[members.size()][members.size()];
		for (int x = 0; x < members.size(); x++) {
			for (int y = 0; y < members.size(); y++){
				compareBox[y][x] = Member.euclideanDistance(members.get(x), members.get(y));
			}
		}
		return compareBox;
	}

	private void print2dArray(float[][] array){
		for (int x = 0; x < members.size(); x++) {
			for (int y = 0; y < members.size(); y++) {
				System.out.print(array[y][x]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
	}
	public void addMembersFromCSV(String path){
		String line = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				String[] m = line.split(",");
				members.add(new Member(m[0], m[1], m[2], Float.valueOf(m[3]), Float.valueOf(m[4]), Float.valueOf(m[5]), Float.valueOf(m[6]), Float.valueOf(m[7])));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void printListOfNames(){
		for (int x = 0; x < members.size(); x++) {
			System.out.println(members.get(x).getFirstName() + "\t" + members.get(x).getLastName());
		}
	}
}
