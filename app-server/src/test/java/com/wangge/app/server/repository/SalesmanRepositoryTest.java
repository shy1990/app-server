package com.wangge.app.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.AppServerApplication;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.common.repository.RegionRepository;
import com.wangge.security.entity.User;
import com.wangge.security.entity.User.UserStatus;
import com.wangge.security.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class SalesmanRepositoryTest {
	@Autowired
	private SalesmanRepository sr;
	@Autowired
	private UserRepository ur;
	@Autowired
	private RegionRepository rr;
	 @Autowired
	  private SalesmanService srService;

	@Test
	public void testAdd() {
		Salesman entity=new Salesman();
		entity.setRegion(rr.findOne("100"));
		User user = new User();
		user.setId("10001");
		user.setNickname("业务01");
		user.setPassword("123456");
		user.setPhone("18615696354");
		user.setStatus(UserStatus.NORMAl);
		user.setUsername("yewu01");
		ur.save(user);
		entity.setUser(user);
		sr.save(entity);
	}
	@Test
	public void testDel() {
		
		sr.delete("10001");
		ur.delete("10001");
	}
	
	@Test
	public void tset(){
	  String s = srService.getSalesman("A371725210");
	  System.out.println("=============="+s);
	}
}
