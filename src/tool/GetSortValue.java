package tool;

import configurations.Configuration;


public class GetSortValue {
	/*
	 * 获取排序值，排序值结构为：前两位根据缴费多少决定，后面为上传时间，
	 * 前两位a/b代表的意义：a表示是否过了指定缴费有效期(0/1表示)，b代表缴费享受优待有效期
	 * money为这个广告所缴费用，目前档次：10、50、100，currentTime为当前时间
	 * 
	 */
	public long sortValue(int money,String time){
		StringBuffer sortValue=new StringBuffer();	
		Long upLoadTime=Long.parseLong(time);	
		int a=0,b=0;		
		int[] moneys=Configuration.MONEY;//获取交钱档次
		if(money==moneys[0]){ 
			a=1;
			b=1;
		}
		else if(money==moneys[1]){
			a=1;
			b=2;
		}
		else if(money==moneys[2]){
			a=1;
			b=3;
		}			
		else{//如果不交钱
			a=0;
			b=0;
		}				
		sortValue.append(a);
		sortValue.append(b);
		sortValue.append(upLoadTime);	
	    String sort=sortValue.toString();	    
	    return Long.parseLong(sort);
	}
	
	//更新指定的sortValue
	public String updateSortValue(long sortValue){
		StringBuffer sort=new StringBuffer(sortValue+"");	
		Long currentTime=Long.parseLong(new GetCurrentTime().currentTime());//获取当前时间	
		//获取前两位的值
		int a=sort.charAt(0);
		int b=sort.charAt(1);
		long upLoadTime=new Long(sort.substring(2));//获取上传时间
		System.out.println(upLoadTime);
		if(a!=0&&(currentTime-upLoadTime)>=b*24*60*60)//如果还处于有效期的广告过期时则将其设置为过期
		{
			a=0;		
		}
		sort.replace(0,1,a+"");//将a的值改变
		return sort.toString();
	}
}
