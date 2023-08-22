package com.model2.mvc.service.user.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.domain.User;


public class UserServiceImpl implements UserService{
	
	private UserDAO userDAO;
	
	public UserServiceImpl() {
		userDAO=new UserDAO();
	}

	public void addUser(User user) throws Exception {
		userDAO.insertUser(user);
	}

	public User loginUser(User user) throws Exception {
			User dbUser=userDAO.findUser(user.getUserId());

			if(! dbUser.getPassword().equals(user.getPassword()))
				throw new Exception("로그인에 실패했습니다.");
			
			return dbUser;
	}

	public User getUser(String userId) throws Exception {
		return userDAO.findUser(userId);
	}

	public Map<String,Object> getUserList(Search search) throws Exception {
		return userDAO.getUserList(search);
	}

	public void updateUser(User user) throws Exception {
		userDAO.updateUser(user);
	}

	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userDAO.findUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}
}