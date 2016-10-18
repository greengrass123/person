package allClasses;

public class UnitType {
	private int unitTypeId;
	private String unitTypeName;
	public UnitType(){
		
	}
	public UnitType(int unitTypeId,String unitTypeName){
		this.unitTypeId=unitTypeId;
		this.unitTypeName=unitTypeName;
	}
	public int getUnitTypeId() {
		return unitTypeId;
	}
	
	public void setUnitTypeId(int unitTypeId) {
		this.unitTypeId = unitTypeId;
	}
	public String getUnitTypeName() {
		return unitTypeName;
	}
	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}
}
