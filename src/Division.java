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
	private int teamSize, teamNum, extraMemberTeams;

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

	public void group(boolean Simularity, boolean teamSize){
		// if Simularity = true then group by Simularity otherwise Difference
		// if teamSize = true then grouping based on having a team size speifed, otherwise it is based on the number of teams
		if (teamSize) {
			teamNum = (int) Math.ceil(((double) members.size()) / this.teamSize);
		} else {
			// checks if any teams need addtiaonal members for all members to be included
			extraMemberTeams = (int) Math.ceil((((float) members.size()) / teamNum) - (members.size() / teamNum) * teamNum);
			this.teamSize = members.size() / teamNum;
		}
		if (Simularity){
			groupBySimularity();
		} else {
			groupByDifference();
		}
	}

	private void groupBySimularity() {
		teamOfMembers = new ArrayList<Team>();
		SortByCenterDistance(); // reorder member list to allow for relative comparison
		ArrayList<Member> unallocatedMembers = (ArrayList<Member>) members.clone(); // create modifiable member list
		int extraMembersUsed = 0;
		int extraMember = 1;
		for (int x = 0; x < teamNum; x++){
			// checks if this team should be given an extra member
			if (extraMemberTeams<=extraMembersUsed)
				extraMember = 0;

			Team tempTeam = new Team(String.valueOf(x)); // creates a new team to fill, ID is 0-n
			tempTeam.addMember(unallocatedMembers.get(0)); // adds the first member, which is the furthest unused point from the center, this prevents the miss allegation of members
			unallocatedMembers.remove(0); // removes the used member from the valid member list
			for (int y = 0; y < teamSize-1+extraMember; y++){
				if(unallocatedMembers.size()<=0) break; // checks if there are more members to add
				int index = 0; // stores the index that member m is stored | lowers run time because no lookup algorithm
				Member m = unallocatedMembers.get(0); // stores the member that will be added to a group
				for (int j =1; j < unallocatedMembers.size(); j++){
					// sort thoughts the valid member list in order to find the member that is closes to the current team layout.
					// averageEuclideanDistance returns the average euclidean distance between the member m and all members stored in the team
					if (averageEuclideanDistance(m, tempTeam) > averageEuclideanDistance(unallocatedMembers.get(j), tempTeam)){
						// resets m and index to reflect a member that fits the team more
						m = unallocatedMembers.get(j);
						index = j;
					}
				}
				tempTeam.addMember(m);
				unallocatedMembers.remove(index); // member is removed from the unallocatedMembers members for the sake of efficiency
			}
			// determines if extraMember was used and if so documents it
			extraMembersUsed++;
			teamOfMembers.add(tempTeam); // team is added to list of teams for further usage
		}
		printTeams();
	}

	private void groupByDifference() {
		teamOfMembers = new ArrayList<Team>();
		SortByCenterDistance(); // reorder member list to allow for relative comparison
		ArrayList<Member> unallocatedMembers = (ArrayList<Member>) members.clone(); // create modifiable member list
		int extraMembersUsed = 0;
		int extraMembers = 1;
		for (int x = 0; x < teamNum; x++){
			// checks if this team should be given an extra member
			if (extraMemberTeams<=extraMembersUsed)
				extraMembers = 0;
			Team tempTeam = new Team(String.valueOf(x)); // creates a new team to fill, ID is 0-n
			// adds the first member, which is the closes unused point from the center
			// this prevents algorithm from group all outliers together and instead bases grouping on a member near the center allowing for a more equal distribution of members without addional runtime complexity
			tempTeam.addMember(unallocatedMembers.get(unallocatedMembers.size()-1));
			unallocatedMembers.remove(unallocatedMembers.size()-1); // removes the used member from the valid member list
			System.out.println("Size" + unallocatedMembers.size());
			for (int y = 0; y < teamSize-1; y++){
				if(unallocatedMembers.size()<=0) break; // checks if there are more members to add
				int index = 0; // stores the index that member m is stored | lowers run time because no lookup algorithm
				Member m = unallocatedMembers.get(0); // stores the member that will be added to a group
				for (int j =1; j < unallocatedMembers.size(); j++){
					// sort thoughts the valid member list in order to find the member that is closes to the current team layout.
					// averageEuclideanDistance returns the average euclidean distance between the member m and all members stored in the team
					if (averageEuclideanDistance(m, tempTeam) < averageEuclideanDistance(unallocatedMembers.get(j), tempTeam)) {
						// resets m and index to reflect a member that fits the team more
						m = unallocatedMembers.get(j);
						index = j;
					}
				}
				tempTeam.addMember(m);
				unallocatedMembers.remove(index); // member is removed from the unallocatedMembers members for the sake of efficiency
			}
			// determines if extraMember was used and if so documents it
			if (extraMemberTeams>=extraMembersUsed)
				extraMembersUsed++;
			teamOfMembers.add(tempTeam); // team is added to list of teams for further usage
		}
		printTeams();
	}

	private float averageEuclideanDistance(Member m, Team t){
		int dis = 0;
		for (int x = 0; x < t.getSize(); x++)
			dis += (int)(100*Math.abs(Member.euclideanDistance(t.getMember(x), m)));
		return (dis/t.getSize())/100.00f;
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
