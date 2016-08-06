package com.wangge.app.server.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.service.DataSaojieService;
import com.wangge.app.server.service.OilCostRecordService;

@RestController
@RequestMapping(value = "/v1")
public class SaojieDataController {

	private static final Logger logger = Logger.getLogger(SaojieDataController.class);
	
	
	 @Value("${app-interface.url}")
	  private String interfaceUrl;
	  
	  @Resource
	  private HttpRequestHandler httpRequestHandler;

	/**
	 * 获取指定业务扫街数据
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/{regionId}/saojie_data", method = RequestMethod.GET)
	public ResponseEntity<Object> list(@PathVariable("regionId") String regionId) {
	  return httpRequestHandler.exchange(interfaceUrl+regionId+"/saojie_data", HttpMethod.GET,null,JSONObject.class, regionId);
	}

	@RequestMapping(value = "/{regionId}/{userId}/saojie_data", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@PathVariable("regionId") String regionId,@PathVariable("userId") String userId, @RequestBody JSONObject jsons) {
	  return httpRequestHandler.exchange(interfaceUrl+regionId+"/"+userId+"/saojie_data", HttpMethod.POST, null, JSONObject.class, regionId,userId,jsons);
	}
  /**
   * 
    * upload:(上传图片). <br/> 
    * @author Administrator 
    * @param file
    * @param id
    * @param request
    * @return 
    * @since JDK 1.8
   */
	@RequestMapping(value = "/images/upload", method = RequestMethod.POST)
	public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id,
			HttpServletRequest request) {
	    return httpRequestHandler.exchange(interfaceUrl+"/images/upload", HttpMethod.POST, null, JSONObject.class, file,id);

	}

	/**
	 * @description (修改扫街数据)
	 * @param jsons
	 * @return
	 * @author SongBaozhen
	 */
	@RequestMapping(value = "/update_saojieData", method = RequestMethod.POST)
	public ResponseEntity<Object> updateDataSaojie(@RequestBody JSONObject jsons) {
		
	  return httpRequestHandler.exchange(interfaceUrl+"update_saojieData", HttpMethod.POST,null, JSONObject.class, jsons);

	}
}

