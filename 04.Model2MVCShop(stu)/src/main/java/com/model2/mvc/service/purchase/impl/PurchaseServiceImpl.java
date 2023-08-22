package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;

public class PurchaseServiceImpl implements PurchaseService {

	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		// TODO Auto-generated constructor stub
		purchaseDAO = new PurchaseDAO();
	}
	
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDAO.insertPurchase(purchase);
//		return purchaseDAO.findPurchase(purchase.getPurchaseProd().getProdNo());
//		return PurchaseDAO.findPurchase(result);
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}

	public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception {
		return purchaseDAO.getPurchaseList(search, userId);
	}

	public Purchase updatePurchase(Purchase purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);
		return purchaseDAO.findPurchase(purchase.getTranNo());
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDAO.updateTranCode(purchase);
	}

}
