package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {

	public ProductDAO() {
	}

	public void insertProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO product VALUES (seq_product_prod_no.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";

		PreparedStatement stmt = con.prepareStatement(sql);
		//		PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.executeUpdate();

		//		ResultSet rs = stmt.getGeneratedKeys();
		//		int result = 0;
		//		if (rs.next()) result = rs.getInt("PROD_NO");

		stmt.close();
		con.close();

		//		return result;
	}

	public Product findProduct(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT P.*, tran_status_code FROM product P LEFT OUTER JOIN transaction T ON P.prod_no = T.prod_no WHERE P.prod_no = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			
			if (rs.getString("tran_status_code") != null) 
				product.setProTranCode(rs.getString("tran_status_code").trim());
		}
		
		rs.close();
		stmt.close();
		con.close();

		return product;
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		Connection con = DBUtil.getConnection();

		Map<String,Object> map = new HashMap<String,Object>();
				
		String sql = "SELECT P.*, tran_status_code FROM product P LEFT OUTER JOIN transaction T ON P.prod_no = T.prod_no";
		
		System.out.println("search.getCurrentPage():" + search.getCurrentPage());
		System.out.println("search.getPageSize():" + search.getPageSize());

		if (search.getSearchCondition() != null &&  !search.getSearchKeyword().equals("") ) {
			if (search.getSearchCondition().equals("1")) {
				sql += " WHERE LOWER(prod_name) LIKE '%" + search.getSearchKeyword().toLowerCase() + "%'";
			} else if (search.getSearchCondition().equals("2")) {
				sql += " WHERE price BETWEEN " + search.getSearchKeyword().split(",")[0]
						+ " AND " + search.getSearchKeyword().split(",")[1];
			}
		}
		if (search.getOrderCondition() != null) {
			if ( search.getOrderCondition().equals("1")) {
				sql += " order by P.price";
			} else if ( search.getOrderCondition().equals("2")) {
				sql += " order by P.price DESC";
			}
		} else {
			sql += " order by P.prod_no";
		}
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		System.out.println("ProductDAO::Original SQL :: " + sql);

		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);

		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		System.out.println(search);

		List<Product> list = new ArrayList<Product>();

		while (rs.next()) {
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			if (rs.getString("tran_status_code") != null) 
				product.setProTranCode(rs.getString("tran_status_code").trim());
			
			list.add(product);
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

	public void updateProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product SET prod_name = ?, prod_detail = ?, "
				+ "manufacture_day = ?, price = ?, image_file = ? WHERE prod_no = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.setInt(6, product.getProdNo());
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

		System.out.println("ProductDAO :: make SQL :: "+ sql);	

		return sql;
	}

}
