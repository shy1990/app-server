package com.wangge.app.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class SalesmanService {


	@Autowired
	private SalesmanRepository salesmanRepository;

	/*public Salesman findByUsernameAndPassword(String username, String password) {

		Salesman salesman = salesmanRepository.findByUsernameAndPassword(
				username, password);

		return salesman;
	}*/

	public Salesman findByUsername(String username) {

		return salesmanRepository.findByUserUsername(username);
	}

	public void save(Salesman salesman) {
		salesmanRepository.save(salesman);
	}

	public List<Salesman> findAll(){
		return salesmanRepository.findAll();
	}
	/*public Json login(String username, String password, String phone) {
		Json json = new Json();
		Salesman salesman = salesmanRepository.findByUsernameAndPassword(username, password);
		if(salesman !=null && !"".equals(salesman)){
			if (!password.equals(salesman.getPassword()) || phone.equals(salesman.getPhone())) {
				json.setMsg("用戶名或密码错误！");
			}else if(!phone.equals(salesman.getPhone())){
				json.setMsg("手机号错误！");
			}else{
				json.setMsg("登陆成功！");
			}
		}
		return json;
	}*/

}
