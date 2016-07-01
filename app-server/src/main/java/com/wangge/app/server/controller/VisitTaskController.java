package com.wangge.app.server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.SaojieData;
import com.wangge.app.server.entity.Visit;
import com.wangge.app.server.entity.Visit.VisitStatus;
import com.wangge.app.server.event.afterDailyEvent;
import com.wangge.app.server.monthTask.service.MonthTaskServive;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.VisitRepository;
import com.wangge.app.server.service.DataSaojieService;
import com.wangge.app.server.service.RegistDataService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.service.TaskVisitService;
import com.wangge.app.server.util.SortUtil;
import com.wangge.app.server.vo.VisitVo;

@RestController
@RequestMapping(value = "/v1")
public class VisitTaskController {
	private static final Logger logger = Logger.getLogger(VisitTaskController.class);
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private TaskVisitService taskVisitService;
	@Autowired
	private RegistDataService rds;
	@Resource
	private VisitRepository vr;
	@Resource
	private RegistDataService registDataService;
	@Autowired
	private SalesmanService salesmanService;
	@Resource
  private DataSaojieService dataSaojieService;
	@Resource
	private ApplicationContext cxt;
	@Resource
	private MonthTaskServive monthTaskServive;
	
	Json json = new Json();
	
	/**
	 * 
	 * @Description: 获取拜访任务列表
	 * @param @param salesman
	 * @param @return
	 * @return ResponseEntity<List<Visit>>
	 * @author peter
	 * @date 2015年12月11日
	 * @version V2.0
	 */
	@RequestMapping(value = "/task/{userId}/visitList",method = RequestMethod.POST)
	public ResponseEntity<Json> visitList(@PathVariable("userId")Salesman salesman,@RequestBody JSONObject jsons) {
		int flag = jsons.getIntValue("flag");
		Json result = null;
    try {
      PageRequest pageRequest = SortUtil.buildPageRequest(jsons.getInteger("pageNumber"), jsons.getInteger("pageSize"),"visitVo");
      result = taskVisitService.findBySalesman(salesman.getId(),pageRequest,flag);
      return new ResponseEntity<Json>(result, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      result.setSuccess(false);
      return new ResponseEntity<Json>(result, HttpStatus.UNAUTHORIZED);
      
    }
		
	}
	
	/**
	 * 
	 * @Description: 拉取一条拜访记录
	 * @param @param visitId
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author Peter
	 * @date 2015年12月11日
	 * @version V2.0
	 * 添加拜访任务
	 * @param S
	 * @return 店铺名，图片链接，坐标，备注，摆放时间,状态(待定)
	 */
	@RequestMapping(value = "/task/addVisit",method = RequestMethod.POST)
	public ResponseEntity<String> addVisit(String taskStart,String taskEnd,String rdid,String userid){
		
		Visit visit = new Visit();
		RegistData rData = rds.findRegistDataById(Long.parseLong(rdid));
		Salesman salesman = salesmanService.findSalesmanbyId(userid);
		visit.setSalesman(salesman);
		visit.setRegistData(rData);
		try {
			visit.setBeginTime(sdf.parse(taskStart));
			visit.setExpiredTime(sdf.parse(taskEnd));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  visit.setStatus(VisitStatus.PENDING);
		taskVisitService.addVisit(visit);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	
	/**
	 * 根据用户选择的拜访或已拜访进行处理
	 * @param 状态，任务id,
	 * @return 
	 */
	@RequestMapping(value = "/task/{visitId}/infoVisit",method = RequestMethod.GET)
	public ResponseEntity<VisitVo> visitInfo(@PathVariable("visitId") String visitId) {
		Visit taskVisit = taskVisitService.findByVisitId(Long.parseLong(visitId));
		VisitVo visitVo = new VisitVo();
		if(taskVisit != null && !"".equals(taskVisit)){
			if(VisitStatus.FINISHED.equals(taskVisit.getStatus())){
				visitVo.setShopName(taskVisit.getRegistData().getShopName());
				visitVo.setAddress(taskVisit.getAddress());
				visitVo.setSummary(taskVisit.getSummary());
				visitVo.setStatus(taskVisit.getStatus());
				String imageurl1=taskVisit.getImageurl1();
				String imageurl2=taskVisit.getImageurl2();
				String imageurl3=taskVisit.getImageurl3();
				if(imageurl1 != null && !"".equals(imageurl1)){
					visitVo.setImageurl1(imageurl1);
				}
				if(imageurl2 != null && !"".equals(imageurl2)){
					visitVo.setImageurl2(imageurl2);
				}
				if(imageurl3 != null && !"".equals(imageurl3)){
					visitVo.setImageurl3(imageurl3);
				}
				return new ResponseEntity<VisitVo>(visitVo, HttpStatus.OK);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 保存提交的拜访记录
	 * @param @param jsons
	 * @param @return
	 * @return ResponseEntity<Json>
	 * @author peter
	 * @date 2015年12月11日
	 * @version V2.0
	 */
	@RequestMapping(value = "/task/{userId}/addVisit",method = RequestMethod.POST)
	public ResponseEntity<Json> addVisit(@PathVariable("userId")Salesman salesman,@RequestBody JSONObject jsons) {
	      String id = null;
		try {
		  int fixGeo= jsons.getIntValue("fixGeo");
		  String coordinate=jsons.getString("coordinate");
			if(jsons.containsKey("visitId")){
				String visitId=jsons.getString("visitId");
				Visit taskVisit = taskVisitService.findByVisitId(Long.parseLong(visitId));
				if(taskVisit != null && !"".equals(taskVisit)){
					taskVisit.setAddress(jsons.getString("address"));
					taskVisit.setStatus(VisitStatus.FINISHED);
					taskVisit.setExpiredTime(new Date());
					taskVisit.setSummary(jsons.getString("summary"));
					if(jsons.containsKey("imageurl1")){
						String imageurl1=jsons.getString("imageurl1");
						taskVisit.setImageurl1(imageurl1);
					}
					if(jsons.containsKey("imageurl2")){
						String imageurl2 = jsons.getString("imageurl2");
						taskVisit.setImageurl2(imageurl2);
					}
					if(jsons.containsKey("imageurl3")){
						String imageurl3 = jsons.getString("imageurl3");
						taskVisit.setImageurl3(imageurl3);
					}
					taskVisit.setFinishTime(new Date());
					if(jsons.containsKey("isPrimary")){
					  int isPrimaryAccount = jsons.getIntValue("isPrimary");
            taskVisit.setIsPrimaryAccount(isPrimaryAccount);
            if(isPrimaryAccount == 1){
              id = jsons.getString("childId");
            }else{
              id = jsons.getString("userId");
            }
					}
					taskVisit.setAccountId(id);
					taskVisit.setSalesman(salesman);
					taskVisitService.save(taskVisit);
					RegistData rd = registDataService.findRegistDataById(taskVisit.getRegistData().getId());
					monthTaskServive.saveExecution(rd.getId(), "拜访");
					 if(rd != null){
					   if(fixGeo==1){//修改坐标点 1是修改，0不修改
		            SaojieData saojiedata= dataSaojieService.findByRegistData(rd);
		            if(saojiedata!=null){
		              saojiedata.setCoordinate(coordinate);
		              dataSaojieService.addDataSaojie(saojiedata, salesman);
		            }
		          }
		          cxt.publishEvent(new afterDailyEvent(rd.getRegion().getId(),salesman.getId(),rd.getShopName(), jsons.getString("coordinate"),jsons.getIntValue("isPrimary"),jsons.getString("childId"),4));
		        }
					json.setSuccess(true);
					json.setMsg("保存成功!");
					
					return new ResponseEntity<Json>(json, HttpStatus.CREATED);
				}else{
					json.setMsg("保存失败!");
					return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
				}
			}else{
				Visit taskVisit = new Visit();
				String registId = jsons.getString("registId");
				RegistData rd = registDataService.findRegistDataById(Long.parseLong(registId));
				taskVisit.setRegistData(rd);
				taskVisit.setSalesman(rd.getSalesman());
				taskVisit.setAddress(jsons.getString("address"));
				taskVisit.setStatus(VisitStatus.FINISHED);
				taskVisit.setExpiredTime(new Date());
				taskVisit.setSummary(jsons.getString("summary"));
				if(jsons.containsKey("imageurl1")){
					String imageurl1=jsons.getString("imageurl1");
					taskVisit.setImageurl1(imageurl1);
				}
				if(jsons.containsKey("imageurl2")){
					String imageurl2 = jsons.getString("imageurl2");
					taskVisit.setImageurl2(imageurl2);
				}
				if(jsons.containsKey("imageurl3")){
					String imageurl3 = jsons.getString("imageurl3");
					taskVisit.setImageurl3(imageurl3);
				}
				if(jsons.containsKey("isPrimary")){
          int isPrimaryAccount = jsons.getIntValue("isPrimary");
          taskVisit.setIsPrimaryAccount(isPrimaryAccount);
          if(isPrimaryAccount == 1){
            id = jsons.getString("childId");
          }else{
            id = jsons.getString("userId");
          }
        }
			  taskVisit.setAccountId(id);
				taskVisit.setFinishTime(new Date());
				taskVisit.setSalesman(salesman);
				taskVisitService.save(taskVisit);
				monthTaskServive.saveExecution(Long.parseLong(registId), "拜访");
        if(rd != null){
          if(fixGeo==1){//修改坐标点 1是修改，0不修改
            SaojieData saojiedata= dataSaojieService.findByRegistData(rd);
            if(saojiedata!=null&&coordinate!=null){
              saojiedata.setCoordinate(coordinate);
              dataSaojieService.addDataSaojie(saojiedata, salesman);
            }
          }
          cxt.publishEvent(new afterDailyEvent(rd.getRegion().getId(),salesman.getId(),rd.getShopName(), jsons.getString("coordinate"),jsons.getIntValue("isPrimary"),jsons.getString("childId"),4));
        }
        
				json.setSuccess(true);
				json.setMsg("保存成功!");
				return new ResponseEntity<Json>(json, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addVisit() occur error..."+ e);
			json.setMsg("保存异常!");
			return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
}


