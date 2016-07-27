package com.wangge.app.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.app.server.AppServerApplication;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.entity.User;
import com.wangge.app.server.service.SalesmanService;

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
		user.setStatus(User.UserStatus.NORMAL);
		user.setUsername("yewu01");
		ur.save(user);
	//	entity.setUser(user);
		sr.save(entity);
	}
	@Test
	public void testDel() {
		
		sr.delete("10001");
		ur.delete("10001");
	}
	
	@Test
	public void tset(){
	  Salesman s = srService.findByUsernameAndPassword("caochangzhi", "123456");
	  System.out.println("=============="+s.getMobile());
	}
}
