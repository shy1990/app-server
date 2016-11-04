package com.wangge.app.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.repository.RegistDataRepository;
import com.wangge.app.server.repository.RegistRepository;
import com.wangge.app.server.repositoryimpl.MemberImpl;

@Service
public class RegistDataService {

	@Resource
	private RegistDataRepository registDataRepository;
	@Autowired
	private RegistRepository registRepository;
	@Autowired
	private MemberImpl memberImpl;
	
	public Regist findByRegion(Region region) {
		
		return registRepository.findByRegion(region);
	}
	
	public List<RegistData> findAll(){
		
		return registDataRepository.findAll();
	}
	
	public RegistData addRegistData(RegistData registData) {

		RegistData data =  registDataRepository.save(registData);
		
		return data;
	}


	
	/*public int getDataCountByRegistId(Long registId) {
		List<RegistData>  dataregist = registDataRepository.findByRegistId(registId);
		if(dataregist != null && dataregist.size() > 0){
			return dataregist.size();
		}else{
			return 0;
		}
	}*/
	
	public Regist findByOrder(Integer id) {
		
		return registRepository.findOne(Long.parseLong(String.valueOf(id)));
	}
	
	public void updateRegist(Regist regist) {
		registRepository.save(regist);
	}
	
	public RegistData findRegistDataById(Long registDataId) {
		
		return registDataRepository.findOne(registDataId);
	}
	
	public Map<String,String> findMemberInfo(String loginAccount){
		return memberImpl.findMemberInfo(loginAccount);
	}

  public RegistData findByMemberId(String memberId) {
    return registDataRepository.findByMemberId(memberId);
  }

  public Double countByRegionId(String area) {
    // TODO Auto-generated method stub
    return registDataRepository.countByRegionId(area);
  }
  
  public List<RegistData>  findByLoginAccount(String loginAccount){
    return registDataRepository.findByLoginAccount(loginAccount);
  }
  
  
  /**
   * 
  * @Title: getSalesmanId 
  * @Description: TODO(根据商城用户电话号码查询注册信息，返回业务id) 
  * @param @param mobile
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  public String getSalesmanId(String mobile) {
    RegistData data = registDataRepository.findByPhoneNum(mobile);
    if(data != null){
      return data.getAccountId();
    }
    return null;
  }

  public String getRegionName(String storePhone) {
    RegistData data = registDataRepository.findByPhoneNum(storePhone);
    if(data != null){
      return data.getRegion().getName();
    }
    return null;
  }

  public Map<String, String> getMap(String storePhone) {
    Map<String, String> map = new HashMap<String, String>();
    RegistData data = registDataRepository.findByPhoneNum(storePhone);
    if(data != null){
     
      map.put("regionName", data.getRegion().getName());
      map.put("shopName", data.getShopName());
      map.put("regionId", data.getRegion().getId());
      return map;
    }
    
    return null;
  }

  public RegistData getRegistData(String registId) {
   
    return  registDataRepository.findOne(Long.parseLong(registId));
  }

  public RegistData findByPhoneNum(String phone){
    
    return registDataRepository.findByPhoneNum(phone);
  }

    public void saveRegistData(RegistData registData){
        registDataRepository.save(registData);
    }
}

