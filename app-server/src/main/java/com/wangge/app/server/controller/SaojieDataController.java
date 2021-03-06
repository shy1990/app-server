package com.wangge.app.server.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.Saojie;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.entity.Saojie.SaojieStatus;
import com.wangge.app.server.event.afterDailyEvent;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.service.DataSaojieService;
import com.wangge.app.server.service.OilCostRecordService;
import com.wangge.app.server.service.SaojieDataService;
import com.wangge.app.server.util.UploadUtil;

@RestController
@RequestMapping(value = "/v1")
public class SaojieDataController {

	private static final Logger logger = Logger.getLogger(SaojieDataController.class);
	@Resource
	private DataSaojieService dataSaojieService;
	@Resource
	private OilCostRecordService oilsCostRecordService;
	@Resource
	private ApplicationContext cxt;
	
	// private static String url="http://192.168.2.247/uploadfile/"; 内网测试
	// private static String url="http://imagetest.3j168.cn/uploadfile/";
	// //外网测试环境
	private static String url = "http://image.3j1688.com/uploadfile/"; // 外网正式环境

	/**
	 * 获取指定业务扫街数据
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/{regionId}/saojie_data", method = RequestMethod.GET)
	public ResponseEntity<List<SaojieData>> list(@PathVariable("regionId") String regionId) {

		List<SaojieData> Data = dataSaojieService.getSaojieDataByregion(regionId);
		
		List<SaojieData> listsj = new ArrayList<SaojieData>();
  if(Data != null){
      for (SaojieData sj : Data) {
        // select
        // sjd.id,sjd.imageUrl,sjd.name,sjd.description,sjd.coordinate from
        // SaojieData sjd left join sjd.region r where r.id = ?")
        SaojieData sjdata = new SaojieData();
        sjdata.setId(sj.getId());
        sjdata.setImageUrl(sj.getImageUrl());
        sjdata.setName(sj.getName());
        sjdata.setCoordinate(sj.getCoordinate());
        sjdata.setDescription(sj.getDescription());
        // sjdata.setRegion(sj.getRegion());
        listsj.add(sjdata);
      }
    }
		
		return new ResponseEntity<List<SaojieData>>(listsj, HttpStatus.OK);
	}

	@RequestMapping(value = "/{regionId}/{userId}/saojie_data", method = RequestMethod.POST)
	public ResponseEntity<Json> add(@PathVariable("regionId") Region region,@PathVariable("userId") Salesman salesman, @RequestBody JSONObject jsons) {
		Json json = new Json();
		String name = jsons.getString("name");
		String description = jsons.getString("description");
		String coordinate = jsons.getString("coordinate");
	  int	isPrimaryAccount = jsons.getIntValue("isPrimary");
	  String childId = jsons.getString("childId");
		String imageUrl = null;
		String id = null;
		if (jsons.containsKey("imageUrl")) {
			imageUrl = jsons.getString("imageUrl");
		}
		Saojie saojie = dataSaojieService.findByRegionAndSalesman(region,salesman);

		if (saojie != null || salesman.getIsOldSalesman()==1) {
			SaojieData data = new SaojieData(name, coordinate);
			data.setDescription(description);
			data.setImageUrl(imageUrl);
			data.setRegion(region);
			data.setSaojieDate(new Date());
			data.setSaojie(saojie);
			data.setIsPrimaryAccount(isPrimaryAccount);
			if(isPrimaryAccount ==0){
			  id = salesman.getId();
			}else{
			  id = childId;
			}
			data.setAccountId(id);
			data.setSalesman(salesman);
			SaojieData saojiedata = dataSaojieService.addDataSaojie(data,salesman);
			cxt.publishEvent(new afterDailyEvent(region.getId(),salesman.getId(),name,coordinate,isPrimaryAccount,childId,2));
			//oilsCostRecordService.addHandshake(region.getId(),salesman.getId(),name,coordinate,isPrimaryAccount,childId,2);
			
			if (saojiedata != null) {
				SaojieData sj = new SaojieData();
				sj.setId(saojiedata.getId());
				sj.setName(saojiedata.getName());
				sj.setCoordinate(saojiedata.getCoordinate());
				sj.setDescription(saojiedata.getDescription());
				sj.setImageUrl(saojiedata.getImageUrl());
				sj.setSaojieDate(new Date());
				json.setMsg("保存成功！");
				json.setObj(sj);
			} else {
				json.setMsg("保存失败！");
			}
		} else {
			json.setMsg("任务不存在！");
		}

		return new ResponseEntity<Json>(json, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/images/upload", method = RequestMethod.POST)
	public ResponseEntity<Json> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id,
			HttpServletRequest request) {
		Json json = new Json();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH/");
		// String pathdir = "/images/uploadfile/" + dateformat.format(new
		// Date());// 构件文件保存目录
		 String pathdir = "/var/sanji/images/uploadfile/" + dateformat.format(new Date());// 构件文件保存目录
		// 得到图片保存目录的真实路径
		// String realpathdir = request.getSession().getServletContext()
		// .getRealPath(pathdir);
		// System.out.println(realpathdir+"pathpath*******");
		String filename = UUID.randomUUID().toString() + ".jpg";// 构建文件名称
		// System.out.println("http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath()+"/"+pathdir+filename+"imageimage*******");
		try {
			// String path = UploadUtil.saveImg(file, realpathdir, filename);
			String path = UploadUtil.saveFile(pathdir, filename, file);
			if (path != null && !"".equals(path)) {
				json.setMsg("上传成功！");
				// json.setObj(path);
				// json.setObj("http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath()+"/"+pathdir+filename);
				json.setObj(url + dateformat.format(new Date()) + filename);
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
	@RequestMapping(value = "/update_saojieData", method = RequestMethod.POST)
	public ResponseEntity<Json> updateDataSaojie(@RequestBody JSONObject jsons) {
		String saojieDataId = jsons.getString("id");
		String name = jsons.getString("name");
		String description = jsons.getString("description");
		Json json = new Json();
		SaojieData dataSaojie = dataSaojieService.findSaojieDataById(Long.parseLong(saojieDataId));
		if (dataSaojie != null) {
			dataSaojie.setName(name);
			dataSaojie.setDescription(description);

			try {
				dataSaojieService.addDataSaojie(dataSaojie,null);
				json.setMsg("修改成功！");
				return new ResponseEntity<Json>(json, HttpStatus.OK);
			} catch (Exception e) {
				logger.error("login() occur error. ", e);
				e.printStackTrace();
				json.setMsg("修改异常！");
				return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);

			}
		} else {
			json.setMsg("修改失败！");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}

	}
}

