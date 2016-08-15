package com.wangge.app.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.pojo.Json;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class RegistDataController {
	@Resource
	private HttpRequestHandler httpRequestHandler;
	@Value("${app-interface.url}")
	private String url;

	/**
	 * 
	 * @Description: 获取已注册和未注册店铺
	 * @param @param
	 *            regionId
	 * @param @return
	 * @return ResponseEntity<Map<String,List<SaojieData>>>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/{regionId}/regist_data", method = RequestMethod.GET)
	public ResponseEntity<Object> list(@PathVariable("regionId") String regionId) {
		return httpRequestHandler.exchange(url + "/{regionId}/regist_data", HttpMethod.GET,null,null,regionId);
	}
	
	/**
	 * 
	 * @Description: 添加注册店铺数据
	 * @param @param
	 *            region
	 * @param @param
	 *            jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/{regionId}/{saojieId}/regist_data", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@PathVariable("regionId") String regionId, @PathVariable("saojieId") String saojieId,
			@RequestBody JSONObject jsons) {
		return httpRequestHandler.exchange(url + "/{regionId}/{saojieId}/regist_data", HttpMethod.POST,null,jsons,regionId,saojieId);
	}

	/**
	 * 
	 * @Description: 修改店铺录入信息
	 * @param @param
	 *            jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月5日
	 * @version V2.0
	 */
	@RequestMapping(value = "/update_registData", method = RequestMethod.POST)
	public ResponseEntity<Object> updateDataSaojie(@RequestBody JSONObject jsons) {
		return httpRequestHandler.exchange(url + "/update_registData", HttpMethod.POST,null,jsons);
	}

	/**
	 * 
	 * @Title: registDataInfo @Description: TODO(获取一条注册店铺信息) @param @param
	 * registDataId @param @return 设定文件 @return ResponseEntity<RegistData>
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/{registDataId}/infoRegist", method = RequestMethod.GET)
	public ResponseEntity<Object> registDataInfo(@PathVariable("registDataId") String registDataId) {
		return httpRequestHandler.exchange(url + "/{registDataId}/infoRegist", HttpMethod.GET,null,null,registDataId);
	}

	@RequestMapping(value = "/rd/registData", method = RequestMethod.POST)
	public ResponseEntity<Object> list() {
		return httpRequestHandler.exchange(url + "/rd/registData", HttpMethod.POST,null,null);
	}
}

