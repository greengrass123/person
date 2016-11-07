package tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurrentTime {
	public static   String currentTime() {//将当前时间按指定格式返回
		Date date = new Date();
		//DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}
}
