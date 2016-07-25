package com.wangge.app.server.controller;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.ChildAccount;
import com.wangge.app.server.entity.Salary;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.User.UserStatus;
import com.wangge.app.server.pojo.JsonCustom;
import com.wangge.app.server.service.AssessService;
import com.wangge.app.server.service.ChildAccountService;
import com.wangge.app.server.service.SalaryService;
import com.wangge.app.server.service.SalesmanService;

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
	@Resource
	private SalaryService salaryService;
	/**
	 * 登录 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)

  public String login(@RequestBody JSONObject jsons){
    
     return 
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
		json.setPhone(salesman.getMobile());
		json.setRegionId(salesman.getRegion().getId());
		json.setId(salesman.getId());
		if(salesman.getIsOldSalesman()==1){
		  json.setStatus(3);
		}else{
		  json.setStatus(salesman.getStatus().getNum());
		}
		json.setIsOldSalesman(salesman.getIsOldSalesman());
		json.setNickName(salesman.getTruename().replace("/n", "").trim());
		json.setIsPrimaryAccount(0);
		json.setMsg("登陆成功！");
		json.setStage(salesman.getAssessStage());
		if(null!=salesman.getMobile()&&!"".equals(salesman)){
			Calendar calendar=Calendar.getInstance();
			int month=calendar.get(Calendar.MONTH);//上个月
			Salary salary=salaryService.findSalary(salesman.getMobile().trim(),month+"");
			if(null!=salary){
				json.setSalary(salary.getSalary()+"");
			}else{
				json.setSalary("");
			}
		}else{
			json.setSalary("");
		}
		
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
	    json.setPhone(salesman.getMobile());
	    json.setRegionId(salesman.getRegion().getId());
	    json.setId(salesman.getId());
	    if(salesman.getIsOldSalesman()==1){
	      json.setStatus(3);
	    }else{
	      json.setStatus(salesman.getStatus().getNum());
	    }
	    json.setIsOldSalesman(salesman.getIsOldSalesman());
	    json.setNickName(salesman.getTruename().replace("/n", "").trim());
	    json.setChildName(childAccount.getTruename().replace("/n", "").trim());
	    json.setChildId(childAccount.getChildId());
	    json.setIsPrimaryAccount(1);
	    json.setMsg("登陆成功！");
	    json.setStage(salesman.getAssessStage());
	    if(null!=salesman.getMobile()&&!"".equals(salesman)){
			Calendar calendar=Calendar.getInstance();
			int month=calendar.get(Calendar.MONTH);//上个月
			Salary salary=salaryService.findSalary(salesman.getMobile().trim(),month+"");
			if(null!=salary){
				json.setSalary(salary.getSalary()+"");
			}else{
				json.setSalary("");
			}
		}else{
			json.setSalary("");
		}
	    return new ResponseEntity<JsonCustom>(json, HttpStatus.OK);
	  }
	  
	
}

