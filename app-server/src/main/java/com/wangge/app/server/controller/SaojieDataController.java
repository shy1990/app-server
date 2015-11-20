package com.wangge.app.server.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.DataSaojieService;
import com.wangge.app.server.util.UploadUtil;
import com.wangge.common.entity.Region;

@RestController
@RequestMapping(value = "/v1")
public class SaojieDataController {

	private static final Logger logger = Logger
			.getLogger(SaojieDataController.class);
	@Resource
	private DataSaojieService dataSaojieService;

	/**
	 * 获取指定业务扫街数据
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/{regionId}/saojie_data", method = RequestMethod.GET)
	public ResponseEntity<List<SaojieData>> list(
			@PathVariable("regionId") Region region) {

		List<SaojieData> Data = dataSaojieService
				.getSaojieDataByregion(region);

		return new ResponseEntity<List<SaojieData>>(Data, HttpStatus.OK);
	}

	@RequestMapping(value = "/{username}/saojie_data", method = RequestMethod.POST)
	public ResponseEntity<SaojieData> add(
			@PathVariable("username") String username,
			@RequestBody JSONObject json) {
		String name = json.getString("name");
		String remark = json.getString("remark");
		String coordinate = json.getString("coordinate");
		String imageUrl = null;
		if (json.containsKey("imageUrl")) {
			imageUrl = json.getString("imageUrl");
		}
		SaojieData data = new SaojieData(name, coordinate);
		//data.setRemark(remark);
		data.setImageUrl(imageUrl);
		//data.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		dataSaojieService.addDataSaojie(data);
		return new ResponseEntity<SaojieData>(data, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/images/upload", method = RequestMethod.POST)
	public ResponseEntity<Json> upload(@RequestParam("file") File file,
			@RequestParam("id") String id, HttpServletRequest request) {
		Json json = new Json();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH/");
		String pathdir = "/images/uploadfile/" + dateformat.format(new Date());// 构件文件保存目录
		// 得到图片保存目录的真实路径
		String realpathdir = request.getSession().getServletContext()
				.getRealPath(pathdir);

		String filename = UUID.randomUUID().toString() + ".jpg";// 构建文件名称
		try {
			String path = UploadUtil.saveImg(file, realpathdir, filename);
			if (path != null && !"".equals(path)) {
				json.setMsg("上传成功！");
				json.setObj(path);
				return new ResponseEntity<Json>(json, HttpStatus.OK);
			} else {
				json.setMsg("上传失败！");
				return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error("login() occur error. ", e);
			e.printStackTrace();
			json.setMsg("图片上传异常！");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}

	}
}
