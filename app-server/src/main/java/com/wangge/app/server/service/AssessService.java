package com.wangge.app.server.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangge.app.server.entity.Assess;
import com.wangge.app.server.entity.Assess.AssessStatus;
import com.wangge.app.server.entity.Regist;
import com.wangge.app.server.entity.Regist.RegistStatus;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.repository.AssessRepository;
import com.wangge.app.server.repository.RegistDataRepository;
import com.wangge.app.server.repository.RegistRepository;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.vo.RegionVo;
import com.wangge.common.entity.Region;
import com.wangge.common.repository.RegionRepository;

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
          String assArea = assess.getAssessArea();
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
        } else {
          String assArea = assess.getAssessArea();
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
      }else{
        String assArea = assess.getAssessArea();
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
    result.put("dev", dev);
    result.put("nodev", nodev);
    result.put("deved", deved);
    return result;
  }
}