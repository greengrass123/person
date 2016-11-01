package logicalConduct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import jdbc.ConnectDB;
import jdbc.OperationData;

import tool.GetCurrentTime;

public class TimeProcessThread extends Thread {

    Timer timer = new Timer();

    private volatile static TimeProcessThread singleton;  
    private TimeProcessThread (){}  
    public static TimeProcessThread getTimeProcessThread() {  
    if (singleton == null) {  
        synchronized (TimeProcessThread.class) {  
        if (singleton == null) {  
            singleton = new TimeProcessThread();  
        }  
        }  
    }  
    return singleton;  
    }  
    @Override
    public void run() {
        OperationData operationData = new OperationData();
        final Map<Integer, Double> timeMap = operationData.getSortUploadTime();
        Iterator keyValue = timeMap.entrySet().iterator();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {// 获取当前系统时间
                AdminLogic adminLogic=new AdminLogic();
                String nowTime = new GetCurrentTime().currentTime();
                double currentDouble = TransformDouble(nowTime);
                List<Integer> adIdList=new ArrayList<Integer>();             
                Iterator keyValue = timeMap.entrySet().iterator();
                for (int i = 0; i < timeMap.size(); i++) {
                    Map.Entry entry = (Map.Entry) keyValue.next();
                    int key = (Integer) entry.getKey();
                    double value = (Double) entry.getValue();
                    if (currentDouble - value >= 168
                            && currentDouble - value < 8640) {// 控制在一周到一年的范围内
                        adIdList.add(key);
                        System.out.println("add key"+key);
                    }
                    System.out.println("run" + key + "   " + value);
                }            
             adminLogic.delBatch_pic_ad(adIdList);
            }

        }, 2000, 100000);

    }

    @Override
    public void interrupt() {
        timer.cancel();
        super.interrupt();
    }

    public double TransformDouble(String times) {
        double year, month, day, hour;

        year = (Double.parseDouble(times.substring(0, 4)) - 2014) * 8760;
        month = Double.parseDouble(times.substring(4, 6)) * 720;
        day = Double.parseDouble(times.substring(6, 8)) * 24;
        hour = Double.parseDouble(times.substring(8, 10));

        return year + month + day + hour;
    }
}
