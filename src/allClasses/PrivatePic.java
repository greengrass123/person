package allClasses;

public class PrivatePic {
	private int picId;
	private String picAddr;
	private int width;
	private int height;
	private int adId;

	public PrivatePic() {
		super();
	}
	public PrivatePic(int picId, String picAddr, int width, int height,
			 int adId) {
		super();
		this.picId = picId;
		this.picAddr = picAddr;
		this.width = width;
		this.height = height; 
		this.adId = adId;
	}

	public int getPicId() {
		return picId;
	}

	public void setPicId(int picId) {
		this.picId = picId;
	}

	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

}
