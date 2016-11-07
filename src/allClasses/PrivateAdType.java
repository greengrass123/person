package allClasses;

public class PrivateAdType {
	private int adTypeId;
	private String adTypeName;
	private int postId;
	
	
	public PrivateAdType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PrivateAdType(int adTypeId, String adTypeName, int postId) {
		super();
		this.adTypeId = adTypeId;
		this.adTypeName = adTypeName;
		this.postId = postId;
	}
	public int getAdTypeId() {
		return adTypeId;
	}
	public void setAdTypeId(int adTypeId) {
		this.adTypeId = adTypeId;
	}
	public String getAdTypeName() {
		return adTypeName;
	}
	public void setAdTypeName(String adTypeName) {
		this.adTypeName = adTypeName;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	
}
