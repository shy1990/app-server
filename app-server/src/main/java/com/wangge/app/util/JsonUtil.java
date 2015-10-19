package com.wangge.app.util;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;


public class JsonUtil {

	 /**
     * 将map集合转换为json语句表示
     *
     * @param map 集合
     * @return 得到的Map解析的json语句
     */
    public  static String mapToJson(Map<Object, Object> map) {
        return JSONArray.fromObject(map).toString();     
    }
    
    /**
     * 将list集合转换为json语句表示
     *
     * @param list 集合
     * @return 得到的List解析的json语句
     */
    public static String listToJson(List<Object> list){
    	return JSONArray.fromObject(list).toString();
    }
}
