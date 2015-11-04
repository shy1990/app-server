package com.wangge.app.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class UploadUtil {
	
	
	 public  static String saveImg(File file , String filePath,String fileName) throws Exception{
		 
		 InputStream is = new FileInputStream(file);  
         File filedir = new File(filePath);    
         if (!filedir.exists())    
             filedir.mkdirs();
         
         OutputStream os = new FileOutputStream(new File(filedir,fileName));  
         byte[] byteStr = new byte[1024];    
         int len = 0;    
         while ((len = is.read(byteStr)) > 0) {    
             os.write(byteStr,0,len);    
         }    
         is.close();    
             
         os.flush();    
         os.close();    
		 
		return filePath+fileName;
 	
 }
	 
}
