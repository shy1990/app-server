package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Receipt;


public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

@Query(value="select  '主账号' ,  r.amount_collected, r.receipt_type, r.create_time"+
  " from BIZ_RECEIPT r"+
  " left join sys_salesman s on s.user_id = r.account_id"+
  " left join biz_child_account c on s.user_id = c.parent_id"+
 " where r.ORDER_NO = ?1"+
 
" union"+

" select  c.truename,  r.amount_collected, r.receipt_type, r.create_time"+
 " from BIZ_RECEIPT r"+
  " left join biz_child_account c on r.account_id = c.parent_id"+
 " where r.ORDER_NO = ?1", nativeQuery = true)
	public List<Object[]> findReceiptVoByOrderNo(String orderno);
	

}
