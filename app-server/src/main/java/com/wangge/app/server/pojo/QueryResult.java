package com.wangge.app.server.pojo;

import java.util.List;

/**
 * ��ѯ���
 * @author dell
 *
 * @param <T>
 */
public class QueryResult<T> {
    
	private List<T> content;
	
	private  Long totalPages;
	
  public List<T> getContent() {
    return content;
  }
  public void setContent(List<T> content) {
    this.content = content;
  }
  public Long getTotalPages() {
    return totalPages;
  }
  public void setTotalPages(Long totalrecord , Long pageSize) {
    this.totalPages = totalrecord % pageSize == 0 ? totalrecord/ pageSize : totalrecord/ pageSize + 1;
  }
	
}
