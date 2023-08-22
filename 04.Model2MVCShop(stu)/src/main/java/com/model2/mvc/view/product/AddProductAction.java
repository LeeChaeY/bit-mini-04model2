package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class AddProductAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Product product = new Product();
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate").replace("-", ""));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(product);
		
		System.out.println("AddProductAction : product : "+product);
		
		request.setAttribute("product", product);
		
		return "forward:/product/addProduct.jsp";
	}

}
