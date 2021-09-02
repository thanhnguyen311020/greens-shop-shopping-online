package edu.poly.rest.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.entity.Product;
import edu.poly.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("greens/rest/product")
public class ProductRestController {
	
	@Autowired
	ProductService productService;
	
	
	
	@GetMapping("{id}")
	public Product getOne(@PathVariable("id")Integer id) {
		return productService.findById(id).get();
	}
	
	@GetMapping()
	public List<Product> getAll(Model model) {
		return productService.findAll();
	}
	
	
	@PostMapping()
	public Product create(@RequestBody Product product) {
		return productService.save(product);
	}
	
	@PutMapping("{id}")
	public Product update(@PathVariable("id")Integer id,@RequestBody Product product) {
		return productService.save(product);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")Integer id) {
		productService.deleteById(id);
	}
	
	@GetMapping("price/{id}")
	public Float getPriceProduct(@PathVariable("id")Integer id) {
		return productService.getPrice(id);
	}
	
	@PostMapping("importExcel")
	public Product saveExcel(@RequestBody Product product) {
		 productService.saveByExcel(product);
		 return product;
	}
}
