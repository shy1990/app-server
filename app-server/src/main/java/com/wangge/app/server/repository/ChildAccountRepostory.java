package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.ChildAccount;
/**
 * 
* @ClassName: ChildAccountRepostory
* @Description: TODO(子账号repository接口)
* @author SongBaoZhen
* @date 2016年3月25日 下午5:29:02
*
 */
public interface ChildAccountRepostory extends JpaRepository<ChildAccount, Long> {

  List<ChildAccount> findByParentId(String parentId);

  ChildAccount findBySimId(String string);


}
