package com.wangge.app.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;

@RestController
@RequestMapping(value = "/v1/saleman")
public class SalesmanController {

	@Resource
	private SalesmanService salesmanService;
	@Resource
	private SalesmanRepository smRepository;
	@Resource
	private RegionRepository reRepository;
	
	/**
	 * 业务员申请
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/addYw",method = RequestMethod.POST)
	public ResponseEntity<String> addYw(String username,String password,String userid,String phone,String regionid){
		Region region = reRepository.findById(regionid);
		int num = smRepository.findSaleNum(regionid);
		String uid=null;
		if(num==0){
			uid="A"+userid;
		}else if(num==1){
			uid="B"+userid;
		}else if(num==2){
			uid="C"+userid;
		}else if(num==3){
			uid="D"+userid;
		}
		Salesman salesman = new Salesman();
		salesman.setId(uid);
		salesman.getUser().setPassword(password);
		salesman.getUser().setPhone(phone);
		salesman.getUser().setUsername(username);
		salesman.setRegion(region);
		smRepository.save(salesman);
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}
	
	/**
	 * 地区业务员列表
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/saleList",method = RequestMethod.POST)
	public ResponseEntity<Json> saleList(){
		Json json = new Json();
		List<Salesman> salelist = salesmanService.findAll();
		
		List<Map<String,Object>> slm = new ArrayList<Map<String,Object>>();
		System.out.println(slm.size());
		if(slm != null && slm.size()>0){
			for(Salesman salesman : salelist){
				System.out.println("11111111111111");
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("username", salesman.getUser().getUsername());
				m.put("password", salesman.getUser().getPassword());
				m.put("phone", salesman.getUser().getPhone());
				m.put("regionname", salesman.getRegion().getName());
				m.put("userid", salesman.getId());
				m.put("regionid", salesman.getRegion().getId());
				slm.add(m);
			}
		}
		json.setObj(slm);
		return new ResponseEntity<Json>(json,HttpStatus.OK);
		/*StringBuffer jsbuf=new StringBuffer();
		int i=0;
		jsbuf.append("{").append("\"").append("data").append("\"").append(":").append("[");
		for(Salesman ssm:salelist){
			if(i++>0){
				jsbuf.append(",");
			}
			jsbuf.append("{").append("\"").append("username").append("\"").append(":").append("\"").append(ssm.getUsername()).append("\",")
			.append("\"").append("password").append("\"").append(":").append("\"").append(ssm.getPassword()).append("\",")
			.append("\"").append("phone").append("\"").append(":").append("\"").append(ssm.getPhone()).append("\",")
			.append("\"").append("userid").append("\"").append(":").append("\"").append(ssm.getId()).append("\",")
			.append("\"").append("regionid").append("\"").append(":").append("\"").append(ssm.getRegion().getId()).append("\"}");
		}
	jsbuf.append("]}");*/
		
	}
	
}
