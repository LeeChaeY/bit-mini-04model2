package com.model2.mvc.service.user.dao;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;


public interface UserDao {

	public int addUser(User user) throws Exception;

	public User getUser(String userId) throws Exception;

	public List<User> getUserList(Search search) throws Exception;

	public int updateUser(User user) throws Exception;
	
	public int removeUser(String userId) throws Exception;

	public int getTotalCount(Search search) throws Exception;
}