package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class UpdateProductViewAction extends Action {
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
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
//		ProductService service = new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);
		
		System.out.println("UpdateProductViewAction : product : "+product);
		
		request.setAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}

}
