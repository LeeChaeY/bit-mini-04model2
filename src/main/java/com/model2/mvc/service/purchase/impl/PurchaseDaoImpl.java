package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.PanelUI;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.dao.PurchaseDao;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println(":: "+getClass()+".setSqlSession() Call.....");
		this.sqlSession = sqlSession;
	}

	public PurchaseDaoImpl() {
		System.out.println(":: "+getClass()+" default Constructor Call.....");
	}

	public int addPurchase(Purchase purchase) throws Exception {
		return sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		Purchase purchase = sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
		purchase.setBuyer((User)sqlSession.selectOne("UserMapper.getUser", purchase.getBuyer().getUserId()));
		purchase.setPurchaseProd((Product)sqlSession.selectOne("ProductMapper.getProduct", purchase.getPurchaseProd().getProdNo()));
		purchase.setPaymentOption(purchase.getPaymentOption().trim());
		purchase.setTranCode(purchase.getTranCode().trim());
		
		return purchase;
	}
	
	@Override
	public int updatePurchase(Purchase purchase) throws Exception {
		return sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}
	
	@Override
	public int removePurchase(int tranNo) throws Exception {
		return sqlSession.delete("PurchaseMapper.removePurchase", tranNo);
	}

	public List<Purchase> getPurchaseList(Search search, String userId) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("beginDate", "");
		map.put("endDate", "");
		if (search.getSearchKeyword() != null && !search.getSearchKeyword().equals("")) {
			map.put("beginDate", search.getSearchKeyword().split(",")[0]);
			map.put("endDate", search.getSearchKeyword().split(",")[1]);
		}
		
		map.put("search", search);
		map.put("userId", "%"+userId.toLowerCase()+"%");
		map.put("startRowNum", (search.getCurrentPage()-1) * search.getPageSize() + 1);
		map.put("endRowNum", search.getCurrentPage() * search.getPageSize());
		
		List<Purchase> list = sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setBuyer((User)sqlSession.selectOne("UserMapper.getUser", list.get(i).getBuyer().getUserId()));
			list.get(i).setPurchaseProd((Product)sqlSession.selectOne("ProductMapper.getProduct", list.get(i).getPurchaseProd().getProdNo()));
			list.get(i).setPaymentOption(list.get(i).getPaymentOption().trim());
			list.get(i).setTranCode(list.get(i).getTranCode().trim());
		}
		
		return list;
	}

	public int updateTranCode(Purchase purchase) throws Exception{
		return sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}

	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(Search search, String userId) throws Exception {
Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("beginDate", "");
		map.put("endDate", "");
		if (search.getSearchKeyword() != null && !search.getSearchKeyword().equals("")) {
			map.put("beginDate", search.getSearchKeyword().split(",")[0]);
			map.put("endDate", search.getSearchKeyword().split(",")[1]);
		}
		
		map.put("search", search);
		map.put("userId", "%"+userId.toLowerCase()+"%");
		
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", map);
	}

}
