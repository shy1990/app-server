package com.wangge.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	/** 时间格式 */  
    private static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";  
      
    /** 
     * 对比两个时间相差毫秒数
     *  
     * @param dateA 
     * @param dateB 
     * @return 
     * @throws ParseException 
     */  
    public static long compare(String dateA, String dateB) throws ParseException {  
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);  
        return Math.abs(df.parse(dateA).getTime() - df.parse(dateB).getTime());  
    }  
   
    /**
     * 对比当前时间，返回时间差
     * @param dateA
     * @param dateB
     * @return 时间差（毫秒）
     * @throws ParseException
     */
    public static long compareNowDate(String date) throws ParseException {  
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);  
        long compare = Math.abs(new Date().getTime() - df.parse(date).getTime());
        return compare;  
    }  
    
    
    /**
     * 对比当前时间，返回时间差
     * @param dateA
     * @param dateB
     * @return 时间差（毫秒）
     * @throws ParseException
     */
    public static long compareNowDate(Date date) {
    	long compare = 0;
    	try {
    		 compare = Math.abs(new Date().getTime() - date.getTime() );
		} catch (Exception e) {
			System.out.println("时间转换异常："+e.getMessage());
		}
        return compare;  
    } 
    
    /**
     * long字符串转date类型
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date longStrToDate(String str){
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
	    try {
			return sdf.parse(sdf.format(new Date(Long.parseLong(str))));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    return null;
    }
    
    /**
     * 格式化时间戳字符串
     * @param str
     * @return
     */
    public static String dateStrFormat(String str){
    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
	    return sdf.format(new Date(Long.parseLong(str))); 
    }
    
    /**
     * 把时间转换成时间格式字符串
     * @param date
     * @return
     */
    public static String dateToStr(Date date){
    	SimpleDateFormat sdf=new SimpleDateFormat(DATE_TIME_FORMAT);  
    	String str=sdf.format(date);  
    	return str;
    }
    

    /**
     * 字符串转时间
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr){
    	DateFormat fmt =new	SimpleDateFormat(DATE_TIME_FORMAT);
    	try {
			Date date = fmt.parse(dateStr);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 
      * getYesterdayDate:获取昨日时间
      * @author robert 
      * @return 
      * @since JDK 1.8
     */
    public static Date getYesterdayDate(){
      Calendar   cal   =   Calendar.getInstance();
      cal.add(Calendar.DATE,   -1);
      String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date=null;
      try {
        date = sdf.parse(yesterday);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return date;
    }
    
    /**
     * 
      * getMonthFirstDay:当月第一天
      * @author Administrator 
      * @return 
      * @since JDK 1.8
     */
    public static Date getMonthFirstDay(){
      Calendar   cal   =   Calendar.getInstance();
      cal.add(Calendar.MONTH, 0);
      cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
      String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date=null;
      try {
        date = sdf.parse(yesterday);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return date;
    }
    
    /**
     * 
      * gettoday:今天
      * @author Administrator 
      * @return 
      * @since JDK 1.8
     */
    public static Date gettoday(){
      Calendar   cal   =   Calendar.getInstance();
      cal.add(Calendar.MONTH, 0);
      cal.add(Calendar.DATE, 0);;//设置为1号,今天
      String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date=null;
      try {
        date = sdf.parse(yesterday);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return date;
    }
    
    /**
     * 
      * getNowMonth:当前月. <br/> 
      * 
      * @author Administrator 
      * @return 
      * @since JDK 1.8
     */
    public static int getNowMonth(){
      Calendar   cal   =   Calendar.getInstance();
      int month=cal.get(Calendar.MONTH)+1;
      return month;
    }
    
    /**
     * 
      * getNowMonth:当前年份
      * @author Administrator 
      * @return 
      * @since JDK 1.8
     */
    public static int getNowYear(){
      Calendar   cal   =   Calendar.getInstance();
      int year=cal.get(Calendar.YEAR);
      return year;
    }
    
    /**
     * 
      * getMonth：通过date获取月份
      * @author Administrator 
      * @return 
      * @since JDK 1.8
     */
    public static int getMonth(Date date){
      Calendar c = Calendar.getInstance();
      c.setTime(date);
     
      return  c.get(Calendar.DAY_OF_MONTH);
    }
    
    
    /**
     * 
      * getLastDayOfMonth:某个月的最后一天
      * @author Administrator 
      * @param year
      * @param month
      * @return 
      * @since JDK 1.8
     */
    public static Date getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        
        Date date=null;
        try {
          date = sdf.parse(lastDayOfMonth);
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 
      * getFisrtDayOfMonth:某个月的第一天
      * @author Administrator 
      * @param year
      * @param month
      * @return 
      * @since JDK 1.8
     */
    public static Date getFisrtDayOfMonth(int year,int month){
      Calendar cal = Calendar.getInstance();
      //设置年份
      cal.set(Calendar.YEAR,year);
      //设置月份
      cal.set(Calendar.MONTH, month-1);
      //获取某月最小天数
      int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
      //设置日历中月份的最小天数
      cal.set(Calendar.DAY_OF_MONTH, firstDay);
      //格式化日期
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String firstDayOfMonth = sdf.format(cal.getTime());
      Date date=null;
      try {
        date = sdf.parse(firstDayOfMonth);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return date;
      
    }
	
    
    
    
    
    
 /*   *//** 
     * 主函数 
     *  
     * @param args 
     *//*  
    public static void main(String[] args) {  
           // boolean isExceed = compare("2010-01-05 22:22:21", "2010-01-05 22:22:25"); 
            //System.out.println("两个时间相比, 是否相差超过3秒：" + compareNowDate("2015-06-05 09:57:00"));
        	System.out.println(strToDate("1434608814000"));
            
    }  */
}
