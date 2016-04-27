package com.wangge.app.server.service;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.ApplyPrice;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.repository.ApplyPriceRepository;
import com.wangge.app.server.repository.RegionRepository;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.vo.Apply;

@Service
public class ApplyPriceService {
	@Resource
	private ApplyPriceRepository apr;
	
	@Resource
	private SalesmanRepository sr;
	
	@Resource
	private RegionRepository rr;
	/**
	 * 
	 * @Description: 保存申请
	 * @param @param ap
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author changjun
	 * @date 2015年12月2日
	 */
	public String saveApply(ApplyPrice ap,String salesmanId,String regionId){
		ap.setSalesman(sr.findOne(salesmanId));
		ap.setRegion(rr.findById(regionId));
		try {
			apr.save(ap);
			return "suc";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "err";
	}
	/**
	 * 
	 * @Description: 获取申请列表
	 * @param @param sid
	 * @param @param pageRequest
	 * @param @return   
	 * @return Page<ApplyPrice>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月3日
	 */
	public List<Apply> getList(String sid,Pageable pageRequest){
		Salesman sm = sr.findOne(sid);
		
		Page<ApplyPrice> aplist = apr.findBySalesman(sm, pageRequest);
		
		
		List<Apply> list = new ArrayList<Apply>();
		Apply app ;
		String state = "";
		for(ApplyPrice ap:aplist){
			app = new Apply();
			app.setTotalPage(aplist.getTotalPages());
			app.setAmount(ap.getPriceRange());
			app.setId(ap.getId());
			app.setApplyDate(ap.getApplyTime());
			app.setSalesman(ap.getSalesman().getUser().getUsername());
			app.setGoodsName(ap.getProductName());
			app.setReason(ap.getApplyReason());
			app.setReplyReason(ap.getApproveReason());
			if("0".equals(ap.getStatus())){
				state = "审核中";
			}else if("1".equals(ap.getStatus())){
				state = "审核通过";
			}else{
				state = "审核未通过";
			}
			app.setStatus(state);
			list.add(app);
		}
		
		return list;
	}
	/**
	 * 
	 * @Description: 根据id查看申请详情
	 * @param @param id
	 * @param @return   
	 * @return ApplyPrice  
	 * @throws
	 * @author changjun
	 * @date 2015年12月3日
	 */
	public ApplyPrice selApplyById(Long id){
		return apr.findOne(id);
	}
}
