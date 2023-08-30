package com.model2.mvc.service.user;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;


public interface UserService {
	
	public int addUser(User user) throws Exception;
	
	public User loginUser(User user) throws Exception;
	
	public User getUser(String userId) throws Exception;
	
	public Map<String,Object> getUserList(Search search) throws Exception;
	
	public int updateUser(User user) throws Exception;
	
	public int removeUser(String userId) throws Exception;
	
	public boolean checkDuplication(String userId) throws Exception;
	
}