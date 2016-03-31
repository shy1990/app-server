package com.wangge.app.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import com.wangge.app.server.vo.Exam.Town;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;
import com.wangge.security.entity.User;
import com.wangge.security.entity.User.UserStatus;
import com.wangge.security.repository.UserRepository;

@RestController
@RequestMapping(value = "/v1/saleman")
public class SalesmanController {
  @PersistenceContext  
  private EntityManager em; 
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

	
	@RequestMapping(value = "/findRegionBySale", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> findByUserId(String userid) {
		Salesman salesman=salesmanService.findSalesmanbyId(userid.trim());
		String regionId = salesman.getRegion().getId();
		String regionName = salesman.getRegion().getName();
		 Map<String, Object>  map=new HashMap<String, Object>();
		   map.put("regionId", regionId);
		   map.put("regionName", regionName);
		
	 return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
	
}
	
	
	@RequestMapping(value = "/findRegionIdBySale", method = RequestMethod.POST)
	public ResponseEntity<String> findRegionIdBySaleId(String salesmanid) {
		Salesman salesman=salesmanService.findSalesmanbyId(salesmanid.trim());
		 String regionId=null;
		 if(null !=salesman.getRegionMore()&&!"".equals(salesman.getRegionMore())){
	       regionId = salesman.getRegionMore();
	    }else{
	       regionId = salesman.getRegion().getId();
	    }
		
		
	 return new ResponseEntity<String>(regionId,HttpStatus.OK);
	
}
	
	@RequestMapping(value = "/findRegBySale", method = RequestMethod.POST)
	public ResponseEntity<String> findById(String userid) {
		Salesman salesman=salesmanService.findSalesmanbyId(userid.trim());
//		String regionId = salesman.getRegion().getId();
		String regionName = salesman.getRegion().getName();
//		 Map<String, Object>  map=new HashMap<String, Object>();
//		   map.put("regionId", regionId);
//		   map.put("regionName", regionName);
		
	 return new ResponseEntity<String>(regionName,HttpStatus.OK);
	
}
	
	
	/**
	 * 业务员申请
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/addYw",method = RequestMethod.POST)
	public ResponseEntity<String> addYw(String username,String password,String userid,String phone,String regionid,String nickname,String isoldsale){
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
		salesman.setIsOldSalesman( Integer.valueOf(isoldsale.trim()).intValue());
		salesman.setRegion(region);
		salesman.setOldid(getOldId(phone));
		salesman.setUser(user);
		salesmanService.save(salesman);
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
					json.setMsg("旧密码不正确！");
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
	
	public String getOldId(String phone){
	  String oldId=null;
	  
	   //指标信息    a.USERNAME=(select USERNAME from SJ_DB.SYS_USER where USER_ID='"+salesId+"' ) 
    String sql = "select * from  SJZAIXIAN.SJ_TB_ADMIN t where t.mobilephone="+phone;
    Query query =  em.createNativeQuery(sql);
    List obj = query.getResultList();
    int phoneNum = 0; //手机数   
    if(obj!=null && obj.size()>0){
      Iterator it = obj.iterator();
      Set<Town> set = new HashSet<Town>();
      while(it.hasNext()){
        Object[] o = (Object[])it.next();
        System.out.println(o[0]);
        oldId=o[0].toString();
        
      }
    }
	  
	  return  oldId;
	}
	
	
}
