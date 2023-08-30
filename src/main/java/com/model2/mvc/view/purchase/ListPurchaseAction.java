package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ListPurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Search search = new Search();
		HttpSession session = request.getSession();

		int currentPage=1;
		String beginDate = null;
		String endDate = null;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		if(request.getParameter("beginDate") != null && !request.getParameter("beginDate").equals("")){
			beginDate = request.getParameter("beginDate");
			request.setAttribute("beginDate", beginDate);
			if(request.getParameter("endDate") != null && !request.getParameter("endDate").equals("")){
				endDate = request.getParameter("endDate");
				request.setAttribute("endDate", endDate);
			}
			else {
				endDate = "SYSDATE";
			}
			search.setSearchKeyword(beginDate+","+endDate);
		}
		
		search.setCurrentPage(currentPage);

		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		PurchaseService purService=new PurchaseServiceImpl();
		Map<String,Object> map = purService.getPurchaseList(search, ((User)session.getAttribute("user")).getUserId());
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);

		return "forward:/purchase/listPurchase.jsp";
	}
}
