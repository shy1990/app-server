package com.wangge.app.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.common.repository.RegionRepository;

@RestController
@RequestMapping(value = "/v1/saleman")
public class SalesmanController {

	@Resource
	private SalesmanService salesmanService;
	@Resource
	private SalesmanRepository smRepository;

	/*@RequestMapping(value = "/{username}/password", method = RequestMethod.PUT)
	public ResponseEntity<Json> changePassword(@PathVariable("username") String username,
			@RequestBody JSONObject jsons) {
		String oldPassword = jsons.getString("oldPassword");
		String planPassword = jsons.getString("planPassword");
		Json json = new Json();
		Salesman sa = salesmanService.findByUsernameAndPassword(username, oldPassword);
		if (sa != null && !"".equals(sa)) {
//			sa.setPassword(planPassword);
			salesmanService.save(sa);
			json.setMsg("修改成功！");
			return new ResponseEntity<Json>(json, HttpStatus.OK);
		} else {
			json.setMsg("修改失败！");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}

	}*/
	
	/**
	 * 地区业务员列表
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/saleList",method = RequestMethod.POST)
	public ResponseEntity<Json> saleList(){
		Json json = new Json();
		List<Salesman> salelist = smRepository.findAll();
		
		List<Map<String,Object>> slm = new ArrayList<Map<String,Object>>();
		
		if(slm != null && slm.size()>0){
			for(Salesman salesman : salelist){
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
