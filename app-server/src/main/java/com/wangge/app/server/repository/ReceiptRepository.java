package com.wangge.app.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.Receipt;


public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	public List<Receipt> findByOrderno(String orderno);

}
