package com.model2.mvc.service.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;


public class UserDAO {

	public UserDAO(){
	}

	public void insertUser(User user) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO users VALUES (? , ? , ? , 'user', ?, ? ,? ,? ,SYSDATE)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, user.getUserId());
		stmt.setString(2, user.getUserName());
		stmt.setString(3, user.getPassword());
		stmt.setString(4, user.getSsn());
		stmt.setString(5, user.getPhone());
		stmt.setString(6, user.getAddr());
		stmt.setString(7, user.getEmail());
		stmt.executeUpdate();

		stmt.close();
		con.close();
	}

	public User findUser(String userId) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM users WHERE LOWER(user_id) LIKE ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userId.toLowerCase());

		ResultSet rs = stmt.executeQuery();

		User user = null;
		while (rs.next()) {
			user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			user.setSsn(rs.getString("ssn"));
			user.setPhone(rs.getString("cell_phone"));
			user.setAddr(rs.getString("addr"));
			user.setEmail(rs.getString("email"));
			user.setRegDate(rs.getDate("reg_date"));
		}

		rs.close();
		stmt.close();
		con.close();

		return user;
	}

	public Map<String,Object> getUserList(Search search) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();

		Connection con = DBUtil.getConnection();

		String sql = "SELECT *  FROM  users ";

		System.out.println("search.getCurrentPage():" + search.getCurrentPage());
		System.out.println("search.getPageSize():" + search.getPageSize());

		if (search.getSearchCondition() != null &&  !search.getSearchKeyword().equals("") ) {
			if ( search.getSearchCondition().equals("0")) {
				sql += " WHERE LOWER(user_id) LIKE '%" + search.getSearchKeyword().toLowerCase()+"%'";
			} else if ( search.getSearchCondition().equals("1")) {
				sql += " WHERE LOWER(user_name) LIKE '%" + search.getSearchKeyword().toLowerCase()+"%'";
			}
		}
		sql += " ORDER BY user_id";

		System.out.println("UserDAO::Original SQL :: " + sql);

		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);

		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);

		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		System.out.println(search);

		List<User> list = new ArrayList<User>();

		while (rs.next()) {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			user.setSsn(rs.getString("ssn"));
			user.setPhone(rs.getString("cell_phone"));
			user.setAddr(rs.getString("addr"));
			user.setEmail(rs.getString("email"));
			user.setRegDate(rs.getDate("reg_date"));

			list.add(user);
		}
		
		map.put("totalCount", totalCount);
		map.put("list", list);
		
		System.out.println("list.size() : "+ list.size());
		System.out.println("map().size() : "+ map.size());
		
		rs.close();
		stmt.close();
		con.close();

		return map;
	}

	public void updateUser(User user) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE users SET user_name = ?, cell_phone = ?, "
				+ "addr = ?, email = ? WHERE LOWER(user_id) LIKE ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, user.getUserName());
		stmt.setString(2, user.getPhone());
		stmt.setString(3, user.getAddr());
		stmt.setString(4, user.getEmail());
		stmt.setString(5, user.getUserId().toLowerCase());
		stmt.executeUpdate();

		stmt.close();
		con.close();
	}

	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) "+
				"FROM ( " +sql+ ") countTable";

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
				" 	FROM (	"+sql+" ) inner_table "+
				"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();

		System.out.println("UserDAO :: make SQL :: "+ sql);	

		return sql;
	}
}