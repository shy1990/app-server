package com.wangge.app.server.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.dto.Order;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.util.JPush;

@RestController
public class Push{
	@Autowired
	private SalesmanRepository sale;
	
	/**
	 * 
	 * @Description: 新订单提醒
	 * @param @param state
	 * @param @param info
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping(value = { "/pushNewOrder" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET }, produces = { "application/json"})
	@ResponseBody
	public ResponseEntity<JSONObject> pushNewOrder(Order order) {
		/**
		 * 
		 */
		Map map = new HashMap();
		map.put("state", "changjun");
		map.put("info", "logan");
//		JSONObject jsonStr = JSONObject.fromObject(map);
		 JSONArray array = new JSONArray();
		  array.put(map);
		  System.out.println("array==="+array);
//		System.out.println("jsonStr===" + jsonStr+sale.findByUsername(info).getPhone());
		return new ResponseEntity(array, HttpStatus.OK);
	}

	/**
	 * 
	 * @Description: 消息通知
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping({ "/pushNews" })
	public ResponseEntity<JSONObject> pushNews() {
		Map map = new HashMap();
		JSONObject jsonStr = JSONObject.fromObject(map);
		try {
			JPush.sendMessageV3(null, "041e6c3a4be,08056d82579", "title",
					"test", map);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("jsonStr===" + jsonStr);
		return new ResponseEntity(jsonStr, HttpStatus.OK);
	}

	/**
	 * 
	 * @Description: 活动通知
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping({ "/pushActivi" })
	public ResponseEntity<JSONObject> pushActivi() {
		Map map = new HashMap();
		JSONObject jsonStr = JSONObject.fromObject(map);
		try {
			JPush.sendMessageV3(null, "041e6c3a4be,08056d82579", "title",
					"test", map);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("jsonStr===" + jsonStr);
		return new ResponseEntity(jsonStr, HttpStatus.OK);
	}

	/**
	 * 
	 * @Description: 拜访任务提醒
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping({ "/visitTask" })
	public ResponseEntity<JSONObject> visitTask() {
		Map map = new HashMap();
		JSONObject jsonStr = JSONObject.fromObject(map);
		try {
			JPush.sendMessageV3(null, "041e6c3a4be,08056d82579", "title",
					"test", map);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("jsonStr===" + jsonStr);
		return new ResponseEntity(jsonStr, HttpStatus.OK);
	}

	/**
	 * 
	 * @Description: 任务-收货款
	 * @param @return
	 * @return ResponseEntity<JSONObject>
	 * @throws
	 * @author changjun
	 * @date 2015年10月23日
	 */
	@RequestMapping({ "/pushTakeMoney" })
	public ResponseEntity<JSONObject> pushTakeMoney() {
		Map map = new HashMap();
		JSONObject jsonStr = JSONObject.fromObject(map);
		try {
			JPush.sendMessageV3(null, "041e6c3a4be,08056d82579", "title",
					"test", map);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("jsonStr===" + jsonStr);
		return new ResponseEntity(jsonStr, HttpStatus.OK);
	}
}