package tool;

public class Check {
	public boolean checkFileType(String fileType){//检查上传文件类型
		String[] type1 = { "JPG", "jpg", "gif", "bmp", "BMP" }; //规定类型
		int searchType = java.util.Arrays.binarySearch(type1, fileType); 
		if(searchType==-1){
			return false;
		}
		return true;
	}
}
