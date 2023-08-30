package com.model2.mvc.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDao;
import com.model2.mvc.service.domain.User;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	@Autowired
	@Qualifier("userDaoImpl")
	UserDao userDao;
	public void setUserDao(UserDao userDAO) {
		System.out.println(":: "+getClass()+".setUserDAO() Call.....");
		this.userDao = userDAO;
	}
	
	public UserServiceImpl() {
		System.out.println(":: "+getClass()+" default Constructor Call.....");
	}

	public int addUser(User user) throws Exception {
		return userDao.addUser(user);
	}

	public User loginUser(User user) throws Exception {
			User dbUser=userDao.getUser(user.getUserId());

			if(! dbUser.getPassword().equals(user.getPassword()))
				throw new Exception("로그인에 실패했습니다.");
			
			return dbUser;
	}

	public User getUser(String userId) throws Exception {
		return userDao.getUser(userId);
	}

	public Map<String,Object> getUserList(Search search) throws Exception {
		int totalCount = userDao.getTotalCount(search);
		System.out.println("totalCount :: "+totalCount);
		
		List<User> list = userDao.getUserList(search);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", totalCount);
		map.put("list", list);
		
		return map;
	}

	public int updateUser(User user) throws Exception {
		return userDao.updateUser(user);
	}
	
	public int removeUser(String userId) throws Exception {
		return userDao.removeUser(userId);
	}

	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userDao.getUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}
}