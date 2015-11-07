package com.wangge.app.server.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.codec.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.CustomRegion;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.vo.TreeVo;

/**
 * 
 * @author SongBaozhen
 *
 */
@RestController
@RequestMapping(value = "/v1")
public class RegionController {

	private static final Logger logger = Logger
			.getLogger(RegionController.class);
	@Resource
	private RegionService regionService;
	/**
	 * 业务人员区域信息
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/{username}/regions",method=RequestMethod.GET)
	public ResponseEntity<Map<String,List<Region>>> salesmanRegions(@PathVariable("username") Salesman salesman){
		logger.debug("username:"+salesman);
	     Map<String, List<Region>>   regionMap = regionService.getSaojie(salesman);
	   
		return new ResponseEntity<Map<String,List<Region>>>(regionMap,HttpStatus.OK);
	}
	
	/**
	 * 
	 * 功能: 查询区域
	 * 详细： 	
	 * 作者： 	jiabin
	 * 版本：  1.0
	 * 日期：  2015年11月6日下午1:48:34
	 *
	 */
	@RequestMapping(value="/findRegion",method=RequestMethod.POST)
	public ResponseEntity<List<TreeVo>> findRegion(String id){
		logger.debug("id:"+id);
	     List<TreeVo> listTreeVo =new ArrayList<TreeVo>();
	     listTreeVo=regionService.findTreeRegion(id);
	     System.out.println(new ResponseEntity<List<TreeVo>>(listTreeVo,HttpStatus.OK));
		return new ResponseEntity<List<TreeVo>>(listTreeVo,HttpStatus.OK);
	}
	
	/**
	 * 
	 * 功能: 添加自定义区域
	 * 详细： 	
	 * 作者： 	jiabin
	 * 版本：  1.0
	 * 日期：  2015年11月6日下午3:54:51
	 *
	 */
	@RequestMapping(value="/saveRegions",method=RequestMethod.POST)
	public ResponseEntity<String> addPoints(String parentid,String pointStr,String name){
		logger.debug("parentid"+parentid+"pointStr"+pointStr);
		/*try {
			name=Base64.decodeToString(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		List<Region> listRegion=regionService.findRegionSort(parentid);
		int id;
		if(listRegion.size()>0){
			id=Integer.parseInt(listRegion.get(0).getId())+1 ;
		}else{
			id=Integer.parseInt(parentid+"00")+1;
		}
		Region entity=new CustomRegion(String.valueOf(id), name, pointStr);
		entity.setParent(regionService.findRegion(parentid));
		regionService.saveRegion(entity);
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
}

