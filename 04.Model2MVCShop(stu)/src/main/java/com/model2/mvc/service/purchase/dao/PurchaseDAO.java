package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {

	public PurchaseDAO() {
		// TODO Auto-generated constructor stub
	}

	public void insertPurchase(Purchase purchase) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction VALUES(seq_transaction_tran_no.NEXTVAL, "
				+ "?, ?, ?, ?, ?, ?, ?, '2', SYSDATE, ?)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getDivyDate());
		stmt.executeUpdate();

		stmt.close();
		con.close();
	}

	public Purchase findPurchase(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE tran_no = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		UserService userService=new UserServiceImpl();
		ProductService prodService=new ProductServiceImpl();

		Purchase purchase = null;
		while (rs.next()) {
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setBuyer(userService.getUser(rs.getString("buyer_id")));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setPaymentOption(rs.getString("payment_option").trim());
			purchase.setPurchaseProd(prodService.getProduct(rs.getInt("prod_no")));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
		}

		rs.close();
		stmt.close();
		con.close();

		return purchase;
	}

	public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception {
		Connection con = DBUtil.getConnection();

		Map<String,Object> map = new HashMap<String,Object>();
		
		String sql = "SELECT * FROM transaction "
				+ "WHERE LOWER(buyer_id) LIKE '%"+userId.toLowerCase()+"%'";
				
		System.out.println("search.getCurrentPage():" + search.getCurrentPage());
		System.out.println("search.getPageSize():" + search.getPageSize());
		
		if (search.getSearchKeyword() != null && !search.getSearchKeyword().equals("") ) {
			sql += " AND order_data BETWEEN '" + search.getSearchKeyword().split(",")[0]
						+ "' AND " + (search.getSearchKeyword().split(",")[1].equals("SYSDATE") ? 
								"SYSDATE" : "'"+search.getSearchKeyword().split(",")[1] + "'");
		}
		sql += " ORDER BY order_data DESC";
		
		//==> TotalCount GET
		System.out.println("PurchaseDAO::Original SQL :: " + sql);
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
//		sql =  "SELECT * FROM transaction  WHERE LOWER(buyer_id) LIKE ?"
//				+ " ORDER BY order_data DESC";
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		
		PreparedStatement stmt = con.prepareStatement(sql);
//		stmt.setString(1, userId.toLowerCase());
		ResultSet rs = stmt.executeQuery();

		System.out.println(search);

		ArrayList<Purchase> list = new ArrayList<Purchase>();
		UserService userService=new UserServiceImpl();
		ProductService prodService=new ProductServiceImpl();

		while (rs.next()) {
			Purchase purchase = new Purchase();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setBuyer(userService.getUser(rs.getString("buyer_id")));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setPaymentOption(rs.getString("payment_option").trim());
			purchase.setPurchaseProd(prodService.getProduct(rs.getInt("prod_no")));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			if (rs.getString("tran_status_code") != null) 
				purchase.setTranCode(rs.getString("tran_status_code").trim());

			list.add(purchase);
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

	public void updatePurchase(Purchase purchase) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET demailaddr = ?, dlvy_date = ?, "
				+"dlvy_request = ?, payment_option = ?, receiver_name = ?, "
				+ "receiver_phone = ? WHERE tran_no = ?";

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, purchase.getDivyAddr());
		stmt.setString(2, purchase.getDivyDate());
		stmt.setString(3, purchase.getDivyRequest());
		stmt.setString(4, purchase.getPaymentOption());
		stmt.setString(5, purchase.getReceiverName());
		stmt.setString(6, purchase.getReceiverPhone());
		stmt.setInt(7, purchase.getTranNo());
		stmt.executeUpdate();

		stmt.close();
		con.close();
	}

	public void updateTranCode(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();

		int no = 0;
		String tranCode = purchase.getTranCode().trim();
		System.out.println("tranCode : "+tranCode);

		String sql = "UPDATE transaction SET tran_status_code = ?";
		if (tranCode.equals("2")) {
			sql += " WHERE prod_no = ?";
			no = purchase.getPurchaseProd().getProdNo();
			tranCode = "3";
		} else if (tranCode.equals("3")) {
			sql += " WHERE tran_no = ?";
			no = purchase.getTranNo();
			tranCode = "4";
		}
		System.out.println("PurchaseDAO sql :: "+sql);

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, tranCode);
		stmt.setInt(2, no);
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

		System.out.println("PurchaseDAO :: make SQL :: "+ sql);	

		return sql;
	}

}
