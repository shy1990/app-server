package com.wangge.app.server.controller;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.neo4j.cypher.internal.compiler.v2_0.repeat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.util.LogUtil;

@RestController
@RequestMapping(value = "/v1")
public class SaojieDataController {

	
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
	@ApiOperation(value="根据区域获取代理商扫街数据",notes="根据区域获取代理商扫街数据")
	@ApiImplicitParam(name="regionId",value="regionId",required=true,dataType="String")
	@RequestMapping(value = "/{regionId}/saojie_data", method = RequestMethod.GET)
	public JSONObject list(@PathVariable("regionId") String regionId) {
	  LogUtil.info("根据区域获取代理商扫街数据, regionId="+regionId);
	  return httpRequestHandler.exchange(interfaceUrl+"{regionId}/saojie_data", HttpMethod.GET, null, null, new ParameterizedTypeReference<JSONObject>() {}, regionId);
	}

	@ApiOperation(value="添加扫街数据",notes="添加扫街数据")
  @ApiImplicitParam(name="regionId,userId,jsons",value="regionId,userId,jsons",required=true,dataType="String,JSONObject")
	@RequestMapping(value = "/{regionId}/{userId}/saojie_data", method = RequestMethod.POST)
	public JSONObject add(@PathVariable("regionId") String regionId,@PathVariable("userId") String userId, @RequestBody JSONObject jsons) {
	  LogUtil.info("添加扫街数据, regionId="+regionId+"userId="+userId+"jsons="+jsons.toJSONString());
	  Map<String, String> urlParam = new HashMap<String, String>();
    urlParam.put("regionId",regionId);
    urlParam.put("userId",userId);
    
	  return httpRequestHandler.exchange(interfaceUrl+"{regionId}/{userId}/saojie_data", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
    }, urlParam);
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
	@ApiOperation(value="添加扫街数据",notes="添加扫街数据")
  @ApiImplicitParam(name="regionId,userId,jsons",value="regionId,userId,jsons",required=true,dataType="String,JSONObject")
	@RequestMapping(value = "/images/upload", method = RequestMethod.POST)
	public JSONObject upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id,
			HttpServletRequest request) {
	    JSONObject jsons = new JSONObject();
	    jsons.put("file", file);
	    jsons.put("id", id);
	    return httpRequestHandler.exchange(interfaceUrl+"images/upload", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
      });

	}

	/**
	 * @description (修改扫街数据)
	 * @param jsons
	 * @return
	 * @author SongBaozhen
	 */
	@ApiOperation(value="修改扫街数据",notes="修改扫街数据")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
	@RequestMapping(value = "/update_saojieData", method = RequestMethod.POST)
	public JSONObject updateDataSaojie(@RequestBody JSONObject jsons) {
		LogUtil.info("修改扫街数据, jsons="+jsons.toJSONString());
	  return httpRequestHandler.exchange(interfaceUrl+"update_saojieData", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
    });

	}
}

