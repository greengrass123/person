package allClasses;

public class AdType {
	private int adTypeId;
	private String adTypeName;
	public AdType() {
		super(); 
	}
	public AdType(int adTypeId, String adTypeName) {	 
		this.adTypeId = adTypeId;
		this.adTypeName = adTypeName;
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
	
}
