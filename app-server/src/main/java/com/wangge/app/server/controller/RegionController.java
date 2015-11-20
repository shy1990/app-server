package com.wangge.app.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.vo.TreeVo;
import com.wangge.common.entity.Region;

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
	
	
	@RequestMapping(value="/findbyParentid",method=RequestMethod.POST)
	public ResponseEntity<List<Region>> findbyParentid(String parentid,String flag){
		logger.debug("parentid"+parentid);
		List<Region> listRegion=new ArrayList<Region>();
		if("true".equals(flag)){
			//listRegion=regionService.findCustomRegiond(salesmanService.findByid(parentid).getRegion().getId());
		}else{
			listRegion=regionService.findRegiondbyParentid(parentid);
		}
		
		return new ResponseEntity<List<Region>>(listRegion,HttpStatus.OK);
	}
	
}

