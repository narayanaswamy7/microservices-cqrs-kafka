package com.ns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ns.dto.ProductEvent;
import com.ns.entity.Product;
import com.ns.repository.ProductRepository;

@Service
public class ProductCommandService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	public Product createProduct(ProductEvent productEvent) {
		Product productDO = repository.save(productEvent.getProduct());
		ProductEvent event = new ProductEvent("CreateProduct", productDO);
		kafkaTemplate.send("product-event-topic", event);
		return productDO;
	}
	
	public Product updateProduct(long id, ProductEvent productEvent) {
		Product existingProduct = repository.findById(id).get();
		Product newProduct = productEvent.getProduct();
		existingProduct.setName(newProduct.getName());
		existingProduct.setDescription(newProduct.getDescription());
		existingProduct.setPrice(newProduct.getPrice());
		Product productDO = repository.save(existingProduct);
		ProductEvent event = new ProductEvent("UpdateProduct", productDO);
		kafkaTemplate.send("product-event-topic", event);
		return productDO;
		
	}

}
