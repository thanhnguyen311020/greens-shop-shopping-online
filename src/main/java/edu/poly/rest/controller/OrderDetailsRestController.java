package edu.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.entity.OrderDetail;
import edu.poly.service.OrderDetailService;

@CrossOrigin("*")
@RestController
@RequestMapping("/greens/rest/orderDetail")
public class OrderDetailsRestController {
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping()
	public List<OrderDetail> findAll(){
		return orderDetailService.findAll();
	} 	 
	
	@GetMapping("{id}") 
	public List<OrderDetail> finDetailsByOrderId(@PathVariable("id")Long id) {
		return orderDetailService.findByOrderId(id);
	} 
	
	@PostMapping()
	public OrderDetail create(@RequestBody OrderDetail orderDetail) {
		return orderDetailService.save(orderDetail);
	}
	
	@PutMapping("{id}")
	public OrderDetail update(@PathVariable("id")Long id, @RequestBody OrderDetail detail) {
		return orderDetailService.save(detail);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")Long id) {
		orderDetailService.deleteById(id);
	}
} 
