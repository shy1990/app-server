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
	
	private  int totalPages;
	
  public List<T> getContent() {
    return content;
  }
  public void setContent(List<T> content) {
    this.content = content;
  }
  public int getTotalPages() {
    return totalPages;
  }
  public void setTotalPages(int totalPages,int pageSize) {
    this.totalPages = totalPages % pageSize == 0 ? totalPages/ pageSize : totalPages/ pageSize + 1;
  }
	
}
