package com.wangge.app.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangge.app.server.entity.Assess;
import com.wangge.app.server.entity.Assess.AssessStatus;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Color;
import com.wangge.app.server.repository.AssessRepository;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.repositoryimpl.ActiveImpl;
import com.wangge.app.server.vo.RegionVo;
import com.wangge.app.server.vo.RegistAreaVo;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class AssessService {

  @Autowired
  private SalesmanRepository salesmanRepository;
  @Autowired
  private AssessRepository assessRepository;
  @Autowired
  private RegionService regionService;
  @Resource
  private ActiveImpl apl;
  @Resource
  private RegistDataService registDataService;

  /**
   * 
   * @Description: 修改后的业务注册区域(可开发，不可开发，已开发)
   * @param @param salesman
   * @param @return
   * @return Map<String,List<RegionVo>>
   * @author
   * @date 2016年1月16日
   * @version V2.0
   */
  
  public List<RegistAreaVo>  getRegistRegions(Salesman salesman){
    List<RegistAreaVo> vo = new ArrayList<RegistAreaVo>();
    if(salesman.getIsOldSalesman()==1){
      for(Region reg:regionService.findRegiondbyParentid(salesman.getRegion().getId())){
          RegistAreaVo r = new RegistAreaVo();
          r.setId(reg.getId());
          r.setName(reg.getName());
          r.setCoordinates(reg.getCoordinates());
          r.setColorStatus(getPercent(reg.getId()).getNum());;
          vo.add(r);
      }
      
    }else{
      List<Assess> assTasks = assessRepository.findBySalesman(salesman);
      for(Assess assess : assTasks){
      //  if (AssessStatus.PENDING.equals(assess.getStatus()) || AssessStatus.AGREE.equals(assess.getStatus())) {
      //    if (AssessStatus.PENDING.equals(assess.getStatus())) {
            String assArea = assess.getAssessDefineArea();
            if(assArea != null && !"".equals(assArea)){
              String[] strRegion = assArea.split(",");
              for (int i = 0; i < strRegion.length; i++) {
                Region region = regionService.findRegion(strRegion[i].trim());
                if(region != null && !"".equals(region)){
                  RegistAreaVo r = new RegistAreaVo();
                  r.setId(region.getId());
                  r.setName(region.getName());
                  r.setCoordinates(region.getCoordinates());
                  r.setColorStatus(getPercent(region.getId()).getNum());;
                  vo.add(r);
                }
              }
            }
          }
    }
    return vo;
  }
  
  private Color getPercent(String area){
    Double a =  apl.examTwiceShopNum(area);
    Double b = registDataService.countByRegionId(area);
    if(b <= 0){
      return Color.black;
    }
    if(a/b > 0.4){
      return Color.green;
    }else {
      Double c = apl.examOneceShopNum(area);
      if(c/b > 0.5){
        return Color.yellow;
      }else{
        return Color.black;
      }

    }
    
   
  }
  
  
  
  public Map<String, List<RegionVo>> getRegistRegion(Salesman salesman) {
    Map<String, List<RegionVo>> result = Maps.newHashMap();
    List<RegionVo> dev = Lists.newArrayList();
    List<RegionVo> nodev = Lists.newArrayList();
    List<RegionVo> deved = Lists.newArrayList();
    List<Assess> assTasks = assessRepository.findBySalesman(salesman);
    // TODO 可用java8 stream过滤
    for (Assess assess : assTasks) {
      // if (SaojieStatus.PENDING.equals(taskSaojie.getStatus())) {
      if (AssessStatus.PENDING.equals(assess.getStatus()) || AssessStatus.AGREE.equals(assess.getStatus())) {
        if (AssessStatus.PENDING.equals(assess.getStatus())) {
          String assArea = assess.getAssessDefineArea();
          if(assArea != null && !"".equals(assArea)){
            String[] strRegion = assArea.split(",");
            for (int i = 0; i < strRegion.length; i++) {
              Region region = regionService.findRegion(strRegion[i].trim());
              if(region != null && !"".equals(region)){
                RegionVo r = new RegionVo();
                r.setId(region.getId());
                r.setName(region.getName());
                r.setCoordinates(region.getCoordinates());
                dev.add(r);
              }
            }
          }
        } else {
          String assArea = assess.getAssessDefineArea();
          if(assArea != null && !"".equals(assArea)){
            if (assArea != null && !"".equals(assArea)) {
              String[] strRegion = assArea.split(",");
              for (int i = 0; i < strRegion.length; i++) {
                Region region = regionService.findRegion(strRegion[i].trim());
                if(region != null && !"".equals(region)){
                  RegionVo r = new RegionVo();
                  r.setId(region.getId());
                  r.setName(region.getName());
                  r.setCoordinates(region.getCoordinates());
                  deved.add(r);
                }
              }
            }
          }
          }
      }else{
        String assArea = assess.getAssessDefineArea();
        if(assArea != null && !"".equals(assArea)){
          if (assArea != null && !"".equals(assArea)) {
            String[] strRegion = assArea.split(",");
            for (int i = 0; i < strRegion.length; i++) {
              Region region = regionService.findRegion(strRegion[i].trim());
              if(region != null && !"".equals(region)){
                RegionVo r = new RegionVo();
                r.setId(region.getId());
                r.setName(region.getName());
                r.setCoordinates(region.getCoordinates());
                deved.add(r);
              }
            }
          }
        }
      }
    }
    result.put("dev", dev);
    result.put("nodev", nodev);
    result.put("deved", deved);
    return result;
  }
  
  /**
   * 
   * @Description: 客户开发列表
   * @param @param 
   * @param @param 
   * @param @return   
   * @return  
   * @throws
   * @author
   * @date 
   */
  public List<Assess> getAllList(){
    List<Assess> list =(List<Assess>) assessRepository.findAll();
    
    return list;
  }
  
  public String addAssesses(Assess assess){
    
    assessRepository.save(assess);
    return "OK";
  }

  public Double countByAssessDefineArea(String area) {
    
    return assessRepository.countByAssessDefineArea(area);
  }
  
}
	
	
