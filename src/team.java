import java.util.ArrayList;

public class team {
	private ArrayList<Member> teamOfMembers = new ArrayList<Member>();
	private String teamID, divisionID;
	private int size;

	public team(String teamID, String divisionID) {
		this.teamID = teamID;
		this.divisionID = divisionID;
	}

	public team(String teamID) {
		this.teamID = teamID;
	}

	public void setTeamOfMembers(ArrayList<Member> teamOfMembers) {
		this.teamOfMembers = teamOfMembers;
		this.size = teamOfMembers.size();
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	public void setDivisionID(String divisionID) {
		this.divisionID = divisionID;
	}
	;

	public ArrayList<Member> getTeamOfMembers() {
		return teamOfMembers;
	}

	public String getTeamID() {
		return teamID;
	}

	public String getDivisionID() {
		return divisionID;
	}

	public int getSize() {
		return size;
	}

	public void addMember(Member member){
		this.teamOfMembers.add(member);
		this.size++;
	}

	public void clearAllMembers(){
		this.teamOfMembers = new ArrayList<Member>();
	}

	public void addMembers(ArrayList<Member> members){
		this.teamOfMembers.addAll(members);
	}

	public void removeLast(){
		this.teamOfMembers.remove(--this.size);
	}

	public void remove(int index){
		this.teamOfMembers.remove(index);
		size--;
	}

	public boolean remove(String memberId){
		for(int x = 0; x < size; x++) {
			if (this.teamOfMembers.get(x).getMemberID().equals(memberId)){
				this.teamOfMembers.remove(x);
				return true;
			}
		}
		return false;
	}

	public boolean remove(String firstname, String middlename, String lastname){
		for(int x = 0; x < size; x++) {
			if (this.teamOfMembers.get(x).getFullname().equals(String.format("%s %s %s", firstname, middlename, lastname))){
				this.teamOfMembers.remove(x);
				return true;
			}
		}
		return false;
	}
	public Member findMemberById(String memeberId){
		for (int x = 0; x < size; x++){
			if (teamOfMembers.get(x).getMemberID().equals(memeberId)) return teamOfMembers.get(x);
		}
		return new Member();
	}

}
