package com.model2.mvc.service.purchase.dao;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {

	public int addPurchase(Purchase purchase) throws Exception;

	public Purchase getPurchase(int tranNo) throws Exception;

	public List<Purchase> getPurchaseList(Search search, String userId) throws Exception;

	public int updatePurchase(Purchase purchase) throws Exception;
	
	public int removePurchase(int tranNo) throws Exception;

	public int updateTranCode(Purchase purchase) throws Exception;

	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(Search search, String userId) throws Exception;
}
