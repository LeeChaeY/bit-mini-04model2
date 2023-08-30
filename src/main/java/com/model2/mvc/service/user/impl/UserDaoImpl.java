package com.model2.mvc.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.dao.UserDao;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println(":: "+getClass()+".setSqlSession() Call.....");
		this.sqlSession = sqlSession;
	}

	public UserDaoImpl() {
		System.out.println(":: "+getClass()+" default Constructor Call.....");
	}
	
	@Override
	public int addUser(User user) throws Exception {
		return sqlSession.insert("UserMapper.addUser", user);
	}

	@Override
	public User getUser(String userId) throws Exception {
		return sqlSession.selectOne("UserMapper.getUser", userId);
	}
	
	@Override
	public int updateUser(User user) throws Exception {
		return sqlSession.update("UserMapper.updateUser", user);
	}
	
	@Override
	public int removeUser(String userId) throws Exception {
		return sqlSession.delete("UserMapper.removeUser", userId);
	}

	@Override
	public List<User> getUserList(Search search) throws Exception {
		if (search.getSearchKeyword() != null && !search.getSearchKeyword().equals(""))
			search.setSearchKeyword("%"+search.getSearchKeyword().toLowerCase()+"%");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("startRowNum", (search.getCurrentPage()-1) * search.getPageSize() + 1);
		map.put("endRowNum", search.getCurrentPage() * search.getPageSize());
		
		return sqlSession.selectList("UserMapper.getUserList", map);
	}
	
	@Override
	public int getTotalCount(Search search) throws Exception {
		if (search.getSearchKeyword() != null && !search.getSearchKeyword().equals(""))
			search.setSearchKeyword("%"+search.getSearchKeyword().toLowerCase()+"%");
		
		return sqlSession.selectOne("UserMapper.getTotalCount", search);
	}

}
