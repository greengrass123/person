package tool;

import java.util.ArrayList;
import java.util.List;

import jdbc.OperationData;

import allClasses.Ad;
import allClasses.Pic;
 

public class judgeTime {
	
	//返回满足给定时间的广告队列
	public List adjustTime(String time,List list){
		int len=list.size();
		Pic p=new Pic();
		String alltime[] =new String[len],t;
		OperationData od=new  OperationData();
		int year[] = new int[len],month[]=new int[len],day[]=new int[len];
		int y,m,d;
		List info_list1 = new ArrayList(),info_list2 = new ArrayList(),info_list3 =new ArrayList();
		
		
		t=GetCurrentTime.currentTime();	
		y=Integer.parseInt(t.substring(0,4));
		m=Integer.parseInt(t.substring(4,6));
		d=Integer.parseInt(t.substring(6,8));
	
		for(int i=0;i<len;i++)
		{
			//ad=(Ad) list.get(i);
		//	alltime[i]=(String)ad.getUpLoadTime();
			  p=(Pic) list.get(i);
			alltime[i]=od.getUpTimeByPicId(p.getPicId());
			System.out.println("uploadTime="+alltime[i]);
			 if(alltime[i]==null)
			 {
				 continue;
			 }
			year[i]=Integer.parseInt(alltime[i].substring(0, 4));
			month[i]=Integer.parseInt(alltime[i].substring(4,6));//		month[i]=Integer.parseInt(alltime[i].substring(5,7));
			day[i]=Integer.parseInt(alltime[i].substring(6,8));			
				
			if(m-month[i]==0){
				info_list2.add(p);
				if(d-day[i]<=7){
					info_list1.add(p);
				}
			}else if(m-month[i]==1){
				info_list2.add(p);
				if(30-day[i]+d<7){
					info_list1.add(p);
				}
			}
				
			
		}
		if(time.equals("近一周")){			
			return info_list1;
		}else if(time.equals("近一个月")){
			return info_list2;
		}else{
			return list;
		}		
		
	}
	
	 
	
	
}
