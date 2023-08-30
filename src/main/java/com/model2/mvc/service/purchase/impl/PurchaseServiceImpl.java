package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDao;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		System.out.println(":: "+getClass()+".setPurchaseDao() Call.....");
		this.purchaseDao = purchaseDao;
	}
	
	public PurchaseServiceImpl() {
		System.out.println(":: "+getClass()+" default Constructor Call.....");
	}
	
	public int addPurchase(Purchase purchase) throws Exception {
		return purchaseDao.addPurchase(purchase);
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.getPurchase(tranNo);
	}

	public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception {
		int totalCount = purchaseDao.getTotalCount(search, userId);
		System.out.println("totalCount :: "+totalCount);
		
		List<Purchase> list = purchaseDao.getPurchaseList(search, userId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", totalCount);
		map.put("list", list);
		
		return map;
	}

	public int updatePurchase(Purchase purchase) throws Exception {
		return purchaseDao.updatePurchase(purchase);
	}

	@Override
	public int removePurchase(int tranNo) throws Exception {
		return purchaseDao.removePurchase(tranNo);
	}
	
	public int updateTranCode(Purchase purchase) throws Exception {
		return purchaseDao.updateTranCode(purchase);
	}

}
