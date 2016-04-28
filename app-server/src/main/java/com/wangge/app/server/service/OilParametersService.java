package com.wangge.app.server.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.OilParameters;
import com.wangge.app.server.repository.OilParametersRepository;

/**
 * 
* @ClassName: OilParametersService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author A18ccms a18ccms_gmail_com
* @date 2016年4月22日 上午10:00:32
*
 */
@Service
public class OilParametersService {

  @Resource
  private OilParametersRepository oilParametersRepository;
  @Resource
  private RegionService regionService;
  
  public OilParameters getOilParameters (String regionId){
   
   
    OilParameters oilParameters = oilParametersRepository.findByRegionId(regionId);
    if(oilParameters != null){
      
      return getOilParameters(oilParameters);
   
    }else{
    
      OilParameters Parameters = oilParametersRepository.findByRegionIdIn(regionService.findParentIds(regionId));
       return getOilParameters(Parameters);
    }
    
}
    private OilParameters getOilParameters(OilParameters oilParameters){
     
          if (oilParameters.getKmOilSubsidy() != null) {
            return oilParameters;
          } else {
            OilParameters Parameters = oilParametersRepository.findOilParameters();
            oilParameters.setKmOilSubsidy(Parameters.getKmOilSubsidy());
            return oilParameters;
          }
    }
   
  }


