package com.wangge.app.server.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.DataSaojieService;
import com.wangge.app.server.service.SalesmanService;
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
			@PathVariable("regionId") String regionId) {

		List<SaojieData> Data = dataSaojieService
				.getSaojieDataByregion(regionId);

		return new ResponseEntity<List<SaojieData>>(Data, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}/saojie_data", method = RequestMethod.POST)
	public ResponseEntity<SaojieData> add(
			@PathVariable("userId") Salesman salesman,
			@RequestBody JSONObject json) {
		int taskValue = 0;
		int dataSaojieNum = 0;
		List<Saojie> child = new ArrayList<Saojie>();
		String name = json.getString("name");
		String description = json.getString("description");
		String coordinate = json.getString("coordinate");
		String imageUrl = null;
		if (json.containsKey("imageUrl")) {
			imageUrl = json.getString("imageUrl");
		}
		Saojie saojie  = dataSaojieService.findBySalesman(salesman);
		SaojieData data = new SaojieData(name, coordinate);
		data.setDescription(description);
		data.setImageUrl(imageUrl);
		data.setRegion(saojie.getRegion());
		data.setSaojie(saojie);
		//data.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		SaojieData saojiedata = dataSaojieService.addDataSaojie(data);
		
		SaojieData sj = new SaojieData();
		sj.setId(saojiedata.getId());
		sj.setCoordinate(saojiedata.getCoordinate());
		sj.setDescription(saojiedata.getDescription());
		sj.setImageUrl(saojiedata.getImageUrl());
		
		// taskValue = (int)saojie.getChildren();
		//for(Saojie s : saojie.getChildren()){
			//if(SaojieStatus.PENDING.equals(saojie.getStatus())){
				taskValue = saojie.getMinValue();
			    dataSaojieNum = dataSaojieService.getDtaCountBySaojieId(Integer.parseInt(String.valueOf(saojie.getId())));
				if(taskValue == dataSaojieNum){
					saojie.setStatus(SaojieStatus.COMMIT);
					Saojie sj2 =  dataSaojieService.findByOrder(saojie.getOrder());
					sj2.setStatus(SaojieStatus.AGREE);
					child.add(saojie);
					child.add(sj2);
					//task.getNext().setStatus(TaskStatus.PENDING);
					//taskSaojieService.save((TaskSaojie)task);
					saojie.setChildren(child);
					dataSaojieService.updateSaojie(saojie);
				
				}
		//	}
			
		//}
		
		
		
		
		return new ResponseEntity<SaojieData>(sj, HttpStatus.CREATED);
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
				//json.setObj(path);
				json.setObj("http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath()+"/"+pathdir+filename);
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
	
	/**
	 * @description (修改扫街数据)
	 * @param jsons
	 * @return
	 * @author SongBaozhen
	 */
	@RequestMapping(value = "/update_saojieData",method = RequestMethod.POST)
	public ResponseEntity<Json> updateDataSaojie(@RequestBody JSONObject jsons){
		String saojieDataId = jsons.getString("id");
		String name = jsons.getString("name");
		String description = jsons.getString("description");
		Json json = new Json();
		SaojieData dataSaojie = dataSaojieService.findSaojieDataById(Long.parseLong(saojieDataId));
		if(dataSaojie != null && !"".equals(dataSaojie)){
			dataSaojie.setName(name);
			dataSaojie.setDescription(description);
			
			try {
				dataSaojieService.addDataSaojie(dataSaojie);
				json.setMsg("修改成功！");
				return new ResponseEntity<Json>(json, HttpStatus.OK);
			} catch (Exception e) {
				logger.error("login() occur error. ", e);
				e.printStackTrace();
				json.setMsg("修改异常！");
				return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
				
			}
		}else{
			json.setMsg("修改失败！");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}
		
		
	}
}
