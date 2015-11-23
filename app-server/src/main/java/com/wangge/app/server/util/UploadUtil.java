package com.wangge.app.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	
	
	public static String saveFile(String filePath,String fileName, MultipartFile filedata) {
        /* 构建文件目录 */
        File filedir = new File(filePath);    
         if (!filedir.exists())    
             filedir.mkdirs();
 
        try {
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            // 写入文件
            out.write(filedata.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return filePath  + fileName;
 
    }

	 
}
