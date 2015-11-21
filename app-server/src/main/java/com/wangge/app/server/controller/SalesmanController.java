package com.wangge.app.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;
import com.wangge.security.entity.User;
import com.wangge.security.entity.User.UserStatus;
import com.wangge.security.repository.UserRepository;

@RestController
@RequestMapping(value = "/v1/saleman")
public class SalesmanController {

	@Resource
	private SalesmanService salesmanService;
	@Autowired
	private SalesmanRepository smRepository;
	@Autowired
	private RegionRepository reRepository;
	@Autowired
	private UserRepository ur;
	
	
	@RequestMapping(value = "/findSalesman", method = RequestMethod.POST)
	public ResponseEntity<List<Salesman>> findSalesman() {
		List<Salesman> listSalesman=salesmanService.findSalesman();
			
	 return new ResponseEntity<List<Salesman>>(listSalesman,HttpStatus.OK);
	
}

	
	
	/**
	 * 业务员申请
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/addYw",method = RequestMethod.POST)
	public ResponseEntity<String> addYw(String username,String password,String userid,String phone,String regionid,String nickname){
		System.out.println("username:"+username);
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
		User user = new User();
		user.setId(uid);
		user.setPassword(password);
		user.setPhone(phone);
		user.setStatus(UserStatus.NORMAl);
		user.setUsername(username);
		user.setNickname(nickname);
		ur.save(user);
		salesman.setUser(user);
		salesman.setRegion(region);
		smRepository.save(salesman);
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}
			
	@RequestMapping(value = "/{username}/password", method = RequestMethod.PUT)
	public ResponseEntity<Json> changePassword(@PathVariable("username") String username,
					@RequestBody JSONObject jsons) {
				String oldPassword = jsons.getString("oldPassword");
				String planPassword = jsons.getString("planPassword");
				Json json = new Json();
				Salesman sa = salesmanService.findByUsernameAndPassword(username, oldPassword);
				if (sa != null && !"".equals(sa)) {
					sa.getUser().setPassword(planPassword);
					salesmanService.save(sa);
					json.setMsg("修改成功！");
					return new ResponseEntity<Json>(json, HttpStatus.OK);
				} else {
					json.setMsg("修改失败！");
					return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
				}
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
//		System.out.println(salelist.size());
		if(salelist != null && salelist.size()>0){
			for(Salesman salesman : salelist){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("username", salesman.getUser().getUsername());
				m.put("nickname", salesman.getUser().getNickname());
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
