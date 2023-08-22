package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class GetPuchaseAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("GetPuchaseAction : tranNo : "+tranNo);
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(tranNo);
		
		request.setAttribute("purchase", purchase);
		System.out.println("GetPuchaseAction purchase : "+purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
