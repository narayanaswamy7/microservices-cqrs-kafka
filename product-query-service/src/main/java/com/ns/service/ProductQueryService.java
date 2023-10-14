package com.ns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ns.dto.ProductEvent;
import com.ns.entity.Product;
import com.ns.repository.ProductRepository;

@Service
public class ProductQueryService {
	
	@Autowired
	private ProductRepository repository;
	
	public List<Product> getProducts() {
		return repository.findAll();
	}
	
	@KafkaListener(topics = "product-event-topic", groupId = "product-event-group")
	public void processProductEvents(ProductEvent productEvent) {
		Product product = productEvent.getProduct();
		if (productEvent.getEventType().equals("CreateProduct")) {
			repository.save(product);
		}
		if (productEvent.getEventType().equals("UpdateProduct")) {
			Product existingProduct = repository.findById(product.getId()).get();
			existingProduct.setName(product.getName());
			existingProduct.setDescription(product.getDescription());
			existingProduct.setPrice(product.getPrice());
			repository.save(existingProduct);
		}
	}
	
}
