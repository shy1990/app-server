package com.wangge.app.server.customTask.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wangge.app.server.customTask.entity.CustomMessages;
import com.wangge.app.server.customTask.entity.CustomTask;
import com.wangge.app.server.customTask.server.CustomTaskServer;
import com.wangge.app.server.customTask.server.ImplCustomTaskServe;

@Controller
@RequestMapping(value = "/v1/customTask")
public class CustomTaskController {
	@Autowired
	CustomTaskServer customServ;

	private Log log = LogFactory.getLog(this.getClass());

	// 查询月任务详情
	@RequestMapping(value = "/{salesmanId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllTask(HttpServletRequest request, @PathVariable String salesmanId,
			Pageable pageRequest) {
		try {
			return new ResponseEntity<Map<String, Object>>(customServ.getList(salesmanId, pageRequest), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> repMap = new HashMap<String, Object>();
			repMap.put("code", "0");
			repMap.put("msg", "数据服务器错误");
			log.debug(e.getStackTrace());
			return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 查询单个任务详情数据
	 * 
	 * @param customTask
	 * @return
	 */
	@RequestMapping(value = "/details/{id}/{salesmanId}", method = RequestMethod.GET)
	public ResponseEntity<Object> pointdetail(@PathVariable("id") CustomTask customTask,
			@PathVariable("salesmanId") String salesmanId, HttpServletRequest request) {
		Map<String, Object> repMap = new HashMap<String, Object>();
		try {
			return new ResponseEntity<Object>(customServ.findCustomTask(customTask, salesmanId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			repMap.put("code", "0");
			repMap.put("msg", "数据服务器错误");
			log.debug(e.getStackTrace());
			return new ResponseEntity<Object>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 跳转单条记录详情
	 * 
	 * @param customTask
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}/{salesmanId}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") CustomTask customTask, @PathVariable("salesmanId") String salesmanId,
			HttpServletRequest request, Model model) {
		model.addAttribute("task", customTask);
		model.addAttribute("taskId", customTask.getId());
		model.addAttribute("salesmanId", salesmanId);
		List<CustomMessages> sdf= customServ.findMessageList(customTask, salesmanId);
		model.addAttribute("messageList", sdf);
		model.addAttribute("messagesCount", sdf.size());
		model.addAttribute("taskType",ImplCustomTaskServe.TASKTYPEARR[customTask.getType()]);
		customServ.updateStatus(customTask,salesmanId);
		return "customTask/detail";
	}

	/**
	 * 保存新建的消息记录
	 * 
	 * @param customTask
	 * @return
	 */
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createSub(@RequestBody CustomMessages message) {
		Map<String, Object> repMap = new HashMap<String, Object>();
		repMap.put("code", "1");
		try {
			customServ.saveMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			repMap.put("code", "0");
			repMap.put("msg", e.getMessage());
			log.debug(e.getStackTrace());
			return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);

	}
}
