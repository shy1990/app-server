package com.wangge.app.server.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.ChildAccount;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.pojo.JsonCustom;
import com.wangge.app.server.service.AssessService;
import com.wangge.app.server.service.ChildAccountService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.security.entity.User;

@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private SalesmanService salesmanService;
	@Resource
  private AssessService assessService;
	@Resource
	private ChildAccountService childAccountService;
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ResponseEntity<JsonCustom> login(@RequestBody JSONObject jsons){
		String username=jsons.getString("username");
		String password=jsons.getString("password");
		String simId=jsons.getString("simId");
		JsonCustom json = new JsonCustom();
		Salesman salesman =salesmanService.login(username,password);
		
		
		if(salesman !=null && !"".equals(salesman.getId())){
			
			if((salesman.getSimId() == null || "".equals(salesman.getSimId()))){
				salesman.setSimId(simId);
				salesmanService.save(salesman);
			}else if(salesman.getSimId() != null && !"".equals(salesman.getSimId()) && simId.equals(salesman.getSimId())){
				return returnLogSucMsg(json, salesman);
		
			}else{
			  List<ChildAccount> childList  =   childAccountService.getChildAccountByParentId(salesman.getId());
        if(childList!=null && childList.size() > 0){
          for(ChildAccount chil : childList){
             if(chil.getSimId() == null || "".equals(chil.getSimId())){
               chil.setSimId(simId);
               childAccountService.save(chil);
               return returnLogSucMsg(json, salesman, chil);
             }else if(chil.getSimId().equals(simId)){
               return returnLogSucMsg(json, salesman, chil);
             }
           
          }
          json.setMsg("与你上一次登录手机卡不同");
				return new ResponseEntity<JsonCustom>(json, HttpStatus.UNAUTHORIZED);
				}
			}
			
				return returnLogSucMsg(json, salesman);
	
		}else {
			json.setMsg("用戶名或密码错误！");
			return new ResponseEntity<JsonCustom>(json, HttpStatus.UNAUTHORIZED);
		}
	}
	/**
	 * 
	* @Title: returnLogSucMsg 
	* @Description: TODO(返回主账号信息) 
	* @param @param json
	* @param @param salesman
	* @param @return    设定文件 
	* @return ResponseEntity<JsonCustom>    返回类型 
	* @throws
	 */
	private ResponseEntity<JsonCustom> returnLogSucMsg(JsonCustom json, Salesman salesman) {
		json.setPhone(salesman.getUser().getPhone());
		json.setRegionId(salesman.getRegion().getId());
		json.setId(salesman.getId());
		if(salesman.getIsOldSalesman()==1){
		  json.setStatus(3);
		}else{
		  json.setStatus(salesman.getStatus().getNum());
		}
		json.setIsOldSalesman(salesman.getIsOldSalesman());
		json.setNickName(salesman.getUser().getNickname().replace("/n", "").trim());
		json.setIsPrimaryAccount(0);
		json.setMsg("登陆成功！");
		json.setStage(salesman.getAssessStage());
		return new ResponseEntity<JsonCustom>(json, HttpStatus.OK);
	}
	
	/**
	 * 
	* @Title: returnLogSucMsg 
	* @Description: TODO(返回子账号信息) 
	* @param @param json
	* @param @param salesman
	* @param @param childAccount
	* @param @return    设定文件 
	* @return ResponseEntity<JsonCustom>    返回类型 
	* @throws
	 */
	
	 private ResponseEntity<JsonCustom> returnLogSucMsg(JsonCustom json, Salesman salesman,ChildAccount childAccount) {
	    json.setPhone(salesman.getUser().getPhone());
	    json.setRegionId(salesman.getRegion().getId());
	    json.setId(salesman.getId());
	    if(salesman.getIsOldSalesman()==1){
	      json.setStatus(3);
	    }else{
	      json.setStatus(salesman.getStatus().getNum());
	    }
	    json.setIsOldSalesman(salesman.getIsOldSalesman());
	    json.setNickName(childAccount.getTruename().replace("/n", "").trim());
	    json.setChildId(String.valueOf(childAccount.getId()));
	    json.setChildName(childAccount.getTruename().replace("/n", "").trim());
	    json.setIsPrimaryAccount(1);
	    json.setMsg("登陆成功！");
	    json.setStage(salesman.getAssessStage());
	    return new ResponseEntity<JsonCustom>(json, HttpStatus.OK);
	  }
	  
	
}
