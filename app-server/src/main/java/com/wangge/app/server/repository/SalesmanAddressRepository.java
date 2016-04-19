package com.wangge.app.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.app.server.entity.SalesmanAddress;

public interface SalesmanAddressRepository extends JpaRepository<SalesmanAddress,Long > {

  SalesmanAddress findByUserId(String userId);
  
    

}
