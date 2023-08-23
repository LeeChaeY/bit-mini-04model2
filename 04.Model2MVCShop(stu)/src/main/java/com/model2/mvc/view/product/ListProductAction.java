package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
																	new String[] {	"/config/commonservice.xml"	 }
									                                   );
		System.out.println("\n");

//		==> Bean/IoC Container ∑Œ ∫Œ≈Õ »πµÊ«— UserService ¿ŒΩ∫≈œΩ∫ »πµÊ
		ProductService productService = (ProductService)context.getBean("productServiceImpl");
		
		Search search = new Search();
		
		int currentPage=1;
		String menu = null;
		String beginPrice = null;
		String endPrice = null;
		String orderCondition = null;
		
		if(request.getParameter("currentPage") != null)
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		if(request.getParameter("menu") != null)
			menu = request.getParameter("menu");
		
		search.setCurrentPage(currentPage);
		if (request.getParameter("searchCondition") != null)
			search.setSearchCondition("%"+request.getParameter("searchCondition").toLowerCase()+"%");
		if (request.getParameter("searchKeyword") != null)
			search.setSearchKeyword("%"+request.getParameter("searchKeyword").toLowerCase()+"%");
		
		if(request.getParameter("beginPrice") != null && !request.getParameter("beginPrice").equals("") 
				&& request.getParameter("endPrice") != null &&!request.getParameter("endPrice").equals("")) {
			search.setSearchCondition("2");
			beginPrice = request.getParameter("beginPrice");
			request.setAttribute("beginPrice", request.getParameter("beginPrice"));
			endPrice = request.getParameter("endPrice");
			request.setAttribute("endPrice", endPrice);
		} else if (request.getParameter("searchKeyword") != null && !request.getParameter("searchKeyword").equals("")) {
			search.setSearchCondition("1");
		}
		
		if(request.getParameter("orderCondition") != null)
			search.setOrderCondition(request.getParameter("orderCondition"));
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		System.out.println("ListProductAction ::"+search);
		
//		ProductService service=new ProductServiceImpl();
		Map<String,Object> map = productService.getProductList(search);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);

		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}

}
