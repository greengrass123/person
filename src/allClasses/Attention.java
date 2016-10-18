package allClasses;

public class Attention {
	private int attentionId;
	private int userId;
	private int postId;

	public Attention() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Attention(int userId, int postId) {
		super();
		this.userId = userId;
		this.postId = postId;
	}
	public Attention(int attentionId, int userId, int postId) {
		super();
		this.attentionId = attentionId;
		this.userId = userId;
		this.postId = postId;
	}
	public int getAttentionId() {
		return attentionId;
	}
	public void setAttentionId(int attentionId) {
		this.attentionId = attentionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
}
