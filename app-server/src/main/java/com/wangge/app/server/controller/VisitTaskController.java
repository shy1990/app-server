package com.wangge.app.server.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.TaskVisit;
import com.wangge.app.server.service.VisitService;
import com.wangge.app.util.DateUtil;

@RestController
@RequestMapping("/v1/task")
public class VisitTaskController {
	@Autowired
	private VisitService visitService;
	
	/**
	 * 查看该业务当前的拜访任务列表
	 * @param username 业务员
	 * @return 店铺名，图片链接，坐标，备注，摆放时间,状态(待定)
	 */
	@RequestMapping(value = "/visitList")
	public ResponseEntity<String> visitList(String username) {
		JSONObject obj=new JSONObject("{\"name\":\"xiaoming\"}");
		System.out.println(new ResponseEntity<JSONObject>(obj, HttpStatus.OK));
		String jsonStr="{\"name\":\"xiaoming\"}";
		return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
	}
	
	/**
	 * 根据用户选择的拜访或已拜访进行处理
	 * @param 状态，任务id,
	 * @return 
	 */
	@RequestMapping(value = "/visitInfo")
	public ResponseEntity<String> visitInfo(int status,int taskId) {
		JSONObject obj=new JSONObject("{\"name\":\"xiaoming\"}");
		System.out.println(new ResponseEntity<JSONObject>(obj, HttpStatus.OK));
		String jsonStr="{\"name\":\"xiaoming\"}";
		return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
	}
	
	/**
	 * 保存业务提交的客户拜访信息
	 * @param TaskVisit
	 * @return ok
	 */
	@RequestMapping(value = "/addVisit",method = RequestMethod.GET)
	public ResponseEntity<String> addVisit(@RequestParam int id,@RequestParam String remark,@RequestParam String image_url) {
		try {
			TaskVisit taskVisit = visitService.findById(id);
			taskVisit.setRemark(remark);
			taskVisit.setImage_url(image_url);
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String tmp = dFormat.format(new Date());
			taskVisit.setVisit_time(DateUtil.strToDate(tmp));
			visitService.save(taskVisit);
			return new ResponseEntity<String>("保存成功!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("保存失败!", HttpStatus.OK);
		}
		
	}
	
	
}
