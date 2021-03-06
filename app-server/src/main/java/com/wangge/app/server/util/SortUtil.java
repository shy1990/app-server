package com.wangge.app.server.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortUtil {
	/**
     * 创建分页请求.
     */
    public static PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
        Sort sort = null;
        //根据下单时间降序排序
        if ("push".equals(sortType)) {
            sort = new Sort(Direction.DESC, "sendTime");
        }else if ("order".equals(sortType)) {
            sort = new Sort(Direction.DESC, "createTime");
        } else if("apply".equals(sortType)){
        	 sort = new Sort(Direction.DESC, "applyTime");
        }else if("visitVo".equals(sortType)){
          sort = new Sort(Direction.DESC,"id");
        }
        return new PageRequest(pageNumber - 1, pagzSize, sort);
    }
}
