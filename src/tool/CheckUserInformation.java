package tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckUserInformation {
	//检查输入的用户信息，用户名、密码、确认密码为必须，邮箱、手机号码可以没有，没有或者邮箱、手机号码为错误格式，则都设为null
	public int check(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		int error=0;/*用error来表示错误信息，-1为必填信息中出现错误，0为全部正确，1为邮箱错误、将邮箱设置为null,2为手机号码错误、将手机号码设置为null,
		       3为手机跟邮箱均要设置为null*/
		if(null==request.getParameter("userName")||null==request.getParameter("password")||null==request.getParameter("repassword")){
			System.out.println("输入注册信息不全面");
			error=-1;
		}
		else{
			String userName=request.getParameter("userName");
			String password=request.getParameter("password");
			String repassword=request.getParameter("repassword");
			String email=request.getParameter("email");
			String phone=request.getParameter("phone");
			/*判断userName、password、repassword、email是否满足条件，
			 * userName的长度为1~20个字符，password至少长度为6，repassword与password相同
			 * email格式正确			
			*/		
			if(userName.length()<=0){
				System.out.println("用户名不能为空");
				error=-1;
			}
			
			else if(password.length()<6){
				System.out.println("密码"+password+"长度不能小于6");
				return error=-1;
			}
			else if(!password.equals(repassword)){
				System.out.println("密码"+password+"与确认密码"+repassword+"不一致");
				return error=-1;
			}
		
			else{
				//判断邮箱是否正确
				if(checkEmail(email)){//邮箱正确则看电话号码是否正确
					if(!checkPhone(phone)){//邮箱对电话错为2
						error=2;
					}
				}
				else{//邮箱错对
					if(checkPhone(phone)){//邮箱错电话对为1
						error=1;
					}
					else{
						error=3;
					}
				}
			}				
		}
		return error;
	}
	//检查邮箱信息，邮箱中必须有@、“.”、@在“.”前面且两者不能相邻、最后四位为“.com”
	public boolean checkEmail(String email){
		if (email.indexOf("@") <= 0 || email.indexOf(".") < 0
				|| email.indexOf(".") - email.indexOf("@") <= 1
				|| !(".com").equals(email.substring(email.length() - 4))){
			return false;
		}
		else{
			return true;
		}
	}
	
	//检查电话号码信息，号码必须为11位，且每一位均为数字
	public boolean checkPhone(String num) {
		 
		if (num.length()!= 11) {			 
			return false;
		} 
		else {
			char[] nums=num.toCharArray();
			for(int i=0;i<nums.length;i++){
				if(!(nums[i]>=0&&nums[i]<=9)){
					return false;
				}
			} 
			return true;
		}		
	}
}
