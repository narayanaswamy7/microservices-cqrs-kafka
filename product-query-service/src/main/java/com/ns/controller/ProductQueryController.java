package com.ns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ns.entity.Product;
import com.ns.service.ProductQueryService;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
	
	@Autowired
	private ProductQueryService queryService;
	
	@GetMapping
	public List<Product> fetchAllProducts() {
		return queryService.getProducts();
	}

}
