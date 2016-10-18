package allClasses;

public class Post {
	private int postId;
	private int unitId;
	private int userId;
	private String postName;
	private String createTime;
	private int visitorsOfToday;
	private int allVisitors;
	public Post(){
		
	}
	
	public Post(int postId,String postName, int unitId, int userId, 
			String createTime, int visitorsOfToday, int allVisitors) { 
		this.postId = postId;
		this.unitId = unitId;
		this.userId = userId;
		this.postName = postName;
		this.createTime = createTime;
		this.visitorsOfToday = visitorsOfToday;
		this.allVisitors = allVisitors;
	}

	public int getVisitorsOfToday() {
		return visitorsOfToday;
	}
	public void setVisitorsOfToday(int visitorsOfToday) {
		this.visitorsOfToday = visitorsOfToday;
	}
	public int getAllVisitors() {
		return allVisitors;
	}
	public void setAllVisitors(int allVisitors) {
		this.allVisitors = allVisitors;
	}
	
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
