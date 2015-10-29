package com.wangge.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
