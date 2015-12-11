package com.wangge.app.server.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Visit;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.VisitRepository;
import com.wangge.app.server.service.TaskVisitService;

@RestController
@RequestMapping(value = "/v1")
public class VisitTaskController {
	@Autowired
	private TaskVisitService taskVisitService;
	@Resource
	private VisitRepository vr;
	
	Json json = new Json();
	
	/**
	 * 查看该业务当前的拜访任务列表
	 * @param username 业务员
	 * @return 店铺名，图片链接，坐标，备注，摆放时间,状态(待定)
	 */
	@RequestMapping(value = "/task/{userId}/visitList",method = RequestMethod.GET)
	public ResponseEntity<List<Visit>> visitList(@PathVariable("userId")Salesman salesman) {
		List<Visit> tv = taskVisitService.findBySalesman(salesman);
		for(int i=0; i<tv.size(); i++){
			System.out.println(tv.get(i));
		}
		return new ResponseEntity<List<Visit>>(tv, HttpStatus.OK);
	}
	
	/**
	 * 根据用户选择的拜访或已拜访进行处理
	 * @param 状态，任务id,
	 * @return 
	 */
	/*@RequestMapping(value = "/task/{taskId}/visitInfo")
	public ResponseEntity<Json> visitInfo(@PathVariable("taskId") String taskId) {
		Json json = new Json();
		Task task = vr.findOne(taskId);
		if(task != null && !"".equals(task)){
			if(TaskStatus.FINISHED.equals(task.getStatus())){
				json.setObj(task);
			}
		}
		return new ResponseEntity<Json>(json, HttpStatus.OK);
	}*/
	
	/**
	 * 保存业务提交的客户拜访信息
	 * @param TaskVisit
	 * @return ok
	 */
	/*@RequestMapping(value = "/task/addVisit",method = RequestMethod.POST)
	public ResponseEntity<Json> addVisit(@RequestBody JSONObject jsons) {
		try {
			TaskVisit tv = visitService.findById(jsons.getString("id"));
			if(tv != null && !"".equals(tv)){
				tv.setDescription(jsons.getString("description"));
				tv.setStatus(TaskStatus.FINISHED);
				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String tmp = dFormat.format(new Date());
				tv.setEndTime(DateUtil.strToDate(tmp));
				visitService.save(tv);
				json.setMsg("保存成功!");
				return new ResponseEntity<Json>(json, HttpStatus.CREATED);
			}else{
				json.setMsg("保存失败!");
				return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("保存异常!");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}
		
	}*/
	
}
