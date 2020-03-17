import java.util.HashMap;

public class Member {
	private String firstName, lastName, middleName, memberID, teamID, divisionID, role;
	private int euclideanDistanceCenter = -1, euclideanDistanceOrigin = -1, openness = -1, neuroticism = -1, conscientiousness = -1, extraversion = -1, agreeableness = -1;

	public static float euclideanDistance(Member a, Member b){
		if(a.isValidTraitData() && b.isValidTraitData()) return (float) Math.sqrt(Math.pow(a.getAgreeableness()-b.getAgreeableness(), 2)+Math.pow(a.getOpenness()-b.getOpenness(), 2)+Math.pow(a.getExtraversion()-b.getExtraversion(), 2)+Math.pow(a.getNeuroticism()-b.getNeuroticism(), 2)+Math.pow(a.getConscientiousness()-b.getConscientiousness(), 2));
		return -100000000000000.0f;
	}

	public Member(String firstName, String lastName, String memberID, float openness, float neuroticism, float conscientiousness, float extraversion, float agreeableness) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberID = memberID;
		this.openness = (int) openness*100;
		this.neuroticism = (int) neuroticism*100;
		this.conscientiousness = (int) conscientiousness*100;
		this.extraversion = (int) extraversion*100;
		this.agreeableness = (int) agreeableness*100;
		euclideanDistanceOrigin();
		euclideanDistanceCenter();
	}

	public Member(String firstName, String lastName, String memberID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberID = memberID;
	}

	public Member(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Member() {
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	public void setDivisionID(String divisionID) {
		this.divisionID = divisionID;
	}

	public void setOpenness(float openness) {
		this.openness = (int) openness*100;
	}

	public void setNeuroticism(float neuroticism) {
		this.neuroticism = (int) neuroticism*100;
		euclideanDistanceOrigin();
		euclideanDistanceCenter();
	}

	public void setConscientiousness(float conscientiousness) {
		this.conscientiousness = (int) conscientiousness*100;
		euclideanDistanceOrigin();
		euclideanDistanceCenter();
	}

	public void setExtraversion(float extraversion) {
		this.extraversion = (int) extraversion*100;
		euclideanDistanceOrigin();
		euclideanDistanceCenter();
	}

	public void setAgreeableness(float agreeableness) {
		this.agreeableness = (int) agreeableness*100;
		euclideanDistanceOrigin();
		euclideanDistanceCenter();
	}

	public void setAllTraits(float openness, float neuroticism, float conscientiousness, float extraversion, float agreeableness){
		this.openness = (int) openness*100;
		this.agreeableness = (int) agreeableness*100;
		this.conscientiousness = (int) conscientiousness*100;
		this.extraversion = (int) extraversion*100;
		this.neuroticism = (int) neuroticism*100;
		euclideanDistanceOrigin();
		euclideanDistanceCenter();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getMemberID() {
		return memberID;
	}

	public String getTeamID() {
		return teamID;
	}

	public String getDivisionID() {
		return divisionID;
	}

	public float getOpenness() {
		return openness/100f;
	}

	public float getNeuroticism() {
		return neuroticism/100f;
	}

	public float getConscientiousness() {
		return conscientiousness/100f;
	}

	public float getExtraversion() {
		return extraversion/100f;
	}

	public float getAgreeableness() {
		return agreeableness/100f;
	}

	public String getFullname(){
		return String.format("%s %s %s", this.firstName, this.middleName, this.lastName);
	}

	public HashMap<String, Object> getMemberAsHashMap(){
		HashMap<String, Object> tempMap = new HashMap<>();
		tempMap.put("firstName", this.firstName);
		tempMap.put("lastName", this.lastName);
		tempMap.put("middleName", this.middleName);
		tempMap.put("memberID", this.memberID);
		tempMap.put("teamID", this.teamID);
		tempMap.put("divisionID", this.divisionID);
		tempMap.put("openness", Float.valueOf(this.openness/100f));
		tempMap.put("neuroticism", Float.valueOf(this.neuroticism/100f));
		tempMap.put("conscientiousness", Float.valueOf(this.conscientiousness/100f));
		tempMap.put("extraversion", Float.valueOf(this.extraversion/100f));
		tempMap.put("agreeableness", Float.valueOf(this.agreeableness/100f));
		tempMap.put("euclideanDistanceOrigin", Float.valueOf(this.euclideanDistanceOrigin/100f));
		tempMap.put("euclideanDistanceCenter", Float.valueOf(this.euclideanDistanceCenter/100f));
		return tempMap;
	}

	public boolean isValidMember(){
		if (firstName != null || lastName != null || middleName != null || memberID != null || teamID != null) return true;
		return false;
	}
	public boolean isValidTraitData() {
		if (openness != -1 && neuroticism != -1 && conscientiousness != -1 && extraversion != -1 && agreeableness != -1) return true;
		return false;
	}

	private int euclideanDistanceOrigin(){
		if(openness != -1 && neuroticism != -1 && conscientiousness != -1 && extraversion != -1 && agreeableness != -1){
			euclideanDistanceOrigin = (int) (100 * Math.sqrt(Math.pow(getAgreeableness(), 2)+Math.pow(getOpenness(), 2)+Math.pow(getExtraversion(), 2)+Math.pow(getNeuroticism(), 2)+Math.pow(getConscientiousness(), 2)));
			return euclideanDistanceOrigin;
		}
		return -1;
	}

	public float getEuclideanDistanceOrigin(){
		return euclideanDistanceOrigin/100f;
	}

	private int euclideanDistanceCenter(){
		if(openness != -1 && neuroticism != -1 && conscientiousness != -1 && extraversion != -1 && agreeableness != -1){
			euclideanDistanceCenter = (int) (100 * Math.sqrt(Math.pow(50 - getAgreeableness(), 2)+Math.pow(50 - getOpenness(), 2)+Math.pow(50 - getExtraversion(), 2)+Math.pow(50 - getNeuroticism(), 2)+Math.pow(50 - getConscientiousness(), 2)));
			return euclideanDistanceCenter;
		}
		return -1;
	}

	public float getEuclideanDistanceCenter(){
		return euclideanDistanceCenter/100f;
	}

}
