package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.app.server.entity.Receipt;


public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

@Query(value="select * from "
		+ "(select  '主账号' ,  r.amount_collected, r.receipt_type, r.create_time"+
  " from BIZ_RECEIPT r"+
  " right join sys_salesman s on s.user_id = r.account_id"+
 " where r.ORDER_NO = ?1 "+
 
" union"+

" select  c.truename,  r.amount_collected, r.receipt_type, r.create_time"+
 " from BIZ_RECEIPT r"+
  " right join biz_child_account c on r.account_id = c.CHILD_ID"+
 " where r.ORDER_NO = ?1 ) t  order by t.create_time desc ", nativeQuery = true)
	public List<Object[]> findReceiptVoByOrderNo(String orderno);
 
  public List<Receipt> findAllByOrderNo(String string);
	

}
