package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ProductService prodService=new ProductServiceImpl();
		
		Purchase purchase = new Purchase();
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setPurchaseProd(prodService.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		
		PurchaseService purService = new PurchaseServiceImpl();
		purService.addPurchase(purchase);
		
		System.out.println("AddPurchaseAction : purchase : "+purchase);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
