//package com.wangge.app.server.controller;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.annotation.Resource;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.wangge.app.server.entity.Phone;
//import com.wangge.app.server.service.PhoneService;
//
////@RestController
//@RequestMapping({ "/v1/phone" })
//public class PhoneController {
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//
//	@Resource
//	private PhoneService phoneService;
//
//	@RequestMapping(value = "/addPhone", method = RequestMethod.POST)
//	public ResponseEntity<String> addPhone(String phoneModel,String phoneNum,String actTime,String intTime) throws ParseException {
//		Phone phone = new Phone();
//		phone.setPhoneModel(phoneModel);
//		phone.setPhoneNum(phoneNum);
//		phone.setActTime(actTime);
//		phone.setIntTime(intTime);
//		Date date = new Date();
//		phone.setServerTime(sdf.format(date));
//		
//		phoneService.addPhone(phone);
//		
//		return new ResponseEntity<String>("OK", HttpStatus.OK);
//	}
//}
