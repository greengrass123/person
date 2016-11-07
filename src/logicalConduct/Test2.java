package logicalConduct;

public class Test2 {
	public static void main(String[] args) {
		String num="a113";	
		char[] nums=num.toCharArray();
		for(int i=0;i<nums.length;i++){
			if(!(nums[i]>=0&&nums[i]<=9)){
			 	System.out.println("不是数字");
			}

		}  
	}
}