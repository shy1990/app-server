package com.wangge.app.server.monthTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.wangge.app.server.monthTask.entity.MonthTaskSub;
import com.wangge.app.server.monthTask.service.MonthTaskServive;

@RestController
@RequestMapping(value = "/v1/monthTask")
public class MonthTaskController {
	@Autowired
	MonthTaskServive monthSevice;
	private static final String SEARCH_OPERTOR = "SC_";

	private Log log = LogFactory.getLog(MonthTaskController.class);

	// 查询月任务详情
	@RequestMapping(value = "/monthTaskMains/{saleId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllTask(@PathVariable String saleId) {
		return monthSevice.findTask(saleId);

	}

	// 查询父区域下所有子区域
	@RequestMapping(value = "/regions/parentId/{regionId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findRegion(@PathVariable String regionId) {
		return monthSevice.findRegion(regionId);
	}

	// 查询父区域下所有子区域
	/**
	 * http://localhost:8080/cash?sc_EQ_cashId=3206
	 */
	@RequestMapping(value = "/historydata/allShops", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findShopData(HttpServletRequest request, Pageable pageRequest) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		return monthSevice.findShopBy(searchParams, pageRequest);
	}

	@RequestMapping(value = "/MonthTaskSubs", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveMonthTaskSub(@RequestBody Map<String, Object> talMap) {

		Map<String, Object> repMap = new HashMap<String, Object>();
		try {
			monthSevice.save(talMap);
			repMap.put("code", "1");
		} catch (Exception e) {
			e.printStackTrace();
			repMap.put("code", "0");
			repMap.put("msg", e.getMessage());
			log.debug(e.getStackTrace());
			return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/MonthTaskSubs", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findMonthTaskSub(HttpServletRequest request, Pageable pageRequest) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		try {
			return new ResponseEntity<Map<String, Object>>(monthSevice.findTask(searchParams, pageRequest),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("code", "1");
			return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "executions/{shopId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findActions(@PathVariable Long shopId) {
		try {
			//测试用
//			monthSevice.saveExecution(shopId, "主动拜访");
			return new ResponseEntity<Map<String, Object>>(monthSevice.findExecution(shopId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("code", "1");
			return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
