package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdatePurchaseAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		
		PurchaseService service = new PurchaseServiceImpl();
		purchase = service.updatePurchase(purchase);
		System.out.println("UpdatePurchaseAction : purchase : " +purchase);
		
		request.setAttribute("purchase", purchase);
		request.setAttribute("tranNo", tranNo);
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
