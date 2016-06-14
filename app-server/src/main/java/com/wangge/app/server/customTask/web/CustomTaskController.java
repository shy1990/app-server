package com.wangge.app.server.customTask.web;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wangge.app.server.customTask.entity.CustomTask;
import com.wangge.app.server.customTask.server.CustomTaskServer;

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

	// 查询月任务详情
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") CustomTask customTask, HttpServletRequest request, Model model) {
		//model.addAttribute("task",customTask);
		return "detail.jsp";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createSub(CustomTask customTask) {
		customServ.save(customTask);
		return null;
	}
}
