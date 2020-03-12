import java.util.ArrayList;
import java.util.HashMap;

public class Division {
	private ArrayList<Member> members = new ArrayList<Member>();
	private ArrayList<team> teamOfMembers = new ArrayList<team>();
	private String divisionID;
	private int teamSize, teamNum;

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

	public Division(ArrayList<team> teamOfMembers, int teamSize, int teamNum) {
		this.teamOfMembers = teamOfMembers;
		this.teamSize = teamSize;
		this.teamNum = teamNum;
	}

	public Division(ArrayList<team> teamOfMembers, String divisionID, int teamSize, int teamNum) {
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

	public void groupBySimularity(){

	}
}
