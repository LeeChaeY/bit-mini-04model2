package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {
	public int addPurchase(Purchase purchase) throws Exception;

	public Purchase getPurchase(int tranNo) throws Exception;

	public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception;

	public int updatePurchase(Purchase purchase) throws Exception;
	
	public int removePurchase(int tranNo) throws Exception;

	public int updateTranCode(Purchase purchase) throws Exception;
}
