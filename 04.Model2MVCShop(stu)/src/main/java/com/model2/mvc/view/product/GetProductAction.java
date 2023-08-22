package com.model2.mvc.view.product;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class GetProductAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String menu = request.getParameter("menu");
		String resultPage = "forward:/product/getProduct.jsp";
		
		System.out.println("GetProductAction : prodNo : "+prodNo+", menu : "+menu);
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		request.setAttribute("menu", menu);
		
		if (menu.equals("manage") && product.getProTranCode() == null) 
			resultPage = "redirect:/updateProductView.do?prodNo="+prodNo;

		Cookie[] cookies = request.getCookies();
		String history = null;
		if (cookies!=null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					history = cookie.getValue();
				}
			}
			if (history != null) {
				List<String> h = new ArrayList<String>(Arrays.asList(history.split(URLEncoder.encode(","))));
				if (h.indexOf(prodNo+"") != -1) h.remove(h.indexOf(prodNo+""));
				h.add(""+prodNo);
				history = String.join(URLEncoder.encode(","), h);
			}
			else {
				history = prodNo+"";
			}
		}
		
		System.out.println("GetProductAction :: cookie history : "+history);
		Cookie cookie = new Cookie("history", history);
		cookie.setMaxAge(60*60);
		response.addCookie(cookie);

		return resultPage;
	}

}
