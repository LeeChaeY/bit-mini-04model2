package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdateTranCodeByProdAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String tranCode = request.getParameter("tranCode");
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(new Product(prodNo));
		purchase.setTranCode(tranCode);
		System.out.println("UpdateTranCodeByProdAction : prodNo : "+prodNo+", tranCode : "+tranCode);
		
		PurchaseService purService=new PurchaseServiceImpl();
		purService.updateTranCode(purchase);
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
