package allClasses;

public class Pic {
	private int picId;
	private String picAddr;
	private int width;
	private int height;
	private int checked;
	private int adId;
	
	public Pic() {
		super(); 
	}
	public Pic(int picId, String picAddr, int width, int height, int checked,
			int adId) {
		super();
		this.picId = picId;
		this.picAddr = picAddr;
		this.width = width;
		this.height = height;
		this.checked = checked;
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

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}
	
}
