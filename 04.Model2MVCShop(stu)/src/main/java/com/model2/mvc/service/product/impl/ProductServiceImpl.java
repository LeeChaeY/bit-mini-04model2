package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;

public class ProductServiceImpl implements ProductService{

	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		// TODO Auto-generated constructor stub
		productDAO = new ProductDAO();
	}
	
	public void addProduct(Product product) throws Exception {
		productDAO.insertProduct(product);
		//int result = productDAO.insertProduct(product);
//		return productDAO.findProduct(result);
	}

	public Product getProduct(int productNo) throws Exception {
		return productDAO.findProduct(productNo);
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		return productDAO.getProductList(search);
	}

	public void updateProduct(Product Product) throws Exception {
		productDAO.updateProduct(Product);
		//return productDAO.findProduct(Product.getProdNo());
	}

}
