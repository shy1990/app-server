//package com.wangge.app.server.controller;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.wangge.app.server.entity.Changerprice;
//import com.wangge.app.server.entity.Saojie;
//import com.wangge.app.server.service.ChangePriceService;
//
//
//@Controller
//@RequestMapping(value = "/v1/price/")
//public class PriceController {
//	@Resource
//	private ChangePriceService cps;
//	
//	@RequestMapping(value="/findAllChange", method = RequestMethod.POST)
//	public ResponseEntity<List<Changerprice>> findAll(){
//		List<Changerprice> list = cps.findAllChangerprice();
////		List<Map<String,Object>> clist = new ArrayList<Map<String,Object>>();
////		if(list != null && list.size()>0){
////			for(Changerprice cpc : list){
////				Map<String,Object> m = new HashMap<String,Object>();
////				m.put("applyreason", cpc.getApplyReason());
////				m.put("applytime", cpc.getApplyTime());
////				if(cpc.getApproveReason()!=null){
////					m.put("approvereason", cpc.getApproveReason());
////				}else{
////					m.put("approvereason", "无");
////				}
////				m.put("approvetime", cpc.getApproveTime());
////				m.put("id", cpc.getId());
////				m.put("pricerange", cpc.getPricerange());
////				m.put("productname", cpc.getProductname());
////				m.put("regionname", cpc.getRegion().getName());
////				m.put("salemanname", cpc.getSalesman().getUser().getNickname());
////				m.put("status", cpc.getStatus());
////				if(cpc.getUser()!=null){
////					m.put("username", cpc.getUser().getNickname());
////				}else{
////					m.put("username", "无");
////				}
////			
////				clist.add(m);
////			}
////		}
//		
//		return new ResponseEntity<List<Changerprice>>(list,HttpStatus.OK);
//	}
//	
//	@RequestMapping(value="/upstatus", method = RequestMethod.POST)
//	public ResponseEntity<String> updatePrice(String id){
//		Changerprice changerprice = cps.findById(id.trim());
//		changerprice.setStatus(changerprice.getStatus().AGREE);
//		cps.saveChangerprice(changerprice);;
//		return new ResponseEntity<String>("OK",HttpStatus.OK);
//	}
//}
