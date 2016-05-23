package com.wangge.app.server.service;


import java.util.List;

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
    
      List<OilParameters> Parameters = oilParametersRepository.findByRegionIdIn(regionService.findParentIds(regionId));
       return getOilParameters(Parameters);
    }
    
}   
  
  private OilParameters getOilParameters(List<OilParameters> oilParametersList){
    for(OilParameters  oilParameters : oilParametersList){
        if(null == oilParametersList){
          OilParameters Parameters = oilParametersRepository.findOilParameters();
          return Parameters;
        }else{
             

            if (oilParameters.getKmOilSubsidy() != null && oilParameters.getKmRatio()!=null) {
              return oilParameters;
            }
          
         
            if (oilParameters.getKmOilSubsidy() != null && oilParameters.getKmRatio()==null) {
              OilParameters Parameters = oilParametersRepository.findOilParameters();
              oilParameters.setKmRatio(Parameters.getKmRatio());
              return oilParameters;
            }
            
            if (oilParameters.getKmOilSubsidy() == null && oilParameters.getKmRatio()!=null) {
              OilParameters Parameters = oilParametersRepository.findOilParameters();
              oilParameters.setKmOilSubsidy(Parameters.getKmOilSubsidy());
              return oilParameters;
            }
            
          }
          
    }
    
    return null;
}
  
    private OilParameters getOilParameters(OilParameters oilParameters){
          if(null==oilParameters){
            OilParameters Parameters = oilParametersRepository.findOilParameters();
            return Parameters;
          }else{
            if (oilParameters.getKmOilSubsidy() != null && oilParameters.getKmRatio()==null) {
              OilParameters Parameters = oilParametersRepository.findOilParameters();
              oilParameters.setKmRatio(Parameters.getKmRatio());
            }
            
            if (oilParameters.getKmOilSubsidy() == null && oilParameters.getKmRatio()!=null) {
              OilParameters Parameters = oilParametersRepository.findOilParameters();
              oilParameters.setKmOilSubsidy(Parameters.getKmOilSubsidy());
            }
          }
          return oilParameters;
     
    }
   
  }

