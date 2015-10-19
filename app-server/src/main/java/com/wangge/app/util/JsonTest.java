package com.wangge.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		list.add("shitou");
		list.add("baozhen");
		list.add("xiaoming");
		list.add("xiaoqiang");
		System.out.println(JsonUtil.listToJson(list));
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("1", "shitou");
		map.put("2", "xiaoming");
		map.put("3", "xiaoqiang");
		map.put("4", "baozhen");
		System.out.println(JsonUtil.mapToJson(map));
	}

}
