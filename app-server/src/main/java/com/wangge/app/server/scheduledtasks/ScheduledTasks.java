package com.wangge.app.server.scheduledtasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//import com.tencent.xinge.Message;
//import com.tencent.xinge.XingeApp;

@Component
@EnableScheduling
public class ScheduledTasks {
  
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  //@Scheduled(cron = "*/6 * * * * *")
  @Scheduled(cron = "*/10 * 6-20  * * * ")
  public void reportCurrentTime() {
    
//    XingeApp xinge = new XingeApp(2100183465, "1bb83a34cdb8ca8b41dcd37b028139d2");
//    Message message = new Message();
//    message.setType(Message.TYPE_MESSAGE);
//    message.setTitle("title");
//    message.setContent("content");
//    message.setExpireTime(10);
//    JSONObject ret =      
//        xinge.pushAllDevice(0, message);
//    System.out.println("The time is now " + dateFormat.format(new Date())+"ret"+ret);
   // return (ret);
  }
  
 /* public static void main(String[] args) {
    ScheduledTasks st = new ScheduledTasks();
    st.reportCurrentTime();
  }*/

}
